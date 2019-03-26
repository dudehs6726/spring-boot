package com.douzone.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {}
	
	@RequestMapping(value="join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(
		@ModelAttribute @Valid UserVo userVo, 
		BindingResult result, 
		Model model) {
		
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			
//			for(ObjectError error:list) {
//				System.out.println(error);
//			}
			
			model.addAllAttributes(result.getModel());//map으로 만들어 보냄
			return "user/join";
		}
		
		userService.join(userVo);
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@Auth(Role.USER)
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Model model) {
		
		UserVo userVo = userService.modifyForm(authUser);

		model.addAttribute("vo", userVo);
		
		return "user/modify";
	}

	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, @ModelAttribute UserVo userVo) {
		
		userVo.setNo(authUser.getNo());
		userService.modify(userVo);
		
		//session의 authUser 변경
		authUser.setName(userVo.getName());
		
		return "redirect:/";
	}
	/*
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		
		if(session != null && session.getAttribute("authuser") != null) {
			//logout 처리
			session.removeAttribute("authuser");
			session.invalidate();
		}
		
		return "redirect:/";
	}
	*/
}
