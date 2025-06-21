package datn.service.impl;

import datn.dto.KhoDto;
import datn.entity.DanhMuc;
import datn.entity.SanPham;
import datn.entity.SanPhamChiTiet;
import datn.entity.ThuongHieu;
import datn.repository.DanhMucRepository;
import datn.repository.KhoRepository;
import datn.repository.ThuongHieuRepository;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KhoServiceImpl implements KhoService {

    private final KhoRepository khoRepository;

    private final ThuongHieuRepository thuongHieuRepository;

    private final DanhMucRepository danhMucRepository;

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

        sp.setGhiChu(ghiChu);
        LocalDate today = LocalDate.now();
        sp.setNgayNhap(today);

        khoRepository.save(sp);
    }


    @Override
    @Transactional
    public void exportProduct(Integer id, Integer soLuong, String ghiChu) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        sp.setSoLuong(sp.getSoLuong() - soLuong);

        sp.setGhiChu(ghiChu);
        LocalDate today = LocalDate.now();
        sp.setNgayXuat(today);
        sp.setNgayNhap(sp.getNgayNhap());
        khoRepository.save(sp);
    }


    @Override
    public void updateQuantity(Integer id, Integer soLuong, String ghiChu) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        sp.setSoLuong(soLuong);

        sp.setGhiChu(ghiChu);
        LocalDate today = LocalDate.now();
        sp.setNgayXuat(sp.getNgayXuat());
        sp.setNgayNhap(sp.getNgayNhap());
        sp.setNgayUpdate(today);

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


}
