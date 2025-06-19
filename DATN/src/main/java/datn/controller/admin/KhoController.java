package datn.controller.admin;

import datn.dto.KhoDto;
import datn.service.KhoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/quan-ly/kho")
@AllArgsConstructor
public class KhoController {

    private final KhoService khoService;


    @GetMapping("")
    public String home (@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "2") int size,
                        @RequestParam(value = "keyword", required = false) String keyword,
                        @RequestParam(value = "thuongHieu", required = false) Integer thuongHieuId,
                        @RequestParam(value = "danhMuc", required = false) Integer danhMucId,
                        @RequestParam(value = "status", required = false) Integer trangThai,
                        Model model) {

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
        Page<KhoDto> khoPage = khoService.filterProductInSockPageable(keyword, thuongHieuId, danhMucId, trangThai, page, size);

        model.addAttribute("listInSock", khoPage.getContent());
        model.addAttribute("startPage", page);
        model.addAttribute("totalPage", khoPage.getTotalPages());

        // loọc
        model.addAttribute("keyword", keyword);
        model.addAttribute("thuongHieu", thuongHieuId);
        model.addAttribute("danhMuc", danhMucId);
        model.addAttribute("status", trangThai);


        // thương hiệu
        model.addAttribute("listCategory", khoService.findAllCategoryInSock());

        // danh mục
        model.addAttribute("listBrand", khoService.findAllBrandInSock());


        return "admin/quan-ly-kho";

    }

    @PostMapping("/nhap")
    public String enterProduct(@RequestParam("id") Integer id,
                               @RequestParam("soLuong") Integer soLuong,
                               @RequestParam("ghiChuNhap") String ghiChu) {

        khoService.enterProduct(id, soLuong, ghiChu);

        return "redirect:/quan-ly/kho";
    }

    @PostMapping("/xuat")
    public String exportProduct(@RequestParam("id") Integer id,
                                @RequestParam("soLuong") Integer soLuong,
                                @RequestParam("ghiChuXuat") String ghiChu) {

        khoService.exportProduct(id, soLuong, ghiChu);

        return "redirect:/quan-ly/kho";
    }

    @PostMapping("/sua")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam("soLuong") Integer soLuong,
                         @RequestParam("ghiChuUpdate") String ghiChu) {

        khoService.updateQuantity(id, soLuong, ghiChu);

        return "redirect:/quan-ly/kho";
    }





}
