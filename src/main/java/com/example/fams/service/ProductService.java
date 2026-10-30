package com.example.fams.service;

import com.example.fams.model.SanPham;
import com.example.fams.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<SanPham> findAll() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo id
    public Optional<SanPham> findById(int id) {
        return productRepository.findById(id);
    }

    // Thêm hoặc cập nhật sản phẩm
    public SanPham save(SanPham sanPham) {
        return productRepository.save(sanPham);
    }

    // Xóa sản phẩm theo id
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    // Tìm kiếm sản phẩm theo tên (Không phân biệt hoa thường)
    public List<SanPham> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();  // nếu rỗng → trả tất cả
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim());
    }
}
