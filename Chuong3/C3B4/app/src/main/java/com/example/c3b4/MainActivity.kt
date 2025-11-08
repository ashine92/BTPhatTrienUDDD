package com.example.c3b4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CongViecAdapter
    private lateinit var listCV: MutableList<CongViec>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtCongViec = findViewById<EditText>(R.id.edtCongViec)
        val btnThem = findViewById<Button>(R.id.btnThem)
        val listView = findViewById<ListView>(R.id.listViewCV)

        // Danh sách mẫu ban đầu
        listCV = mutableListOf(
            CongViec("Làm bài tập Android"),
            CongViec("Đi chợ"),
            CongViec("Tập thể dục buổi sáng")
        )

        adapter = CongViecAdapter(this, R.layout.item_congviec, listCV)
        listView.adapter = adapter

        // Thêm công việc mới
        btnThem.setOnClickListener {
            val ten = edtCongViec.text.toString().trim()
            if (ten.isNotEmpty()) {
                listCV.add(CongViec(ten, false))
                adapter.notifyDataSetChanged()
                edtCongViec.text.clear()
                Toast.makeText(this, "Đã thêm: $ten", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show()
            }
        }

        // Xóa công việc bằng Long Click
        listView.setOnItemLongClickListener { _, _, position, _ ->
            val cv = listCV[position]

            AlertDialog.Builder(this)
                .setTitle("Xóa công việc")
                .setMessage("Bạn có muốn xóa '${cv.ten}' không?")
                .setPositiveButton("Xóa") { _, _ ->
                    listCV.removeAt(position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Đã xóa: ${cv.ten}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Hủy", null)
                .show()

            true
        }
    }
}
