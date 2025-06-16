package datn.service.impl;

import datn.entity.NguoiDung;
import datn.repository.NguoiDungRepo;
import datn.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NguoiDungServiceImpl implements NguoiDungService {
    private final NguoiDungRepo nguoiDungRepo;

    @Override
    public NguoiDung dangNhap(String email, String matKhau) {
        NguoiDung nd = nguoiDungRepo.findByEmailAndMatKhau(email,matKhau);
        if (nd != null && nd.getMatKhau().equals(matKhau)) {
            return nd;
        }
        return null;
    }
}
