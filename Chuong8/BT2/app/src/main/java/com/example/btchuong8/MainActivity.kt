package com.example.btchuong8

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var etUrl: EditText
    private lateinit var rbPicasso: RadioButton
    private lateinit var rbGlide: RadioButton
    private lateinit var btnLoad: Button
    private lateinit var btnSave: Button
    private lateinit var btnRead: Button
    private lateinit var btnSaveImage: Button
    private lateinit var ivImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "ImagePrefs"
        private const val KEY_IMAGE_URL = "imageUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo views
        initViews()

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Thiết lập sự kiện click
        setupClickListeners()
    }

    private fun initViews() {
        etUrl = findViewById(R.id.etUrl)
        rbPicasso = findViewById(R.id.rbPicasso)
        rbGlide = findViewById(R.id.rbGlide)
        btnLoad = findViewById(R.id.btnLoad)
        btnSave = findViewById(R.id.btnSave)
        btnRead = findViewById(R.id.btnRead)
        btnSaveImage = findViewById(R.id.btnSaveImage)
        ivImage = findViewById(R.id.ivImage)

        // Mặc định chọn Picasso
        rbPicasso.isChecked = true
    }

    private fun setupClickListeners() {
        btnLoad.setOnClickListener {
            loadImage()
        }

        btnSave.setOnClickListener {
            saveUrl()
        }

        btnRead.setOnClickListener {
            readUrl()
        }

        btnSaveImage.setOnClickListener {
            saveImageToStorage()
        }
    }

    private fun loadImage() {
        val url = etUrl.text.toString().trim()

        if (url.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập URL", Toast.LENGTH_SHORT).show()
            return
        }

        if (rbPicasso.isChecked) {
            loadImageWithPicasso(url)
        } else if (rbGlide.isChecked) {
            loadImageWithGlide(url)
        }
    }

    private fun loadImageWithPicasso(url: String) {
        try {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(ivImage)
            Toast.makeText(this, "Tải ảnh bằng Picasso", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImageWithGlide(url: String) {
        try {
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(ivImage)
            Toast.makeText(this, "Tải ảnh bằng Glide", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUrl() {
        val url = etUrl.text.toString().trim()

        if (url.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập URL để lưu", Toast.LENGTH_SHORT).show()
            return
        }

        val editor = sharedPreferences.edit()
        editor.putString(KEY_IMAGE_URL, url)
        editor.apply()

        Toast.makeText(this, "Đã lưu URL", Toast.LENGTH_SHORT).show()
    }

    private fun readUrl() {
        val savedUrl = sharedPreferences.getString(KEY_IMAGE_URL, "")

        if (savedUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Chưa có URL được lưu", Toast.LENGTH_SHORT).show()
            return
        }

        etUrl.setText(savedUrl)
        Toast.makeText(this, "Đã đọc URL từ bộ nhớ", Toast.LENGTH_SHORT).show()
    }

    private fun saveImageToStorage() {
        val drawable = ivImage.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            saveBitmapToFile(bitmap)
        } else {
            Toast.makeText(this, "Không có ảnh để lưu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap) {
        val imageName = "image_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, imageName)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Đã lưu ảnh vào thư viện", Toast.LENGTH_SHORT).show()
        }
    }
}