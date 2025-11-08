package com.example.c3b10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CuaHangAdapter
    private lateinit var dsCuaHang: MutableList<CuaHang>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewCuaHang)

        // Danh sách cửa hàng có logo
        dsCuaHang = mutableListOf(
            CuaHang(
                "The Coffee House",
                "12 Nguyễn Văn Bảo, Gò Vấp, TP.HCM",
                4.5f,
                R.drawable.logo_coffeehouse
            ),
            CuaHang(
                "Highlands Coffee",
                "123 Lê Lợi, Quận 1, TP.HCM",
                5f,
                R.drawable.logo_highlands
            ),
            CuaHang(
                "Phúc Long Coffee & Tea",
                "45 Nguyễn Huệ, Quận 1, TP.HCM",
                4f,
                R.drawable.logo_phuclong
            ),
            CuaHang(
                "Starbucks",
                "88 Lý Tự Trọng, Quận 1, TP.HCM",
                3.5f,
                R.drawable.logo_starbucks
            )
        )

        adapter = CuaHangAdapter(this, R.layout.item_cuahang, dsCuaHang)
        listView.adapter = adapter
    }
}
