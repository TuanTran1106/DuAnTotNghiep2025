package datn.controller.admin;

import datn.dto.KhoDto;
import datn.entity.DanhMuc;
import datn.entity.SanPham;
import datn.entity.SanPhamChiTiet;
import datn.entity.ThuongHieu;
import datn.repository.DanhMucRepository;
import datn.repository.ThuongHieuRepository;
import datn.service.KhoService;
import datn.service.LichSuKhoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/quan-ly/kho")
@AllArgsConstructor
public class QuanLyKhoController {

    private final KhoService khoService;

    private final LichSuKhoService lichSuKhoService;
    private final DanhMucRepository danhMucRepository;
    private final ThuongHieuRepository thuongHieuRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";


    @GetMapping("")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "3") int size,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "thuongHieu", required = false) Integer thuongHieuId,
                       @RequestParam(value = "danhMuc", required = false) Integer danhMucId,
                       @RequestParam(value = "status", required = false) Integer trangThai,
                       Model model) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // tổng sản phẩm
        model.addAttribute("totalProduct", khoService.getTotalProduct());

        // tổng sản phẩm tồn kho
        model.addAttribute("totalProductInSock", khoService.getTotalProductInSock());

        // toong giá trị ton trong kho
        BigDecimal totalPriceInSock = khoService.getTotalPriceInSock();
        String formattedPrice = currencyFormat.format(totalPriceInSock);
        model.addAttribute("totalPriceInSock", formattedPrice);

        // sản phẩm sắp hết
        model.addAttribute("totalProductIsOut", khoService.getTotalProductIsOut());

        // ds tồn kho
        Page<KhoDto> khoPage = khoService.filterProductInSockPageable(keyword, thuongHieuId, danhMucId, page, size);

        model.addAttribute("listInSock", khoPage.getContent());
        model.addAttribute("startPage", page);
        model.addAttribute("totalPage", khoPage.getTotalPages());

        // loọc
        model.addAttribute("keyword", keyword);
        model.addAttribute("thuongHieu", thuongHieuId);
        model.addAttribute("danhMuc", danhMucId);

        // thương hiệu
        model.addAttribute("listCategory", khoService.findAllCategoryInSock());

        // danh mục
        model.addAttribute("listBrand", khoService.findAllBrandInSock());

        //ls kho
        model.addAttribute("listHistoryInSock", lichSuKhoService.findAllHistoryInSock());

        model.addAttribute("newProduct", new KhoDto());

        return "admin/quan-ly-kho";

    }


    @PostMapping("/them")
    public String addProduct(@RequestParam("tenSanPham") String tenSanPham,
                             @RequestParam("thuongHieu") Integer thuongHieuId,
                             @RequestParam("danhMuc") Integer danhMucId,
                             @RequestParam("mauSac") String mauSac,
                             @RequestParam("kichThuoc") String kichThuoc,
                             @RequestParam("chatLieu") String chatLieu,
                             @RequestParam("giaNhap") BigDecimal giaNhap,
                             @RequestParam("soLuong") Integer soLuong,
                             @RequestParam(value = "hinhAnh", required = false) MultipartFile hinhAnh,
                             RedirectAttributes redirectAttributes) {

        try {
            if (tenSanPham == null || tenSanPham.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
            }
            if (thuongHieuId == null) {
                throw new IllegalArgumentException("Vui lòng chọn thương hiệu!");
            }
            if (danhMucId == null) {
                throw new IllegalArgumentException("Vui lòng chọn danh mục!");
            }
            if (mauSac == null || mauSac.trim().isEmpty()) {
                throw new IllegalArgumentException("Màu sắc không được để trống!");
            }
            if (kichThuoc == null || kichThuoc.trim().isEmpty()) {
                throw new IllegalArgumentException("Kích thước không được để trống!");
            }
            if (chatLieu == null || chatLieu.trim().isEmpty()) {
                throw new IllegalArgumentException("Chất liệu không được để trống!");
            }
            if (giaNhap == null || giaNhap.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Giá nhập phải lớn hơn 0!");
            }
            if (soLuong == null || soLuong <= 0) {
                throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
            }

            Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(thuongHieuId);
            Optional<DanhMuc> danhMuc = danhMucRepository.findById(danhMucId);

            if (!thuongHieu.isPresent()) {
                throw new IllegalArgumentException("Thương hiệu không tồn tại!");
            }
            if (!danhMuc.isPresent()) {
                throw new IllegalArgumentException("Danh mục không tồn tại!");
            }

            String imageName = null;
            if (hinhAnh != null && !hinhAnh.isEmpty()) {
                imageName = saveUploadedFile(hinhAnh);
            }

            SanPham sanPham = new SanPham();
            sanPham.setMaSanPham(generateProductCode());
            sanPham.setTenSanPham(tenSanPham.trim());
            sanPham.setGiaNhap(giaNhap);
            sanPham.setNgayNhap(LocalDate.now());
            sanPham.setTrangThai(1);
            sanPham.setHinhAnh(imageName);
            sanPham.setThuongHieu(thuongHieu.get());
            sanPham.setDanhMuc(danhMuc.get());

            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setMauSac(mauSac.trim());
            sanPhamChiTiet.setKichThuoc(kichThuoc.trim());
            sanPhamChiTiet.setChatLieu(chatLieu.trim());
            sanPhamChiTiet.setSoLuong(soLuong);
            sanPhamChiTiet.setGiaBan(giaNhap.multiply(new BigDecimal("1.2")));
            sanPhamChiTiet.setTrangThai(1);
            sanPhamChiTiet.setSanPham(sanPham);

            khoService.addNewProduct(sanPham, sanPhamChiTiet);

            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
        }

        return "redirect:/quan-ly/kho";
    }

    @PostMapping("/nhap")
    public String enterProduct(@RequestParam("id") Integer id,
                               @RequestParam("soLuong") Integer soLuong,
                               @RequestParam("ghiChuNhap") String ghiChu,
                               RedirectAttributes redirectAttributes) {

        try {
            khoService.enterProduct(id, soLuong, ghiChu);

            redirectAttributes.addFlashAttribute("successMessage", "Nhập hàng thành công!");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/quan-ly/kho";
    }

    @PostMapping("/xuat")
    public String exportProduct(@RequestParam("id") Integer id,
                                @RequestParam("soLuong") Integer soLuong,
                                @RequestParam("ghiChuXuat") String ghiChu,
                                RedirectAttributes redirectAttributes) {
        try {
            khoService.exportProduct(id, soLuong, ghiChu);

            redirectAttributes.addFlashAttribute("successMessage", "Xuất hàng thành công!");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/quan-ly/kho";
    }


    @PostMapping("/sua")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam("soLuong") Integer soLuong,
                         @RequestParam("ghiChuUpdate") String ghiChu,
                         RedirectAttributes redirectAttributes) {

        try {
            khoService.updateQuantity(id, soLuong, ghiChu);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật số lượng mới thành công!");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/quan-ly/kho";
    }


    @PostMapping("/xoa")
    public String softDeleteProduct(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            khoService.deleteProductInSock(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/quan-ly/kho";
    }



    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn file để import!");
                return "redirect:/quan-ly/kho";
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                redirectAttributes.addFlashAttribute("errorMessage", "File phải có định dạng Excel (.xlsx hoặc .xls)!");
                return "redirect:/quan-ly/kho";
            }

            if (file.getSize() > 10 * 1024 * 1024) {
                redirectAttributes.addFlashAttribute("errorMessage", "File quá lớn! Vui lòng chọn file nhỏ hơn 10MB.");
                return "redirect:/quan-ly/kho";
            }

            khoService.importFromExcel(file);
            redirectAttributes.addFlashAttribute("successMessage", "Import dữ liệu thành công!");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi đọc file Excel: " + e.getMessage());
        }

        return "redirect:/quan-ly/kho";
    }


    private String generateProductCode() {
        return "SP" + System.currentTimeMillis();
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }
}
