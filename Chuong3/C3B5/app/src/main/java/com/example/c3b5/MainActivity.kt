package com.example.c3b5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import android.content.Intent
import android.net.Uri

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewDanhBa)

        // Danh sách liên hệ tĩnh
        val dsLienHe = listOf(
            LienHe("Nguyễn Văn A", "0912345678", R.drawable.avatar1),
            LienHe("Trần Thị B", "0987654321", R.drawable.avatar2),
            LienHe("Lê Văn C", "0909999999", R.drawable.avatar3)
        )

        val adapter = LienHeAdapter(this, R.layout.item_lienhe, dsLienHe)
        listView.adapter = adapter

        // Khi click vào 1 liên hệ
        listView.setOnItemClickListener { _, _, position, _ ->
            val lh = dsLienHe[position]
            Toast.makeText(this, "Gọi đến: ${lh.ten}", Toast.LENGTH_SHORT).show()

            // Tạo Intent gọi điện
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${lh.soDienThoai}")
            startActivity(intent)
        }
    }
}
