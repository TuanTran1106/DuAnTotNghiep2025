package datn.service.impl;

import datn.dto.KhoDto;
import datn.entity.*;
import datn.repository.DanhMucRepository;
import datn.repository.KhoRepository;
import datn.repository.LichSuKhoRepository;
import datn.repository.ThuongHieuRepository;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

@Service
@AllArgsConstructor
public class KhoServiceImpl implements KhoService {

    private final KhoRepository khoRepository;

    private final ThuongHieuRepository thuongHieuRepository;

    private final DanhMucRepository danhMucRepository;

    private final LichSuKhoRepository lichSuKhoRepository;

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

                    String tenSp = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null;
                    String mauSac = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null;
                    String kichThuoc = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null;
                    String chatLieu = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null;
                    double giaBan = row.getCell(4) != null ? row.getCell(4).getNumericCellValue() : 0;
                    int soLuong = row.getCell(5) != null ? (int) row.getCell(5).getNumericCellValue() : 0;
                    String tenThuongHieu = row.getCell(6) != null ? row.getCell(6).getStringCellValue() : null;
                    String tenDanhMuc = row.getCell(7) != null ? row.getCell(7).getStringCellValue() : null;

                    SanPhamChiTiet sp = khoRepository.findBySanPham_TenSanPhamAndMauSacAndKichThuoc(tenSp, mauSac, kichThuoc);
                    if (sp != null) {
                        sp.setSoLuong(sp.getSoLuong() + soLuong);
                        sp.setChatLieu(chatLieu);
                        sp.setGiaBan(BigDecimal.valueOf(giaBan));
                        khoRepository.save(sp);
                    } else {
                        System.out.println("Không tìm thấy sản phẩm: " + tenSp + ", " + mauSac + ", " + kichThuoc);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Lỗi không triển khai được Excel!", e);
            }
    }


}
