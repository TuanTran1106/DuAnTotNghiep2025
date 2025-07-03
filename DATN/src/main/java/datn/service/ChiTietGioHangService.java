package datn.service;

import datn.entity.ChiTietGioHang;

import java.util.List;

public interface ChiTietGioHangService {


    List<ChiTietGioHang> laySanPhamTrongGio(Integer idNguoiDung);

    void xoaSanPhamKhoiGio(Integer gioHangId, Integer spctId);

    void tangSoLuong(Integer chiTietGioHangId);

    void giamSoLuong(Integer chiTietGioHangId);

    void xoaSanPham(Integer chiTietGioHangId);
}
