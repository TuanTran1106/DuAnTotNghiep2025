package datn.service.impl;

import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.repository.GioHangRepo;
import datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepo gioHangRepo;

    @Override
    public GioHang layHoacTaoGioHang(NguoiDung nguoiDung) {
        return gioHangRepo.findByNguoiDung(nguoiDung)
                .orElseGet(() -> {
                    GioHang gioHang = new GioHang();
                    gioHang.setNguoiDung(nguoiDung);
                    return gioHangRepo.save(gioHang);
                });
    }
}
