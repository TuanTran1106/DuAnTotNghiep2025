package datn.controller.admin;

import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.DefaultFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/quan-ly/kho")
@AllArgsConstructor
public class KhoController {

    private final KhoService khoService;

    @GetMapping("")
    public String home (Model model) {

        // tổng sản phẩm
        model.addAttribute("totalProduct", khoService.getTotalProduct());

        // tổng sản phẩm tồn kho
        model.addAttribute("totalProductInSock", khoService.getTotalProductInSock());

        // toong giá trị ton trong kho
        BigDecimal totalPriceInSock = khoService.getTotalPriceInSock();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(totalPriceInSock);
        model.addAttribute("totalPriceInSock", formattedPrice);

        // sản phẩm sắp hết
        model.addAttribute("totalProductIsOut", khoService.getTotalProductIsOut());

        // ds tồn kho
        model.addAttribute("listInSock", khoService.getListInSock());
        return "admin/quan-ly-kho";

    }

}
