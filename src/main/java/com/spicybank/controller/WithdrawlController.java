package com.spicybank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.service.User_Details;
import com.spicybank.service.Impl.User_DetailsImpl;

/**
 * Servlet implementation class WithdrawlController
 */
public class WithdrawlController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WithdrawlController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User_Details user_Details= new User_DetailsImpl();
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if (session == null) {

			out.print("<center><h1>Please Login First</h1></center>");
			out.print("<h4><a href='/Spicy_Bank_V2'>Click here for home page </a> ");
		} 
		else {
			
			try {
				float amount=Float.parseFloat(request.getParameter("withdrawl"));
				long account = (long) session.getAttribute("account");
				float amt = user_Details.checkBalance(account);
				
				if(amount>amt)
				{
					out.print("<h3>NOT ENOUGH BALANCE</h3>");
				}
				else if(amount<=0)
				{
					out.print("<h3>Amount Should Not Be Zero</h3>");
				}
				else
				{
				user_Details.withdrawBal( amount,  amt, account);
				response.sendRedirect("useroperations.html");
				}
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			
			}
	}

}
