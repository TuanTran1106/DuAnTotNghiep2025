package datn.repository;

import datn.dto.DonHangDto;
import datn.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    // ds hóa đơn
    @Query(value = "SELECT " +
            "dh.id, " +
            "sp.hinh_anh, " +
            "sp.ten_san_pham, " +
            "ctdh.don_gia, " +
            "ctdh.so_luong_dat, " +
            "dh.tong_gia, " +
            "ttdh.ten_trang_thai " +
            "FROM don_hang dh " +
            "JOIN nguoi_dung nd ON dh.id_nguoi_dung = nd.id " +
            "JOIN trang_thai_don_hang ttdh ON dh.id_trang_thai = ttdh.id " +
            "LEFT JOIN nhan_vien nv ON dh.id_nhan_vien = nv.id " +
            "JOIN chi_tiet_don_hang ctdh ON ctdh.id_don_hang = dh.id " +
            "JOIN san_pham_chi_tiet spct ON ctdh.id_san_pham_chi_tiet = spct.id " +
            "JOIN san_pham sp ON spct.id_san_pham = sp.id " +
            "ORDER BY dh.ngay_mua DESC",
            nativeQuery = true)
    List<DonHangDto> listOrder();


}
