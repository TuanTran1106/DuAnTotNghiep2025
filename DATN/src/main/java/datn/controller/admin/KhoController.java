package datn.controller.admin;

import datn.dto.KhoDto;
import datn.entity.SanPhamChiTiet;
import datn.service.HinhAnhSanPhamService;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DefaultFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/quan-ly/kho")
@AllArgsConstructor
public class KhoController {

    private final KhoService khoService;
    private final HinhAnhSanPhamService hinhAnhSanPhamService;

    @GetMapping("")
    public String home (Model model) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));


        // tổng sản phẩm
        model.addAttribute("totalProduct", khoService.getTotalProduct());

        // tổng sản phẩm tồn kho
        model.addAttribute("totalProductInSock", khoService.getTotalProductInSock());

        // toong giá trị ton trong kho
        BigDecimal totalPriceInSock = khoService.getTotalPriceInSock();
        String formattedPrice = currencyFormat.format(totalPriceInSock);
        model.addAttribute("totalPriceInSock", formattedPrice);

        // sản phẩm sắp hết
        model.addAttribute("totalProductIsOut", khoService.getTotalProductIsOut());

        // ds tồn kho
        model.addAttribute("listInSock", khoService.getListInSock());
        List<KhoDto> sock = khoService.getListInSock();
        model.addAttribute("giaBanFomat", currencyFormat.format(sock.get(0).getGiaBan()));
        // thương hiệu

        // danh mục

        return "admin/quan-ly-kho";

    }

    @PostMapping("/nhap")
    public String enterProduct(@RequestParam("id") Integer id,
                               @RequestParam("soLuong") Integer soLuong) {

        khoService.enterProduct(id, soLuong);
        return "redirect:/quan-ly/kho";
    }

    @PostMapping("/xuat")
    public String exportProduct(@RequestParam("id") Integer id,
                                @RequestParam("soLuong") Integer soLuong) {

        khoService.exportProduct(id, soLuong);
        return "redirect:/quan-ly/kho";
    }

    @PostMapping("/sua")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam("soLuong") Integer soLuong) {

        khoService.updateQuantity(id, soLuong);
        return "redirect:/quan-ly/kho";
    }




}
