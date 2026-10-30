package com.example.fams.service;

import com.example.fams.model.*;
import com.example.fams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DonHangService {

    @Autowired private DonHangRepository donHangRepo;
    @Autowired private DonHangChiTietRepository chiTietRepo;
    @Autowired private GioHangRepository gioHangRepo;
    @Autowired private GioHangChiTietRepository gioHangChiTietRepo;

    /** 🔹 Tạo đơn hàng từ giỏ hàng */
    @Transactional
    public DonHang taoDonHang(NguoiDung nguoiDung) {

        // Lấy giỏ hàng của user
        GioHang gioHang = gioHangRepo.findByNguoiDung(nguoiDung)
                .orElse(null);

        if (gioHang == null) {
            throw new RuntimeException("Không tìm thấy giỏ hàng");
        }

        List<GioHangChiTiet> items = gioHangChiTietRepo.findByGioHangId(gioHang.getId());

        if (items.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể thanh toán");
        }

        // Tổng tiền đơn hàng
        double tongTien = items.stream()
                .mapToDouble(i -> i.getSoluong() * i.getSanPham().getPrice())
                .sum();

        // Tạo đơn hàng
        DonHang donHang = new DonHang();
        donHang.setNguoiDung(nguoiDung);

        // Format ngày đặt
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        donHang.setNgayDat(LocalDateTime.now().format(formatter));

        donHang.setTongTien(tongTien);
        donHang.setTrangThai("CHO_XAC_NHAN");

        DonHang savedOrder = donHangRepo.save(donHang);

        // Tạo đơn hàng chi tiết
        for (GioHangChiTiet item : items) {

            DonHangChiTiet ct = new DonHangChiTiet();
            ct.setDonHang(savedOrder);
            ct.setSanPham(item.getSanPham());
            ct.setSoluong(item.getSoluong());
            ct.setGia(item.getSanPham().getPrice());

            chiTietRepo.save(ct);
        }

        // Xóa giỏ hàng sau khi thanh toán
        gioHangChiTietRepo.deleteAll(items);

        return savedOrder;
    }

    /** 🔹 Lấy danh sách đơn hàng theo người dùng */
    public List<DonHang> getDonHangByUser(NguoiDung user) {
        return donHangRepo.findByNguoiDung(user);
    }

    /** 🔹 Lấy chi tiết của một đơn hàng */
    public List<DonHangChiTiet> getChiTietDonHang(Integer donHangId) {
        DonHang dh = donHangRepo.findById(donHangId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        return chiTietRepo.findByDonHang(dh);
    }
    /** 🔹 Lấy tất cả đơn hàng (dùng cho Admin) */
    public List<DonHang> findAll() {
        return donHangRepo.findAll();
    }

}

