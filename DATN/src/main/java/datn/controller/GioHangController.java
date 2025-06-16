package datn.controller;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.entity.SanPhamChiTiet;
import datn.repository.SanPhamChiTietRepo;
import datn.service.ChiTietGioHangService;
import datn.service.GioHangService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gio-hang")

public class GioHangController {
    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
    private SanPhamChiTietRepo sanPhamChiTietRepo;

    @GetMapping
    public String hienThiGioHang(Model model, HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");

        if (nguoiDung == null) {
            return "redirect:/login"; // hoặc bất kỳ trang nào bạn muốn
        }

        List<ChiTietGioHang> gioHang = chiTietGioHangService.layTheoNguoiDung(nguoiDung);
        model.addAttribute("gioHang", gioHang);
        return "gio-hang";
    }

    @PostMapping("/them")
    public String themVaoGio(@RequestParam("id_spct") Integer idSpct,
                             @RequestParam("so_luong") int soLuong,
                             @SessionAttribute("nguoiDung") NguoiDung nguoiDung) {

        SanPhamChiTiet spct = sanPhamChiTietRepo.findById(idSpct).orElse(null);
        if (spct != null) {
            chiTietGioHangService.themVaoGio(nguoiDung, spct, soLuong);
        }

        return "redirect:/gio-hang";
    }
    @PostMapping("/xoa")
    public String xoaKhoiGio(@RequestParam("id") int id) {
        chiTietGioHangService.xoaKhoiGio(id);
        return "redirect:/gio-hang";
    }

    @PostMapping("/tang-so-luong")
    public String tangSoLuong(@RequestParam("id") int id) {
        chiTietGioHangService.tangSoLuong(id);
        return "redirect:/gio-hang";
    }

    @PostMapping("/giam-so-luong")
    public String giamSoLuong(@RequestParam("id") int id) {
        chiTietGioHangService.giamSoLuong(id);
        return "redirect:/gio-hang";
    }


}
