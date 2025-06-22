package datn.repository;

import datn.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {
    ThuongHieu findByTenThuongHieu(String tenThuongHieu);
}
