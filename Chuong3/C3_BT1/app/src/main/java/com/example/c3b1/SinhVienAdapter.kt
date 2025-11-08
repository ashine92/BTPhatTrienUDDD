package com.example.c3b1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class SinhVienAdapter(context: Context, private val resource: Int, private val sinhVienList: List<SinhVien>)
    : ArrayAdapter<SinhVien>(context, resource, sinhVienList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val sv = sinhVienList[position]

        val imgGioiTinh = view.findViewById<ImageView>(R.id.imgGioiTinh)
        val tvTen = view.findViewById<TextView>(R.id.tvTen)
        val tvTuoi = view.findViewById<TextView>(R.id.tvTuoi)

        // Gán dữ liệu
        tvTen.text = sv.ten
        tvTuoi.text = "Tuổi: ${sv.tuoi}"
        imgGioiTinh.setImageResource(
            if (sv.gioiTinh) R.drawable.male else R.drawable.female
        )

        return view
    }
}
