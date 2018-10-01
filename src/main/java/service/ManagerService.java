package service;

import java.util.List;

import dao.ManagerDAO;
import model.Employee;
import model.Reimbursement;

public class ManagerService {
	public static List<Reimbursement> viewPending(){
		return new ManagerDAO().viewPending();
	}
	public static List<Reimbursement> viewResolved(){
		return new ManagerDAO().viewResolved();
	}
	public static boolean updateRequest(int id, String status, String username) {
		return new ManagerDAO().updateRequest(id, status, username);
	}
	public static Employee viewEmployee(String username) {
		return new ManagerDAO().viewEmployee(username);
	}
	public static List<Reimbursement> viewReimbursementsByEmployee(String username){
		return new ManagerDAO().viewReimbursementsByEmployee(username);
	}
}
