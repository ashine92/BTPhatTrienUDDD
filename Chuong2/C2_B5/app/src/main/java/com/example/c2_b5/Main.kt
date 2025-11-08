package com.example.c2_b5

fun main() {
    print("Nhap diem (0 - 10): ")
    val diem = readLine()?.toDoubleOrNull()


    if (diem == null || diem !in 0.0..10.0) {
        println("Vui long nhap diem hop le tu 0 den 10")
    } else {
        val xepLoai = when (diem) {
            in 9.0..10.0 -> "Xuat sac"
            in 8.0..8.9 -> "Gioi"
            in 6.5..7.9 -> "Kha"
            in 5.0..6.4 -> "Trung binh"
            else -> "Yeu"
        }
        println("Diem: $diem -> Xep loai: $xepLoai")
    }
}
