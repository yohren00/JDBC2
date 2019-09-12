package com.len.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class StudentDbUtil {
	// 建立資料庫連結池
	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {

		this.dataSource = dataSource;

	}

	// 建立收尋所有資料方式
	public List<Student> getStudent() throws Exception {

		// 建立List讓student資訊可放入
		List<Student> student = new ArrayList<>();

		Student theStudnet = null;

		// 建立SQL連結資料庫
		Connection conn = null;

		// 建立一個用來執行SQL語句，並得到結果的物件。
		PreparedStatement stmt = null;

		// 建立ResultSet取得Statement得到的結果，加以收尋裡面的資料
		ResultSet rs = null;

		try {

			// 建立連結
			conn = dataSource.getConnection();

			// 建立SQL select語法
			String sql = "select*from student order by last_name";
			stmt = conn.prepareStatement(sql);

			// 執行結果
			rs = stmt.executeQuery(sql);

			// 收尋rs執行後的所有資料
			while (rs.next()) {

				// 取出id資料
				int id = rs.getInt("id");

				// 取出first_name資料
				String firstname = rs.getString("first_name");

				// 取出last_name資料
				String lastname = rs.getString("last_name");

				// 取出email資料
				String email = rs.getString("email");

				// 把取出的資料整合放入List中
				theStudnet = new Student(id, firstname, lastname, email);

				student.add(theStudnet);
			}
			return student;

		} finally {
			close(conn, stmt, rs);
		}

	}

	// 建立新增方式
	public void addStudent(Student theStudent) throws Exception {

		// 建立SQL連結資料庫
		Connection conn = null;

		// 建立一個用來執行SQL語句，並得到結果的物件。
		PreparedStatement stmt = null;

		try {
			// 建立連結
			conn = dataSource.getConnection();

			// 建立SQL語法
			String sql = "insert into student (first_name,last_name,email) value(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, theStudent.getFirstName());
			stmt.setString(2, theStudent.getLastName());
			stmt.setString(3, theStudent.getEmail());

			// 執行結果
			stmt.executeUpdate();

		}

		finally {
			close(conn, stmt, null);
		}

	}

	public Student loadStudent(String id) throws Exception {

		Student theStudnet = null;

		// 建立資料庫連結
		Connection conn = null;

		// 建立SQL語法
		PreparedStatement stmt = null;

		// 建立ResultSet取得Statement得到的結果，加以收尋裡面的資料
		ResultSet rs = null;

		int theId;

		try {

			// 把字串轉換成int
			theId = Integer.parseInt(id);

			// 建立連結
			conn = dataSource.getConnection();

			// 建立SQL語法,並使用
			String sql = "select* from student where id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, theId);

			// 執行結果
			rs = stmt.executeQuery();

			// 收尋rs執行後的所有資料
			if (rs.next()) {

				// 取出first_name資料
				String firstname = rs.getString("first_name");

				// 取出last_name資料
				String lastname = rs.getString("last_name");

				// 取出email資料
				String email = rs.getString("email");

				// 把取出的資料放入theStudnet中
				theStudnet = new Student(theId, firstname, lastname, email);

			} else {
				throw new Exception("發生錯誤收尋不到此ID:" + theId);
			}

			return theStudnet;

		} finally {
			close(conn, stmt, rs);
		}

	}

	// 修改方法
	public void updateStudent(Student theStudent) throws Exception {

		Connection conn = null;

		PreparedStatement stmt = null;

		try {

			// 連結資料庫
			conn = dataSource.getConnection();

			// 建立SQL語法
			String sql = "update student set first_name = ? , last_name = ? , email = ? where id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, theStudent.getFirstName());
			stmt.setString(2, theStudent.getLastName());
			stmt.setString(3, theStudent.getEmail());
			stmt.setInt(4, theStudent.getId());

			// 執行語法
			stmt.executeUpdate();

		} finally {
			close(conn, stmt, null);
		}
	}

	// 刪除方法
	public void deleteStudent(String theId) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		int id;

		try {

			id = Integer.parseInt(theId);

			// 連結資料庫
			conn = dataSource.getConnection();

			// 使用SQL語法
			String sql = "delete from student where id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			// 執行語法
			stmt.executeUpdate();

		} finally {
			close(conn, stmt, null);
		}

	}
	
	public List<Student> searchStudent(String searchStudent) throws Exception {

		List<Student> students = new ArrayList<>();
		Student student = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int studentId;

		try {

			// 建立連線
			conn = dataSource.getConnection();

		
			if (searchStudent != null && searchStudent.trim().length() > 0) {

				// 建立語法
				String sql = 
						"select* from student where lower(first_name) like ? or lower(last_name) like ? ";
				stmt = conn.prepareStatement(sql);
				String theSearchStudent1 = "%" + searchStudent.toLowerCase() + "%";
				String theSearchStudent2 = searchStudent.toLowerCase() + "%";
				stmt.setString(1, theSearchStudent1);
				stmt.setString(1, theSearchStudent2);
				stmt.setString(2, theSearchStudent1);
				stmt.setString(2, theSearchStudent2);

			
			} else {

				String sql = "select* from student order by last_name";
				stmt = conn.prepareStatement(sql);

			}
			// 執行語法
			rs = stmt.executeQuery();

			while (rs.next()) {

				// 取出id資料
				int id = rs.getInt("id");

				// 取出first_name資料
				String firstname = rs.getString("first_name");

				// 取出last_name資料
				String lastname = rs.getString("last_name");

				// 取出email資料
				String email = rs.getString("email");

				// 把取出的資料整合放入List中
				student = new Student(id, firstname, lastname, email);
				students.add(student);

			}

			return students;

		} finally {

			close(conn, stmt, rs);

		}

	}

	// 建立關閉方式
	private void close(Connection conn, PreparedStatement stmt, ResultSet rs) {

		try {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
