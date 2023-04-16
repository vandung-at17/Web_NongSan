package vn.fs.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.fs.entities.UserEntity;
import vn.fs.repository.UserRepository;

/**
 * @author DongTHD
 *
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController{
	
	@Autowired
	UserRepository userRepository;
	
	@ModelAttribute(value = "user")
	public UserEntity user(Model model, Principal principal, UserEntity user) {

		if (principal != null) {
			model.addAttribute("user", new UserEntity());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	@GetMapping(value = "/home")
	public String index() {
		
		return "admin/index";
	}
}
