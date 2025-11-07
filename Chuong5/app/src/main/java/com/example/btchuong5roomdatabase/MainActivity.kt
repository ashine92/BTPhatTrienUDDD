package com.example.btchuong5roomdatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btchuong5roomdatabase.adapter.UserAdapter
import com.example.btchuong5roomdatabase.data.User
import com.example.btchuong5roomdatabase.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var btnThem: Button
    private lateinit var btnSua: Button
    private lateinit var btnXoa: Button
    private lateinit var btnHienThi: Button
    private lateinit var recyclerView: RecyclerView

    private var selectedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Views
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        btnThem = findViewById(R.id.btnThem)
        btnSua = findViewById(R.id.btnSua)
        btnXoa = findViewById(R.id.btnXoa)
        btnHienThi = findViewById(R.id.btnHienThi)
        recyclerView = findViewById(R.id.recyclerView)

        // Setup RecyclerView
        userAdapter = UserAdapter(
            onItemClick = { user ->
                selectedUser = user
                editTextName.setText(user.name)
                editTextAge.setText(user.age.toString())
                Toast.makeText(this, "Đã chọn: ${user.name}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { user ->
                showDeleteConfirmDialog(user)
            }
        )

        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Setup ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.allUsers.observe(this) { users ->
            userAdapter.setUsers(users)
        }

        // Button Listeners
        btnThem.setOnClickListener {
            insertUser()
        }

        btnSua.setOnClickListener {
            updateUser()
        }

        btnXoa.setOnClickListener {
            deleteUser()
        }

        btnHienThi.setOnClickListener {
            // RecyclerView tự động hiển thị qua LiveData
            Toast.makeText(this, "Đang hiển thị tất cả users", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertUser() {
        val name = editTextName.text.toString().trim()
        val ageStr = editTextAge.text.toString().trim()

        if (name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageStr.toIntOrNull()
        if (age == null || age <= 0) {
            Toast.makeText(this, "Tuổi không hợp lệ!", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(name = name, age = age)
        userViewModel.insert(user)

        clearInputs()
        Toast.makeText(this, "Đã thêm user: $name", Toast.LENGTH_SHORT).show()
    }

    private fun updateUser() {
        if (selectedUser == null) {
            Toast.makeText(this, "Vui lòng chọn user để sửa!", Toast.LENGTH_SHORT).show()
            return
        }

        val name = editTextName.text.toString().trim()
        val ageStr = editTextAge.text.toString().trim()

        if (name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageStr.toIntOrNull()
        if (age == null || age <= 0) {
            Toast.makeText(this, "Tuổi không hợp lệ!", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedUser = selectedUser!!.copy(name = name, age = age)
        userViewModel.update(updatedUser)

        clearInputs()
        selectedUser = null
        Toast.makeText(this, "Đã cập nhật user", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser() {
        if (selectedUser == null) {
            Toast.makeText(this, "Vui lòng chọn user để xóa!", Toast.LENGTH_SHORT).show()
            return
        }

        showDeleteConfirmDialog(selectedUser!!)
    }

    private fun showDeleteConfirmDialog(user: User) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa user: ${user.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                userViewModel.delete(user)
                clearInputs()
                selectedUser = null
                Toast.makeText(this, "Đã xóa user", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun clearInputs() {
        editTextName.text.clear()
        editTextAge.text.clear()
        editTextName.requestFocus()
    }
}