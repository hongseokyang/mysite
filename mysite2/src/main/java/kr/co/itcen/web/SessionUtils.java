package kr.co.itcen.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.vo.UserVo;

public class SessionUtils {
	public static boolean SesssionCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform");
			return false;
		}
		return true;
	}
}
