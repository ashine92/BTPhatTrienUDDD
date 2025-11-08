package com.example.c3b6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgPoster = findViewById<ImageView>(R.id.imgPosterDetail)
        val tvTen = findViewById<TextView>(R.id.tvTenDetail)
        val tvTheLoai = findViewById<TextView>(R.id.tvTheLoaiDetail)
        val tvMoTa = findViewById<TextView>(R.id.tvMoTaDetail)

        val ten = intent.getStringExtra("ten")
        val theLoai = intent.getStringExtra("theloai")
        val moTa = intent.getStringExtra("mota")
        val poster = intent.getIntExtra("poster", 0)

        tvTen.text = ten
        tvTheLoai.text = theLoai
        tvMoTa.text = moTa
        imgPoster.setImageResource(poster)
    }
}
