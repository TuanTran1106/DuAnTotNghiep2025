package datn.controller;

import datn.entity.SanPhamChiTiet;
import datn.repository.SanPhamChiTietRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/san-pham-chi-tiet")
@AllArgsConstructor
public class SanPhamChiTietController {

    private final SanPhamChiTietRepo sanPhamChiTietRepo;

    @GetMapping("/chi-tiet/{id}")
    public String chiTietSanPham(@PathVariable("id") Integer id, Model model) {
        SanPhamChiTiet chiTiet = sanPhamChiTietRepo.findById(id).orElse(null);
        model.addAttribute("chiTiet", chiTiet);
        return "chi-tiet-san-pham"; // view chi tiết
    }
}

