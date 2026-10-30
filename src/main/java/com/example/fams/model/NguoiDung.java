package com.example.fams.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nguoi_dung")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tendangnhap;
    private String email;
    private String matkhau;
    private String hoten;
    private String diachi;
    private String sdt;
}
