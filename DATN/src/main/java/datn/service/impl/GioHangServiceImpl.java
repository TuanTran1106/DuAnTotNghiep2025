package datn.service.impl;

import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.entity.SanPhamChiTiet;
import datn.repository.GioHangRepo;
import datn.repository.NguoiDungRepo;
import datn.repository.SanPhamChiTietRepo;
import datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepo gioHangRepo;

    @Autowired
    private SanPhamChiTietRepo sanPhamChiTietRepo;

    @Autowired
    private NguoiDungRepo nguoiDungRepo;

    @Override
    public List<GioHang> layGioHangTheoNguoiDung(int nguoiDungId) {
        return gioHangRepo.findByNguoiDung_Id(nguoiDungId);
    }

    @Override
    public void themSanPhamVaoGio(int nguoiDungId, int idSanPhamChiTiet) {
        // kiểm tra đã có sản phẩm này trong giỏ chưa
        List<GioHang> danhSach = gioHangRepo.findByNguoiDung_Id(nguoiDungId);
        Optional<GioHang> daCo = danhSach.stream()
                .filter(g -> g.getSanPhamChiTiet().getId().equals(idSanPhamChiTiet))
                .findFirst();

        if (daCo.isPresent()) {
            GioHang gh = daCo.get();
            gh.setSoLuong(gh.getSoLuong() + 1);
            gioHangRepo.save(gh);
        } else {
            GioHang moi = new GioHang();
            moi.setNguoiDung(nguoiDungRepo.findById(nguoiDungId).get());
            moi.setSanPhamChiTiet(sanPhamChiTietRepo.findById(idSanPhamChiTiet).get());
            moi.setSoLuong(1);
            moi.setNgayThem(LocalDateTime.now());
            gioHangRepo.save(moi);
        }
    }
    @Override
    public BigDecimal tinhTongTien(List<GioHang> gioHang) {
        return gioHang.stream()
                .map(g -> BigDecimal.valueOf(g.getSoLuong())
                        .multiply(g.getSanPhamChiTiet().getGiaBan()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public void tangSoLuong(int gioHangId) {
        GioHang gh = gioHangRepo.findById(gioHangId).orElseThrow();
        gh.setSoLuong(gh.getSoLuong() + 1);
        gioHangRepo.save(gh);
    }

    @Override
    public void giamSoLuong(int gioHangId) {
        GioHang gh = gioHangRepo.findById(gioHangId).orElseThrow();
        int newSoLuong = Math.max(1, gh.getSoLuong() - 1);
        gh.setSoLuong(newSoLuong);
        gioHangRepo.save(gh);
    }

    @Override
    public void xoaSanPham(int gioHangId) {
        gioHangRepo.deleteById(gioHangId);
    }
}
