package datn.controller.admin;


import datn.entity.DonHang;
import datn.service.DonHangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly/don-hang")
@AllArgsConstructor
public class QuanLyDonHangController {

    private final DonHangService donHangService;

    @GetMapping("")
    public String home (Model model) {

        //ds hóa đơn
        model.addAttribute("orders", donHangService.findAllOrder());

        return "admin/quan-ly-don-hang";
    }

}
