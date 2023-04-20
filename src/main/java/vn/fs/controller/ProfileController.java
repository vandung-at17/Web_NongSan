package vn.fs.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.fs.commom.CommomDataService;
import vn.fs.entities.OrderEntity;
import vn.fs.entities.OrderDetailEntity;
import vn.fs.entities.UserEntity;
import vn.fs.model.dto.UserDto;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.OrderRepository;
import vn.fs.repository.UserRepository;
import vn.fs.service.IUserService;

/**
 * @author DongTHD
 *
 */
@Controller
public class ProfileController extends CommomController{

	@Autowired
	UserRepository userRepository;

	@Autowired
	private IUserService userService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	CommomDataService commomDataService;

	@GetMapping(value = "/profile")
	public String profile(Model model, Principal principal, UserDto userDto, Pageable pageable,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

		if (principal != null) {

			model.addAttribute("user", new UserEntity());
			userDto = userService.findByEmail(principal.getName());
			model.addAttribute("user", userDto);
		}
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<OrderEntity> orderPage = findPaginated(PageRequest.of(currentPage - 1, pageSize), userDto);

		int totalPages = orderPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		commomDataService.commonData(model, userDto);
		model.addAttribute("orderByUser", orderPage);

		return "web/profile";
	}

	public Page<OrderEntity> findPaginated(Pageable pageable, UserDto userDto) {

		List<OrderEntity> orderPage = orderRepository.findOrderByUserId(userDto.getUserId());

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<OrderEntity> list;

		if (orderPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, orderPage.size());
			list = orderPage.subList(startItem, toIndex);
		}

		Page<OrderEntity> orderPages = new PageImpl<OrderEntity>(list, PageRequest.of(currentPage, pageSize), orderPage.size());

		return orderPages;
	}
	
	@GetMapping("/order/detail/{order_id}")
	public ModelAndView detail(Model model, Principal principal, UserDto userDto, @PathVariable("order_id") Long id) {

		if (principal != null) {

			model.addAttribute("user", new UserEntity());
			userDto = userService.findByEmail(principal.getName());
			model.addAttribute("user", userDto);
		}
		
		List<OrderDetailEntity> listO = orderDetailRepository.findByOrderId(id);

//		model.addAttribute("amount", orderRepository.findById(id).get().getAmount());
		model.addAttribute("orderDetail", listO);
//		model.addAttribute("orderId", id);
		// set active front-end
//		model.addAttribute("menuO", "menu");
		commomDataService.commonData(model, userDto);
		
		return new ModelAndView("web/historyOrderDetail");
	}
	
	@RequestMapping("/order/cancel/{order_id}")
	public ModelAndView cancel(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<OrderEntity> o = orderRepository.findById(id);
		if (o.isPresent()) {
			OrderEntity oReal = o.get();
			oReal.setStatus((short) 3);
			orderRepository.save(oReal);

			return new ModelAndView("redirect:/profile", model);
			
			
			
		}
		return new ModelAndView("redirect:/profile", model);
	}

}
