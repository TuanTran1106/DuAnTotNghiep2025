package datn.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_kho")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LichSuKho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private nhan_vien nhanVien;

    private String loai;

    private Integer soLuong;

    private LocalDate ngayNhap;

    private LocalDate ngayXuat;

    private String ghiChu;

    private LocalDate ngayUpdate;

    private LocalDateTime thoiGian ;
}
