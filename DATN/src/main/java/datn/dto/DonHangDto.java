package datn.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonHangDto {

    private Integer id;

    private String hinhAnh;

    private String tenSanPham;

    private BigDecimal donGia;

    private int soLuongDat;

    private BigDecimal tongGia;

    private String trangThai;

}
