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
import com.spicybank.service.User_Details;
import com.spicybank.service.Impl.User_DetailsImpl;

/**
 * Servlet implementation class Registeruser
 */
public class Registeruser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registeruser() {
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
		User users=new User();
		
		try {
		Gson gson=new Gson();
		users=gson.fromJson(request.getReader(),User.class);

		
			if(user_Details.createUser(users)==true)
			{
				String s=String.valueOf(users.getUserid());
				Cookie c1 = new Cookie("userid",s);
				response.addCookie(c1);
//				response.sendRedirect("registeruseraccount.html");
//				RequestDispatcher requestDispatcher = request.getRequestDispatcher("http://localhost:8080/Spicy_Bank_V2/index.html");
//				requestDispatcher.forward(request, response);
			}
			else
			{
				PrintWriter out=response.getWriter();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("registeruser.html");
				requestDispatcher.include(request, response);
				out.print("<h2>&nbsp;&nbsp;&nbsp;&nbsp; invalid credentials</h2>");
			}
		}
		catch (IllegalStateException | JsonSyntaxException e)
		{
			System.out.println("catch error");
		}
		catch(BusinessException e) {
			e.getMessage();
		}
		
		}

		
	}


