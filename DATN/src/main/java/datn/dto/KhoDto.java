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

    @NotNull(message = "Tên không được để trống!")
    private String tenSanPham;

    @NotNull(message = "Màu sắc không được để trống!")
    private String mauSac;

    @NotNull(message = "Kích thước không được để trống!")
    private String kichThuoc;

    @NotNull(message = "Chất liệu không được để trống!")
    private String chatLieu;

    private Integer soLuong;

    private BigDecimal giaBan;

    private BigDecimal giaNhap;

    private String tenThuongHieu;

    private String tenDanhMuc;

    private int trangThai;

    private String hinhAnh;

}
