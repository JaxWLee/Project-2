package serve;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import model.Employee;
import model.Reimbursement;
import service.EmployeeService;
import service.ManagerService;

import org.json.simple.JSONObject;

public class GetHelper {
	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals("/Project1.do")) {
			// grab index page
			response.sendRedirect("/Project1/index.html");
		}
		if (uri.equals("/Project1/logout.do")) {
			request.getSession().invalidate();
			response.setStatus(200);
		}
		if (uri.equals("/Project1/username.do")) {
			// get the username to dispaly in dashboard
			System.out.println(request.getSession().getAttribute("username").toString());
			response.getWriter().append(request.getSession().getAttribute("username").toString());
		}
		if (uri.equals("/Project1/dashboard.do")) {
			// get dashboard
			System.out.println(request.getSession().getAttribute("manager").toString());
			if (request.getSession().getAttribute("manager").toString().equals("1")) {
				response.setStatus(201);
			}
			if(request.getSession().getAttribute("manager").toString().equals("0")) {
				response.setStatus(202);
			}

		}

		// Employee Routes
		if (uri.equals("/Project1/employee.do")) {
			// get employee dashboard
			response.sendRedirect("/Project1/employee.html");
		}
		if (uri.equals("/Project1/employee/pending.do")) {
			// get pending requests for the employee logged in
			List<Reimbursement> reimbursements = EmployeeService.viewPending(request.getSession().getAttribute("username").toString());
			response.getWriter().append("[");
			for (int i = 0; i < reimbursements.toArray().length; i++) {
				if(i != reimbursements.toArray().length - 1) {
					response.getWriter().append(reimbursements.toArray()[i].toString());
					response.getWriter().append(",");
				} else {
					response.getWriter().append(reimbursements.toArray()[i].toString());
				}
			} 
			response.getWriter().append("]");

		}
		if (uri.equals("/Project1/employee/resolved.do")) {
			// view resolved requests
			List<Reimbursement> reimbursements = EmployeeService.viewResolved(request.getSession().getAttribute("username").toString());
			System.out.println(reimbursements);
			response.getWriter().append("[");
			for (int i = 0; i < reimbursements.toArray().length; i++) {
				if(i != reimbursements.toArray().length - 1) {
					response.getWriter().append(reimbursements.toArray()[i].toString());
					response.getWriter().append(",");
				} else {
					response.getWriter().append(reimbursements.toArray()[i].toString());
				}
			} 
			response.getWriter().append("]");
		}
		if (uri.equals("/Project1/employee/reimburse.do")) {
			// get reimbursement form
			response.sendRedirect("/Project1/create.html");
		}
		if (uri.equals("/Project1/employee/info.do")) {
			// view employee information page
			response.sendRedirect("/Project1/info.html");
			
		}
		if(uri.equals("/Project1/employee/info/get.do")) {
			// view employee information
			Employee employee = EmployeeService.viewInfo(request.getSession().getAttribute("username").toString());
			JSONObject json = new JSONObject();
			json.put("first_name", employee.getFirst_name());
			json.put("last_name", employee.getLast_name());
			response.getWriter().append(json.toJSONString());
						
		}
		// Manager Routes
		if (uri.equals("/Project1/manager.do")) {
			// get manager dashboard
			response.sendRedirect("/Project1/manager.html");
		}
		if (uri.equals("/Project1/manager/resolved.do")) {
			// view all resolved requests
			List <Reimbursement> reimbursements = ManagerService.viewResolved();
			response.getWriter().append("[");
			for (int i = 0; i < reimbursements.toArray().length; i++) {
				if(i != reimbursements.toArray().length - 1) {
					response.getWriter().append(reimbursements.toArray()[i].toString());
					response.getWriter().append(",");
				} else {
					response.getWriter().append(reimbursements.toArray()[i].toString());
				}
			} 
			response.getWriter().append("]");
		}
		if (uri.equals("/Project1/manager/pending.do")) {
			// view all pending requests
			List <Reimbursement> reimbursements = ManagerService.viewPending();
			response.getWriter().append("[");
			for (int i = 0; i < reimbursements.toArray().length; i++) {
				if(i != reimbursements.toArray().length - 1) {
					response.getWriter().append(reimbursements.toArray()[i].toString());
					response.getWriter().append(",");
				} else {
					response.getWriter().append(reimbursements.toArray()[i].toString());
				}
			} 
			response.getWriter().append("]");
		}
		if (uri.equals("/Project1/manager/employee.do")) {
			// view one employee
			Employee employee = ManagerService.viewEmployee(request.getParameter("username"));
			JSONObject json = new JSONObject();
			json.put("username", employee.getUsername());
			json.put("first_name", employee.getFirst_name());
			json.put("last_name", employee.getLast_name());
			response.getWriter().append(json.toJSONString());
		}

	}
}
