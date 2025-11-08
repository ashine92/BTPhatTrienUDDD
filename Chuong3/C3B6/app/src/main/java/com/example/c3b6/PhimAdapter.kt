package com.example.c3b6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class PhimAdapter(context: Context, private val resource: Int, private val list: List<Phim>)
    : ArrayAdapter<Phim>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val phim = list[position]

        val imgPoster = view.findViewById<ImageView>(R.id.imgPoster)
        val tvTen = view.findViewById<TextView>(R.id.tvTenPhim)
        val tvTheLoai = view.findViewById<TextView>(R.id.tvTheLoai)

        imgPoster.setImageResource(phim.poster)
        tvTen.text = phim.ten
        tvTheLoai.text = phim.theLoai

        return view
    }
}
