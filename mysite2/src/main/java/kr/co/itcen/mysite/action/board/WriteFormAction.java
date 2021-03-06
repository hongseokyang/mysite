package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.SessionUtils;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteFormAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SessionUtils.SesssionCheck(request, response)) {
			return;
		}
		
		//String p = request.getParameter("p");
		//request.setAttribute("p", p);
		
		String no = request.getParameter("no");
		if(no != null) {
			BoardVo vo = new BoardDao().get(Long.parseLong(no));
			request.setAttribute("vo", vo);
		}
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/write.jsp");
	}

}
