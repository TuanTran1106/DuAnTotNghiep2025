package datn.controller;

import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.repository.GioHangRepo;
import datn.repository.SanPhamChiTietRepo;
import datn.service.GioHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangRepo gioHangRepo;

    @Autowired
    private SanPhamChiTietRepo sanPhamChiTietRepo;

    @Autowired
    private GioHangService gioHangService;

    // Hiển thị giỏ hàng
    @GetMapping
    public String xemGioHang(Model model) {
        int nguoiDungId = 1;

        List<GioHang> gioHang = gioHangRepo.findByNguoiDung_Id(nguoiDungId);
        BigDecimal tongTien = gioHangService.tinhTongTien(gioHang);
        model.addAttribute("gioHang", gioHang);
        model.addAttribute("tongTien", tongTien);
        return "gio-hang";
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/them")
    public String themVaoGio(@RequestParam int idSanPhamChiTiet, HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDungDangNhap");
        if (nguoiDung == null) {
            return "redirect:/dang-nhap";
        }

        Optional<GioHang> optional = gioHangRepo.findByNguoiDung_IdAndSanPhamChiTiet_Id(
                nguoiDung.getId(), idSanPhamChiTiet
        );

        if (optional.isPresent()) {
            GioHang gioHang = optional.get();
            gioHang.setSoLuong(gioHang.getSoLuong() + 1);
            gioHangRepo.save(gioHang);
        } else {
            GioHang gioHang = new GioHang();
            gioHang.setNguoiDung(nguoiDung);
            gioHang.setSanPhamChiTiet(sanPhamChiTietRepo.findById(idSanPhamChiTiet).orElse(null));
            gioHang.setSoLuong(1);
            gioHang.setNgayThem(LocalDateTime.now());
            gioHangRepo.save(gioHang);
        }

        return "redirect:/gio-hang";
    }

    // Xóa
    @PostMapping("/xoa")
    public String xoa(@RequestParam int id) {
        gioHangRepo.deleteById(id);
        return "redirect:/gio-hang";
    }

    // Tăng
    @PostMapping("/tang")
    public String tang(@RequestParam int id) {
        GioHang gh = gioHangRepo.findById(id).get();
        gh.setSoLuong(gh.getSoLuong() + 1);
        gioHangRepo.save(gh);
        return "redirect:/gio-hang";
    }

    // Giảm
    @PostMapping("/giam")
    public String giam(@RequestParam int id) {
        GioHang gh = gioHangRepo.findById(id).get();
        gh.setSoLuong(Math.max(1, gh.getSoLuong() - 1));
        gioHangRepo.save(gh);
        return "redirect:/gio-hang";
    }

}
