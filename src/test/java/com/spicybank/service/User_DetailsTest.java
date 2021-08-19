package com.spicybank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.User;
import com.spicybank.service.Impl.User_DetailsImpl;

class User_DetailsTest {

	private static User_Details service;

	@BeforeAll
	public static void setup() {
		service = new User_DetailsImpl();
	}
	@Test
	void testIsValidUserForTrue() {
		try {
			User user = new User();
			user.setUserid(10003);
			user.setPass("123");
			assertTrue(true== service.isValidUser(user));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	@Test
	void testIsValidUserForFalse() {
		try {
			User user = new User();
			user.setUserid(10003);
			user.setPass("1234");
			assertFalse(true== service.isValidUser(user));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	
	@Test
	void testIsValidUserEquals() {
		try {
			User user = new User();
			user.setUserid(10003);
			user.setPass("123");
			assertEquals(true,service.isValidUser(user));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	
	@Test
	void testRegisterUserForTrue() {
		try {
			User user = new User();
			user.setUserid(10006);
			user.setPass("1234");
			user.setCustname("test");
			user.setPhone("301254875");
			assertTrue(true==service.createUser(user));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	
	@Test
	void testRegisterUserForFalse() {
		try {
			User user = new User();
			user.setUserid(0);
			user.setPass(null);
			user.setCustname("test");
			user.setPhone("301254875");
			assertFalse(true==service.createUser(user));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	
	@Test
	void testRegisterUser() {
		try {
			User user = new User();
			user.setUserid(1524);
			user.setPass("testing");
			user.setCustname("test");
			user.setPhone("301254875");
			assertEquals(true,service.createUser(user));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	
}
