
import datn.entity.DonHang;
import datn.entity.NhanVien;

import datn.repository.ChiTietDonHangRepository;
import datn.repository.DonHangRepository;
import datn.repository.NhanvienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@org.springframework.stereotype.Controller
public class NhanvienController {
    @Autowired
    private NhanvienRepository nhanVienRepository;
    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;
    @GetMapping("/mango")
    public String hienThi(Model model) {
        List<NhanVien> list = nhanVienRepository.findAll();
        model.addAttribute("nhanvien", new NhanVien());
        model.addAttribute("list", list);
        return "nhanvien.html";
    }
    @PostMapping("/mango/save")
    public String addNhanVien(@Validated @ModelAttribute("nhanvien") NhanVien nhanVien,
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
    public String xoaNhanVien(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            List<DonHang> donHangs = donHangRepository.findByNhanVienId(id);
            List<Integer> donHangIds = donHangs.stream().map(DonHang::getId).toList();
            chiTietDonHangRepository.deleteByDonHangIds(donHangIds);
            donHangRepository.deleteAll(donHangs);
            nhanVienRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa nhân viên và các dữ liệu liên quan thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa: " + e.getMessage());
        }
        return "redirect:/mango";
    }
    @GetMapping("/nhanvien/detail/{id}")
    public String detailNhanVien(Model model, @PathVariable("id") Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id).orElse(null);
        model.addAttribute("nhanvien", nhanVien);
        List<NhanVien> list = nhanVienRepository.findAll();
        model.addAttribute("list", list);
        return "detail.html";
    }
    @PostMapping("/nhanvien/update")
    public String updateNhanVien(@Validated @ModelAttribute("nhanvien") NhanVien nhanVien,
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
}
