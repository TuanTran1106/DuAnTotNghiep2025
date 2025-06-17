package datn.service;

import datn.dto.KhoDto;

import java.math.BigDecimal;
import java.util.List;

public interface KhoService {

    // lất tổng sản phẩm
    Integer getTotalProduct();

    // tỏng sản phẩm tồn kho
    Integer getTotalProductInSock();

    BigDecimal getTotalPriceInSock();

    Integer getTotalProductIsOut();

    List<KhoDto> getListInSock();

}
