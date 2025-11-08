package com.example.c3b4

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class CongViecAdapter(
    context: Context,
    private val resource: Int,
    private val listCV: MutableList<CongViec>
) : ArrayAdapter<CongViec>(context, resource, listCV) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val cv = listCV[position]

        val chk = view.findViewById<CheckBox>(R.id.chkHoanThanh)
        val tvTen = view.findViewById<TextView>(R.id.tvTenCV)

        tvTen.text = cv.ten
        chk.isChecked = cv.hoanThanh

        // Gạch ngang nếu hoàn thành
        if (cv.hoanThanh) {
            tvTen.paintFlags = tvTen.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvTen.setTextColor(0xFF888888.toInt()) // xám
        } else {
            tvTen.paintFlags = tvTen.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            tvTen.setTextColor(0xFF000000.toInt()) // đen
        }

        // Khi CheckBox thay đổi
        chk.setOnCheckedChangeListener { _, isChecked ->
            cv.hoanThanh = isChecked
            notifyDataSetChanged()
        }

        return view
    }
}
