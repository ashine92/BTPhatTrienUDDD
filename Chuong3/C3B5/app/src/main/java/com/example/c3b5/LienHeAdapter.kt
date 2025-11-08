package com.example.c3b5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class LienHeAdapter(context: Context, private val resource: Int, private val list: List<LienHe>)
    : ArrayAdapter<LienHe>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val lh = list[position]

        val img = view.findViewById<ImageView>(R.id.imgAvatar)
        val tvTen = view.findViewById<TextView>(R.id.tvTen)
        val tvSDT = view.findViewById<TextView>(R.id.tvSoDienThoai)

        img.setImageResource(lh.avatar)
        tvTen.text = lh.ten
        tvSDT.text = lh.soDienThoai

        return view
    }
}
