package com.example.c2_b4

fun main() {
    // Nhập n
    print("Nhap n > 0: ")
    val nStr = readLine()?.trim()
    val n = nStr?.toIntOrNull()


    // Kiểm tra n hợp lệ
    if (n == null || n <= 0) {
        println("Gia tri n khong hop le!")
        return
    }


    // Tính tổng 1 + 2 + ... + n
    var s = 0
    for (i in 1..n) {
        s += i
    }


    // In kết quả
    println("Tong tu 1 den $n la: $s")
}
