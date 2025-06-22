//package datn.controller.admin;
//
//import datn.dto.KhoDto;
//import datn.service.KhoService;
//import datn.service.LichSuKhoService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//
//import java.util.Locale;
//
//
//@Controller
//@RequestMapping("/quan-ly/kho")
//@AllArgsConstructor
//public class KhoController {
//    @Autowired
//    private final KhoService khoService;
//    @Autowired
//    private final LichSuKhoService lichSuKhoService;
//
//
//    @GetMapping("")
//    public String home(@RequestParam(defaultValue = "0") int page,
//                       @RequestParam(defaultValue = "3") int size,
//                       @RequestParam(value = "keyword", required = false) String keyword,
//                       @RequestParam(value = "thuongHieu", required = false) Integer thuongHieuId,
//                       @RequestParam(value = "danhMuc", required = false) Integer danhMucId,
//                       @RequestParam(value = "status", required = false) Integer trangThai,
//                       Model model) {
//
//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//
//
//        // tổng sản phẩm
//        model.addAttribute("totalProduct", khoService.getTotalProduct());
//
//        // tổng sản phẩm tồn kho
//        model.addAttribute("totalProductInSock", khoService.getTotalProductInSock());
//
//        // toong giá trị ton trong kho
//        BigDecimal totalPriceInSock = khoService.getTotalPriceInSock();
//        String formattedPrice = currencyFormat.format(totalPriceInSock);
//        model.addAttribute("totalPriceInSock", formattedPrice);
//
//        // sản phẩm sắp hết
//        model.addAttribute("totalProductIsOut", khoService.getTotalProductIsOut());
//
//        // ds tồn kho
//        Page<KhoDto> khoPage = khoService.filterProductInSockPageable(keyword, thuongHieuId, danhMucId, trangThai, page, size);
//
//        model.addAttribute("listInSock", khoPage.getContent());
//        model.addAttribute("startPage", page);
//        model.addAttribute("totalPage", khoPage.getTotalPages());
//
//        // loọc
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("thuongHieu", thuongHieuId);
//        model.addAttribute("danhMuc", danhMucId);
//        model.addAttribute("status", trangThai);
//
//
//        // thương hiệu
//        model.addAttribute("listCategory", khoService.findAllCategoryInSock());
//
//        // danh mục
//        model.addAttribute("listBrand", khoService.findAllBrandInSock());
//
//        //ls kho
//        model.addAttribute("listHistoryInSock", lichSuKhoService.findAllHistoryInSock());
//
//        return "admin/quan-ly-kho";
//
//    }
//
//
//    @PostMapping("/nhap")
//    public String enterProduct(@RequestParam("id") Integer id,
//                               @RequestParam("soLuong") Integer soLuong,
//                               @RequestParam("ghiChuNhap") String ghiChu,
//                               RedirectAttributes redirectAttributes) {
//
//        try {
//            khoService.enterProduct(id, soLuong, ghiChu);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Nhập hàng thành công!");
//
//        } catch (IllegalArgumentException e) {
//
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//
//        return "redirect:/quan-ly/kho";
//    }
//
//    @PostMapping("/xuat")
//    public String exportProduct(@RequestParam("id") Integer id,
//                                @RequestParam("soLuong") Integer soLuong,
//                                @RequestParam("ghiChuXuat") String ghiChu,
//                                RedirectAttributes redirectAttributes) {
//        try {
//            khoService.exportProduct(id, soLuong, ghiChu);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Xuất hàng thành công!");
//
//        } catch (IllegalArgumentException e) {
//
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//        return "redirect:/quan-ly/kho";
//    }
//
//
//    @PostMapping("/sua")
//    public String update(@RequestParam("id") Integer id,
//                         @RequestParam("soLuong") Integer soLuong,
//                         @RequestParam("ghiChuUpdate") String ghiChu,
//                         RedirectAttributes redirectAttributes) {
//
//        try {
//            khoService.updateQuantity(id, soLuong, ghiChu);
//
//
//            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật số lượng mới thành công!");
//
//        } catch (IllegalArgumentException e) {
//
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//
//
//        return "redirect:/quan-ly/kho";
//    }
//
//
//    @PostMapping("/import")
//    public String importExcel(@RequestParam("file") MultipartFile file,
//                              RedirectAttributes redirectAttributes) {
//
//        try {
//            // validateion
//            if (file.isEmpty()) {
//                redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn file để import!");
//                return "redirect:/quan-ly/kho";
//            }
//
//            // chec đinh dạng p=fial
//            String fileName = file.getOriginalFilename();
//            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
//                redirectAttributes.addFlashAttribute("errorMessage", "File phải có định dạng Excel (.xlsx hoặc .xls)!");
//                return "redirect:/quan-ly/kho";
//            }
//
//            // check file nếu quá lơn
//            if (file.getSize() > 10 * 1024 * 1024) {
//                redirectAttributes.addFlashAttribute("errorMessage", "File quá lớn! Vui lòng chọn file nhỏ hơn 10MB.");
//                return "redirect:/quan-ly/kho";
//            }
//
//            // Import data
//            khoService.importFromExcel(file);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Import dữ liệu thành công!");
//
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi import: " + e.getMessage());
//        }
//
//        return "redirect:/quan-ly/kho";
//    }
//
//
//
//}
