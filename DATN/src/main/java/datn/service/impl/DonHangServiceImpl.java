package datn.service.impl;

import datn.dto.DonHangDto;
import datn.repository.DonHangRepository;
import datn.service.DonHangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DonHangServiceImpl implements DonHangService {

    private final DonHangRepository donHangRepository;

    @Override
    public List<DonHangDto> findAllOrder() {

        return donHangRepository.listOrder();

    }

}
