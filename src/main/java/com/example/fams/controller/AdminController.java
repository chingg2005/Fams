package com.example.fams.controller;

import com.example.fams.model.Admin;
import com.example.fams.model.SanPham;
import com.example.fams.service.AdminService;
import com.example.fams.service.DonHangService;
import com.example.fams.service.ProductService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DonHangService donHangService;


    /* ============================
     * 1. TRANG ĐĂNG NHẬP
     * ============================ */

    @GetMapping("/login")
    public String loginPage() {
        return "admin/admin_login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        Admin admin = adminService.login(username, password)
                .orElse(null); // unwrap Optional

        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
        return "admin/admin_login";
    }


    /* ============================
     * 2. DASHBOARD
     * ============================ */

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) return "redirect:/admin/login";

        model.addAttribute("admin", admin);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orders", donHangService.findAll());

        return "admin/admin_dashboard";
    }


    /* ============================
     * 3. CẬP NHẬT THÔNG TIN ADMIN
     * ============================ */

    @PostMapping("/update")
    public String updateInfo(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             HttpSession session) {

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) return "redirect:/admin/login";

        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(password);

        adminService.save(admin);

        return "redirect:/admin/dashboard?success=updated";
    }


    /* ============================
     * 4. ĐĂNG XUẤT
     * ============================ */

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }


    /* ============================
     * 5. THÊM SẢN PHẨM
     * ============================ */

    @PostMapping("/product/save")
    public String addProduct(@ModelAttribute SanPham product, HttpSession session) {

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) return "redirect:/admin/login";

        productService.save(product);
        return "redirect:/admin/dashboard?success=added";
    }


    /* ============================
     * 6. XÓA SẢN PHẨM
     * ============================ */

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) return "redirect:/admin/login";


        productService.deleteById(id);
        return "redirect:/admin/dashboard?success=deleted";
    }

    @GetMapping("/products")
    public String productList(@RequestParam(value = "search", required = false) String keyword,
                              Model model, HttpSession session) {

        if (session.getAttribute("admin") == null) return "redirect:/admin/login";

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("products", productService.search(keyword));
            model.addAttribute("search", keyword);
        } else {
            model.addAttribute("products", productService.findAll());
        }

        return "admin/admin_dashboard";
    }
    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute SanPham product, HttpSession session) {

        if (session.getAttribute("admin") == null)
            return "redirect:/admin/login";

        productService.save(product); // save() tự động update nếu có id

        return "redirect:/admin/dashboard?success=updated";
    }


}
