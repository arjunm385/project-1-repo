package com.spicybank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.User;
import com.spicybank.model.UserAccount;
import com.spicybank.service.User_Details;
import com.spicybank.service.Impl.User_DetailsImpl;

/**
 * Servlet implementation class CreateAccount
 */
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User_Details user_Details = new User_DetailsImpl();

		Cookie[] cookies=request.getCookies();
		String s = "";
		for (Cookie cookie : cookies) {
			s=cookie.getValue();
		}
		int id=Integer.parseInt(s);
		
		UserAccount userAccount = new UserAccount ();
		
	    
		try {
			Gson gson=new Gson();
			userAccount = gson.fromJson(request.getReader(), UserAccount.class);
		    userAccount.setUserid(id);
		    
		    if(user_Details.createUserAccount(userAccount)) 
		    {
		    	String t="Opening Deposit";
		    	user_Details.txnAdd(t,userAccount.getAccount(),userAccount.getBalance());
		    	System.out.println("if true");
//		    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("infopage.html");
//				requestDispatcher.forward(request, response);
		    	response.sendRedirect("http://localhost:8080/Spicy_Bank_V2/infopage.html");
		    }

		else
		{			
			System.out.println("in else");
			PrintWriter out=response.getWriter();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registeruseraccount.html");
			requestDispatcher.include(request, response);
			out.print("<h2><center>invalid credentials</center></h2>");
		}
		}

		catch(BusinessException e) {
			e.getMessage();
		}
		catch(IllegalStateException | JsonSyntaxException e)
	{		
		System.out.println("catch");
			response.sendRedirect("infopage.html");
		}
		
	}

}
