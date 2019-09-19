package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.MemberVO;
import com.yi.persistence.MemberDao;
@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao dao;
	
	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return dao.getTime();
	}

	@Override
	public void insertMember(MemberVO vo) {
		// TODO Auto-generated method stub
		dao.insertMember(vo);
	}

	@Override
	public MemberVO readMember(String userid) {
		// TODO Auto-generated method stub
		return dao.readMember(userid);
	}

	@Override
	public List<MemberVO> selectAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

	@Override
	public void update(MemberVO vo) {
		// TODO Auto-generated method stub
		dao.update(vo);
	}

	@Override
	public void delete(String userid) {
		// TODO Auto-generated method stub
		dao.delete(userid);
	}

	@Override
	public MemberVO selectMemberByIdAndPw(String userid, String userpw) {
		// TODO Auto-generated method stub
		return dao.selectMemberByIdAndPw(userid, userpw);
	}

}
