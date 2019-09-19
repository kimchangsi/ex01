package com.yi.service;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

public interface BoardService {
	public void regist(BoardVO board) throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public BoardVO read(int bno) throws Exception;
	public void delete(int bno) throws Exception;
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	
	
	public int listCountCriteria() throws Exception;
	
	public void update(BoardVO vo,String[] filename) throws Exception;
	public int listSearchCount(SearchCriteria cri) throws Exception;
	public List<String> getAttach(int bno) throws Exception;
}
