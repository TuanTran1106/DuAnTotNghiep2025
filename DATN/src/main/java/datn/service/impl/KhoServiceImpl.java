package datn.service.impl;

import datn.dto.KhoDto;
import datn.entity.*;
import datn.repository.*;
import datn.repository.HinhAnhSanPham;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KhoServiceImpl implements KhoService {

    @Autowired
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    private final KhoRepository khoRepository;
    @Autowired
    private final ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private final DanhMucRepository danhMucRepository;
    @Autowired
    private final LichSuKhoRepository lichSuKhoRepository;
    @Autowired
    private final SanPhamRepository sanPhamRepository;

    private final SanPhamChiTietRepo sanPhamChiTietRepository;



    @Override
    public Integer getTotalProduct() {
        return khoRepository.totalProduct();
    }

    @Override
    public Integer getTotalProductInSock() {
        return khoRepository.totalProductInSock();
    }

    @Override
    public BigDecimal getTotalPriceInSock() {
        return khoRepository.totalPriceInSock();
    }

    @Override
    public Integer getTotalProductIsOut() {
        return khoRepository.totalProductIsOut();
    }

    @Override
    @Transactional
    public void enterProduct(Integer id, Integer soLuong, String ghiChu) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        sp.setSoLuong(sp.getSoLuong() + soLuong);

        LichSuKho lichSuKho = new LichSuKho();
        lichSuKho.setSanPhamChiTiet(sp);
        lichSuKho.setNgayNhap(LocalDate.now());
        lichSuKho.setNgayXuat(lichSuKho.getNgayNhap());
        lichSuKho.setNgayUpdate(lichSuKho.getNgayNhap());
        lichSuKho.setLoai("NHAP");
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(1);
        lichSuKho.setNhanVien(nhanVien);
        lichSuKho.setSoLuong(soLuong);
        lichSuKho.setGhiChu(ghiChu);

        ZonedDateTime vnNow = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime now = vnNow.toLocalDateTime();
        lichSuKho.setThoiGian(now);
        lichSuKhoRepository.save(lichSuKho);


        khoRepository.save(sp);
    }

    @Override
    @Transactional
    public void exportProduct(Integer id, Integer soLuong, String ghiChu) {

        SanPhamChiTiet sp = khoRepository.findById(id)
                .orElseThrow();
        if (sp.getSoLuong() < soLuong) {
            throw new IllegalArgumentException("Số lượng không đủ để xuất!");
        }
        sp.setSoLuong(sp.getSoLuong() - soLuong);

        LichSuKho lichSuKho = new LichSuKho();
        lichSuKho.setSanPhamChiTiet(sp);

        lichSuKho.setNgayNhap(lichSuKho.getNgayNhap());
        lichSuKho.setNgayXuat(LocalDate.now());
        lichSuKho.setNgayXuat(lichSuKho.getNgayNhap());
        lichSuKho.setLoai("XUAT");
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(1);
        lichSuKho.setNhanVien(nhanVien);
        lichSuKho.setSoLuong(soLuong);
        lichSuKho.setGhiChu(ghiChu);

        ZonedDateTime vnNow = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime now = vnNow.toLocalDateTime();
        lichSuKho.setThoiGian(now);
        lichSuKhoRepository.save(lichSuKho);

        khoRepository.save(sp);
    }

    @Override
    public void updateQuantity(Integer id, Integer soLuong, String ghiChu) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        sp.setSoLuong(soLuong);

        LichSuKho lichSuKho = new LichSuKho();
        lichSuKho.setSanPhamChiTiet(sp);
        lichSuKho.setNgayNhap(LocalDate.now());
        lichSuKho.setNgayNhap(lichSuKho.getNgayNhap());
        lichSuKho.setNgayNhap(lichSuKho.getNgayNhap());
        lichSuKho.setLoai("CAP NHAT");
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(1);
        lichSuKho.setNhanVien(nhanVien);
        lichSuKho.setSoLuong(soLuong);
        lichSuKho.setGhiChu(ghiChu);

        ZonedDateTime vnNow = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime now = vnNow.toLocalDateTime();
        lichSuKho.setThoiGian(now);
        lichSuKhoRepository.save(lichSuKho);

        khoRepository.save(sp);
    }

    @Override
    public void deleteProductInSock(Integer id) {

        SanPhamChiTiet sp = khoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        sp.setTrangThai(0);
        sp.setSoLuong(0);
        khoRepository.save(sp);

        LichSuKho lichSuKho = new LichSuKho();
        lichSuKho.setSanPhamChiTiet(sp);
        lichSuKho.setNgayNhap(LocalDate.now());
        lichSuKho.setNgayNhap(lichSuKho.getNgayNhap());
        lichSuKho.setNgayNhap(lichSuKho.getNgayNhap());
        lichSuKho.setLoai("XOA");
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(1);
        lichSuKho.setNhanVien(nhanVien);
        lichSuKho.setSoLuong(sp.getSoLuong()    );
        lichSuKho.setGhiChu("Bạn đã xóa sản phẩm" +" "+ sp.getSanPham().getTenSanPham());

        ZonedDateTime vnNow = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime now = vnNow.toLocalDateTime();
        lichSuKho.setThoiGian(now);
        lichSuKhoRepository.save(lichSuKho);

    }

    @Override
    public List<ThuongHieu> findAllBrandInSock() {
        return thuongHieuRepository.findAll();
    }

    @Override
    public List<DanhMuc> findAllCategoryInSock() {
        return danhMucRepository.findAll();
    }

    @Override
    public Page<KhoDto> filterProductInSockPageable(String keyword, Integer thuongHieuId, Integer danhMucId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return khoRepository.filterWithPaging(keyword, thuongHieuId, danhMucId, pageable);
    }

    @Override
    public void addNewProduct(SanPham sanPham, SanPhamChiTiet sanPhamChiTiet) {

        try {
            SanPham savedProduct = sanPhamRepository.save(sanPham);

            sanPhamChiTiet.setSanPham(savedProduct);

            sanPhamChiTietRepository.save(sanPhamChiTiet);

            LichSuKho lichSuKho = new LichSuKho();
            lichSuKho.setSanPhamChiTiet(sanPhamChiTiet);
            lichSuKho.setLoai("THEM_MOI");
            lichSuKho.setSoLuong(sanPhamChiTiet.getSoLuong());
            lichSuKho.setThoiGian(LocalDateTime.now());
            lichSuKho.setGhiChu("Thêm sản phẩm mới vào kho");
            NhanVien nhanVien = new NhanVien();
            nhanVien.setId(1);
            lichSuKho.setNhanVien(nhanVien);
            lichSuKhoRepository.save(lichSuKho);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm sản phẩm mới: " + e.getMessage());
        }

    }

    @Override
    public void importFromExcel(MultipartFile file) {
        List<String> errors = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
                throw new IllegalArgumentException("File Excel không có dữ liệu hoặc không có sheet hợp lệ!");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null || headerRow.getPhysicalNumberOfCells() < 9) {
                throw new IllegalArgumentException("File Excel thiếu cột! Yêu cầu 9 cột: Tên Sản Phẩm, Màu Sắc, Kích Thước, Chất Liệu, Giá Nhập, Số Lượng, Thương Hiệu, Danh Mục, Hình Ảnh.");
            }

            String[] expectedHeaders = {
                    "Tên Sản Phẩm", "Màu Sắc", "Kích Thước", "Chất Liệu",
                    "Giá Nhập", "Số Lượng", "Thương Hiệu", "Danh Mục", "Hình Ảnh"
            };

            for (int i = 0; i < expectedHeaders.length; i++) {
                String header = getCellStringValue(headerRow.getCell(i));
                if (header == null || !header.trim().equalsIgnoreCase(expectedHeaders[i])) {
                    throw new IllegalArgumentException("Cột thứ " + (i + 1) + " phải là: " + expectedHeaders[i] + ". Đang thấy: '" + header + " '");
                }
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                List<String> rowErrors = new ArrayList<>();
                try {
                    String tenSp = getCellStringValue(row.getCell(0));
                    String mauSac = getCellStringValue(row.getCell(1));
                    String kichThuoc = getCellStringValue(row.getCell(2));
                    String chatLieu = getCellStringValue(row.getCell(3));
                    Double giaNhap = getCellNumericValue(row.getCell(4));
                    Double soLuongDouble = getCellNumericValue(row.getCell(5));
                    String tenThuongHieu = getCellStringValue(row.getCell(6));
                    String tenDanhMuc = getCellStringValue(row.getCell(7));
                    String hinhAnhUrl = getCellStringValue(row.getCell(8)); // URL hoặc tên file ảnh

                    if (tenSp == null || tenSp.trim().isEmpty()) rowErrors.add("Tên Sản Phẩm trống");
                    if (mauSac == null || mauSac.trim().isEmpty()) rowErrors.add("Màu Sắc trống");
                    if (kichThuoc == null || kichThuoc.trim().isEmpty()) rowErrors.add("Kích Thước trống");
                    if (chatLieu == null || chatLieu.trim().isEmpty()) rowErrors.add("Chất Liệu trống");
                    if (giaNhap == null || giaNhap <= 0) rowErrors.add("Giá Nhập không hợp lệ (phải > 0)");
                    if (soLuongDouble == null || soLuongDouble < 0 || soLuongDouble != Math.floor(soLuongDouble)) {
                        rowErrors.add("Số Lượng không hợp lệ (phải là số nguyên >= 0)");
                    }
                    if (tenThuongHieu == null || tenThuongHieu.trim().isEmpty()) rowErrors.add("Thương Hiệu trống");
                    if (tenDanhMuc == null || tenDanhMuc.trim().isEmpty()) rowErrors.add("Danh Mục trống");

                    if (!rowErrors.isEmpty()) {
                        errors.add("Dòng " + (row.getRowNum() + 1) + ": " + String.join(", ", rowErrors));
                        continue;
                    }

                    int soLuong = soLuongDouble.intValue();

                    String savedImageName = null;
                    if (hinhAnhUrl != null && !hinhAnhUrl.trim().isEmpty()) {
                        try {
                            savedImageName = downloadAndSaveImage(hinhAnhUrl.trim(), tenSp);
                        } catch (Exception e) {
                            System.out.println("Không thể tải ảnh cho sản phẩm: " + tenSp + ". Lỗi: " + e.getMessage());
                            savedImageName = null;
                        }
                    }

                    SanPhamChiTiet spct = khoRepository.findBySanPham_TenSanPhamAndMauSacAndKichThuoc(tenSp, mauSac, kichThuoc);

                    if (spct != null) {
                        if (spct.getTrangThai() == 0) spct.setTrangThai(1);
                        spct.setSoLuong(spct.getSoLuong() + soLuong);
                        spct.setChatLieu(chatLieu);

                        if (spct.getSanPham().getGiaNhap() == null ||
                                spct.getSanPham().getGiaNhap().compareTo(BigDecimal.valueOf(giaNhap)) != 0) {
                            spct.getSanPham().setGiaNhap(BigDecimal.valueOf(giaNhap));

                            if (savedImageName != null) {
                                spct.getSanPham().setHinhAnh(savedImageName);
                            }

                            sanPhamRepository.save(spct.getSanPham());
                        }

                        khoRepository.save(spct);

                    } else {
                        SanPham sanPham = sanPhamRepository.findByTenSanPham(tenSp);
                        if (sanPham == null) {
                            sanPham = new SanPham();
                            sanPham.setTenSanPham(tenSp);
                            sanPham.setGiaNhap(BigDecimal.valueOf(giaNhap));
                            sanPham.setNgayNhap(LocalDate.now());
                            sanPham.setTrangThai(1);
                            sanPham.setHinhAnh(savedImageName);

                            ThuongHieu thuongHieu = thuongHieuRepository.findByTenThuongHieu(tenThuongHieu);
                            if (thuongHieu == null) {
                                thuongHieu = new ThuongHieu();
                                thuongHieu.setTenThuongHieu(tenThuongHieu);
                                thuongHieu = thuongHieuRepository.save(thuongHieu);
                            }
                            sanPham.setThuongHieu(thuongHieu);

                            DanhMuc danhMuc = danhMucRepository.findByTenDanhMuc(tenDanhMuc);
                            if (danhMuc == null) {
                                danhMuc = new DanhMuc();
                                danhMuc.setTenDanhMuc(tenDanhMuc);
                                danhMuc = danhMucRepository.save(danhMuc);
                            }
                            sanPham.setDanhMuc(danhMuc);

                            sanPham = sanPhamRepository.save(sanPham);
                        } else {
                            if (savedImageName != null) {
                                sanPham.setHinhAnh(savedImageName);
                                sanPham = sanPhamRepository.save(sanPham);
                            }
                        }

                        SanPhamChiTiet newSpct = new SanPhamChiTiet();
                        newSpct.setSanPham(sanPham);
                        newSpct.setMauSac(mauSac);
                        newSpct.setKichThuoc(kichThuoc);
                        newSpct.setChatLieu(chatLieu);
                        newSpct.setSoLuong(soLuong);
                        newSpct.setTrangThai(1);
                        khoRepository.save(newSpct);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    errors.add("Dòng " + (row.getRowNum() + 1) + ": Lỗi xử lý - " + e.getMessage());
                }
            }

            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(String.join("; ", errors));
            }

        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc file Excel: " + e.getMessage(), e);
        }
    }
    private String downloadAndSaveImage(String imageUrl, String productName) throws IOException {
        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            return imageUrl;
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = getFileExtensionFromUrl(imageUrl);
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(uniqueFilename);

        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return uniqueFilename;
    }

    private String getFileExtensionFromUrl(String url) {
        try {
            String path = new URL(url).getPath();
            int lastDotIndex = path.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < path.length() - 1) {
                String extension = path.substring(lastDotIndex);
                if (extension.matches("\\.(jpg|jpeg|png|gif|bmp|webp)")) {
                    return extension;
                }
            }
        } catch (Exception e) {
        }
        return ".jpg"; // Default extension
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    return String.valueOf((int) cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getStringCellValue().trim();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Double getCellNumericValue(Cell cell) {
        if (cell == null) return null;

        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                    return Double.parseDouble(cell.getStringCellValue().trim());
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }




}
