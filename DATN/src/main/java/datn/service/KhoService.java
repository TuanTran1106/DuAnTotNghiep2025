package datn.service;

import datn.dto.KhoDto;
import datn.entity.SanPhamChiTiet;

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

    List<KhoDto> getListInSock();

    void enterProduct(Integer id, Integer soLuong);

    void exportProduct(Integer id, Integer soLuong);

    void updateQuantity(Integer id, Integer soLuong);
}
