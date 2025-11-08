package com.example.c3b2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.c3b2.R

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: SanPhamAdapter
    private lateinit var listSP: MutableList<SanPham>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtTenSP = findViewById<EditText>(R.id.edtTenSP)
        val edtGiaSP = findViewById<EditText>(R.id.edtGiaSP)
        val btnThem = findViewById<Button>(R.id.btnThem)
        val listView = findViewById<ListView>(R.id.listViewSP)

        // Danh sách mẫu ban đầu
        listSP = mutableListOf(
            SanPham("SP01", "Chuột Logitech", 250000.0, R.drawable.sp1),
            SanPham("SP02", "Bàn phím Cơ", 750000.0, R.drawable.sp2)
        )

        adapter = SanPhamAdapter(this, R.layout.item_sanpham, listSP)
        listView.adapter = adapter

        // Thêm sản phẩm mới
        btnThem.setOnClickListener {
            val ten = edtTenSP.text.toString()
            val gia = edtGiaSP.text.toString().toDoubleOrNull()

            if (ten.isNotEmpty() && gia != null) {
                val ma = "SP" + String.format("%02d", listSP.size + 1)
                val sp = SanPham(ma, ten, gia, R.drawable.sp1)
                listSP.add(sp)
                adapter.notifyDataSetChanged()

                edtTenSP.text.clear()
                edtGiaSP.text.clear()
                Toast.makeText(this, "Đã thêm: $ten", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập tên và giá hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        // Xóa sản phẩm khi nhấn giữ lâu
        listView.setOnItemLongClickListener { _, _, position, _ ->
            val sp = listSP[position]
            listSP.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Đã xóa ${sp.tenSP}", Toast.LENGTH_SHORT).show()
            true
        }
    }
}
