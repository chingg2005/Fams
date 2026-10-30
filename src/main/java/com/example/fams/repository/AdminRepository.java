package com.example.fams.repository;

import com.example.fams.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // Tìm admin theo username và password (dùng cho đăng nhập)
    Optional<Admin> findByUsernameAndPassword(String username, String password);

    // Tìm admin theo username (dùng cho kiểm tra trùng username)
    Optional<Admin> findByUsername(String username);
}
