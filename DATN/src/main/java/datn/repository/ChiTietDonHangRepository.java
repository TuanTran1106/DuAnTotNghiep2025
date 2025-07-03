package datn.repository;


import datn.entity.ChiTietDonHang;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ChiTietDonHang c WHERE c.donHang.id IN :ids")
    void deleteByDonHangIds(@Param("ids") List<Integer> ids);
}
