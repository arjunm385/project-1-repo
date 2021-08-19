package com.spicybank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.Emp;
import com.spicybank.service.Impl.Emp_DetailsImpl;


class Emp_DetailsTest {
	private static Emp_Details service;

	@BeforeAll
	public static void setup() {
		service = new Emp_DetailsImpl();
	}
	@Test
	void testIsValidEmpForTrue() {
		try {
			Emp emp = new Emp(0,null, null);
			emp.setEmpid(54235);
			emp.setEmppassword("admin123");
			assertTrue(true== service.isValidEmp(emp));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	@Test
	void testIsValidEmpForFalse() {
		try {
			Emp emp = new Emp(0,null, null);
			emp.setEmpid(54235);
			emp.setEmppassword("Amin123");
			assertFalse(true== service.isValidEmp(emp));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}
	@Test
	void testIsValidEmp() {
		try {
			Emp emp = new Emp(0,null, null);
			emp.setEmpid(54235);
			emp.setEmppassword("admin123");
			assertEquals(true, service.isValidEmp(emp));
		} catch (BusinessException e) {
			fail("Test case Failed");
		}
	}

}
