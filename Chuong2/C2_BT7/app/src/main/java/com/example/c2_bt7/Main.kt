package com.example.c2_bt7

data class SinhVien(
    var maSV: String,
    var hoTen: String,
    var tuoi: Int
)


val danhSachSV = mutableListOf<SinhVien>()


fun main() {
    while (true) {
        println("\n===== MENU =====")
        println("1. Them sinh vien")
        println("2. Xoa sinh vien")
        println("3. Sua thong tin sinh vien")
        println("4. Hien thi danh sach sinh vien")
        println("5. Thong ke tuoi (max, min, trung binh)")
        println("0. Thoat")
        print("Chon chuc nang: ")


        when (readLine()?.toIntOrNull()) {
            1 -> themSinhVien()
            2 -> xoaSinhVien()
            3 -> suaSinhVien()
            4 -> hienThiDS()
            5 -> thongKe()
            0 -> {
                println("Thoat chuong trinh...")
                return
            }
            else -> println("Lua chon khong hop le!")
        }
    }
}


fun themSinhVien() {
    print("Nhap ma SV: ")
    val maSV = readLine().orEmpty()
    print("Nhap ho ten: ")
    val hoTen = readLine().orEmpty()
    print("Nhap tuoi: ")
    val tuoi = readLine()?.toIntOrNull() ?: -1


    if (tuoi <= 0) {
        println("Tuoi khong hop le!")
        return
    }


    val sv = SinhVien(maSV, hoTen, tuoi)
    danhSachSV.add(sv)
    println("Them thanh cong!")
}


fun xoaSinhVien() {
    print("Nhap ma SV can xoa: ")
    val ma = readLine().orEmpty()
    val sv = danhSachSV.find { it.maSV == ma }
    if (sv != null) {
        danhSachSV.remove(sv)
        println("Xoa thanh cong!")
    } else {
        println("Khong tim thay sinh vien co ma $ma")
    }
}


fun suaSinhVien() {
    print("Nhap ma SV can sua: ")
    val ma = readLine().orEmpty()
    val sv = danhSachSV.find { it.maSV == ma }
    if (sv != null) {
        print("Nhap ho ten moi (${sv.hoTen}): ")
        val tenMoi = readLine()
        if (!tenMoi.isNullOrBlank()) sv.hoTen = tenMoi


        print("Nhap tuoi moi (${sv.tuoi}): ")
        val tuoiMoi = readLine()?.toIntOrNull()
        if (tuoiMoi != null && tuoiMoi > 0) sv.tuoi = tuoiMoi


        println("Cap nhat thanh cong!")
    } else {
        println("Khong tim thay sinh vien co ma $ma")
    }
}


fun hienThiDS() {
    if (danhSachSV.isEmpty()) {
        println("Danh sach rong!")
    } else {
        println("===== DANH SACH SINH VIEN =====")
        danhSachSV.forEach {
            println("MaSV: ${it.maSV}, HoTen: ${it.hoTen}, Tuoi: ${it.tuoi}")
        }
    }
}


fun thongKe() {
    if (danhSachSV.isEmpty()) {
        println("Danh sach rong, khong co du lieu thong ke!")
    } else {
        val maxTuoi = danhSachSV.maxOf { it.tuoi }
        val minTuoi = danhSachSV.minOf { it.tuoi }
        val tbTuoi = danhSachSV.map { it.tuoi }.average()


        println("Thong ke tuoi:")
        println("- Lon nhat: $maxTuoi")
        println("- Nho nhat: $minTuoi")
        println("- Trung binh: ${"%.2f".format(tbTuoi)}")
    }
}
