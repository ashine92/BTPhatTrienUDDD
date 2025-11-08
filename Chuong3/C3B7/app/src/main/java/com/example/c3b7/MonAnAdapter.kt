package com.example.c3b7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MonAnAdapter(
    private val ctx: Context,
    private val resource: Int,
    private val list: List<MonAn>,
    private val onDatMon: (MonAn) -> Unit
) : ArrayAdapter<MonAn>(ctx, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(ctx).inflate(resource, parent, false)
        val mon = list[position]

        val img = view.findViewById<ImageView>(R.id.imgMonAn)
        val tvTen = view.findViewById<TextView>(R.id.tvTenMon)
        val tvGia = view.findViewById<TextView>(R.id.tvGiaMon)
        val btnDat = view.findViewById<Button>(R.id.btnDatMon)

        img.setImageResource(mon.hinh)
        tvTen.text = mon.ten
        tvGia.text = "${mon.gia} đ"

        btnDat.setOnClickListener {
            onDatMon(mon)
        }

        return view
    }
}
