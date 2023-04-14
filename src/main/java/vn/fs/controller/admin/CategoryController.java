package vn.fs.controller.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.fs.entities.CategoryEntity;
import vn.fs.entities.User;
import vn.fs.model.dto.CategoryDto;
import vn.fs.model.response.PaginateResponse;
import vn.fs.repository.CategoryRepository;
import vn.fs.repository.UserRepository;
import vn.fs.service.ICategoryService;


/**
 * @author DongTHD
 *
 */
@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private ICategoryService categoryService;

	@Autowired
	UserRepository userRepository;
	
	private String message;
	
	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	// show list category - table list
	// Hiển thị danh mục - danh sách thể loại
//	@ModelAttribute("categories")
//	public List<CategoryEntity> showCategory(Model model) {
//		List<CategoryEntity> categories = categoryRepository.findAll();
//		model.addAttribute("categories", categories);
//		model.addAttribute("message", message);
//		message=null;
//		return categories;
//	}

	@GetMapping(value = "/categories")
	public String categories(Model model, Principal principal) {
		model.addAttribute("category", new CategoryEntity());
		int currentPage = 1;
		int limit = 5;
		Pageable pageable = PageRequest.of(currentPage-1, limit);	
		List<CategoryDto> categoryDtos = categoryService.findAllCategoryOfPage(pageable);
		model.addAttribute("categories", categoryService.findAllCategoryOfPage(pageable));
		PaginateResponse paginateResponse = new PaginateResponse();
		paginateResponse.setTotalPage((int) Math.ceil((double) categoryService.getTotalItem() /limit));
		paginateResponse.setPage(currentPage);
		model.addAttribute("paginate", paginateResponse);
		return "admin/categories";
	}

	// add category
	@PostMapping(value = "/addCategory")
	public String addCategory(@Validated @ModelAttribute("category") CategoryEntity category, ModelMap model,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "failure");

			return "admin/categories";
		}

		categoryRepository.save(category);
		model.addAttribute("message", "successful!");

		return "redirect:/admin/categories";
	}

	// get Edit category
	@GetMapping(value = "/editCategory/{id}")
	public String editCategory(@PathVariable("id") Long id, ModelMap model) {
		CategoryEntity category = categoryRepository.findById(id).orElse(null);

		model.addAttribute("category", category);

		return "admin/editCategory";
	}

	// delete category
	@GetMapping("/delete/{id}")
	public String delCategory(@PathVariable("id") Long id, Model model) {
		categoryRepository.deleteById(id);
		message = "Delete successful!";
		model.addAttribute("message", message);

		return "redirect:/admin/categories";
	}
}
