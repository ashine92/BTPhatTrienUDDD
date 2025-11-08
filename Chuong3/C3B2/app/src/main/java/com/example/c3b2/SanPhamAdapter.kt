package com.example.c3b2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class SanPhamAdapter(context: Context, private val resource: Int, private val items: MutableList<SanPham>)
    : ArrayAdapter<SanPham>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val sp = items[position]

        val img = view.findViewById<ImageView>(R.id.imgSP)
        val ten = view.findViewById<TextView>(R.id.tvTenSP)
        val gia = view.findViewById<TextView>(R.id.tvGiaSP)

        img.setImageResource(sp.hinhAnh)
        ten.text = sp.tenSP
        gia.text = "Giá: ${sp.giaSP} VNĐ"

        return view
    }
}
