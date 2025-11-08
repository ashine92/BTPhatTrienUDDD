package com.example.c1_bt2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Khai báo và ánh xạ
        val edtName: EditText = findViewById(R.id.edtName)
        val btnShow: Button = findViewById(R.id.btnShow)
        val tvResult: TextView = findViewById(R.id.tvResult)

        // 2. Xử lý sự kiện khi nhấn nút
        btnShow.setOnClickListener {
            val name = edtName.text.toString()
            tvResult.text = "Xin chào: $name"
        }
    }
}
