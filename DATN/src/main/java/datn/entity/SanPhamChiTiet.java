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

    @Column(name = "tenSanPham")
    private String tenSanPham;

    @Column(name = "mau_sac")
    private String mauSac;

    @Column(name = "kich_thuoc")
    private String kichThuoc;

    @Column(name = "chat_lieu")
    private String chatLieu;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @NotNull(message = "Vui lòng nhập chữ số!")
    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "gia_ban")
    private BigDecimal giaBan;

    @Column(name = "ngay_nhap")
    private LocalDate ngayNhap;

    @Column(name = "ngay_xuat")
    private LocalDate ngayXuat;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ngay_update")
    private LocalDate ngayUpdate;

    @ManyToOne
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    private SanPham sanPham;
}
