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

    private String tenThuongHieu;

    private String tenDanhMuc;

    public KhoDto(Integer id, String tenSanPham, String tenThuongHieu, String tenDanhMuc, Integer soLuong, String mauSac, String kichThuoc, BigDecimal giaBan) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.tenThuongHieu = tenThuongHieu;
        this.tenDanhMuc = tenDanhMuc;
        this.soLuong = soLuong;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.giaBan = giaBan;
    }
}
