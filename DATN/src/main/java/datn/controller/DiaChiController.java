package datn.controller;

import com.example.demo.dto.AddAddressRequest;
import com.example.demo.dto.EditAddressRequest;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.NguoiDung;
import com.example.demo.repo.DiaChiRepository;
import com.example.demo.repo.NguoiDungRepository;
import datn.dto.AddAddressRequest;
import datn.entity.DiaChiNguoiDung;
import datn.entity.NguoiDung;
import datn.repository.DiaChiRepository;
import datn.repository.NguoiDungRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dia-chi")
public class DiaChiController {

    @Autowired
    private datn.repository.DiaChiRepository diaChiRepository;

    @Autowired
    private NguoiDungRepo nguoiDungRepository;

    @GetMapping("")
    public String getDiaChiList(Model model) {
        List<DiaChiNguoiDung> diaChiList = diaChiRepository.findAll();
        model.addAttribute("diaChiList", diaChiList);
        model.addAttribute("newAddressRequest", new datn.dto.AddAddressRequest());
        model.addAttribute("nguoiDungList", nguoiDungRepository.findAll());
        return "dia-chi/list";
    }

    @PostMapping("/save-new")
    @Transactional
    public String saveNewUserAndAddress(@Valid @ModelAttribute("newAddressRequest") datn.dto.AddAddressRequest request,
                                      BindingResult bindingResult,
                                      RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder("Vui lòng kiểm tra lại thông tin:\n");
            bindingResult.getAllErrors().forEach(error -> 
                errorMsg.append("- ").append(error.getDefaultMessage()).append("\n")
            );
            ra.addFlashAttribute("errorMessage", errorMsg.toString());
            return "redirect:/dia-chi";
        }

        datn.entity.NguoiDung newUser = new datn.entity.NguoiDung();
        newUser.setHoTen(request.getHoTen());
        newUser.setSdt(request.getSdt());
        NguoiDungRepo savedUser = nguoiDungRepository.save(newUser);

        DiaChiNguoiDung diaChi = new DiaChiNguoiDung();
        diaChi.setIdNguoiDung(savedUser.getId());
        diaChi.setDiaChi(request.getDiaChi());
        diaChi.setThanhPho(request.getThanhPho());
        diaChi.setQuanHuyen(request.getQuanHuyen());
        diaChi.setPhuongXa(request.getPhuongXa());
        diaChi.setNgayTao(LocalDateTime.now());
        
        if (request.getMacDinh() == null) {
            diaChi.setMacDinh(false);
        } else {
            diaChi.setMacDinh(request.getMacDinh());
        }

        if (Boolean.TRUE.equals(diaChi.getMacDinh())) {
            diaChiRepository.resetMacDinh(savedUser.getId());
        }

        diaChiRepository.save(diaChi);
        ra.addFlashAttribute("successMessage", "Thêm người dùng và địa chỉ mới thành công!");
        return "redirect:/dia-chi";
    }

    @PostMapping("/update")
    @Transactional
    public String updateAddressAndUser(@Valid @ModelAttribute EditAddressRequest request, 
                                     BindingResult bindingResult,
                                     RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder("Vui lòng kiểm tra lại thông tin:\n");
            bindingResult.getAllErrors().forEach(error -> 
                errorMsg.append("- ").append(error.getDefaultMessage()).append("\n")
            );
            ra.addFlashAttribute("errorMessage", errorMsg.toString());
            return "redirect:/dia-chi";
        }

        Optional<DiaChi> diaChiOpt = diaChiRepository.findById(request.getDiaChiId());
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(request.getNguoiDungId());

        if (diaChiOpt.isPresent() && nguoiDungOpt.isPresent()) {
            NguoiDung nguoiDung = nguoiDungOpt.get();
            nguoiDung.setHoTen(request.getHoTen());
            nguoiDung.setSdt(request.getSdt());
            nguoiDungRepository.save(nguoiDung);

            DiaChi diaChi = diaChiOpt.get();
            if (request.getMacDinh() == null) {
                diaChi.setMacDinh(false);
            } else {
                diaChi.setMacDinh(request.getMacDinh());
            }

            if (Boolean.TRUE.equals(diaChi.getMacDinh())) {
                diaChiRepository.resetMacDinh(nguoiDung.getId());
            }

            diaChi.setDiaChi(request.getDiaChi());
            diaChi.setThanhPho(request.getThanhPho());
            diaChi.setQuanHuyen(request.getQuanHuyen());
            diaChi.setPhuongXa(request.getPhuongXa());
            diaChiRepository.save(diaChi);

            ra.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");
        } else {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy dữ liệu để cập nhật.");
        }
        return "redirect:/dia-chi";
    }

    @PostMapping("/delete/{id}")
    public String deleteDiaChi(@PathVariable Integer id, RedirectAttributes ra) {
        Optional<DiaChi> diaChiOpt = diaChiRepository.findById(id);

        if (diaChiOpt.isPresent()) {
            diaChiRepository.deleteById(id);
            ra.addFlashAttribute("successMessage", "Xóa địa chỉ thành công!");
        } else {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy địa chỉ để xóa.");
        }
        return "redirect:/dia-chi";
    }

    @GetMapping("/api/details/{id}")
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<EditAddressRequest> getDiaChiDetails(@PathVariable Integer id) {
        Optional<DiaChi> diaChiOpt = diaChiRepository.findById(id);

        if (diaChiOpt.isPresent()) {
            DiaChi diaChi = diaChiOpt.get();
            NguoiDung nguoiDung = diaChi.getNguoiDung();

            EditAddressRequest dto = new EditAddressRequest();
            dto.setDiaChiId(diaChi.getId());
            dto.setDiaChi(diaChi.getDiaChi());
            dto.setThanhPho(diaChi.getThanhPho());
            dto.setQuanHuyen(diaChi.getQuanHuyen());
            dto.setPhuongXa(diaChi.getPhuongXa());
            dto.setMacDinh(diaChi.getMacDinh());

            if (nguoiDung != null) {
                dto.setNguoiDungId(nguoiDung.getId());
                dto.setHoTen(nguoiDung.getHoTen());
                dto.setSdt(nguoiDung.getSdt());
            }
            
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, RedirectAttributes ra) {
        String errorMessage = "Dữ liệu nhập không hợp lệ. Vui lòng kiểm tra lại.";
        ra.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/dia-chi";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("errorMessage", "Lỗi cơ sở dữ liệu! Có thể ID Người Dùng bạn nhập không tồn tại. Vui lòng thử lại.");
        return "redirect:/dia-chi";
    }
} 