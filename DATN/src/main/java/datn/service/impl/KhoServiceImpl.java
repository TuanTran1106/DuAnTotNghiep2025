package datn.service.impl;

import datn.dto.KhoDto;
import datn.entity.SanPhamChiTiet;
import datn.repository.KhoRepository;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KhoServiceImpl implements KhoService {

    private final KhoRepository khoRepository;

    @Override
    public Integer getTotalProduct() {
        return khoRepository.totalProduct();
    }

    @Override
    public Integer getTotalProductInSock() {
        return khoRepository.totalProductInSock();
    }

    @Override
    public BigDecimal getTotalPriceInSock() {
        return khoRepository.totalPriceInSock();
    }

    @Override
    public Integer getTotalProductIsOut() {
        return khoRepository.totalProductIsOut();
    }

    @Override
    public List<KhoDto> getListInSock() {
        return khoRepository.getListStock();
    }

    @Override
    @Transactional
    public void enterProduct(Integer id, Integer soLuong) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        sp.setSoLuong(sp.getSoLuong() + soLuong);
        khoRepository.save(sp);
    }

    @Override
    @Transactional
    public void exportProduct(Integer id, Integer soLuong) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        int newQuantity = sp.getSoLuong() - soLuong;
        sp.setSoLuong(newQuantity);
        khoRepository.save(sp);
    }

    @Override
    public void updateQuantity(Integer id, Integer soLuong) {

        SanPhamChiTiet sp = khoRepository.findById(id).orElseThrow();
        sp.setSoLuong(soLuong);
        khoRepository.save(sp);
    }


}
