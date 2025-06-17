package datn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KhoDto {

    private Integer id;

    private String tenSanPham;

    private String mauSac;

    private String kichThuoc;

    private String chatLieu;

    private Integer soLuong;

    private BigDecimal giaBan;

    private String tenThuongHieu;

    private String tenDanhMuc;
}
