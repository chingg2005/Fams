package com.example.fams.controller;

import com.example.fams.model.GioHangChiTiet;
import com.example.fams.model.NguoiDung;
import com.example.fams.service.GioHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private GioHangService gioHangService;

    /** Trang giỏ hàng (Thymeleaf) */
    @GetMapping("/gio-hang")
    public String viewCartPage(HttpSession session, Model model) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", user.getId());
        return "user/gio_hang";
    }

    /** API: Lấy giỏ hàng (JSON) */
    @GetMapping("/api/gio-hang")
    @ResponseBody
    public List<GioHangChiTiet> getCartItems(HttpSession session) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) {
            return List.of(); // trả về rỗng nếu chưa đăng nhập
        }
        return gioHangService.getCartItemByUserId(user.getId());
    }



    /** API: Thêm sản phẩm vào giỏ */
    @PostMapping("/api/gio-hang/add")
    @ResponseBody
    public ResponseEntity<String> addToCart(HttpSession session,
                                            @RequestParam Integer sanPhamId,
                                            @RequestParam(required = false, defaultValue = "1") Integer soLuong) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("fail");

        gioHangService.addToCart(user.getId(), sanPhamId, soLuong);
        return ResponseEntity.ok("success");
    }

    /** API: Cập nhật số lượng giỏ hàng */
    @PostMapping("/api/gio-hang/update")
    @ResponseBody
    public ResponseEntity<String> updateCart(HttpSession session,
                                             @RequestParam Integer sanPhamId,
                                             @RequestParam Integer soLuong) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("fail");

        gioHangService.updateCartItem(user.getId(), sanPhamId, soLuong);
        return ResponseEntity.ok("success");
    }
    /** API: Xóa sản phẩm khỏi giỏ */
    @PostMapping("/api/gio-hang/remove")
    @ResponseBody
    public ResponseEntity<String> removeCartItem(HttpSession session,
                                                 @RequestParam Integer sanPhamId) {
        NguoiDung user = (NguoiDung) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("fail");

        gioHangService.removeCartItem(user.getId(), sanPhamId);
        return ResponseEntity.ok("success");
    }


}
