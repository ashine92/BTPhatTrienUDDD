package com.example.c3b10

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CuaHangAdapter(
    private val context: Activity,
    private val resource: Int,
    private val items: MutableList<CuaHang>
) : ArrayAdapter<CuaHang>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val ch = items[position]

        val imgLogo = view.findViewById<ImageView>(R.id.imgLogo)
        val tvTen = view.findViewById<TextView>(R.id.tvTenCuaHang)
        val tvDiaChi = view.findViewById<TextView>(R.id.tvDiaChi)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        // Gán dữ liệu cho view
        imgLogo.setImageResource(ch.logo)
        tvTen.text = ch.ten
        tvDiaChi.text = ch.diaChi
        ratingBar.rating = ch.danhGia

        // Khi người dùng đổi đánh giá
        ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
            ch.danhGia = newRating
            Toast.makeText(context, "⭐ ${ch.ten}: $newRating sao", Toast.LENGTH_SHORT).show()
        }

        // Long click để xóa
        view.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Xóa cửa hàng")
                .setMessage("Bạn có muốn xóa '${ch.ten}' không?")
                .setPositiveButton("Có") { _, _ ->
                    items.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Đã xóa ${ch.ten}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Không", null)
                .show()
            true
        }

        return view
    }
}
