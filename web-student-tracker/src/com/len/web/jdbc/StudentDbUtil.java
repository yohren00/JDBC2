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
	// �إ߸�Ʈw�s����
	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {

		this.dataSource = dataSource;

	}

	// �إߦ��M�Ҧ���Ƥ覡
	public List<Student> getStudent() throws Exception {

		// �إ�List��student��T�i��J
		List<Student> student = new ArrayList<>();

		Student theStudnet = null;

		// �إ�SQL�s����Ʈw
		Connection conn = null;

		// �إߤ@�ӥΨӰ���SQL�y�y�A�ño�쵲�G������C
		PreparedStatement stmt = null;

		// �إ�ResultSet���oStatement�o�쪺���G�A�[�H���M�̭������
		ResultSet rs = null;

		try {

			// �إ߳s��
			conn = dataSource.getConnection();

			// �إ�SQL select�y�k
			String sql = "select*from student order by last_name";
			stmt = conn.prepareStatement(sql);

			// ���浲�G
			rs = stmt.executeQuery(sql);

			// ���Mrs����᪺�Ҧ����
			while (rs.next()) {

				// ���Xid���
				int id = rs.getInt("id");

				// ���Xfirst_name���
				String firstname = rs.getString("first_name");

				// ���Xlast_name���
				String lastname = rs.getString("last_name");

				// ���Xemail���
				String email = rs.getString("email");

				// ����X����ƾ�X��JList��
				theStudnet = new Student(id, firstname, lastname, email);

				student.add(theStudnet);
			}
			return student;

		} finally {
			close(conn, stmt, rs);
		}

	}

	// �إ߷s�W�覡
	public void addStudent(Student theStudent) throws Exception {

		// �إ�SQL�s����Ʈw
		Connection conn = null;

		// �إߤ@�ӥΨӰ���SQL�y�y�A�ño�쵲�G������C
		PreparedStatement stmt = null;

		try {
			// �إ߳s��
			conn = dataSource.getConnection();

			// �إ�SQL�y�k
			String sql = "insert into student (first_name,last_name,email) value(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, theStudent.getFirstName());
			stmt.setString(2, theStudent.getLastName());
			stmt.setString(3, theStudent.getEmail());

			// ���浲�G
			stmt.executeUpdate();

		}

		finally {
			close(conn, stmt, null);
		}

	}

	public Student loadStudent(String id) throws Exception {

		Student theStudnet = null;

		// �إ߸�Ʈw�s��
		Connection conn = null;

		// �إ�SQL�y�k
		PreparedStatement stmt = null;

		// �إ�ResultSet���oStatement�o�쪺���G�A�[�H���M�̭������
		ResultSet rs = null;

		int theId;

		try {

			// ��r���ഫ��int
			theId = Integer.parseInt(id);

			// �إ߳s��
			conn = dataSource.getConnection();

			// �إ�SQL�y�k,�èϥ�
			String sql = "select* from student where id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, theId);

			// ���浲�G
			rs = stmt.executeQuery();

			// ���Mrs����᪺�Ҧ����
			if (rs.next()) {

				// ���Xfirst_name���
				String firstname = rs.getString("first_name");

				// ���Xlast_name���
				String lastname = rs.getString("last_name");

				// ���Xemail���
				String email = rs.getString("email");

				// ����X����Ʃ�JtheStudnet��
				theStudnet = new Student(theId, firstname, lastname, email);

			} else {
				throw new Exception("�o�Ϳ��~���M���즹ID:" + theId);
			}

			return theStudnet;

		} finally {
			close(conn, stmt, rs);
		}

	}

	// �ק��k
	public void updateStudent(Student theStudent) throws Exception {

		Connection conn = null;

		PreparedStatement stmt = null;

		try {

			// �s����Ʈw
			conn = dataSource.getConnection();

			// �إ�SQL�y�k
			String sql = "update student set first_name = ? , last_name = ? , email = ? where id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, theStudent.getFirstName());
			stmt.setString(2, theStudent.getLastName());
			stmt.setString(3, theStudent.getEmail());
			stmt.setInt(4, theStudent.getId());

			// ����y�k
			stmt.executeUpdate();

		} finally {
			close(conn, stmt, null);
		}
	}

	// �R����k
	public void deleteStudent(String theId) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		int id;

		try {

			id = Integer.parseInt(theId);

			// �s����Ʈw
			conn = dataSource.getConnection();

			// �ϥ�SQL�y�k
			String sql = "delete from student where id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			// ����y�k
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

			// �إ߳s�u
			conn = dataSource.getConnection();

		
			if (searchStudent != null && searchStudent.trim().length() > 0) {

				// �إ߻y�k
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
			// ����y�k
			rs = stmt.executeQuery();

			while (rs.next()) {

				// ���Xid���
				int id = rs.getInt("id");

				// ���Xfirst_name���
				String firstname = rs.getString("first_name");

				// ���Xlast_name���
				String lastname = rs.getString("last_name");

				// ���Xemail���
				String email = rs.getString("email");

				// ����X����ƾ�X��JList��
				student = new Student(id, firstname, lastname, email);
				students.add(student);

			}

			return students;

		} finally {

			close(conn, stmt, rs);

		}

	}

	// �إ������覡
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
