package com.example.c2_b1


fun main() {
    // Nhập họ tên
    print("Nhap ho ten: ")
    val hoTen = readLine()?.trim()
    if (hoTen.isNullOrEmpty()) {
        println("Ban chua nhap ho ten!")
        return
    }


    // Nhập tuổi
    print("Nhap tuoi: ")
    val tuoiStr = readLine()?.trim()
    val tuoi = tuoiStr?.toIntOrNull()
    if (tuoi == null || tuoi <= 0) {
        println("Tuoi khong hop le!")
        return
    }


    // Nhập ngành
    print("Nhap nganh: ")
    val nganh = readLine()?.trim()
    if (nganh.isNullOrEmpty()) {
        println("Ban chua nhap nganh!")
        return
    }


    // In ra thông tin
    println("\n=== Thong tin ca nhan ===")
    println("Ho ten: $hoTen")
    println("Tuoi: $tuoi")
    println("Nganh: $nganh")
}
