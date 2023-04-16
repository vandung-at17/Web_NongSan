package vn.fs.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.fs.entities.CategoryEntity;
import vn.fs.entities.UserEntity;
import vn.fs.repository.CategoryRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.UserRepository;

/**
 * @author DongTHD
 *
 */
@Controller
public class CommomController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@ModelAttribute(value = "user")
	public UserEntity user(Model model, Principal principal, UserEntity user) {

		if (principal != null) {
			model.addAttribute("user", new UserEntity());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	@ModelAttribute("categoryList")
	public List<CategoryEntity> showCategory(Model model) {
		List<CategoryEntity> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		return categoryList;
	}

}
