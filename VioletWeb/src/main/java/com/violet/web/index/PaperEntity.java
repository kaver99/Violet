package com.violet.web.index;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("PaperEntity")
public class PaperEntity {
	int paper_no;
    String paper_title;
    String paper_description;
    String create_date;
    String paper_img;
}
