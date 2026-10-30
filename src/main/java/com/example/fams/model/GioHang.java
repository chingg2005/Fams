package com.example.fams.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gio_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "nguoi_dung_id", unique = true)
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy="gioHang", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<GioHangChiTiet> chiTiets = new ArrayList<>();

}
