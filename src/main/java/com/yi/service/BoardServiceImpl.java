package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDao dao;
	
	
	@Override
	@Transactional
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		for(String fullName : board.getFiles()) {
			dao.addAttach(fullName);
		}
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	@Transactional
	public BoardVO read(int bno) throws Exception {
		BoardVO vo = dao.read(bno);
		List<String> files = dao.getAttach(bno);
		vo.setFiles(files);
		return vo;
	}

	@Override
	@Transactional
	public void delete(int bno) throws Exception {
		dao.deleteAttach(bno);
		dao.delete(bno);
		
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		// TODO Auto-generated method stub
		return dao.countPaging();
	}

	@Override
	public void update(BoardVO vo,String[] filenameArr) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(vo.getFiles());
		if(vo.getFiles()!=null) {
			for(String fullName : vo.getFiles()) {
				dao.addAttach2(vo.getBno(),fullName);
			}
		}
			
		if(filenameArr!=null) {
			for(String filename : filenameArr ) {
				dao.deleteAttachByFullName(vo.getBno(), filename);
			}
			
		}
		dao.update(vo);
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listSearchCount(cri);
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.getAttach(bno);
	}

}
