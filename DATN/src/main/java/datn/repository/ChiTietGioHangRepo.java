package datn.repository;

import datn.entity.ChiTietGioHang;
import datn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietGioHangRepo extends JpaRepository<ChiTietGioHang,Integer> {
    List<ChiTietGioHang> findByGioHang(GioHang gioHang);

    ChiTietGioHang findByGioHangIdAndSanPhamChiTietId(Integer gioHangId, Integer spctId);

    void deleteByGioHangIdAndSanPhamChiTietId(Integer gioHangId, Integer spctId);

    Optional<ChiTietGioHang> findByGioHang_IdAndSanPhamChiTiet_Id(int gioHangId, int spctId);

    List<ChiTietGioHang> findByGioHang_Id(int gioHangId);

    void deleteByGioHang_IdAndSanPhamChiTiet_Id(Integer gioHangId, Integer spctId);

    List<ChiTietGioHang> findByGioHang_NguoiDung_Id(Integer idNguoiDung);


}
