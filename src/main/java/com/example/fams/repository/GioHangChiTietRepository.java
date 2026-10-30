package com.example.fams.repository;

import com.example.fams.model.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {

    @Query("SELECT gct FROM GioHangChiTiet gct WHERE gct.gioHang.id = :cartId AND gct.sanPham.id = :productId")
    Optional<GioHangChiTiet> findByGioHangIdAndSanPhamId(@Param("cartId") Integer cartId,
                                                         @Param("productId") Integer productId);

    @Query("SELECT gct FROM GioHangChiTiet gct WHERE gct.gioHang.id = :cartId")
    List<GioHangChiTiet> findByGioHangId(@Param("cartId") Integer cartId);
}
