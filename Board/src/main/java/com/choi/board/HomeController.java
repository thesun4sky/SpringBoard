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
import com.choi.board.util.CalcSeq;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	ArticleDaoImpl articleDao;
	@Autowired
	CalcSeq calcSeq;
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
	@RequestMapping(value="replyBoard",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView replyArticle(Article article,@RequestParam(value="currentPage") String requestPageNumber,@RequestParam(value="parentId") String parentId) throws Exception{
		
		System.out.println("requestPageNumber = "+requestPageNumber);
		System.out.println("parentId = "+parentId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		return mav;
	}
	
	
	@RequestMapping(value="listView",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView selectPage(@RequestParam(value="p") String requestPageNumber) throws Exception{
		System.out.println(requestPageNumber);
		ModelAndView mav = new ModelAndView();
		mav.addObject("listModel", articleDao.getArticleList(Integer.parseInt(requestPageNumber)));
		mav.addObject("currentPage",requestPageNumber);
		
		mav.setViewName("board/listResult");
		return mav;
	}
	
	@RequestMapping(value="readArticle",method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView readArticle(@RequestParam(value="p")String requestPageNumber,@RequestParam(value="articleId") String articleId) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("Article",articleDao.read(Integer.parseInt(articleId),Integer.parseInt(articleId)));
		mav.addObject("currentPage",requestPageNumber);
		mav.setViewName("board/readArticle");
		return mav;
	}
	
	@RequestMapping(value="replyArticle",method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView replyPage(@RequestParam(value="p")String requestPageNumber,@RequestParam(value="parentId") String parentId){
		System.out.println("requestPageNumber"+requestPageNumber);
		System.out.println("parentId" + parentId);
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("currentPage", requestPageNumber);
		mav.addObject("parentId",parentId);
		mav.setViewName("board/replyArticle");
		
		return mav;
	}
	
	@RequestMapping(value="replyWrite",method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView replyWrite(Article article) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		System.out.println("sequence Number 계산 시작");
		Article parent = articleDao.read(article.getId(), article.getCurrentPage());
		System.out.println("parent 세팅 끝");
		String searchMaxSeqNum = parent.getSequenceNumber();
		System.out.println("parent sequenceNumber = " +searchMaxSeqNum );
		String searchMinSeqNum = calcSeq.getSearchMinSeqNum(parent);
		String lastChildSeq = articleDao.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
		String sequenceNumber = calcSeq.getSequenceNumber(parent, lastChildSeq);
		
		System.out.println("sequence Number 계산 끝");
		
		article.setGroupId(parent.getGroupId());
		article.setSequenceNumber(sequenceNumber);
		articleDao.insert(article);
		
		mav.setViewName("home");
		
		return mav;
	}
	
	@RequestMapping(value="pwConfirm",method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView pwConfirm(@RequestParam(value="id") String articleId,@RequestParam(value="action")String action,@RequestParam(value="password") String password,@RequestParam(value="currentPage") String currentPage) throws  Exception{
		
		ModelAndView mav = new ModelAndView();
		System.out.println("articleId = " + articleId);
		System.out.println("currentPage = " + currentPage);
		System.out.println("password = " + password);
		
		Article article = articleDao.read(Integer.parseInt(articleId), Integer.parseInt(currentPage));
		
		if(article.getPassword().equalsIgnoreCase(password)){
			if(action.equalsIgnoreCase("update")){
				//update
				System.out.println("update");
				mav.addObject("Article",article);
				mav.setViewName("updateArticle");
				
			}else{
				//delete
				System.out.println("delete");
				articleDao.delete(Integer.parseInt(articleId));
				mav.setViewName("home");
			}
		}else{
			mav.setViewName("home");
		}
		
		return mav;
	}
	
	
}
