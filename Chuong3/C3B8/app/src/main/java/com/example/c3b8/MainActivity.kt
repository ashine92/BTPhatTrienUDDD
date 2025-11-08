package com.example.c3b8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewBaiHat)

        // 🎄 Danh sách bài hát Giáng Sinh
        val dsBaiHat = listOf(
            BaiHat("Jingle Bells", "James Lord Pierpont", R.raw.jingle_bells),
            BaiHat("O Holy Night", "Adolphe Adam", R.raw.o_holy_night)
        )

        val adapter = BaiHatAdapter(this, R.layout.item_baihat, dsBaiHat)
        listView.adapter = adapter
    }
}


