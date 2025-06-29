package datn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddAddressRequest {
    @NotBlank(message = "Tên không được để trống")
    private String hoTen;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)(3|5|7|8|9)[0-9]{8}$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotBlank(message = "Thành phố không được để trống")
    private String thanhPho;

    @NotBlank(message = "Quận/Huyện không được để trống")
    private String quanHuyen;

    @NotBlank(message = "Phường/Xã không được để trống")
    private String phuongXa;

    private String diaChi;
    private Boolean macDinh;
} 