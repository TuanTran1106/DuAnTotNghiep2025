package datn.service;

import datn.dto.KhoDto;
import datn.entity.DanhMuc;
import datn.entity.SanPhamChiTiet;
import datn.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface KhoService {

    // lất tổng sản phẩm
    Integer getTotalProduct();

    // tỏng sản phẩm tồn kho
    Integer getTotalProductInSock();

    BigDecimal getTotalPriceInSock();

    Integer getTotalProductIsOut();

    void enterProduct(Integer id, Integer soLuong);

    void exportProduct(Integer id, Integer soLuong);

    void updateQuantity(Integer id, Integer soLuong);

    List<ThuongHieu> findAllBrandInSock();

    List<DanhMuc> findAllCategoryInSock();

    Page<KhoDto> filterProductInSockPageable(String keyword, Integer thuongHieuId, Integer danhMucId, Integer trangThai,int page, int size);
}
