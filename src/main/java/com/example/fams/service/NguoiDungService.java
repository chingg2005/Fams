package com.example.fams.service;

import com.example.fams.model.NguoiDung;
import com.example.fams.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public NguoiDung findById(Integer id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }

    public void updateUser(NguoiDung userForm) {
        NguoiDung user = nguoiDungRepository.findById(userForm.getId()).orElse(null);

        if (user != null) {
            user.setHoten(userForm.getHoten());
            user.setEmail(userForm.getEmail());
            user.setSdt(userForm.getSdt());
            user.setDiachi(userForm.getDiachi());

            nguoiDungRepository.save(user);
        }
    }
}
