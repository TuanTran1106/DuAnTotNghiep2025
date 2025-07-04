package datn.repository;

import datn.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    DanhMuc findByTenDanhMuc(String tenDanhMuc);
}
