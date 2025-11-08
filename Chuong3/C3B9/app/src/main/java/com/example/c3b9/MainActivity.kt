package com.example.c3b9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvTongTinChi: TextView
    private lateinit var adapter: MonHocAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewMonHoc)
        tvTongTinChi = findViewById(R.id.tvTongTinChi)

        // 🔹 Danh sách môn học mẫu
        val dsMonHoc = mutableListOf(
            MonHoc("Lập trình Android", 3, false),
            MonHoc("Cơ sở dữ liệu", 3, false),
            MonHoc("Trí tuệ nhân tạo", 4, false),
            MonHoc("Phát triển Web", 3, false)
        )

        adapter = MonHocAdapter(this, R.layout.item_monhoc, dsMonHoc) { tongTinChi ->
            tvTongTinChi.text = "Tổng tín chỉ đã học: $tongTinChi"
        }

        listView.adapter = adapter
    }
}
