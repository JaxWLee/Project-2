package serve;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import model.Reimbursement;
import service.EmployeeService;
import service.ManagerService;

public class PostHelper {
	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals("/Project1/login/submit.do")) {
			// attempt to login and validate
			Employee employee = EmployeeService.login(request.getParameter("username").toString(),
					request.getParameter("password").toString());
			if (employee.getUsername() == "null") {
				response.setStatus(204);
			} else 

				if (employee.getManager() == 1) {
					request.getSession().setAttribute("username", employee.getUsername());
					request.getSession().setAttribute("manager", employee.getManager());
					System.out.println("in manager");
					response.setStatus(201);
				} else if (employee.getManager() == 0) {
					request.getSession().setAttribute("username", employee.getUsername());
					request.getSession().setAttribute("manager", employee.getManager());
					response.setStatus(202);
				}
		}
		if (uri.equals("/Project1/employee/reimburse/submit.do")) {
			// submit new reimbursement request
			Reimbursement reimbursement = new Reimbursement();
			reimbursement.setValue(Double.parseDouble(request.getParameter("value")));
			reimbursement.setSubmittedBy(request.getSession().getAttribute("username").toString());
			EmployeeService.submitReimbursement(reimbursement);
			response.setStatus(201);
		}
		if (uri.equals("/Project1/employee/info/submit.do")) {
			// update employee information
			System.out.println("in update");
			Employee employee = EmployeeService.viewInfo(request.getSession().getAttribute("username").toString());
			employee.setFirst_name(request.getParameter("first_name"));
			employee.setLast_name(request.getParameter("last_name"));
			System.out.println(request.getParameter("first_name"));
			System.out.println(request.getParameter("last_name"));
			EmployeeService.updateInfo(employee);
			response.setStatus(201);
		}
		if (uri.equals("/Project1/manager/pending/update.do")) {
			// update one pending requests
			ManagerService.updateRequest(Integer.parseInt(request.getParameter("id").toString()), request.getParameter("status").toString(), request.getSession().getAttribute("username").toString());
			response.setStatus(200);
		}
		if (uri.equals("/Project1/manager/employee/request.do")) {
			// view all requests by a certain employee
			System.out.println(request.getParameter("username").toString());
			List <Reimbursement> reimbursements = ManagerService.viewReimbursementsByEmployee(request.getParameter("username").toString());
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
		
	}
}
