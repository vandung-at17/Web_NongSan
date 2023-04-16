package vn.fs.api.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.model.response.PageLayOut;

@RestController(value = "Product")
@RequestMapping("/api")
public class ProductAPI {

	@GetMapping(value ="/products")
	public PageLayOut findProductsOfPage (@RequestParam int currentPage,@RequestParam int limit) {
		PageLayOut pageLayOut = new PageLayOut();
		return pageLayOut;
	}
}
