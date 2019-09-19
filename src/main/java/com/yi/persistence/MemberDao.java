package com.yi.persistence;

import java.util.List;

import com.yi.domain.MemberVO;

public interface MemberDao {
	public String getTime();
	public void insertMember(MemberVO vo);
	public MemberVO readMember(String userid);
	
	public List<MemberVO> selectAll();
	public void update(MemberVO vo); //password ,email,name수정 -- id가 일치하는사람찾아서
	public void delete(String userid);
	public MemberVO selectMemberByIdAndPw(String userid,String userpw);
}
