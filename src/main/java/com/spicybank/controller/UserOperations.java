package com.spicybank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.spicybank.exceptions.BusinessException;
import com.spicybank.service.Emp_Details;
import com.spicybank.service.User_Details;
import com.spicybank.service.Impl.Emp_DetailsImpl;
import com.spicybank.service.Impl.User_DetailsImpl;

/**
 * Servlet implementation class UserOperations
 */
public class UserOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserOperations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Emp_Details emp_Details = new Emp_DetailsImpl();
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		Gson gson=new Gson();
		response.setContentType("text/html");
		if (session == null) {

			out.print("<center><h1>Please Login First</h1></center>");
			out.print("<h4><a href='/Spicy_Bank_V2'>Click here for home page </a> ");
		} 
		else {
			String q = request.getParameter("filter");
			System.out.println(q);
			if(q.equals("balance"))
			{
				long account = (long) session.getAttribute("account");
				System.out.println(account);
				try {
					System.out.println(emp_Details.getCustomerByAccount(account));
					out.print(gson.toJson(emp_Details.getCustomerByAccount(account)));
				} catch (BusinessException e) {
					e.getMessage(); }
			}
			
			if(q.equals("txn"))
			{
				long account = (long) session.getAttribute("account");
				System.out.println(account);
				try {
					System.out.println(emp_Details.getCustomerByAccount(account));
					out.print(gson.toJson(emp_Details.getTransactionsByAccount(account)));
				} catch (BusinessException e) {
					e.getMessage(); }
			}
			
			}
	}

}
