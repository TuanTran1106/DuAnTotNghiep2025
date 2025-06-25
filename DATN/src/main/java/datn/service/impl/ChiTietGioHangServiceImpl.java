package datn.service.impl;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import datn.entity.SanPhamChiTiet;
import datn.repository.ChiTietGioHangRepo;
import datn.repository.GioHangRepo;
import datn.repository.SanPhamChiTietRepo;
import datn.service.ChiTietGioHangService;
import datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    @Autowired
    private ChiTietGioHangRepo chiTietGioHangRepo;

    @Autowired
    private GioHangRepo gioHangRepo;

    @Autowired
    private SanPhamChiTietRepo spctRepo;

    @Override
    public void themSanPhamVaoGio(Integer gioHangId, Integer spctId, Integer soLuong) {
        // Lấy giỏ hàng và sản phẩm chi tiết từ DB
        GioHang gioHang = gioHangRepo.findById(gioHangId).orElse(null);
        SanPhamChiTiet spct = spctRepo.findById(spctId).orElse(null);

        // Kiểm tra null tránh lỗi
        if (gioHang == null || spct == null) {
            System.out.println("Không tìm thấy giỏ hàng hoặc sản phẩm chi tiết.");
            return;
        }

        // Kiểm tra xem sản phẩm này đã có trong giỏ chưa
        ChiTietGioHang existing = chiTietGioHangRepo.findByGioHangIdAndSanPhamChiTietId(gioHangId, spctId);

        if (existing != null) {
            existing.setSoLuong(existing.getSoLuong() + soLuong);
            chiTietGioHangRepo.save(existing);
        } else {
            ChiTietGioHang ct = new ChiTietGioHang();
            ct.setGioHang(gioHang); // 👈 Quan trọng: Gán entity, không gán ID
            ct.setSanPhamChiTiet(spct);
            ct.setSoLuong(soLuong);
            chiTietGioHangRepo.save(ct);
        }
    }

    @Override
    public List<ChiTietGioHang> laySanPhamTrongGio(Integer idNguoiDung) {
        return chiTietGioHangRepo.findByGioHang_NguoiDung_Id(idNguoiDung);
    }

    @Override
    public void xoaSanPhamKhoiGio(Integer gioHangId, Integer spctId) {
        chiTietGioHangRepo.deleteByGioHangIdAndSanPhamChiTietId(gioHangId, spctId);
    }
}
