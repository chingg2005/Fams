package com.example.fams.service;

import com.example.fams.model.Admin;
import com.example.fams.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Đăng nhập admin
     * @param username tên đăng nhập
     * @param password mật khẩu
     *AC @return Optional<Admin>
     */
    public Optional<Admin> login(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    /**
     * Lưu hoặc cập nhật admin
     * @param admin đối tượng admin
     * @return admin đã lưu
     */
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    /**
     * Tìm admin theo id
     * @param id id admin
     * @return Optional<Admin>
     */
    public Optional<Admin> findById(int id) {
        return adminRepository.findById(id);
    }
}
