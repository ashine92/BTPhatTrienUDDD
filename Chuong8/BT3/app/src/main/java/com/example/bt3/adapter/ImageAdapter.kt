package com.example.bt3.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.bt3.R
import com.example.bt3.model.ImageItem
import com.example.bt3.model.LoadingState

class ImageAdapter(
    private val images: MutableList<ImageItem>
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val statusText: TextView = view.findViewById(R.id.statusText)
        val titleText: TextView = view.findViewById(R.id.titleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = images[position]
        holder.titleText.text = item.title

        // Cập nhật UI theo trạng thái hiện tại
        updateUIState(holder, item.loadingState)

        // Bắt đầu load ảnh nếu chưa load xong
        if (item.loadingState != LoadingState.COMPLETED) {
            item.loadingState = LoadingState.LOADING
            updateUIState(holder, LoadingState.LOADING)
        }

        // Load ảnh với Glide
        Glide.with(holder.imageView.context)
            .load(item.url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    item.loadingState = LoadingState.ERROR
                    // Cập nhật UI trực tiếp, không gọi notifyItemChanged
                    updateUIState(holder, LoadingState.ERROR)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    item.loadingState = LoadingState.COMPLETED
                    // Cập nhật UI trực tiếp
                    updateUIState(holder, LoadingState.COMPLETED)
                    return false
                }
            })
            .into(holder.imageView)
    }

    private fun updateUIState(holder: ImageViewHolder, state: LoadingState) {
        when (state) {
            LoadingState.IDLE -> {
                holder.progressBar.visibility = View.GONE
                holder.statusText.text = "Chưa tải"
                holder.statusText.setTextColor(holder.itemView.context.getColor(android.R.color.darker_gray))
            }
            LoadingState.LOADING -> {
                holder.progressBar.visibility = View.VISIBLE
                holder.statusText.text = "Đang tải..."
                holder.statusText.setTextColor(holder.itemView.context.getColor(android.R.color.holo_blue_dark))
            }
            LoadingState.COMPLETED -> {
                holder.progressBar.visibility = View.GONE
                holder.statusText.text = "✓ Hoàn thành"
                holder.statusText.setTextColor(holder.itemView.context.getColor(android.R.color.holo_green_dark))
            }
            LoadingState.ERROR -> {
                holder.progressBar.visibility = View.GONE
                holder.statusText.text = "✗ Lỗi"
                holder.statusText.setTextColor(holder.itemView.context.getColor(android.R.color.holo_red_dark))
            }
        }
    }

    override fun getItemCount(): Int = images.size

    fun updateImages(newImages: List<ImageItem>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()
    }
}
