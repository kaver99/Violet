package com.violet.web.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.violet.web.item.ItemEntity;

@Controller
public class IndexController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value = {"/", "/main.violet"})
	public String index(HttpServletRequest request) {
		
		/* Other way : model.addAttribute("Test", "Shop"); */
		// Main Slide Data 
		List<PaperEntity> paperListAll = doPaperListAll();
		request.setAttribute("paperListAll", paperListAll);
		
		// List Data
		List<ItemEntity> newListAll = doNewListAll();
		request.setAttribute("newListAll", newListAll);
		
		return "index";
	}
	
	/**
	 * Paper List
	 * @return paperList
	 */
	public List<PaperEntity> doPaperListAll() {
		int dataCnt = 0;
		List<PaperEntity> paperList = null;
		
		try {
			dataCnt = indexService.paperListCount();
			
			if(dataCnt != 0) {
				paperList = indexService.paperListAll();
			}
			
		} catch(Exception e) {
			log.error(e.toString());
			
		}
		
		return paperList;
	}
	
	
	/**
	 * Event List
	 * @return eventList
	 */
	public List<ItemEntity> doNewListAll() {
		int dataCnt = 0;
		List<ItemEntity> newList = null;

		try {
			dataCnt = indexService.newListCount();
			
			if(dataCnt != 0) {
				newList = indexService.newListAll();
			}
			
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return newList;
		
	}
	/*
	public List<TrendEntity> doTrendListAll() {
		int dataCnt = 0;
		List<TrendEntity> trendList = null;
		
		try {
			
			
		} catch(Exception e) {
			log.error(e.toString());
			
		}
	}
	*/
}
