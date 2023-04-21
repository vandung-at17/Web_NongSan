package vn.fs.controller.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.fs.entities.UserEntity;
import vn.fs.model.dto.UserDto;
import vn.fs.repository.UserRepository;
import vn.fs.service.IUserService;

/**
 * @author DongTHD
 *
 */
@Controller
public class UserController{
	
	@Autowired
	private IUserService userService;

	@Autowired
	UserRepository userRepository;

	@GetMapping(value = "/admin/users")
	public String customer(Model model, Principal principal) {
		
		UserDto userDto = userService.findByEmail(principal.getName());
		model.addAttribute("user", userDto);
		
		List<UserEntity> users = userRepository.findAll();
		model.addAttribute("users", users);
		
		return "/admin/users";
	}
}
