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
import com.spicybank.service.Emp_Details;
import com.spicybank.service.Impl.Emp_DetailsImpl;



/**
 * Servlet implementation class EmpLoginController
 */
public class EmpLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Emp_Details emp_Details=new Emp_DetailsImpl();
		Emp emp=new Emp(0, null, null);
		response.setContentType("text/html");
		String reg=request.getParameter("username");
		RequestDispatcher requestDispatcher=null;
		if(reg.matches("^[0-9]*$") && reg!=null)
		{
		emp.setEmpid(Integer.parseInt(request.getParameter("username")));
		emp.setEmppassword(request.getParameter("password"));
		

			try {
				if(emp_Details.isValidEmp(emp)==true) {
					HttpSession session=request.getSession();
					response.sendRedirect("http://localhost:8080/Spicy_Bank_V2/index.html");
				}

				else {
				
				PrintWriter out=response.getWriter();
				requestDispatcher=request.getRequestDispatcher("emplogin.html");
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
				requestDispatcher=request.getRequestDispatcher("emplogin.html");
				requestDispatcher.include(request, response);
				out.print("<h2>&nbsp;&nbsp;&nbsp;&nbsp; invalid credentials</h2>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
