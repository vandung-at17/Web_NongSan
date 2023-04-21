package vn.fs.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.fs.model.dto.UserDto;
import vn.fs.service.IUserService;

/**
 * @author DongTHD
 *
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController{
	
	@Autowired
	private IUserService userService;
	
	@ModelAttribute(value = "user")
	public UserDto user(Model model, Principal principal, UserDto userDto) {
		if (principal != null) {
			model.addAttribute("userDto", new UserDto());
			userDto = userService.findByEmail(principal.getName());
			model.addAttribute("userDto", userDto);
		}
		return userDto;
	}

	@GetMapping(value = "/home")
	public String index() {
		return "admin/index";
	}
}
