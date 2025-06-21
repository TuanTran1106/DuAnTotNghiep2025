package datn.service.impl;

import datn.entity.LichSuKho;
import datn.repository.LichSuKhoRepository;
import datn.service.LichSuKhoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LichSuKhoServiceImpl implements LichSuKhoService {

    private final LichSuKhoRepository lichSuKhoRepository;

    @Override
    public List<LichSuKho> findAllHistoryInSock() {
        return lichSuKhoRepository.findAll();
    }
}
