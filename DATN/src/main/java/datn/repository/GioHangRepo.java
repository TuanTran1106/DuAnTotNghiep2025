package datn.repository;

import datn.entity.GioHang;
import datn.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepo extends JpaRepository<GioHang,Integer> {
    Optional<GioHang> findByNguoiDung(NguoiDung nguoiDung);
}