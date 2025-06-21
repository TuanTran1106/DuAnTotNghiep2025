//package datn.controller;
//
//import datn.entity.NguoiDung;
//import datn.repository.NguoiDungRepo;
//import jakarta.servlet.http.HttpSession;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@AllArgsConstructor
//public class LoginController {
//    private final NguoiDungRepo nguoiDungRepo;
//
//    @GetMapping("/login")
//    public String formLogin(@RequestParam(value = "error", required = false) String error, Model model) {
//        if (error != null) {
//            model.addAttribute("error", true);
//        }
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String xuLyLogin(@RequestParam String email,
//                            @RequestParam String matKhau,
//                            HttpSession session) {
//        NguoiDung nguoiDung = nguoiDungRepo.findByEmailAndMatKhau(email, matKhau);
//
//        if (nguoiDung != null) {
//            session.setAttribute("nguoiDung", nguoiDung);
//            return "redirect:/gio-hang"; // hoặc "/" nếu muốn về trang chủ
//        } else {
//            return "redirect:/login?error=true";
//        }
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/login";
//    }
//}
