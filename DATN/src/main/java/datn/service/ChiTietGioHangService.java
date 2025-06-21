
package datn.service;

import datn.entity.ChiTietGioHang;
import datn.entity.NguoiDung;
import datn.entity.SanPhamChiTiet;

import java.util.List;

public interface ChiTietGioHangService {
    void themVaoGio(NguoiDung nguoiDung, SanPhamChiTiet spct, int soLuong);
    List<ChiTietGioHang> layTheoNguoiDung(NguoiDung nguoiDung);
    void xoaKhoiGio(int id);

    void tangSoLuong(int id);

    void giamSoLuong(int id);

}
