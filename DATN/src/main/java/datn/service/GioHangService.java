package datn.service;

import datn.entity.GioHang;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface GioHangService {

    List<GioHang> layGioHangTheoNguoiDung(int nguoiDungId);

    void themSanPhamVaoGio(int nguoiDungId, int idSanPhamChiTiet, int soLuong);

    BigDecimal tinhTongTien(GioHang gioHang);

}