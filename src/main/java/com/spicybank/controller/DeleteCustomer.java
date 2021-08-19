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
import com.spicybank.model.UserTransaction;
import com.spicybank.service.Emp_Details;
import com.spicybank.service.Impl.Emp_DetailsImpl;

/**
 * Servlet implementation class DeleteCustomer
 */
public class DeleteCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Emp_Details emp_Details = new Emp_DetailsImpl();
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		if (session == null) {

			out.print("<center><h1>Please Login First</h1></center>");
			out.print("<h4><a href='/Spicy_Bank_V2'>Click here for home page </a> ");
		}

		else {
					String reg;
					reg = request.getParameter("condition");
					
					System.out.println(reg);
					
					if (reg.matches("^[0-9]*$") && reg != "") {
						long account = Long.parseLong(request.getParameter("condition"));
						response.sendRedirect("deletecustomer.html");
					
						try {
							emp_Details.deleteCustomerAccount(account);
						} catch (BusinessException e) {
							
							e.printStackTrace();
						}
					}

				else {
					out.print("Invalid Data");

					
				}
			}
		
	}

}

