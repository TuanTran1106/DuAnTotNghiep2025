package datn.service.impl;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.entity.SanPhamChiTiet;
import datn.repository.ChiTietGioHangRepo;
import datn.service.ChiTietGioHangService;
import datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private ChiTietGioHangRepo chiTietGioHangRepo;

    @Override
    public void themVaoGio(NguoiDung nguoiDung, SanPhamChiTiet spct, int soLuong) {
        GioHang gioHang = gioHangService.layHoacTaoGioHang(nguoiDung);

        Optional<ChiTietGioHang> optional =
                chiTietGioHangRepo.findByGioHangAndSanPhamChiTiet(gioHang, spct);

        if (optional.isPresent()) {
            ChiTietGioHang ct = optional.get();
            ct.setSoLuong(ct.getSoLuong() + soLuong);
            chiTietGioHangRepo.save(ct);
        } else {
            ChiTietGioHang moi = new ChiTietGioHang();
            moi.setGioHang(gioHang);
            moi.setSanPhamChiTiet(spct);
            moi.setSoLuong(soLuong);
            chiTietGioHangRepo.save(moi);
        }
    }

    @Override
    public List<ChiTietGioHang> layTheoNguoiDung(NguoiDung nguoiDung) {
        GioHang gioHang = gioHangService.layHoacTaoGioHang(nguoiDung);
        return chiTietGioHangRepo.findByGioHang(gioHang);
    }
    @Override
    public void xoaKhoiGio(int id) {
        chiTietGioHangRepo.deleteById(id);
    }

    @Override
    public void tangSoLuong(int id) {
        Optional<ChiTietGioHang> optional = chiTietGioHangRepo.findById(id);
        if (optional.isPresent()) {
            ChiTietGioHang ct = optional.get();
            ct.setSoLuong(ct.getSoLuong() + 1);
            chiTietGioHangRepo.save(ct);
        }
    }

    @Override
    public void giamSoLuong(int id) {
        ChiTietGioHang ct = chiTietGioHangRepo.findById(id).orElseThrow();
        if (ct.getSoLuong() > 1) {
            ct.setSoLuong(ct.getSoLuong() - 1);
            chiTietGioHangRepo.save(ct);
        } else {
            chiTietGioHangRepo.delete(ct);
        }
    }

}