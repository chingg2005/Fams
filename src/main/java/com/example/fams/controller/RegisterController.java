package com.example.fams.controller;

import com.example.fams.model.NguoiDung;
import com.example.fams.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String fullname,
            @RequestParam String phone, // Đã là String
            @RequestParam String address,
            @RequestParam String password
    ) {
        NguoiDung newUser = new NguoiDung();

        // Gán các trường
        newUser.setTendangnhap(username);
        newUser.setEmail(email);
        newUser.setMatkhau(password);
        newUser.setHoten(fullname);
        newUser.setDiachi(address);

        newUser.setSdt(phone);
        // -------------------------

        // LƯU VÀO DATABASE
        nguoiDungRepository.save(newUser);

        System.out.println("Đã thêm người dùng: " + username + " vào DB.");

        return "redirect:/login";
    }
}