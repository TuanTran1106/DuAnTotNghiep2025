package datn.service.impl;

import datn.dto.KhoDto;
import datn.entity.*;
import datn.repository.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KhoServiceImpl implements KhoService {

    @Autowired
    private final KhoRepository khoRepository;
    @Autowired
    private final ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private final DanhMucRepository danhMucRepository;
    @Autowired
    private final LichSuKhoRepository lichSuKhoRepository;
    @Autowired
    private final SanPhamRepository sanPhamRepository;

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
    public List<ThuongHieu> findAllBrandInSock() {
        return thuongHieuRepository.findAll();
    }

    @Override
    public List<DanhMuc> findAllCategoryInSock() {
        return danhMucRepository.findAll();
    }

    @Override
    public Page<KhoDto> filterProductInSockPageable(String keyword, Integer thuongHieuId, Integer danhMucId, Integer trangThai, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return khoRepository.filterWithPaging(keyword,thuongHieuId,danhMucId,trangThai,pageable);

    }

    @Override
    public void importFromExcel(MultipartFile file) {

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                try {
                    String tenSp = getCellStringValue(row.getCell(0));
                    String mauSac = getCellStringValue(row.getCell(1));
                    String kichThuoc = getCellStringValue(row.getCell(2));
                    String chatLieu = getCellStringValue(row.getCell(3));

                    if (tenSp == null || tenSp.trim().isEmpty()) {
                        System.out.println("Bỏ qua dòng " + (row.getRowNum() + 1) + ": Tên sản phẩm không được để trống");
                        continue;
                    }

                    double giaBan = getCellNumericValue(row.getCell(4));
                    int soLuong = (int) getCellNumericValue(row.getCell(5));
                    String tenThuongHieu = getCellStringValue(row.getCell(6));
                    String tenDanhMuc = getCellStringValue(row.getCell(7));

                    if (giaBan <= 0) {
                        System.out.println("Bỏ qua dòng " + (row.getRowNum() + 1) + ": Giá bán phải lớn hơn 0");
                        continue;
                    }

                    if (soLuong < 0) {
                        System.out.println("Bỏ qua dòng " + (row.getRowNum() + 1) + ": Số lượng không được âm");
                        continue;
                    }

                    SanPhamChiTiet sp = khoRepository.findBySanPham_TenSanPhamAndMauSacAndKichThuoc(tenSp, mauSac, kichThuoc);

                    if (sp != null) {
                        sp.setSoLuong(sp.getSoLuong() + soLuong);
                        if (chatLieu != null && !chatLieu.trim().isEmpty()) {
                            sp.setChatLieu(chatLieu);
                        }
                        sp.getSanPham().setGiaNhap(BigDecimal.valueOf(giaBan));
                        sanPhamRepository.save(sp.getSanPham());
                        khoRepository.save(sp);
                        System.out.println("Đã cập nhật sản phẩm: " + tenSp + ", " + mauSac + ", " + kichThuoc);
                    } else {
                        SanPham sanPham = sanPhamRepository.findByTenSanPham(tenSp);
                        if (sanPham == null) {
                            sanPham = new SanPham();
                            sanPham.setTenSanPham(tenSp);
                            sanPham.setGiaNhap(BigDecimal.valueOf(giaBan));
                            sanPham.setNgayNhap(LocalDate.now());
                            sanPham.setTrangThai(true);

                            if (tenThuongHieu != null && !tenThuongHieu.trim().isEmpty()) {
                                ThuongHieu thuongHieu = thuongHieuRepository.findByTenThuongHieu(tenThuongHieu);
                                if (thuongHieu == null) {
                                    thuongHieu = new ThuongHieu();
                                    thuongHieu.setTenThuongHieu(tenThuongHieu);
                                    thuongHieu = thuongHieuRepository.save(thuongHieu);
                                }
                                sanPham.setThuongHieu(thuongHieu);
                            }

                            // Tìm hoặc tạo danh mục
                            if (tenDanhMuc != null && !tenDanhMuc.trim().isEmpty()) {
                                DanhMuc danhMuc = danhMucRepository.findByTenDanhMuc(tenDanhMuc);
                                if (danhMuc == null) {
                                    danhMuc = new DanhMuc();
                                    danhMuc.setTenDanhMuc(tenDanhMuc);
                                    danhMuc = danhMucRepository.save(danhMuc);
                                }
                                sanPham.setDanhMuc(danhMuc);
                            }

                            sanPham = sanPhamRepository.save(sanPham);
                        }

                        SanPhamChiTiet newSpChiTiet = new SanPhamChiTiet();
                        newSpChiTiet.setSanPham(sanPham);
                        newSpChiTiet.setMauSac(mauSac);
                        newSpChiTiet.setKichThuoc(kichThuoc);
                        newSpChiTiet.setChatLieu(chatLieu);
                        newSpChiTiet.setSoLuong(soLuong);
                        sanPham.setGiaNhap(BigDecimal.valueOf(giaBan));

                        khoRepository.save(newSpChiTiet);
                    }

                } catch (Exception e) {
                    System.out.println("Lỗi xử lý dòng " + (row.getRowNum() + 1) + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc file Excel: " + e.getMessage(), e);
        }
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;

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
    }

    private double getCellNumericValue(Cell cell) {
        if (cell == null) return 0;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0;
                }
            default:
                return 0;
        }
    }


}
