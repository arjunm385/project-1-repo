package com.spicybank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.spicybank.exceptions.BusinessException;
import com.spicybank.service.Emp_Details;
import com.spicybank.service.Impl.Emp_DetailsImpl;

/**
 * Servlet implementation class UserInfo
 */
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies=request.getCookies();
		String s = "";
		for (Cookie cookie : cookies) {
			s=cookie.getValue();
		}
		int id=Integer.parseInt(s);
		Emp_Details emp_Details=new Emp_DetailsImpl();
		response.setContentType("application/json;charset=UTF-8");
		Gson gson=new Gson();
		PrintWriter out=response.getWriter();
		try {
			out.print(gson.toJson(emp_Details.getCustomerById(id)));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
	}

}
