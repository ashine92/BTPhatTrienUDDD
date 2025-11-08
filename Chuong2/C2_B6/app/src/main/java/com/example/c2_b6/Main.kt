package com.example.c2_b6

class PhepTinh {
    fun cong(a: Double, b: Double) = a + b
}

fun main() {
    val phepTinh = PhepTinh()


    print("Nhap so thu nhat: ")
    val a = readLine()?.toDoubleOrNull()


    print("Nhap so thu hai: ")
    val b = readLine()?.toDoubleOrNull()


    if (a == null || b == null) {
        println("Vui long nhap dung dinh dang so.")
        return
    }


    println("\nKet qua phep tinh:")
    println("Cong: ${phepTinh.cong(a, b)}")
}


