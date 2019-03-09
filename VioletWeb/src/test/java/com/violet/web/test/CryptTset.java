package com.violet.web.test;

import org.springframework.security.core.token.Sha512DigestUtils;

public class CryptTset {

	public static void main(String[] args) {
		
		String result = Sha512DigestUtils.shaHex("1111");
		System.out.println("result : " + result);
	}
}
