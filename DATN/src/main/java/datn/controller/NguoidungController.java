
import datn.entity.NguoiDung;
import datn.repository.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class NguoidungController {
    @Autowired
    private NguoiDungRepo nguoi_dungRepository;

    @GetMapping("/nguoidung")
    public String hienThi(Model model) {
        List<NguoiDung> list = nguoi_dungRepository.findAll();
        model.addAttribute("nguoidung", new NguoiDung());
        model.addAttribute("list", list);
        return "nguoidung.html";
    }
    @PostMapping("/nguoidung/update")
    public String updateNguoiDung(@ModelAttribute("nguoidung") NguoiDung nguoidung,
                                  @RequestParam("file") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (!file.isEmpty()) {
                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get("D:/watchaura/uploads/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                file.transferTo(uploadPath.resolve(filename));
                nguoidung.setHinhAnh(filename);
            }
            nguoi_dungRepository.save(nguoidung);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi upload ảnh!");
        }
        return "redirect:/mango";
    }
    @GetMapping("/nguoidung/detail/{id}")
    public String detailNguoiDung(Model model, @PathVariable("id") Integer id) {
        NguoiDung nguoidung = nguoi_dungRepository.findById(id).orElse(null);
        model.addAttribute("nguoidung", nguoidung);
        List<NguoiDung> list = nguoi_dungRepository.findAll();
        model.addAttribute("list", list);
        return "detailND.html";
    }
}
