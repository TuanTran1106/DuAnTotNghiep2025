package datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chi_tiet_don_hang")
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "so_luong_dat")
    private int soLuongDat;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @ManyToOne
    @JoinColumn(name = "id_don_hang", referencedColumnName = "id")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet", referencedColumnName = "id")
    private SanPhamChiTiet sanPhamChiTiet;
}
