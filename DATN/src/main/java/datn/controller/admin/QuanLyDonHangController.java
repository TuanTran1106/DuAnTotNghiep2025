package datn.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly/don-hang")
public class QuanLyDonHangController {


    @GetMapping("")
    public String home () {

        return "admin/quan-ly-don-hang";
    }

}
