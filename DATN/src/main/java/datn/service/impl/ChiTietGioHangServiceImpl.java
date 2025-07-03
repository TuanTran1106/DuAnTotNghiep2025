package datn.service.impl;

import datn.entity.ChiTietGioHang;
import datn.repository.ChiTietGioHangRepo;;
import datn.service.ChiTietGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

    @Autowired
    private ChiTietGioHangRepo chiTietGioHangRepo;


    @Override
    public List<ChiTietGioHang> laySanPhamTrongGio(Integer idNguoiDung) {
        return chiTietGioHangRepo.findByGioHang_NguoiDung_Id(idNguoiDung);
    }

    @Override
    public void xoaSanPhamKhoiGio(Integer gioHangId, Integer spctId) {
        chiTietGioHangRepo.deleteByGioHang_IdAndSanPhamChiTiet_Id(gioHangId, spctId);
    }

    @Override
    public void tangSoLuong(Integer chiTietGioHangId) {
        ChiTietGioHang ct = chiTietGioHangRepo.findById(chiTietGioHangId).orElse(null);
        if (ct != null) {
            ct.setSoLuong(ct.getSoLuong() + 1);
            chiTietGioHangRepo.save(ct);
        }
    }

    @Override
    public void giamSoLuong(Integer chiTietGioHangId) {
        ChiTietGioHang ct = chiTietGioHangRepo.findById(chiTietGioHangId).orElse(null);
        if (ct != null) {
            if (ct.getSoLuong() > 1) {
                ct.setSoLuong(ct.getSoLuong() - 1);
                chiTietGioHangRepo.save(ct);
            } else {
                chiTietGioHangRepo.delete(ct);
            }
        }
    }

    @Override
    public void xoaSanPham(Integer chiTietGioHangId) {
        chiTietGioHangRepo.deleteById(chiTietGioHangId);
    }
}
