package com.spring.domain;

import lombok.Data;

@Data
public class RegisterVO {
	private String userid;
	private String password;
	private String confirm_password;
	private String name;
	private String gender;
	private String email;
	
	public boolean isPasswordEqualToConfirmPassword() {
		return this.password.equals(confirm_password);
	}

}
