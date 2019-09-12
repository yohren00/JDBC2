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
		
		//初始化
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
			//建立按鈕方式
			String Command = request.getParameter("command");
			
			//如果沒有按鈕動作，就給予list的方式
			if(Command==null) {
				
				Command="List";
			}
			
			//客戶端使用按鈕時，該按鈕的執行方式
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
		
		//從表單取得學生資料
		String searchStudent = request.getParameter("theSearchName");
		
		//收尋該學生資料
		List<Student> students = studentDbUtil.searchStudent(searchStudent);
		
		//接收客戶端請求的資料
		request.setAttribute("STUDENT_LIST", students);
		
		//客戶端的請求，用調派員指定顯示的位置
		RequestDispatcher dispatcher =
		request.getRequestDispatcher("/search-student.jsp");
				
		//發送出去
		dispatcher.forward(request, response);
		
	}

	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//從表單取得要刪除的ID
		String theId = request.getParameter("studentId");
		
		//刪除該ID學生資料
		studentDbUtil.deleteStudent(theId);
	
		//新增完後的資料返回list頁面
		listStudent(request,response);
				
	}

	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//從表單取得要更新的資料
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//修改學生資料
		Student theStudent = new Student(id, firstName, lastName, email);
		studentDbUtil.updateStudent(theStudent);
		
		//新增完後的資料返回list頁面
		listStudent(request,response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//從表單中讀取要顯示的ID
		String id = request.getParameter("studentId");
		
		//取得student資料
		Student theStudent = studentDbUtil.loadStudent(id);
		
		//接收客戶端請求的資料
		request.setAttribute("STUDENT_LOAD",theStudent);
		
		//客戶端的請求，用調派員指定顯示的位置
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/update-student.jsp");
		
		//發送出去
		dispatcher.forward(request, response);
		
	}
	
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//從表單中取得客戶端傳送的student訊息
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//新增學生資料
		Student theStudent = new Student(firstName,lastName,email);
		studentDbUtil.addStudent(theStudent);
		
		//新增完後的資料返回list頁面
		listStudent(request,response);
	}
	
	
	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//取得student的資料
		List<Student> students = studentDbUtil.getStudent();
		
		//接收客戶端請求的資料
		request.setAttribute("STUDENT_LIST", students);
		
		//客戶端的請求，用調派員指定顯示的位置
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		
		//轉發出去
		dispatcher.forward(request, response);
		
	}

}
