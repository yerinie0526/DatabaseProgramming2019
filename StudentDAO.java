package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.dao.*;

import dto.StudentDTO;

public class StudentDAO {

	public StudentDAO() {	// 생성자 
		// JDBC 드라이버 로딩 및 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
	}
	//재사용가능
	private static Connection getConnection() {
		String url = // "jdbc:oracle:thin:@localhost:1521:xe";
						"jdbc:oracle:thin:@202.20.119.117:1521:orcl";	
		String user = "dbp0204";
		String passwd = "20170204";

		// DBMS와의 연결 획득
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}	 
		return conn;
	}
	
	
	public StudentDTO getStudentInfo(int stuId) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		conn = getConnection();
		
		String query = "select stu_id, sname, dname, avail_credit, regi_seme " + 
						"from student " +
						"where stu_id=?";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, stuId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setStu_id(rs.getInt("stu_id"));
				dto.setName(rs.getString("sname"));
				dto.setDept(rs.getString("dname"));
				dto.setAvailCredit(rs.getInt("avail_credit"));
				dto.setRegiSeme(rs.getInt("regi_seme"));
				
				return dto;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(stmt != null) {
				try {stmt.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(conn != null) { //전역변수로 빼서 메인에서 클로즈해도 됨.
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
		return null;
	}


}
