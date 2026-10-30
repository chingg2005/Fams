package com.example.fams.repository;

import com.example.fams.model.DonHang;
import com.example.fams.model.DonHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonHangChiTietRepository extends JpaRepository<DonHangChiTiet, Integer> {

    // Lấy tất cả chi tiết của 1 đơn hàng
    List<DonHangChiTiet> findByDonHang(DonHang donHang);
}
