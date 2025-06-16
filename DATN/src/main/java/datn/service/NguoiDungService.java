package datn.service;

import datn.entity.NguoiDung;

public interface NguoiDungService {
    NguoiDung dangNhap(String email, String matKhau);
}
