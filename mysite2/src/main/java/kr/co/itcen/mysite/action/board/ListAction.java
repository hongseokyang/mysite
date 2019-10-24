package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.Pagination;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cp = request.getParameter("p");
		int curPage = 1;
		if(cp != null) {
			curPage = Integer.parseInt(cp);
		}
		String kwd = request.getParameter("kwd");
		if(kwd==null) {
			kwd = "";
		}
		
		int listCnt = new BoardDao().getListCnt(kwd);
		Pagination pagination = new Pagination(listCnt, curPage);
		List<BoardVo> list = new BoardDao().getList(kwd, pagination.getStartIndex(), pagination.getPageSize());
		
		request.setAttribute("list", list);
		request.setAttribute("pagination", pagination);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
