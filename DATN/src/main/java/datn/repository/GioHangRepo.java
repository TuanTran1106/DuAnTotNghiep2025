package datn.repository;

import datn.entity.GioHang;
import datn.entity.NguoiDung;
import datn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepo extends JpaRepository<GioHang,Integer> {
    List<GioHang> findByNguoiDung_Id(int nguoiDungId);
    Optional<GioHang> findByNguoiDung_IdAndSanPhamChiTiet_Id(int idNguoiDung, int idSpct);
}