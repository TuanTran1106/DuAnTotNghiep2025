package datn.repository;

import datn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepo extends JpaRepository<GioHang,Integer> {

    // ✅ Trả về 1 bản ghi duy nhất (nếu có), an toàn và không lỗi
    Optional<GioHang> findFirstByNguoiDung_Id(Integer idNguoiDung);

    // ✅ Nếu muốn lấy tất cả giỏ hàng của 1 người dùng (ít dùng)
    List<GioHang> findByNguoiDung_Id(Integer nguoiDungId);

}