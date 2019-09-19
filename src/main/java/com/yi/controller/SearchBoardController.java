package com.yi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.service.ReplyService;
import com.yi.util.UploadfileUtils;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri,Model model) throws Exception {
		logger.info("-------------list");
		
		List<BoardVO> list = service.listSearch(cri);
		model.addAttribute("list",list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		model.addAttribute("pageMaker",pageMaker);
		//return : views/sboard/list.jsp
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public void registerGet() throws Exception {
		logger.info("---------------------------register Get");
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo,List<MultipartFile> imgFiles) throws Exception {
		logger.info("---------------------------register Post");
		ArrayList<String> list = new ArrayList<>();
		for(MultipartFile file : imgFiles) {
			logger.info("file name :  " + file.getOriginalFilename());
			logger.info("file size :  " + file.getSize());
			String savedName = UploadfileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()); //업로드,썸네일 파일까지 다만듬
			list.add(savedName);
		}
		vo.setFiles(list);
		service.regist(vo);
		
		return "redirect:list";
	}
	
	@RequestMapping(value="readPage", method=RequestMethod.GET)
	public void readPage(int bno,@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("-------------------readPage, bno="+bno);
		
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
	}
	
	@RequestMapping(value = "removePage", method = RequestMethod.POST)
	public String removePage(int bno,int page,@ModelAttribute("cri") SearchCriteria cri, RedirectAttributes ratt) throws Exception {
		logger.info("---------------------------remove Post");
		
		
		List<String> list =service.getAttach(bno);
		for(String filename : list) {
			UploadfileUtils.deleteFile(uploadPath, filename);
		}
		
		service.delete(bno);
		
		
		
		ratt.addAttribute("page", page);
		ratt.addAttribute("searchType", cri.getSearchType());
		ratt.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "updatePage", method = RequestMethod.GET)
	public void modify(int bno,int page,Model model,@ModelAttribute("cri") SearchCriteria cri) throws Exception {
		BoardVO vo = service.read(bno);
		model.addAttribute("board",vo);
		logger.info("---------------------------modify get");
	}
	
	@RequestMapping(value = "updatePage", method = RequestMethod.POST)
	public String modify(BoardVO vo1,Model model,@ModelAttribute("cri") SearchCriteria cri,RedirectAttributes ratt,String[] fullname,List<MultipartFile> imgFiles) throws Exception {
		System.out.println(fullname+"--------"+vo1.getBno());
		System.out.println(imgFiles.size());
		
		//파일도 지워지도록 처리, 지울 파일 목록
		if(fullname!=null) {
			for(String filename : fullname) {
				UploadfileUtils.deleteFile(uploadPath, filename);
			}
		}
		
		
		if(!(imgFiles.get(0).getOriginalFilename().equals(""))) {
			ArrayList<String> list = new ArrayList<>();
			for(MultipartFile file : imgFiles) {
				logger.info("file name :  " + file.getOriginalFilename());
				logger.info("file size :  " + file.getSize());
				String savedName = UploadfileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()); //업로드,썸네일 파일까지 다만듬
				list.add(savedName);
			}
			vo1.setFiles(list);
			for(String fullName : vo1.getFiles()) {
				System.out.println(fullName);
			}
		}
		
		service.update(vo1,fullname);
		
		BoardVO vo = service.read(vo1.getBno());
		model.addAttribute("board",vo);
		ratt.addAttribute("page",cri.getPage());
		ratt.addAttribute("bno",vo1.getBno());
		ratt.addAttribute("searchType",cri.getSearchType());
		ratt.addAttribute("keyword",cri.getKeyword());
		logger.info("---------------------------modify get");
		return "redirect:readPage";
	}
	
	
	//외부파일에서 들고오기
		@RequestMapping(value="/displayFile",method=RequestMethod.GET)
		public @ResponseBody ResponseEntity<byte[]> displayFile(String filename){
			logger.info("------------dispalyFile, filename = " + filename);
			
			String formatName = filename.substring(filename.lastIndexOf(".")+1);//확장자만 뽑아냄
			MediaType mType =  null;
			ResponseEntity<byte[]> entity = null;
			
			if(formatName.equalsIgnoreCase("jpg")) {
				mType = MediaType.IMAGE_JPEG;
			}else if(formatName.equalsIgnoreCase("gif")) {
				mType = MediaType.IMAGE_GIF;
			}else if(formatName.equalsIgnoreCase("png")) {
				mType = MediaType.IMAGE_PNG;
			}
			InputStream in = null;
			try {
			
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + "/" + filename);
			headers.setContentType(mType);
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
			
			}catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);//404에러
			}finally {
				if(in!=null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return entity;
		}
}
