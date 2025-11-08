package com.example.firebaseapp

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseapp.adapter.UserAdapter
import com.example.firebaseapp.model.TaiKhoan
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private val userList = mutableListOf<TaiKhoan>()

    private var selectedImageUri: Uri? = null
    private var currentDialog: Dialog? = null
    private var isEditMode = false
    private var editingUser: TaiKhoan? = null

    companion object {
        private const val TAG = "DashboardActivity"
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            selectedImageUri = result.data?.data
            currentDialog?.findViewById<CircleImageView>(R.id.ivUserImage)?.let { imageView ->
                Glide.with(this)
                    .load(selectedImageUri)
                    .placeholder(R.drawable.ic_person)
                    .into(imageView)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Khởi tạo Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()

        // Kiểm tra đăng nhập
        if (auth.currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Khởi tạo views
        initViews()
        setupRecyclerView()
        loadUsers()

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showAddUserDialog(null) // null = chế độ thêm mới
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        tvEmpty = findViewById(R.id.tvEmpty)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter(
            userList,
            onDeleteClick = { user -> deleteUser(user) },
            onEditClick = { user -> showAddUserDialog(user) } // Thêm callback edit
        )

        recyclerView.adapter = adapter
    }

    // ==================== READ - Đọc dữ liệu ====================
    private fun loadUsers() {
        val userId = auth.currentUser?.uid ?: return

        showLoading(true)

        // Tham chiếu đến node chứa danh sách tài khoản
        val userRef = database.child("users").child(userId).child("taikhoan")

        // Lắng nghe thay đổi realtime
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                Log.d(TAG, "📊 Database snapshot exists: ${snapshot.exists()}")
                Log.d(TAG, "📊 Children count: ${snapshot.childrenCount}")

                // Duyệt qua tất cả children
                for (userSnapshot in snapshot.children) {
                    try {
                        val user = userSnapshot.getValue(TaiKhoan::class.java)
                        if (user != null) {
                            userList.add(user)
                            Log.d(TAG, "✅ Loaded user: ${user.taikhoan}")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Error parsing user: ${e.message}")
                    }
                }

                // Sắp xếp theo thời gian tạo (mới nhất lên đầu)
                userList.sortByDescending { it.createdAt }

                adapter.notifyDataSetChanged()
                showLoading(false)
                updateEmptyView()

                Log.d(TAG, "📋 Total users loaded: ${userList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "❌ Database error: ${error.message}")
                showLoading(false)
                Toast.makeText(
                    this@DashboardActivity,
                    "Lỗi tải dữ liệu: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // ==================== CREATE - Thêm mới ====================
    private fun showAddUserDialog(user: TaiKhoan?) {
        isEditMode = user != null
        editingUser = user

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_user)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        currentDialog = dialog
        selectedImageUri = null

        // Khởi tạo views trong dialog
        val tvTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val ivUserImage = dialog.findViewById<CircleImageView>(R.id.ivUserImage)
        val fabSelectImage = dialog.findViewById<FloatingActionButton>(R.id.fabSelectImage)
        val etUsername = dialog.findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = dialog.findViewById<TextInputEditText>(R.id.etPassword)
        val btnSave = dialog.findViewById<MaterialButton>(R.id.btnSave)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btnCancel)

        // Nếu là edit mode, điền dữ liệu có sẵn
        if (isEditMode && user != null) {
            tvTitle.text = "Sửa người dùng"
            btnSave.text = "Cập nhật"
            etUsername.setText(user.taikhoan)
            etPassword.setText(user.matkhau)

            if (user.imageUrl.isNotEmpty()) {
                Glide.with(this)
                    .load(user.imageUrl)
                    .placeholder(R.drawable.ic_person)
                    .into(ivUserImage)
            }
        } else {
            tvTitle.text = "Thêm người dùng"
            btnSave.text = "Lưu"
        }

        fabSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        btnSave.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty()) {
                etUsername.error = "Tên tài khoản không được để trống"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Mật khẩu không được để trống"
                return@setOnClickListener
            }

            if (password.length < 6) {
                etPassword.error = "Mật khẩu phải có ít nhất 6 ký tự"
                return@setOnClickListener
            }

            if (isEditMode && user != null) {
                updateUser(user.id, username, password, user.imageUrl)
            } else {
                createUser(username, password)
            }

            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun createUser(username: String, password: String) {
        val userId = auth.currentUser?.uid ?: return

        // Tạo key tự động cho user mới
        val userKey = database.child("users").child(userId).child("taikhoan").push().key

        if (userKey == null) {
            Toast.makeText(this, "Lỗi: Không thể tạo key", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "➕ Creating user with key: $userKey")

        if (selectedImageUri != null) {
            uploadImageAndCreateUser(userId, userKey, username, password)
        } else {
            saveUserToDatabase(userId, userKey, username, password, "")
        }
    }

    private fun uploadImageAndCreateUser(
        userId: String,
        userKey: String,
        username: String,
        password: String
    ) {
        val imageRef = storage.reference.child("user_images/$userId/$userKey.jpg")

        imageRef.putFile(selectedImageUri!!)
            .addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                Log.d(TAG, "📤 Upload progress: ${progress.toInt()}%")
            }
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveUserToDatabase(userId, userKey, username, password, uri.toString())
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Upload failed: ${e.message}")
                Toast.makeText(this, "Lỗi upload ảnh: ${e.message}", Toast.LENGTH_SHORT).show()
                saveUserToDatabase(userId, userKey, username, password, "")
            }
    }

    private fun saveUserToDatabase(
        userId: String,
        userKey: String,
        username: String,
        password: String,
        imageUrl: String
    ) {
        // Tạo object TaiKhoan
        val user = TaiKhoan(
            id = userKey,
            taikhoan = username,
            matkhau = password,
            imageUrl = imageUrl,
            createdAt = System.currentTimeMillis()
        )

        // Đường dẫn: users/[UID]/taikhoan/[userKey]
        val path = "users/$userId/taikhoan/$userKey"
        Log.d(TAG, "💾 Saving to: $path")
        Log.d(TAG, "📦 Data: $user")

        // Lưu vào database
        database.child("users").child(userId).child("taikhoan").child(userKey)
            .setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "✅ User saved successfully!")
                Toast.makeText(this, "Thêm người dùng thành công!", Toast.LENGTH_SHORT).show()
                selectedImageUri = null
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Save failed: ${e.message}")
                Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    // ==================== UPDATE - Cập nhật ====================
    private fun updateUser(userKey: String, username: String, password: String, oldImageUrl: String) {
        val userId = auth.currentUser?.uid ?: return

        Log.d(TAG, "✏️ Updating user: $userKey")

        if (selectedImageUri != null) {
            // Xóa ảnh cũ nếu có
            if (oldImageUrl.isNotEmpty()) {
                try {
                    storage.getReferenceFromUrl(oldImageUrl).delete()
                } catch (e: Exception) {
                    Log.w(TAG, "Old image delete failed: ${e.message}")
                }
            }

            // Upload ảnh mới
            uploadImageAndUpdateUser(userId, userKey, username, password)
        } else {
            // Giữ nguyên ảnh cũ
            updateUserInDatabase(userId, userKey, username, password, oldImageUrl)
        }
    }

    private fun uploadImageAndUpdateUser(
        userId: String,
        userKey: String,
        username: String,
        password: String
    ) {
        val imageRef = storage.reference.child("user_images/$userId/$userKey.jpg")

        imageRef.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    updateUserInDatabase(userId, userKey, username, password, uri.toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Lỗi upload ảnh: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInDatabase(
        userId: String,
        userKey: String,
        username: String,
        password: String,
        imageUrl: String
    ) {
        // Tạo map với các field cần update
        val updates = hashMapOf<String, Any>(
            "taikhoan" to username,
            "matkhau" to password,
            "imageUrl" to imageUrl
        )

        database.child("users").child(userId).child("taikhoan").child(userKey)
            .updateChildren(updates)
            .addOnSuccessListener {
                Log.d(TAG, "✅ User updated successfully!")
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Update failed: ${e.message}")
                Toast.makeText(this, "Lỗi cập nhật: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // ==================== DELETE - Xóa ====================
    private fun deleteUser(user: TaiKhoan) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa người dùng '${user.taikhoan}'?")
            .setPositiveButton("Xóa") { _, _ ->
                performDelete(user)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun performDelete(user: TaiKhoan) {
        val userId = auth.currentUser?.uid ?: return

        Log.d(TAG, "🗑️ Deleting user: ${user.id}")

        // Xóa ảnh từ Storage nếu có
        if (user.imageUrl.isNotEmpty()) {
            try {
                val imageRef = storage.getReferenceFromUrl(user.imageUrl)
                imageRef.delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "✅ Image deleted from storage")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "⚠️ Image delete failed: ${e.message}")
                    }
            } catch (e: Exception) {
                Log.w(TAG, "⚠️ Invalid image URL: ${e.message}")
            }
        }

        // Xóa user từ Database
        database.child("users").child(userId).child("taikhoan").child(user.id)
            .removeValue()
            .addOnSuccessListener {
                Log.d(TAG, "✅ User deleted from database")
                Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Delete failed: ${e.message}")
                Toast.makeText(this, "Lỗi xóa: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // ==================== UI Helper ====================
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun updateEmptyView() {
        if (userList.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}