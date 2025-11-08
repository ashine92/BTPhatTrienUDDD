package com.example.c2_b3

fun main() {
    println("=== Bai tap 3: Kiem tra chan/le ===")


    // Nhập số
    print("Nhap mot so nguyen > 0: ")
    val n = readLine()?.toIntOrNull()


    if (n == null || n <= 0) {
        println(" Vui long nhap so nguyen duong hop le!")
    } else {
        if (n % 2 == 0) {
            println("So $n la so CHAN")
        } else {
            println(" So $n la so LE")
        }
    }
}
