package com.choi.board;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.choi.board.dao.ArticleDaoImpl;
import com.choi.board.model.Article;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	ArticleDaoImpl articleDao;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="WriteForm")
	public String goWriteForm(Model model){
		return "board/writeForm";
		
	}
	
	@RequestMapping(value="WriteBoard",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView writeArticle(Article article)throws Exception{
		System.out.println(article.getTitle());
		System.out.println(article.getWriterName());
		System.out.println(article.getPassword());
		System.out.println(article.getContent());
		
		articleDao.insert(article);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		return mav;
		
	}
	
	@RequestMapping(value="listView",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView selectPage(@RequestParam(value="p") String requestPageNumber) throws Exception{
		System.out.println(requestPageNumber);
		ModelAndView mav = new ModelAndView();
		mav.addObject("listModel", articleDao.getArticleList(Integer.parseInt(requestPageNumber)));
		mav.setViewName("board/listResult");
		return mav;
	}
}
