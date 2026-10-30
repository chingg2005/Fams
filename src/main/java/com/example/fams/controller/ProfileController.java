package com.example.fams.controller;

import com.example.fams.model.NguoiDung;
import com.example.fams.service.NguoiDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    private NguoiDungService nguoiDungService;

    // Hiển thị hồ sơ người dùng
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {// tạm thời, sau này lấy từ session/Spring Security
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if(user == null) {
        return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "user/profile";
    }

    // Hiển thị form chỉnh sửa hồ sơ
    @GetMapping("/profile/edit")
    public String showEditProfile(HttpSession session, Model model) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user/edit-profile";
    }

    // Xử lý cập nhật hồ sơ
    @PostMapping("/profile/update")
    public String updateProfile(NguoiDung userForm) {
        nguoiDungService.updateUser(userForm);
        return "redirect:/profile?updated=true";
    }
    @GetMapping("/edit-profile")
    public String showEditProfileShortcut(HttpSession session, Model model) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user/edit-profile";
    }

}
