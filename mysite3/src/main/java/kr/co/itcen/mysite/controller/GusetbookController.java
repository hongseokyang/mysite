package kr.co.itcen.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GuestbookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GusetbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String list(Model model) {
		
		model.addAttribute("list", guestbookService.getList());
		
		return "guestbook/list";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		
		return "guestbook/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {
		guestbookService.delete(vo);
		
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@ModelAttribute GuestbookVo vo) {
		guestbookService.add(vo);
		
		return "redirect:/guestbook";
	}
}
