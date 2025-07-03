package datn.controller;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.repository.ChiTietGioHangRepo;
import datn.repository.GioHangRepo;
import datn.repository.SanPhamChiTietRepo;
import datn.service.ChiTietGioHangService;
import datn.service.GioHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
    private GioHangRepo gioHangRepo;

    @Autowired
    private GioHangService  gioHangService;

    @Autowired
    private ChiTietGioHangRepo chiTietGioHangRepo;

    @GetMapping()
    public String hienThiGioHang(HttpSession session, Model model) {
        Integer idNguoiDung = (Integer) session.getAttribute("idNguoiDung");

        if (idNguoiDung == null) {
            return "redirect:/login";
        }

        List<ChiTietGioHang> danhSach = chiTietGioHangService.laySanPhamTrongGio(idNguoiDung);
        model.addAttribute("gioHang", danhSach);

        BigDecimal tongTien = danhSach.stream()
                .map(item -> {
                    BigDecimal gia = item.getSanPhamChiTiet().getGiaBan();
                    Integer soLuong = item.getSoLuong();
                    return gia != null && soLuong != null ? gia.multiply(BigDecimal.valueOf(soLuong)) : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("tongTien", tongTien);

        return "gio-hang";
    }

    @PostMapping("/them")
    public String themVaoGio(@RequestParam Integer spctId,
                             @RequestParam(defaultValue = "1") Integer soLuong,
                             HttpSession session) {

        Integer idNguoiDung = (Integer) session.getAttribute("idNguoiDung");
        if (idNguoiDung == null) {
            return "redirect:/login";
        }

        gioHangService.themSanPhamVaoGio(idNguoiDung, spctId, soLuong);
        return "redirect:/gio-hang";
    }

    @GetMapping("/xoa")
    public String xoaKhoiGio(@RequestParam Integer spctId,
                             HttpSession session) {
        Integer idNguoiDung = (Integer) session.getAttribute("idNguoiDung");
        if (idNguoiDung == null) {
            return "redirect:/login";
        }

        chiTietGioHangService.xoaSanPhamKhoiGio(idNguoiDung, spctId);
        return "redirect:/gio-hang";
    }

    @PostMapping("/xoa")
    public String xoaSanPham(@RequestParam("id") Integer id) {
        chiTietGioHangRepo.deleteById(id);
        return "redirect:/gio-hang";
    }

    @PostMapping("/tang")
    public String tangSoLuong(@RequestParam("id") Integer id) {
        ChiTietGioHang ctgh = chiTietGioHangRepo.findById(id).orElse(null);
        if (ctgh != null) {
            ctgh.setSoLuong(ctgh.getSoLuong() + 1);
            chiTietGioHangRepo.save(ctgh);
        }
        return "redirect:/gio-hang";
    }

    @PostMapping("/giam")
    public String giamSoLuong(@RequestParam("id") Integer id) {
        ChiTietGioHang ctgh = chiTietGioHangRepo.findById(id).orElse(null);
        if (ctgh != null && ctgh.getSoLuong() > 1) {
            ctgh.setSoLuong(ctgh.getSoLuong() - 1);
            chiTietGioHangRepo.save(ctgh);
        } else if (ctgh != null) {
            chiTietGioHangRepo.delete(ctgh);
        }
        return "redirect:/gio-hang";
    }
}

