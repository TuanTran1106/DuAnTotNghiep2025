package datn.service;

import datn.entity.GioHang;

import java.math.BigDecimal;
import java.util.List;

public interface GioHangService {

    List<GioHang> layGioHangTheoNguoiDung(int nguoiDungId);

    void themSanPhamVaoGio(int nguoiDungId, int idSanPhamChiTiet);

    void tangSoLuong(int gioHangId);

    void giamSoLuong(int gioHangId);

    void xoaSanPham(int gioHangId);

    BigDecimal tinhTongTien(List<GioHang> gioHang);
}