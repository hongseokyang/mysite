package kr.co.itcen.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.Pagination;

@Repository
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public int getListCnt(String kwd) {
		return boardDao.getListCnt(kwd);
	}

	public List<BoardVo> getList(String kwd, Pagination pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		map.put("pagination", pagination);
		
		return boardDao.getList(map);
	}
	public void updateHit(Long no) {
		boardDao.updateHit(no);
	}
	public BoardVo getBoard(Long no) {
		return boardDao.get(no);
	}

	public BoardVo getBoard(Long boardNo, Long userNo) {
		BoardVo vo = new BoardVo();
		vo.setNo(boardNo);
		vo.setUserNo(userNo);
		return boardDao.get(vo);
	}

	public void delete(Long no) {
		boardDao.delete(no);
	}

	public void add(BoardVo vo) {
		System.out.println(vo);
		if(vo.getGroupNo() == null) {
			boardDao.insertBoard(vo);
		} else {
			vo.setOrderNo(vo.getOrderNo()+1);
			vo.setDepth(vo.getDepth()+1);
			boardDao.updateReply(vo);
			boardDao.insertReply(vo);
		}
	}

	public void modify(BoardVo vo) {
		System.out.println(vo);
		boardDao.update(vo);
	}

	
	
}
