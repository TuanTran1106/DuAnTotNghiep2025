package datn.service;

import datn.dto.DonHangDto;
import datn.entity.DonHang;

import java.util.List;

public interface DonHangService {

    // ds hóa đơn
    List<DonHangDto> findAllOrder();

}
