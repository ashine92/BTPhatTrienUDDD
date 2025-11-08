package com.example.c3b9

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MonHocAdapter(
    private val context: Activity,
    private val resource: Int,
    private val items: MutableList<MonHoc>,
    private val onTongTinChiChanged: (Int) -> Unit
) : ArrayAdapter<MonHoc>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val mon = items[position]

        val tvTen = view.findViewById<TextView>(R.id.tvTenMon)
        val tvTinChi = view.findViewById<TextView>(R.id.tvTinChi)
        val chkDaHoc = view.findViewById<CheckBox>(R.id.chkDaHoc)

        tvTen.text = mon.tenMon
        tvTinChi.text = "${mon.tinChi} tín chỉ"
        chkDaHoc.isChecked = mon.daHoc

        chkDaHoc.setOnCheckedChangeListener { _, isChecked ->
            mon.daHoc = isChecked
            val tong = items.filter { it.daHoc }.sumOf { it.tinChi }
            onTongTinChiChanged(tong)
        }

        return view
    }
}
