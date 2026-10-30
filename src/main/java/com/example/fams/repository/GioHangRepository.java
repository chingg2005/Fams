package com.example.fams.repository;

import com.example.fams.model.GioHang;
import com.example.fams.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {


    Optional<GioHang> findByNguoiDung(NguoiDung user);

}
