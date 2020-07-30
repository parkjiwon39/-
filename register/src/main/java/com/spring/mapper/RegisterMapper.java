package com.spring.mapper;

import com.spring.domain.RegisterVO;

public interface RegisterMapper {
	public int register(RegisterVO vo);
	public int dupId(String userid);
}
