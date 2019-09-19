package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.MemberVO;
@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	private SqlSession sqlSession;

	private static final String namespace = "com.yi.mapper.MemberMapper";

	@Override
	public String getTime() {
		return sqlSession.selectOne(namespace + ".getTime");
	}

	@Override
	public void insertMember(MemberVO vo) {
		sqlSession.insert(namespace + ".insertMember", vo);
	}

	@Override
	public MemberVO readMember(String userid) {
		return sqlSession.selectOne(namespace + ".readMember", userid);
	}

	@Override
	public List<MemberVO> selectAll() {
		return sqlSession.selectList(namespace + ".selectAll");
	}

	@Override
	public void update(MemberVO vo) {
		sqlSession.update(namespace + ".update", vo);
		
	}

	@Override
	public void delete(String userid) {
		sqlSession.delete(namespace + ".delete",userid);
		
	}

	@Override
	public MemberVO selectMemberByIdAndPw(String userid, String userpw) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		return sqlSession.selectOne(namespace + ".selectMemberByIdAndPw",map);
	}

}
