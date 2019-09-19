package com.yi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.LoginDTO;
import com.yi.domain.MemberVO;
import com.yi.service.MemberService;
@RequestMapping("/auth")
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	MemberService service;
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public void loginGET() {
		logger.info("----------- login GET");
	}
	
	
	
	@RequestMapping(value="loginPost",method=RequestMethod.POST)
	public void loginPOST(MemberVO member,Model model) {
		logger.info("----------- login POST"+member);
		
		MemberVO dbMember = service.selectMemberByIdAndPw(member.getUserid(), member.getUserpw());
		
		//db에옶우면
		if(dbMember == null) {
			logger.info("loginPOST ------------ login fail,not member");
			return;
		}
		
		LoginDTO dto = new LoginDTO();
		dto.setUserid(dbMember.getUserid());
		dto.setUsername(dbMember.getUsername());
		model.addAttribute("loginDTO",dto);
	}
}
