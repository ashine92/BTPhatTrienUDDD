package com.example.btchuong8

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var etUrl: EditText
    private lateinit var rbPicasso: RadioButton
    private lateinit var rbGlide: RadioButton
    private lateinit var btnLoad: Button
    private lateinit var btnSave: Button
    private lateinit var btnRead: Button
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
}