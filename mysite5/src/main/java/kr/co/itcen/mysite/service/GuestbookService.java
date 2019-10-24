package kr.co.itcen.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.repository.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Repository
public class GuestbookService {
	@Autowired
	private GuestbookDao guestbookDao;

	public Object getList() {
		
		return guestbookDao.getList();
	}
	
	public void delete(GuestbookVo vo) {
		guestbookDao.delete(vo);
	}
	
	public void add(GuestbookVo vo) {
		guestbookDao.insert(vo);
	}
	
}
