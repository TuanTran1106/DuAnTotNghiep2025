package datn.controller;

import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.repository.GioHangRepo;
import datn.repository.SanPhamChiTietRepo;
import datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
        int nguoiDungId = 1; // hardcoded cho ví dụ
        List<GioHang> gioHang = gioHangRepo.findByNguoiDung_Id(nguoiDungId);
        BigDecimal tongTien = gioHangService.tinhTongTien(gioHang);
        model.addAttribute("gioHang", gioHang);
        model.addAttribute("tongTien", tongTien);
        return "gio-hang";
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/them")
    public String themVaoGio(@RequestParam int idSanPhamChiTiet) {
        int nguoiDungId = 1;
        GioHang item = new GioHang();
        item.setNguoiDung(new NguoiDung(nguoiDungId));
        item.setSanPhamChiTiet(sanPhamChiTietRepo.findById(idSanPhamChiTiet).get());
        item.setSoLuong(1);
        item.setNgayThem(LocalDateTime.now());
        gioHangRepo.save(item);
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
    }}