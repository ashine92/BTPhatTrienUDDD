package com.example.c3b8

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class BaiHatAdapter(
    private val context: Activity,
    private val resource: Int,
    private val list: List<BaiHat>
) : ArrayAdapter<BaiHat>(context, resource, list) {

    private var mediaPlayer: MediaPlayer? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val bh = list[position]

        val imgPlay = view.findViewById<ImageView>(R.id.imgPlay)
        val tvTen = view.findViewById<TextView>(R.id.tvTenBaiHat)
        val tvCaSi = view.findViewById<TextView>(R.id.tvCaSi)

        tvTen.text = bh.ten
        tvCaSi.text = bh.caSi

        imgPlay.setOnClickListener {
            // Giải phóng bài đang phát (nếu có)
            mediaPlayer?.release()

            // Khởi tạo MediaPlayer mới
            mediaPlayer = MediaPlayer.create(context, bh.fileNhac)

            if (mediaPlayer != null) {
                mediaPlayer!!.setOnCompletionListener {
                    Toast.makeText(context, "✅ Bài hát kết thúc!", Toast.LENGTH_SHORT).show()
                }
                mediaPlayer!!.start()
                Toast.makeText(context, "🎵 Đang phát: ${bh.ten}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "⚠️ Không phát được file nhạc!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
