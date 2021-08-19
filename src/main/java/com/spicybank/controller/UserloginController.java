package com.spicybank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spicybank.exceptions.BusinessException;
import com.spicybank.model.Emp;
import com.spicybank.model.User;
import com.spicybank.service.Emp_Details;
import com.spicybank.service.User_Details;
import com.spicybank.service.Impl.Emp_DetailsImpl;
import com.spicybank.service.Impl.User_DetailsImpl;

/**
 * Servlet implementation class UserloginController
 */
public class UserloginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserloginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		User_Details user_Details=new User_DetailsImpl();
		response.setContentType("text/html");
		String reg=request.getParameter("uname");
		RequestDispatcher requestDispatcher=null;
		if(reg.matches("^[0-9]*$") && reg!=null)
		{
			user.setUserid(Integer.parseInt(request.getParameter("uname")));
			user.setPass(request.getParameter("pass"));

			try {
				if(user_Details.isValidUser(user)==true) {
					System.out.println("im in try");

					long acc= user_Details.getAccountNumber(user.getUserid());
					HttpSession session=request.getSession();
					session.setAttribute("account", acc);
					response.sendRedirect("useroperations.html");
				}

				else {
				
				PrintWriter out=response.getWriter();
				requestDispatcher=request.getRequestDispatcher("loginuser.html");
				requestDispatcher.include(request, response);
				out.print("invalid credentials");
				}
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
				PrintWriter out=response.getWriter();
				requestDispatcher=request.getRequestDispatcher("loginuser.html");
				requestDispatcher.include(request, response);
				out.print("<h2>&nbsp;&nbsp;&nbsp;&nbsp; invalid credentials</h2>");
		}
		
	}

}
