package com.example.fams.repository;

import com.example.fams.model.DonHang;
import com.example.fams.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    // Lấy danh sách đơn hàng theo người dùng
    List<DonHang> findByNguoiDung(NguoiDung nguoiDung);

    // Lấy danh sách đơn hàng theo trạng thái
    List<DonHang> findByTrangThai(String trangThai);
}

