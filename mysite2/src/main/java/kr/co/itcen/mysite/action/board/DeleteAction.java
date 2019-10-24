package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.SessionUtils;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SessionUtils.SesssionCheck(request, response)) {
			return;
		}
		
		Long no = Long.parseLong(request.getParameter("no"));
		
		HttpSession session = request.getSession();
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		
		BoardVo vo = new BoardDao().get(no, authVo.getNo());
		if(vo == null) {
			WebUtils.redirect(request, response, request.getContextPath() + "/board");
			return;
		}
		
		new BoardDao().delete(no);
		
		String cp = request.getParameter("p");
		if(cp != null) {
			cp = "1";
		}
		WebUtils.redirect(request, response, request.getContextPath() + "/board?p="+cp);
		
	}

}
