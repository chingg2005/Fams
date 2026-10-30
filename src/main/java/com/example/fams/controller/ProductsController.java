package com.example.fams.controller;

import com.example.fams.repository.ProductRepository;
import com.example.fams.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String productPage(Model model, HttpSession session) {

        Object currentUser = session.getAttribute("user");

        // Đưa vào model để HTML sử dụng
        model.addAttribute("user", currentUser);

        // Lấy danh sách sản phẩm
        model.addAttribute("products", productRepo.findAll());

        return "interface/products";
    }
    // ⭐ TÌM KIẾM SẢN PHẨM
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model,
                                 HttpSession session) {

        Object currentUser = session.getAttribute("user");

        model.addAttribute("user", currentUser);
        model.addAttribute("products", productService.search(keyword));
        model.addAttribute("keyword", keyword);

        return "interface/products";
    }
}
