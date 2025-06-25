package datn.service;

import datn.entity.ChiTietGioHang;

import java.util.List;

public interface ChiTietGioHangService {
    void themSanPhamVaoGio(Integer gioHangId, Integer spctId, Integer soLuong);
    List<ChiTietGioHang> laySanPhamTrongGio(Integer gioHangId);
    void xoaSanPhamKhoiGio(Integer gioHangId, Integer spctId);
}
