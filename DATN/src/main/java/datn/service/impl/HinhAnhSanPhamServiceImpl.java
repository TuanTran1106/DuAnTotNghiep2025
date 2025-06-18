package datn.service.impl;

import datn.entity.HinhAnhSanPham;
import datn.service.HinhAnhSanPhamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HinhAnhSanPhamServiceImpl implements HinhAnhSanPhamService {

    private final HinhAnhSanPhamService hinhAnhSanPhamService;

    @Override
    public List<HinhAnhSanPham> findAll() {
        return hinhAnhSanPhamService.findAll();
    }
}
