package com.yi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.ReplyVO;
import com.yi.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService service;
	
	//   /replies/all/24042
	@RequestMapping(value="/all/{bno}",method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		logger.info("-------------------------------all/bno"+bno);
		ResponseEntity<List<ReplyVO>> entity = null;
		try {
			List<ReplyVO> list = service.list(bno);
			entity = new ResponseEntity<List<ReplyVO>>(list,HttpStatus.OK); //200
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST); //400error
		}
		return entity;
	}
	
	/*// replies/all?bno=24042
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(int bno){
		
	}*/
	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		ResponseEntity<String> entiy = null;
		logger.info("create --------------- vo: " + vo);
		try {
			service.create(vo);
			entiy = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entiy = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entiy;
	}
	
	//put,post 는 데이터가 body에 실려서옴, 따라서 , @RequestBody를 사용해야만, 정상적으로 값을 받을수 있음
	@RequestMapping(value="{rno}",method=RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") int rno,@RequestBody ReplyVO vo){
		ResponseEntity<String> entiy = null;
		logger.info("update --------------- rno: " + rno + "vo:"+vo);
		
		vo.setRno(rno);
		try {
			service.update(vo);
			entiy = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entiy = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entiy;
	}
	
	@RequestMapping(value="{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") int rno){
		logger.info("delete --------------- rno: " + rno);
		ResponseEntity<String> entiy = null;
		
		try {
			service.delete(rno);
			entiy = new ResponseEntity<String>("succes",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entiy = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entiy;
	}
	
	// List<ReplyVO> , PageMaker
	@RequestMapping(value="/{bno}/{page}",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") int bno,@PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entiy = null;
		logger.info("listPage --------------- bno: " + page + ",page="+page);
		Criteria cri = new Criteria();
		cri.setPage(page);
		try {
			List<ReplyVO> list = service.listPage(bno, cri);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.totalCount(bno));
			int cnt = service.totalCount(bno);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			map.put("cnt", cnt);
			entiy = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entiy = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entiy;
	}
}
