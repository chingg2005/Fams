package com.example.fams.controller;

import com.example.fams.model.GioHang;
import com.example.fams.model.GioHangChiTiet;
import com.example.fams.model.NguoiDung;
import com.example.fams.service.GioHangService;
import com.example.fams.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private PaymentService paymentService;

    /** 🔹 Trang hiển thị giỏ hàng + thanh toán */
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {

        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) return "redirect:/gio-hang";

        GioHang cart = gioHangService.getOrCreateCart(user.getId());

        List<GioHangChiTiet> items = gioHangService.getCartItemByUserId(user.getId());

        double total = items.stream()
                .mapToDouble(i -> i.getSanPham().getPrice() * i.getSoluong())
                .sum();

        model.addAttribute("cartItems", items);
        model.addAttribute("total", total);

        return "user/checkout";
    }

    /** 🔹 Xác nhận thanh toán */
    @PostMapping("/checkout/submit")
    public String submitOrder(HttpSession session, Model model) {
        NguoiDung user = (NguoiDung) session.getAttribute("user"); // lấy user từ session
        if (user == null) return "redirect:/login";

        var order = paymentService.createOrder(user.getId());

        model.addAttribute("order", order);

        return "user/checkout_success";
    }
}
