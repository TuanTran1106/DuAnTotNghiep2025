package datn.repository;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import datn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietGioHangRepo extends JpaRepository<ChiTietGioHang,Integer> {
    List<ChiTietGioHang> findByGioHang(GioHang gioHang);
    Optional<ChiTietGioHang> findByGioHangAndSanPhamChiTiet(GioHang gioHang, SanPhamChiTiet spct);
}
