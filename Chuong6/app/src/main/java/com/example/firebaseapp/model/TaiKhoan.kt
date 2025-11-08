package com.example.firebaseapp.model

data class TaiKhoan(
    var id: String = "",
    var taikhoan: String = "",
    var matkhau: String = "",
    var imageUrl: String = "",
    var createdAt: Long = System.currentTimeMillis() // Thêm timestamp
) {
    // Constructor rỗng bắt buộc cho Firebase
    constructor() : this("", "", "", "", 0)
}