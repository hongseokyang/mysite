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

public class WriteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SessionUtils.SesssionCheck(request, response)) {
			return;
		}
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String groupNo = request.getParameter("groupNo");
		String orderNo = request.getParameter("orderNo");
		String depth = request.getParameter("depth");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(authUser.getNo());
		
		if("".equals(groupNo)) {
			new BoardDao().insertBoard(vo);
		} else {
			vo.setGroupNo(Long.parseLong(groupNo));
			vo.setOrderNo(Long.parseLong(orderNo));
			vo.setDepth(Long.parseLong(depth));
			new BoardDao().insertReply(vo);
		}
		
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
