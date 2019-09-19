package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
import com.yi.persistence.BoardDao;
import com.yi.persistence.ReplyDao;
@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyDao dao;
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.list(bno);
	}


	@Override
	@Transactional
	public void create(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.create(vo);
		boardDao.updateReplyCnt(1, vo.getBno());
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
	}

	@Override
	@Transactional
	public void delete(int rno) throws Exception {
		// TODO Auto-generated method stub
		int bno =dao.getBno(rno);
		dao.delete(rno);
		boardDao.updateReplyCnt(-1, bno);
	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listPage(bno, cri);
	}

	@Override
	public int totalCount(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.totalCount(bno);
	}


	@Override
	public int getCnt(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.totalCount(bno);
	}

}
