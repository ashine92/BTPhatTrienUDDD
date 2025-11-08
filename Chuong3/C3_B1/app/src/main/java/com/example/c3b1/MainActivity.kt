package com.example.c3b1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewSinhVien)

        val dsSinhVien = listOf(
            SinhVien("Nguyễn Ngọc Hồng Ánh", 21, true),
            SinhVien("Võ Thị Kim Thoa", 21, false),
            SinhVien("Phan Ngọc Ngân", 21, true),
            SinhVien("Phan Thị Ánh Tuyết", 21, false)
        )

        val adapter = SinhVienAdapter(this, R.layout.item_sinhvien, dsSinhVien)
        listView.adapter = adapter
    }
}
