package datn.repository;

import datn.entity.LichSuKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuKhoRepository extends JpaRepository<LichSuKho, Integer> {
}
