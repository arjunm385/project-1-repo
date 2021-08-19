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
import com.spicybank.model.UserAccount;
import com.spicybank.service.Emp_Details;
import com.spicybank.service.Impl.Emp_DetailsImpl;

/**
 * Servlet implementation class Emp_Operations
 */
public class Emp_Operations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Emp_Operations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Emp_Details emp_Details=new Emp_DetailsImpl();
		response.setContentType("application/json;charset=UTF-8");
		Gson gson=new Gson();
		String q = request.getParameter("filter");
		PrintWriter out=response.getWriter();
		HttpSession session = request.getSession(false);
		
		if (session == null) {

			out.print("<center><h1>Please Login First</h1></center>");
			out.print("<h4><a href='/Spicy_Bank_V2'>Click here for home page </a> ");
		} 
		
		else {
		
		if(q.equals("all")) {
		try {
			
			out.print(gson.toJson(emp_Details.getAllCustomerDetails()));
		} catch (BusinessException e) {
			e.getMessage();
		}
		}
		else if(q.equals("name")) {
			String name=request.getParameter("criteria");
			
			try {
				out.print(gson.toJson(emp_Details.getCustomerByName(name)));
			} catch (BusinessException e) {
				e.getMessage();
			}
		}
		
		else if(q.equals("userid")) {

			String reg;
			reg=request.getParameter("criteria");
			if(reg.matches("^[0-9]*$") && reg!="")
			{
				int userid=Integer.parseInt(request.getParameter("criteria"));
				try {
					out.print(gson.toJson(emp_Details.getCustomerById(userid)));
				} catch (BusinessException e) {
					e.getMessage(); }
			}
			else {
				UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
				out.print(gson.toJson(userAccount));
			
		  }
		}
		
		else if(q.equals("account")) {

			String reg;
			reg=request.getParameter("criteria");
			if(reg.matches("^[0-9]*$") && reg!="")
			{
				long account=Long.parseLong(request.getParameter("criteria"));
				try {
					out.print(gson.toJson(emp_Details.getCustomerByAccount(account)));
				} catch (BusinessException e) {
					e.getMessage(); }
			}
			else {
				UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
				out.print(gson.toJson(userAccount));
			
		  }
		}
		
		else if(q.equals("pan")) {

			String reg;
			reg=request.getParameter("criteria");
			if(reg.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}") && reg!="")
			{
				String pan=request.getParameter("criteria");
				try {
					out.print(gson.toJson(emp_Details.getCustomerByPan(pan)));
				} catch (BusinessException e) {
					e.getMessage(); }
			}
			else {
				UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
				out.print(gson.toJson(userAccount));
			
		  }
		}
		
		else if(q.equals("state")) {

			String reg;
			reg=request.getParameter("criteria");
			if(reg.matches("^[A-Z a-z]*$") && reg!="")
			{
				String state=request.getParameter("criteria");
				try {
					out.print(gson.toJson(emp_Details.getCustomerByState(state)));
				} catch (BusinessException e) {
					e.getMessage(); }
			}
			else {
				UserAccount userAccount = new UserAccount(0, null, 0, null, null, null, null, false, null, 0);
				out.print(gson.toJson(userAccount));
			
		  }
		}
		}
		
	  }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
