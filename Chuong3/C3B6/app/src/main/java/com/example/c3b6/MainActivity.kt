package com.example.c3b6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewPhim)

        // 🔹 Danh sách phim hoạt hình
        val dsPhim = listOf(
            Phim(
                "Elemental",
                "Hoạt hình / Lãng mạn",
                "Bộ phim kể về Ember (nguyên tố lửa) và Wade (nguyên tố nước) – hai cá thể đối lập nhưng cùng học cách hiểu và yêu thương nhau trong thành phố Element.",
                R.drawable.movie1
            ),
            Phim(
                "Zootopia",
                "Hoạt hình / Hài hước / Trinh thám",
                "Câu chuyện về cô thỏ cảnh sát Judy Hopps và cáo lém lỉnh Nick Wilde cùng nhau phá án trong thành phố động vật hiện đại Zootopia.",
                R.drawable.movie2
            ),
            Phim(
                "Inside Out",
                "Hoạt hình / Tâm lý / Gia đình",
                "Phim xoay quanh 5 cảm xúc trong đầu cô bé Riley – Niềm Vui, Nỗi Buồn, Giận Dữ, Sợ Hãi và Ghê Tởm – cùng hành trình cân bằng cảm xúc trong cuộc sống.",
                R.drawable.movie3
            ),
            Phim(
                "Hotel Transylvania",
                "Hoạt hình / Hài hước / Phiêu lưu",
                "Khách sạn Transylvania là nơi nghỉ dưỡng bí mật của các quái vật, do Bá tước Dracula quản lý. Mọi chuyện đảo lộn khi con gái ông yêu một chàng trai loài người.",
                R.drawable.movie4
            )
        )

        val adapter = PhimAdapter(this, R.layout.item_phim, dsPhim)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val phim = dsPhim[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ten", phim.ten)
            intent.putExtra("theloai", phim.theLoai)
            intent.putExtra("mota", phim.moTa)
            intent.putExtra("poster", phim.poster)
            startActivity(intent)
        }
    }
}

