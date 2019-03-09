package com.violet.web.trend;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("TrendEntity")
public class TrendEntity {
	int item_id;
    String class_item;
    String stylist_name;
    String img_path1;
    
}
