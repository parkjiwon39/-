package com.spring.domain;

import lombok.Data;

@Data
public class ChangeVO {
	private String userid;
	private String password;
	private String new_password;
	private String confirm_password;
	
	public boolean newPasswordEqualsConfirm() {
		return new_password.equals(confirm_password);
	}

}
