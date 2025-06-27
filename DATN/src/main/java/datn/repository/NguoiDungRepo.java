package datn.repository;

import datn.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepo extends JpaRepository<NguoiDung,Integer> {

    NguoiDung findByEmailAndMatKhau(String email,String matKhau);
}
