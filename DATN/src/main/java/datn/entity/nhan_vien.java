package datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "nhan_vien")
public class nhan_vien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ho_ten")
    private String hoten;
    @Column(name = " email")
    private String email;
    @Column(name = " sdt")
    private String sdt;
    @Column(name = " dia_chi")
    private String dia_chi;
    @Column(name = " gioi_tinh")
    private boolean gioi_tinh;

}
