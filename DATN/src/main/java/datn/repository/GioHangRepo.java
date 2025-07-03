package datn.repository;

import datn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepo extends JpaRepository<GioHang,Integer> {

    Optional<GioHang> findFirstByNguoiDung_Id(Integer idNguoiDung);

    @Query("SELECT g FROM GioHang g WHERE g.nguoiDung.id = :id ORDER BY g.ngayThem DESC")
    List<GioHang> findByNguoiDung_Id(@Param("id") Integer id);


}