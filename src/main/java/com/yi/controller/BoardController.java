package com.yi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService service;

	@RequestMapping(value = "/ListAll", method = RequestMethod.GET)
	public void listAllGet(Model model) throws Exception {
		logger.info("---------------------------ListAll Get");
		List<BoardVO> list= service.listAll();
		model.addAttribute("list",list);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGet() throws Exception {
		logger.info("---------------------------register Get");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		logger.info("---------------------------register Post");
		service.regist(vo);
		return "redirect:ListAll";
	}
	
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public void read(int bno, Model model) throws Exception {
		logger.info("-------------------read, bno="+bno);
		
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removePost(int bno) throws Exception {
		logger.info("---------------------------remove Post");
		service.delete(bno);
		return "redirect:ListAll";
	}
	
	@RequestMapping(value = "/listPage", method=RequestMethod.GET)
	public void listPage(Model model,Criteria cri) throws Exception {
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list",list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria());
		model.addAttribute("pageMaker",pageMaker);
		
	}
	
	@RequestMapping(value="readPage", method=RequestMethod.GET)
	public void readPage(int bno,Criteria cri, Model model) throws Exception {
		logger.info("-------------------readPage, bno="+bno);
		
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
		model.addAttribute("cri",cri);
	}
	
	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String removePage(int bno,int page) throws Exception {
		logger.info("---------------------------remove Post");
		service.delete(bno);
		return "redirect:listPage?page="+page;
	}
	
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public void modify(int bno,int page,Model model) throws Exception {
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
		logger.info("---------------------------modify get");
	}
	
	@RequestMapping(value = "updatePage", method = RequestMethod.POST)
	public String modify(BoardVO vo1,Model model,String[] filename) throws Exception {
		service.update(vo1,filename);
		
		BoardVO vo = service.read(vo1.getBno());
		model.addAttribute("board",vo);
		logger.info("---------------------------modify get");
		return "redirect:readPage?bno="+vo.getBno();
	}
	
	
	
	
	
	

}
