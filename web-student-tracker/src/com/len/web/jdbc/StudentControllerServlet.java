package com.len.web.jdbc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
      
	@Override
	public void init() throws ServletException {
		super.init();
		
		//��l��
		StudentDbUtil stdDb =new StudentDbUtil(dataSource);
		studentDbUtil = stdDb;
		
	}   
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			//�إ߫��s�覡
			String Command = request.getParameter("command");
			
			//�p�G�S�����s�ʧ@�A�N����list���覡
			if(Command==null) {
				
				Command="List";
			}
			
			//�Ȥ�ݨϥΫ��s�ɡA�ӫ��s������覡
			switch(Command){
				
			case "ADD":
				addStudent(request,response);
				break;
			
			case "LOAD":
				loadStudent(request,response);
				break;
				
			case "UPDATE":
				updateStudent(request,response);
				break;
				
			case "DELETE":
				deleteStudent(request,response);
				break;
				
			case "SEARCH":
				searchStudent(request,response);
				break;
				
			default:
				listStudent(request,response);
			
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	
	private void searchStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//�q�����o�ǥ͸��
		String searchStudent = request.getParameter("theSearchName");
		
		//���M�Ӿǥ͸��
		List<Student> students = studentDbUtil.searchStudent(searchStudent);
		
		//�����Ȥ�ݽШD�����
		request.setAttribute("STUDENT_LIST", students);
		
		//�Ȥ�ݪ��ШD�A�νլ������w��ܪ���m
		RequestDispatcher dispatcher =
		request.getRequestDispatcher("/search-student.jsp");
				
		//�o�e�X�h
		dispatcher.forward(request, response);
		
	}

	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�q�����o�n�R����ID
		String theId = request.getParameter("studentId");
		
		//�R����ID�ǥ͸��
		studentDbUtil.deleteStudent(theId);
	
		//�s�W���᪺��ƪ�^list����
		listStudent(request,response);
				
	}

	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�q�����o�n��s�����
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//�ק�ǥ͸��
		Student theStudent = new Student(id, firstName, lastName, email);
		studentDbUtil.updateStudent(theStudent);
		
		//�s�W���᪺��ƪ�^list����
		listStudent(request,response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�q��椤Ū���n��ܪ�ID
		String id = request.getParameter("studentId");
		
		//���ostudent���
		Student theStudent = studentDbUtil.loadStudent(id);
		
		//�����Ȥ�ݽШD�����
		request.setAttribute("STUDENT_LOAD",theStudent);
		
		//�Ȥ�ݪ��ШD�A�νլ������w��ܪ���m
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/update-student.jsp");
		
		//�o�e�X�h
		dispatcher.forward(request, response);
		
	}
	
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�q��椤���o�Ȥ�ݶǰe��student�T��
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//�s�W�ǥ͸��
		Student theStudent = new Student(firstName,lastName,email);
		studentDbUtil.addStudent(theStudent);
		
		//�s�W���᪺��ƪ�^list����
		listStudent(request,response);
	}
	
	
	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���ostudent�����
		List<Student> students = studentDbUtil.getStudent();
		
		//�����Ȥ�ݽШD�����
		request.setAttribute("STUDENT_LIST", students);
		
		//�Ȥ�ݪ��ШD�A�νլ������w��ܪ���m
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		
		//��o�X�h
		dispatcher.forward(request, response);
		
	}

}
