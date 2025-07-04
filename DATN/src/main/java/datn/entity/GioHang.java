package datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gio_hang")
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "ngay_them")
    private LocalDateTime ngayThem;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung", referencedColumnName = "id")
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "gioHang")
    private List<ChiTietGioHang> chiTietGioHangList;

}
