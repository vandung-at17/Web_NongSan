package vn.fs.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.model.dto.CategoryDto;
import vn.fs.service.ICategoryService;

@RestController(value = "Category")
@RequestMapping("/api")
public class CategoryAPI {
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping(value = "/categories")
	public List<CategoryDto> getCategoryOfPage(@RequestParam int currentPage) {
		int limit = 5;
		Pageable pageable = PageRequest.of(currentPage-1, limit);
		List<CategoryDto> categoryDtos = categoryService.findAllCategoryOfPage(pageable);
		return categoryDtos;
	}
}
