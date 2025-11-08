package com.example.c3b7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tongTien = 0
    private lateinit var tvTong: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewMonAn)
        tvTong = findViewById(R.id.tvTongTien)

        val dsMon = listOf(
            MonAn("Phở bò", 45000, R.drawable.mon1),
            MonAn("Bún chả Hà Nội", 50000, R.drawable.mon2),
            MonAn("Cơm tấm sườn trứng", 55000, R.drawable.mon3),
            MonAn("Gỏi cuốn", 30000, R.drawable.mon4),
            MonAn("Bánh xèo", 40000, R.drawable.mon5)
        )

        val adapter = MonAnAdapter(this, R.layout.item_monan, dsMon) { mon ->
            datMon(mon)
        }
        listView.adapter = adapter
    }

    private fun datMon(mon: MonAn) {
        tongTien += mon.gia
        tvTong.text = "Tổng tiền: ${tongTien} đ"
        Toast.makeText(this, "Đã thêm: ${mon.ten}", Toast.LENGTH_SHORT).show()
    }
}
