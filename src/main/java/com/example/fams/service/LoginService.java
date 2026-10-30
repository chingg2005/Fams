package com.example.fams.service;

import com.example.fams.model.NguoiDung;
import com.example.fams.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public NguoiDung login(String username, String password) {
        NguoiDung user = nguoiDungRepository.findByTendangnhap(username);
        if (user != null && user.getMatkhau().equals(password)) {
            return user;
        }
        return null;
    }
}
