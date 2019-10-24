package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.Pagination;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(@RequestParam(value = "p", required = true, defaultValue = "1") String p, @RequestParam(value = "kwd", required = true, defaultValue = "") String kwd, Model model) {
		model.addAttribute("kwd", kwd);
		int curPage = Integer.parseInt(p);
		kwd = "%"+kwd+"%";
		int listCnt = boardService.getListCnt(kwd);
		Pagination pagination = new Pagination(listCnt, curPage);
		
		List<BoardVo> list = boardService.getList(kwd, pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}/{p}")
	public String view(@PathVariable("no") Long no, @ModelAttribute("p") @PathVariable("p") String p
			, @RequestParam("kwd") String kwd
			, Model model) {
		model.addAttribute("kwd", kwd);
		boardService.updateHit(no);
		model.addAttribute("vo", boardService.getBoard(no));
		
		return "board/view";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@RequestParam(value = "no", required = true, defaultValue = "0") Long no, @ModelAttribute("p") String p, Model model) {
		// session check
		
		if(no != 0) {
			model.addAttribute("vo", boardService.getBoard(no));
		}
		return "board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVo vo, HttpSession session) {
		// session check
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		vo.setUserNo(authUser.getNo());
		
		boardService.add(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/modify/{no}/{p}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, @ModelAttribute("p") @PathVariable("p") String p, HttpSession session, Model model) {
		// session check
		
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		BoardVo vo = boardService.getBoard(no, authVo.getNo());
		if(vo==null) {
			return "redirect:/board";
		}
		model.addAttribute("boardVo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(BoardVo vo) {
		// session check
		
		boardService.modify(vo);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/delete/{no}/{p}")
	public String delete(@PathVariable("no") Long no, @ModelAttribute("p") @PathVariable("p") String p, HttpSession session) {
		// session check
		
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		BoardVo vo = boardService.getBoard(no, authVo.getNo());
		if(vo==null) {
			return "redirect:/board";
		}
		
		boardService.delete(no);
		return "redirect:/board?p="+p;
	}
}
