package com.violet.web.item;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias(value="ItemEntity")
public class ItemEntity {
	int item_id;
	String title;
	String description;
	int price;
	String class_item;
	String stylist_name;
	String creater;
	String create_date;
	String last_date;
	String opening_date;
	char opening_yn;
	String img_path1;
	char event_yn;
	String event_sdate;
	String event_edate;
}
