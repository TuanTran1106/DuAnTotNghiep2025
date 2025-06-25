package datn.controller;


import datn.entity.nhan_vien;
import datn.repository.NhanvienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private NhanvienRepository nhanVienRepository;

    @GetMapping("/mango")
    public String hienThi(Model model) {
        List<nhan_vien> list = nhanVienRepository.findAll();
        model.addAttribute("nhanvien", new nhan_vien());
        model.addAttribute("list", list);
        return "nhanvien.html";
    }

    @PostMapping("/mango/save")
    public String addNhanVien(@Validated @ModelAttribute("nhanvien") nhan_vien nhanVien,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("list", nhanVienRepository.findAll());
            return "nhanvien.html";
        }
        nhanVienRepository.save(nhanVien);
        return "redirect:/mango";

    }

    @GetMapping("/nhanvien/xoa/{id}")
    public String xoaNhanVien(@PathVariable("id") Integer id) {
        nhanVienRepository.deleteById(id);
        return "redirect:/mango";
    }

    @GetMapping("/nhanvien/detail/{id}")
    public String detailNhanVien(Model model, @PathVariable("id") Integer id) {
        nhan_vien nhanVien = nhanVienRepository.findById(id).orElse(null);
        model.addAttribute("nhanvien", nhanVien);

        List<nhan_vien> list = nhanVienRepository.findAll();
        model.addAttribute("list", list);

        return "detail.html";
    }

    @PostMapping("/nhanvien/update")
    public String updateNhanVien(@Validated @ModelAttribute("nhanvien") nhan_vien nhanVien,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("list", nhanVienRepository.findAll());
            return "nhanvien.html";
        }
        nhanVienRepository.save(nhanVien);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        return "redirect:/mango";
    }

    @GetMapping("/mango/search")
    public String timKiemNhanVien(
            @RequestParam(value = "hoten", required = false) String hoten,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "sdt", required = false) String sdt,
            @RequestParam(value = "dia_chi", required = false) String dia_chi,
            Model model) {

        List<nhan_vien> result = nhanVienRepository.findAll().stream()
                .filter(nv ->
                        (hoten == null || hoten.isEmpty() || nv.getHoten().toLowerCase().contains(hoten.toLowerCase())) &&
                                (email == null || email.isEmpty() || nv.getEmail().toLowerCase().contains(email.toLowerCase())) &&
                                (sdt == null || sdt.isEmpty() || nv.getSdt().toLowerCase().contains(sdt.toLowerCase())) &&
                                (dia_chi == null || dia_chi.isEmpty() || nv.getDia_chi().toLowerCase().contains(dia_chi.toLowerCase())))
                .toList();

        model.addAttribute("nhanvien", new nhan_vien());
        model.addAttribute("list", result);
        return "nhanvien.html";
    }

}
