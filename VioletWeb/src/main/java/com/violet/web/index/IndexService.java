package com.violet.web.index;

import java.util.List;

import com.violet.web.item.ItemEntity;

public interface IndexService {

	public int paperListCount();

	public List<PaperEntity> paperListAll();

	public int newListCount();

	public List<ItemEntity> newListAll();

}
