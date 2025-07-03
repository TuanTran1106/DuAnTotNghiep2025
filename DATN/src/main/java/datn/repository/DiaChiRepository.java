package datn.repository;

import com.example.demo.entity.DiaChi;
import datn.entity.DiaChiNguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChiNguoiDung, Integer> {
    @Query("SELECT d FROM DiaChi d WHERE d.idNguoiDung = :idNguoiDung ORDER BY d.macDinh DESC, d.ngayTao DESC")
    List<DiaChiNguoiDung> findByIdNguoiDungOrderByMacDinhDesc(@Param("idNguoiDung") Integer idNguoiDung);

    @Modifying
    @Transactional
    @Query("UPDATE DiaChi d SET d.macDinh = false WHERE d.idNguoiDung = :idNguoiDung")
    void resetMacDinh(@Param("idNguoiDung") Integer idNguoiDung);
} 