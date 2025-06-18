package datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhAnhSanPham extends JpaRepository<datn.entity.HinhAnhSanPham,Integer> {
}
