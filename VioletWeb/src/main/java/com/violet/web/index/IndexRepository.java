package com.violet.web.index;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.violet.web.item.ItemEntity;

@Repository
public class IndexRepository implements IndexService {
	private static Logger log = LoggerFactory.getLogger(IndexRepository.class);

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int paperListCount() {
		int dataCnt = 0;
		
		try {
			dataCnt = ((Integer) sqlSession.selectOne("indexMapper.paperListCount")).intValue();
			
		} catch(Exception e) {
			log.error(e.toString());
			
		}
		return dataCnt;
	}

	@Override
	public List<PaperEntity> paperListAll() {
		List<PaperEntity> paperList = null;
		
		try {
			paperList = sqlSession.selectList("indexMapper.paperListAll");
			
		} catch(Exception e) {
			log.error(e.toString());
			
		}
		return paperList;
	};

	@Override
	public int newListCount() {
		int dataCnt = 0;
		
		try {
			dataCnt = ((Integer) sqlSession.selectOne("indexMapper.newListCount")).intValue();
			
		} catch(Exception e) {
			log.error(e.toString());
			
		}
		
		return dataCnt;
	}

	@Override
	public List<ItemEntity> newListAll() {
		List<ItemEntity> newList = null;
		
		try {
			newList = sqlSession.selectList("indexMapper.newListAll");
			
		} catch(Exception e) {
			log.error(e.toString());
			
		}
		
		return newList;
	}
}