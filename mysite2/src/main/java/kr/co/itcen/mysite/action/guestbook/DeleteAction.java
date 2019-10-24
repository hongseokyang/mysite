package kr.co.itcen.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");

		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		new GuestbookDao().delete(vo);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/guestbook");
	}

}
