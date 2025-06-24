package datn.repository;

import datn.entity.DanhMuc;
import datn.entity.SanPham;
import datn.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    SanPham findByTenSanPham(String tenSanPham);

}
