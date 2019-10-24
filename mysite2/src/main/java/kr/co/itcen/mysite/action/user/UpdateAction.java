package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		if(name == null || password == null) {
			WebUtils.redirect(request, response, request.getContextPath()+"/user?a=updateform");
			return;
		}
		
		HttpSession session = request.getSession();
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		UserVo vo = new UserVo();
		
		vo.setNo(authUser.getNo());
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);
		new UserDao().update(vo);
		session.setAttribute("authUser", vo);
		
		WebUtils.redirect(request, response, request.getContextPath());
	}

}
