package com.example.fams.service;

import com.example.fams.model.GioHang;
import com.example.fams.model.GioHangChiTiet;
import com.example.fams.model.NguoiDung;
import com.example.fams.model.SanPham;
import com.example.fams.repository.GioHangChiTietRepository;
import com.example.fams.repository.GioHangRepository;
import com.example.fams.repository.NguoiDungRepository;
import com.example.fams.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {

    @Autowired private GioHangRepository gioHangRepo;
    @Autowired private GioHangChiTietRepository chiTietRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private NguoiDungRepository nguoiDungRepo;

    /** 🔹 Lấy giỏ hàng, nếu chưa có thì tạo mới */
    @Transactional
    public GioHang getOrCreateCart(Integer userId) {
        NguoiDung user = nguoiDungRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        return gioHangRepo.findByNguoiDung(user)
                .orElseGet(() -> {
                    GioHang cart = new GioHang();
                    cart.setNguoiDung(user);
                    return gioHangRepo.save(cart);
                });
    }

    /** 🔹 Lấy danh sách chi tiết giỏ hàng */
    public List<GioHangChiTiet> getCartItemByUserId(Integer userId) {
        GioHang cart = getOrCreateCart(userId);
        return chiTietRepo.findByGioHangId(cart.getId());
    }

    /** 🔹 Thêm sản phẩm vào giỏ */
    @Transactional
    public void addToCart(Integer userId, Integer sanPhamId, Integer soLuong) {
        GioHang cart = getOrCreateCart(userId);

        SanPham sp = productRepo.findById(sanPhamId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        Optional<GioHangChiTiet> existingOpt =
                chiTietRepo.findByGioHangIdAndSanPhamId(cart.getId(), sanPhamId);

        if (existingOpt.isPresent()) {
            GioHangChiTiet ct = existingOpt.get();
            ct.setSoluong(ct.getSoluong() + (soLuong == null ? 1 : soLuong));
            chiTietRepo.save(ct);
        } else {
            GioHangChiTiet newItem = new GioHangChiTiet();
            newItem.setGioHang(cart);
            newItem.setSanPham(sp);
            newItem.setSoluong(soLuong == null ? 1 : soLuong);
            chiTietRepo.save(newItem);
        }
    }

    /** 🔹 Cập nhật số lượng sản phẩm */
    @Transactional
    public void updateCartItem(Integer userId, Integer sanPhamId, Integer soLuong) {
        GioHang cart = getOrCreateCart(userId);

        Optional<GioHangChiTiet> existing =
                chiTietRepo.findByGioHangIdAndSanPhamId(cart.getId(), sanPhamId);

        if (existing.isPresent()) {
            GioHangChiTiet item = existing.get();
            if (soLuong <= 0) {
                chiTietRepo.delete(item);
            } else {
                item.setSoluong(soLuong);
                chiTietRepo.save(item);
            }
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại trong giỏ");
        }
    }

    /** 🔹 Xóa sản phẩm khỏi giỏ */
    @Transactional
    public void removeCartItem(Integer userId, Integer sanPhamId) {
        updateCartItem(userId, sanPhamId, 0);
    }
}
