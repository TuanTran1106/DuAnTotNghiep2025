package datn.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Khong duoc phep nhat chu cai!")
    private Integer soLuong;

    private BigDecimal giaBan;

    private BigDecimal giaNhap;

    private String tenThuongHieu;

    private String tenDanhMuc;

    private int trangThai;

}
