package datn.repository;

import datn.dto.KhoDto;
import datn.entity.NguoiDung;
import datn.entity.SanPham;
import datn.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhoRepository extends JpaRepository<SanPhamChiTiet,Integer> {

    // tng sản phẩm
    @Query(value = "SELECT COUNT(*) FROM san_pham", nativeQuery = true)
    Integer totalProduct();

    // ổng sản phẩm tồn kho
    @Query(value = "SELECT SUM(so_luong)  FROM san_pham_chi_tiet", nativeQuery = true)
    Integer totalProductInSock();

    // tổng giá trị tồn trong kho
    @Query(value = "SELECT SUM(gia_nhap*so_luong) FROM san_pham JOIN san_pham_chi_tiet on san_pham.id = san_pham_chi_tiet.id" , nativeQuery = true)
    BigDecimal totalPriceInSock();

    // sắp hét hàng
    @Query(value = "SELECT COUNT(so_luong) FROM san_pham_chi_tiet WHERE so_luong <=3" , nativeQuery = true)
    Integer totalProductIsOut();

    // ds kho
        @Query(value = """
        SELECT 
            ct.id AS id,
            sp.ten_san_pham AS tenSanPham,
            ct.mau_sac AS mauSac,
            ct.kich_thuoc AS kichThuoc,
            ct.chat_lieu AS chatLieu,
            ct.so_luong AS soLuong,
            ct.gia_ban AS giaBan,
            sp.gia_nhap AS giaNhap,
            th.ten_thuong_hieu AS tenThuongHieu,
            dm.ten_danh_muc AS tenDanhMuc
        FROM san_pham_chi_tiet ct
        JOIN san_pham sp ON ct.id_san_pham = sp.id
        LEFT JOIN thuong_hieu th ON sp.id_thuong_hieu = th.id
        LEFT JOIN danh_muc dm ON sp.id_danh_muc = dm.id
        WHERE (:keyword IS NULL OR sp.ten_san_pham LIKE %:keyword%)
          AND (:thuongHieuId IS NULL OR th.id = :thuongHieuId)
          AND (:danhMucId IS NULL OR dm.id = :danhMucId)
          AND (:trangThai IS NULL OR sp.trang_thai = :trangThai)
        """,
                countQuery = """
        SELECT COUNT(*)
        FROM san_pham_chi_tiet ct
        JOIN san_pham sp ON ct.id_san_pham = sp.id
        LEFT JOIN thuong_hieu th ON sp.id_thuong_hieu = th.id
        LEFT JOIN danh_muc dm ON sp.id_danh_muc = dm.id
        WHERE (:keyword IS NULL OR sp.ten_san_pham LIKE %:keyword%)
          AND (:thuongHieuId IS NULL OR th.id = :thuongHieuId)
          AND (:danhMucId IS NULL OR dm.id = :danhMucId)
          AND (:trangThai IS NULL OR sp.trang_thai = :trangThai)
        """,
                nativeQuery = true)
        Page<KhoDto> filterWithPaging(
                @Param("keyword") String keyword,
                @Param("thuongHieuId") Integer thuongHieuId,
                @Param("danhMucId") Integer danhMucId,
                @Param("trangThai") Integer trangThai,
                Pageable pageable
        );

    @Query("SELECT sp FROM SanPhamChiTiet sp WHERE sp.sanPham.tenSanPham = :tenSanPham AND sp.mauSac = :mauSac AND sp.kichThuoc = :kichThuoc")
    SanPhamChiTiet findBySanPham_TenSanPhamAndMauSacAndKichThuoc(@Param("tenSanPham") String tenSanPham, @Param("mauSac") String mauSac, @Param("kichThuoc") String kichThuoc);}

