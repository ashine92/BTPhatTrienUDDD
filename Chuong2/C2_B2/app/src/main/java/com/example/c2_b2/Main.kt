package com.example.c2_b2

fun main() {
    // Nhập chiều dài
    print("Nhap chieu dai: ")
    val daiStr = readLine()?.trim()
    val dai = daiStr?.toDoubleOrNull()
    if (dai == null || dai <= 0) {
        println("Chieu dai khong hop le!")
        return
    }


    // Nhập chiều rộng
    print("Nhap chieu rong: ")
    val rongStr = readLine()?.trim()
    val rong = rongStr?.toDoubleOrNull()
    if (rong == null || rong <= 0) {
        println("Chieu rong khong hop le!")
        return
    }


    // Tính chu vi và diện tích
    val chuVi = 2 * (dai + rong)
    val dienTich = dai * rong


    // In kết quả
    println("\n=== Ket qua ===")
    println("Chieu dai: $dai")
    println("Chieu rong: $rong")
    println("Chu vi hinh chu nhat: $chuVi")
    println("Dien tich hinh chu nhat: $dienTich")
}
