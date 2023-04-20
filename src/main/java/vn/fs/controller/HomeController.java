package vn.fs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.fs.commom.CommomDataService;
import vn.fs.entities.FavoriteEntity;
import vn.fs.entities.ProductEntity;
import vn.fs.entities.UserEntity;
import vn.fs.model.dto.ProductDto;
import vn.fs.model.dto.UserDto;
import vn.fs.repository.FavoriteRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.service.IProductService;

/**
 * @author DongTHD
 *
 */
@Controller
public class HomeController extends CommomController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CommomDataService commomDataService;

	@Autowired
	FavoriteRepository favoriteRepository;

	@Autowired
	private IProductService productService;

	@GetMapping(value = "/")
	public String home(Model model, UserDto userDto) {

		commomDataService.commonData(model, userDto);
		bestSaleProduct20(model, userDto);
		return "web/home";
	}

	// list product ở trang chủ limit 10 sản phẩm mới nhất
	// Lấy ra được danh sách 10 sản phẩm mới nhất ở trang chủ
	@ModelAttribute("listProduct10")
	public List<ProductDto> listProduct10(Model model) {
		List<ProductDto> productList = productService.findListProductNewLimit();
		model.addAttribute("productList", productList);
		return productList;
	}

	// Top 20 best sale.
	// Top 20 Sản phẩm bán chạy nhất
	public void bestSaleProduct20(Model model, UserDto userDto) {
		List<ProductDto> listProductNew = productService.findTopProductBestSale(userDto);
		if (listProductNew != null) {
			model.addAttribute("bestSaleProduct20", listProductNew);
		}
	}
}
