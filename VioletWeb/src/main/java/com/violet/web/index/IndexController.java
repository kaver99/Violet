package com.violet.web.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.violet.web.item.ItemEntity;
import com.violet.web.util.paging.PagingEntity;

@Controller
public class IndexController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value = {"/", "/main.violet"})
	public String index(HttpServletRequest request, @RequestParam(defaultValue="1") int page) {
		/* Other way : model.addAttribute("Test", "Shop"); */
		// paper
		List<PaperEntity> paperListAll = doPaperListAll();
		request.setAttribute("paperListAll", paperListAll);
		
		// Brand List (max : 10)
		
		// New List (fixed: 24)
		
		// Tag List (max: 20)
		
		// Styler List (fixed: 6)


		// List Data
		Map<String, Object> newListMap = doNewListAll(page);
		request.setAttribute("pagination", newListMap.get("pagination"));
		request.setAttribute("newListAll", newListMap.get("newListAll"));
		
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
			//전체 게시글 개수
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
	 * New Item List
	 * @return returnMap
	 */
	public Map<String, Object> doNewListAll(int page) {
		int dataCnt = 0;
		List<ItemEntity> newList = null;
		Map<String, Object> returnMap = null;
		try {
			returnMap = new HashMap<String, Object>();
			dataCnt = indexService.newListCount();
			
			/** Paging START **/
			//Paging 객체생성
			PagingEntity pagingEntity = new PagingEntity();
			//Paging 설정
			pagingEntity.pageInfo(page, dataCnt);
			returnMap.put("pagination", pagingEntity);
			/** Paging END **/
			
			if(dataCnt != 0) {
				newList = indexService.newListAll(pagingEntity);
			}
			returnMap.put("newListAll", newList);
			
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return returnMap;
		
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
