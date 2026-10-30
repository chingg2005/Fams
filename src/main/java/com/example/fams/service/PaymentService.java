package com.example.fams.service;

import com.example.fams.model.*;
import com.example.fams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PaymentService {

    @Autowired private GioHangService gioHangService;
    @Autowired private NguoiDungRepository nguoiDungRepo;
    @Autowired private DonHangRepository donHangRepo;
    @Autowired private DonHangChiTietRepository donHangCTRepo;
    @Autowired private GioHangChiTietRepository gioHangCTRepo;

    /** 🔹 Tạo đơn hàng từ giỏ hàng */
    public DonHang createOrder(int userId) {

        GioHang cart = gioHangService.getOrCreateCart(userId);
        List<GioHangChiTiet> items = gioHangService.getCartItemByUserId(userId);

        if (items.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể thanh toán");
        }

        // Tổng tiền
        double total = items.stream()
                .mapToDouble(i -> i.getSanPham().getPrice() * i.getSoluong())
                .sum();

        // Format ngày
        String dateStr = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Tạo đơn hàng
        DonHang order = new DonHang();
        order.setNgayDat(dateStr);
        order.setTongTien(total);
        order.setTrangThai("Chờ xử lý");
        order.setNguoiDung(nguoiDungRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại")));

        donHangRepo.save(order);

        // Thêm chi tiết đơn hàng
        for (GioHangChiTiet item : items) {

            DonHangChiTiet ct = new DonHangChiTiet();
            ct.setDonHang(order);
            ct.setSanPham(item.getSanPham());
            ct.setSoluong(item.getSoluong());
            ct.setGia(item.getSanPham().getPrice());

            donHangCTRepo.save(ct);
        }

        // Xóa giỏ hàng sau thanh toán
        gioHangCTRepo.deleteAll(items);

        return order;
    }
}
