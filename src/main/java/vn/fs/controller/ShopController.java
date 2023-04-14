package vn.fs.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.fs.commom.CommomDataService;
import vn.fs.entities.Favorite;
import vn.fs.entities.ProductEntity;
import vn.fs.entities.User;
import vn.fs.repository.FavoriteRepository;
import vn.fs.repository.ProductRepository;

/**
 * @author DongTHD
 *
 */
@Controller
public class ShopController extends CommomController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	FavoriteRepository favoriteRepository;
	
	@Autowired
	CommomDataService commomDataService;

	@GetMapping(value = "/products")
	public String shop(Model model, Pageable pageable, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, User user) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(12);

		Page<ProductEntity> productPage = findPaginated(PageRequest.of(currentPage - 1, pageSize));

		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		commomDataService.commonData(model, user);
		model.addAttribute("products", productPage);

		return "web/shop";
	}

	public Page<ProductEntity> findPaginated(Pageable pageable) {

		List<ProductEntity> productPage = productRepository.findAll();

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<ProductEntity> list;

		if (productPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, productPage.size());
			list = productPage.subList(startItem, toIndex);
		}

		Page<ProductEntity> productPages = new PageImpl<ProductEntity>(list, PageRequest.of(currentPage, pageSize),productPage.size());

		return productPages;
	}
	
	// search product
	@GetMapping(value = "/searchProduct")
	public String showsearch(Model model, Pageable pageable, @RequestParam("keyword") String keyword,
			@RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page,
			User user) {
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(12);

		Page<ProductEntity> productPage = findPaginatSearch(PageRequest.of(currentPage - 1, pageSize), keyword);

		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		commomDataService.commonData(model, user);
		model.addAttribute("products", productPage);
		return "web/shop";
	}
	
	// search product
	public Page<ProductEntity> findPaginatSearch(Pageable pageable, @RequestParam("keyword") String keyword) {

		List<ProductEntity> productPage = productRepository.searchProduct(keyword);

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<ProductEntity> list;

		if (productPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, productPage.size());
			list = productPage.subList(startItem, toIndex);
		}

		Page<ProductEntity> productPages = new PageImpl<ProductEntity>(list, PageRequest.of(currentPage, pageSize),productPage.size());

		return productPages;
	}
	
	// list books by category
	@GetMapping(value = "/productByCategory")
	public String listProductbyid(Model model, @RequestParam("id") Long id, User user) {
		List<ProductEntity> products = productRepository.listProductByCategory(id);

		List<ProductEntity> listProductNew = new ArrayList<>();

		for (ProductEntity product : products) {

			ProductEntity productEntity = new ProductEntity();

			BeanUtils.copyProperties(product, productEntity);

			Favorite save = favoriteRepository.selectSaves(productEntity.getProductId(), user.getUserId());

			if (save != null) {
				productEntity.favorite = true;
			} else {
				productEntity.favorite = false;
			}
			listProductNew.add(productEntity);

		}

		model.addAttribute("products", listProductNew);
		commomDataService.commonData(model, user);
		return "web/shop";
	}

}
