package com.spring.domain;

import lombok.Data;

@Data
public class AuthVO {
	//db에서 확인된 결과 담는 객체
	private String userid;
	private String name;

}
