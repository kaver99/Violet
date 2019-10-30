package com.violet.web.index;

import java.util.List;

import com.violet.web.item.ItemEntity;
import com.violet.web.util.paging.PagingEntity;

public interface IndexService {

	public int paperListCount();

	public List<PaperEntity> paperListAll();

	public int newListCount();

	public List<ItemEntity> newListAll(PagingEntity pagingEntity);

}
