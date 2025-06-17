package datn.service.impl;

import datn.dto.KhoDto;
import datn.repository.KhoRepository;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
}
