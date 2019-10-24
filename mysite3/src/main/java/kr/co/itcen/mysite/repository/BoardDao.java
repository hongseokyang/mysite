package kr.co.itcen.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean delete(Long no) {
		int count = sqlSession.update("board.delete", no);
		return count == 1;
	} 
	
	public Boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count == 1;
	} 
	
	public Boolean updateHit(Long no) {
		int count = sqlSession.update("board.updateHit", no);
		return count == 1;
	} 
	
	public Boolean insertBoard(BoardVo vo) {
		int count = sqlSession.insert("board.insertBoard", vo);
		return count==1;
	}
	
	public Boolean insertReply(BoardVo vo) {
		int count = sqlSession.insert("board.insertReply", vo);
		return count==1;
	}
	
	public Boolean updateReply(BoardVo vo) {
		int count = sqlSession.update("board.updateReply", vo);
		return count==1;
	}
	
	public BoardVo get(BoardVo vo) {
		return sqlSession.selectOne("board.get", vo);
	}
	
	public BoardVo get(Long boardNo) {
		return sqlSession.selectOne("board.getView", boardNo);
	}
	
	public int getListCnt(String kwd) {
		return sqlSession.selectOne("board.getListCnt", kwd);
	}
	
	
	public List<BoardVo> getList(Map<String, Object> map) {
		return sqlSession.selectList("board.getList", map);
	}
	
}

