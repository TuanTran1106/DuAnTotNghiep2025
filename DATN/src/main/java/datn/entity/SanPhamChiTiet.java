package datn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Màu sắc không được để trống!")
    @Column(name = "mau_sac")
    private String mauSac;

    @NotNull(message = "Kích thước không được để trống!")
    @Column(name = "kich_thuoc")
    private String kichThuoc;

    @NotNull(message = "Chất liệu không được để trống!")
    @Column(name = "chat_lieu")
    private String chatLieu;

    @NotNull(message = "Số lượng không được để trống và phải là số!")
    @NotNull(message = "Vui lòng nhập chữ số!")
    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "gia_ban")
    private BigDecimal giaBan;

    @Column(name = "trang_thai")
    private int trangThai;


    @ManyToOne
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    private SanPham sanPham;
}
