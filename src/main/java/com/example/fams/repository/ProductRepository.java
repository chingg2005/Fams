package com.example.fams.repository;

import com.example.fams.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByNameContainingIgnoreCase(String keyword);
}
