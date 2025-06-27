package datn.repository;

import datn.entity.DonHang;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DonHang dh WHERE dh.nhanVien.id = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
    List<datn.entity.DonHang> findByNhanVienId(Integer id);
}
