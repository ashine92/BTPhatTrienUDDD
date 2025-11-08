package com.example.firebaseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseapp.R
import com.example.firebaseapp.model.TaiKhoan
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
    private val userList: List<TaiKhoan>,
    private val onDeleteClick: (TaiKhoan) -> Unit,
    private val onEditClick: (TaiKhoan) -> Unit // Thêm callback edit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivUserImage: CircleImageView = itemView.findViewById(R.id.ivUserImage)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvPassword: TextView = itemView.findViewById(R.id.tvPassword)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.tvUsername.text = user.taikhoan
        holder.tvPassword.text = "••••••••" // Ẩn mật khẩu

        // Load ảnh
        if (user.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(user.imageUrl)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(holder.ivUserImage)
        } else {
            holder.ivUserImage.setImageResource(R.drawable.ic_person)
        }

        // Click để edit
        holder.btnEdit.setOnClickListener {
            onEditClick(user)
        }

        // Click để xóa
        holder.btnDelete.setOnClickListener {
            onDeleteClick(user)
        }
    }

    override fun getItemCount(): Int = userList.size
}