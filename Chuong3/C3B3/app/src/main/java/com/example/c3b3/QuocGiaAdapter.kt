package com.example.c3b3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class QuocGiaAdapter(context: Context, private val resource: Int, private val quocGiaList: List<QuocGia>)
    : ArrayAdapter<QuocGia>(context, resource, quocGiaList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val quocGia = quocGiaList[position]

        val imgCo = view.findViewById<ImageView>(R.id.imgCo)
        val tvTen = view.findViewById<TextView>(R.id.tvTenNuoc)
        val tvDanSo = view.findViewById<TextView>(R.id.tvDanSo)

        imgCo.setImageResource(quocGia.hinhCo)
        tvTen.text = quocGia.ten
        tvDanSo.text = quocGia.danSo

        return view
    }
}
