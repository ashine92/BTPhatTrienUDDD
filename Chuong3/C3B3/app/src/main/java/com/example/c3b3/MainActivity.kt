package com.example.c3b3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewQuocGia)

        // Dữ liệu tĩnh (mảng quốc gia)
        val dsQuocGia = listOf(
            QuocGia("Việt Nam", "98 triệu dân", R.drawable.vietnam),
            QuocGia("Mỹ", "332 triệu dân", R.drawable.usa),
            QuocGia("Nhật Bản", "125 triệu dân", R.drawable.japan),
            QuocGia("Hàn Quốc", "52 triệu dân", R.drawable.korea),
            QuocGia("Pháp", "67 triệu dân", R.drawable.france)
        )

        val adapter = QuocGiaAdapter(this, R.layout.item_quocgia, dsQuocGia)
        listView.adapter = adapter

        // Khi click vào item
        listView.setOnItemClickListener { _, _, position, _ ->
            val qg = dsQuocGia[position]
            Toast.makeText(
                this,
                "Bạn chọn: ${qg.ten} – ${qg.danSo}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
