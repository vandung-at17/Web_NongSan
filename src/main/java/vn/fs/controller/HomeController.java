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
import vn.fs.entities.Favorite;
import vn.fs.entities.ProductEntity;
import vn.fs.entities.UserEntity;
import vn.fs.repository.FavoriteRepository;
import vn.fs.repository.ProductRepository;

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

	@GetMapping(value = "/")
	public String home(Model model, UserEntity user) {

		commomDataService.commonData(model, user);
		bestSaleProduct20(model, user);
		return "web/home";
	}
	
	// list product ở trang chủ limit 10 sản phẩm mới nhất
	@ModelAttribute("listProduct10")
	public List<ProductEntity> listproduct10(Model model) {
		List<ProductEntity> productList = productRepository.listProductNew20();
		model.addAttribute("productList", productList);
		return productList;
	}
	
	// Top 20 best sale.
	public void bestSaleProduct20(Model model, UserEntity customer) {
		List<Object[]> productList = productRepository.bestSaleProduct20();
		if (productList != null) {
			ArrayList<Integer> listIdProductArrayList = new ArrayList<>();
			for (int i = 0; i < productList.size(); i++) {
				String id = String.valueOf(productList.get(i)[0]);
				listIdProductArrayList.add(Integer.valueOf(id));
			}
			List<ProductEntity> listProducts = productRepository.findByInventoryIds(listIdProductArrayList);

			List<ProductEntity> listProductNew = new ArrayList<>();

			for (ProductEntity product : listProducts) {

				ProductEntity productEntity = new ProductEntity();

				BeanUtils.copyProperties(product, productEntity);

				Favorite save = favoriteRepository.selectSaves(productEntity.getProductId(), customer.getUserId());

				if (save != null) {
					productEntity.favorite = true;
				} else {
					productEntity.favorite = false;
				}
				listProductNew.add(productEntity);

			}

			model.addAttribute("bestSaleProduct20", listProductNew);
		}
	}


}
