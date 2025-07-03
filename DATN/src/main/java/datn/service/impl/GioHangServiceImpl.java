package datn.service.impl;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import datn.repository.ChiTietGioHangRepo;
import datn.repository.GioHangRepo;
import datn.repository.NguoiDungRepo;
import datn.repository.SanPhamChiTietRepo;
import datn.service.GioHangService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private ChiTietGioHangRepo chiTietGioHangRepo;

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
    @Transactional
    public void themSanPhamVaoGio(int idNguoiDung, int idSanPhamChiTiet, int soLuong) {

        List<GioHang> danhSach = gioHangRepo.findByNguoiDung_Id(idNguoiDung);
        GioHang gioHang;

        if (danhSach.isEmpty()) {
            gioHang = new GioHang();
            gioHang.setNguoiDung(nguoiDungRepo.findById(idNguoiDung).orElseThrow());
            gioHang.setNgayThem(LocalDateTime.now());
            gioHang = gioHangRepo.save(gioHang);
        } else {
            gioHang = danhSach.get(0);
        }

        Optional<ChiTietGioHang> daCo = chiTietGioHangRepo.findByGioHang_IdAndSanPhamChiTiet_Id(
                gioHang.getId(), idSanPhamChiTiet
        );

        if (daCo.isPresent()) {
            ChiTietGioHang ct = daCo.get();
            ct.setSoLuong(ct.getSoLuong() + soLuong);
            chiTietGioHangRepo.save(ct);
        } else {
            ChiTietGioHang moi = new ChiTietGioHang();
            moi.setGioHang(gioHang);
            moi.setSanPhamChiTiet(sanPhamChiTietRepo.findById(idSanPhamChiTiet).orElseThrow());
            moi.setSoLuong(soLuong);
            chiTietGioHangRepo.save(moi);
        }
    }


    @Override
    public BigDecimal tinhTongTien(GioHang gioHang) {
        List<ChiTietGioHang> dsChiTiet = chiTietGioHangRepo.findByGioHang_Id(gioHang.getId());
        return dsChiTiet.stream()
                .map(ct -> BigDecimal.valueOf(ct.getSoLuong())
                        .multiply(ct.getSanPhamChiTiet().getGiaBan()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
