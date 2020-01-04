package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ProfDTO;
import dto.StudentDTO;
import model.User;

/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * USERINFO 테이블에 사용자 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class UserDAO {
	private JDBCUtil jdbcUtil = null;
	
	public UserDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
	}
	
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
	
	
	
	public String findUser(String userId, String cate) throws SQLException {
		String sql;
		if(cate.equals("student")) {
			sql = "select pw as pw from student where stu_id=?";
			jdbcUtil.setSqlAndParameters(sql, new Object[] {Integer.parseInt(userId)});
		}
		else {
			sql = "select prof_pw as pw from prof where prof_id=?";
			jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});
		}
                

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {						
				String pw = rs.getString("pw");
				return pw;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return "NO";
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
	
	public ProfDTO getProfInfo(int profId) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		conn = getConnection();
		
		String query = "select prof_id, pname, dname " + 
						"from prof " +
						"where prof_id=?";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, profId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				ProfDTO dto = new ProfDTO();
				dto.setProf_id(rs.getInt("prof_id"));
				dto.setName(rs.getString("pname"));
				dto.setDept(rs.getString("dname"));
				
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
	


	
	
	
	
	
	
	
	
		
	/**
	 * 사용자 관리 테이블에 새로운 사용자 생성.
	 */
	public int create(User user) throws SQLException {
		String sql = "INSERT INTO USERINFO VALUES (?, ?, ?, ?, ?, ?)";		
		Object[] param = new Object[] {user.getUserId(), user.getPassword(), 
						user.getName(), user.getEmail(), user.getPhone(), 
						(user.getCommId()!=0) ? user.getCommId() : null };				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil 에 insert문과 매개 변수 설정
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;			
	}

	/**
	 * 기존의 사용자 정보를 수정.
	 */
	public int update(User user) throws SQLException {
		String sql = "UPDATE USERINFO "
					+ "SET password=?, name=?, email=?, phone=?, commId=? "
					+ "WHERE userid=?";
		Object[] param = new Object[] {user.getPassword(), user.getName(), 
					user.getEmail(), user.getPhone(), 
					(user.getCommId()!=0) ? user.getCommId() : null, 
					user.getUserId()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil에 update문과 매개 변수 설정
			
		try {				
			int result = jdbcUtil.executeUpdate();	// update 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}

	/**
	 * 사용자 ID에 해당하는 사용자를 삭제.
	 */
	public int remove(String userId) throws SQLException {
		String sql = "DELETE FROM USERINFO WHERE userid=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 delete문과 매개 변수 설정

		try {				
			int result = jdbcUtil.executeUpdate();	// delete 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}

	/**
	 * 주어진 사용자 ID에 해당하는 사용자 정보를 데이터베이스에서 찾아 User 도메인 클래스에 
	 * 저장하여 반환.
	 */
	
	/**
	 * 전체 사용자 정보를 검색하여 List에 저장 및 반환
	 */
	public List<User> findUserList() throws SQLException {
        String sql = "SELECT userId, name, email, NVL(commId,0) AS commId, cName " 
        		   + "FROM USERINFO u LEFT OUTER JOIN Community c ON u.commId = c.cId "
        		   + "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil에 query문 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
			List<User> userList = new ArrayList<User>();	// User들의 리스트 생성
			while (rs.next()) {
				User user = new User(			// User 객체를 생성하여 현재 행의 정보를 저장
					rs.getString("userId"),
					null,
					rs.getString("name"),
					rs.getString("email"),
					null,
					rs.getInt("commId"),
					rs.getString("cName"));
				userList.add(user);				// List에 User 객체 저장
			}		
			return userList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	/**
	 * 전체 사용자 정보를 검색한 후 현재 페이지와 페이지당 출력할 사용자 수를 이용하여
	 * 해당하는 사용자 정보만을 List에 저장하여 반환.
	 */
	public List<User> findUserList(int currentPage, int countPerPage) throws SQLException {
		String sql = "SELECT userId, name, email, NVL(commId, 0), cName " 
					+ "FROM USERINFO u LEFT OUTER JOIN Community c ON u.commId = c.cId "
					+ "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null,					// JDBCUtil에 query문 설정
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll 가능
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();				// query 실행			
			int start = ((currentPage-1) * countPerPage) + 1;	// 출력을 시작할 행 번호 계산
			if ((start >= 0) && rs.absolute(start)) {			// 커서를 시작 행으로 이동
				List<User> userList = new ArrayList<User>();	// User들의 리스트 생성
				do {
					User user = new User(			// User 객체를 생성하여 현재 행의 정보를 저장
						rs.getString("userId"),
						null,
						rs.getString("name"),
						rs.getString("email"),
						null,
						rs.getInt("commId"),
						rs.getString("cName"));
					userList.add(user);							// 리스트에 User 객체 저장
				} while ((rs.next()) && (--countPerPage > 0));		
				return userList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}

	/**
	 * 특정 커뮤니티에 속한 사용자들을 검색하여 List에 저장 및 반환
	 */
	public List<User> findUsersInCommunity(int communityId) throws SQLException {
        String sql = "SELECT userId, name FROM UserInfo "
     				+ "WHERE commId = ?";                         
		jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId});	// JDBCUtil에 query문과 매개 변수 설정
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			List<User> memList = new ArrayList<User>();	// member들의 리스트 생성
			while (rs.next()) {
				User member = new User(			// User 객체를 생성하여 현재 행의 정보를 저장
					rs.getString("userId"),
					rs.getString("name"));
				memList.add(member);			// List에 Community 객체 저장
			}		
			return memList;					
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	/**
	 * 특정 커뮤니티에 속한 사용자들의 수를 count하여 반환
	 */
	public int getNumberOfUsersInCommunity(int communityId) {
		String sql = "SELECT COUNT(userId) FROM UserInfo "
     				+ "WHERE commId = ?";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId});	// JDBCUtil에 query문과 매개 변수 설정
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			rs.next();										
			return rs.getInt(1);			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return 0;
	}
	
	/**
	 * 주어진 사용자 ID에 해당하는 사용자가 존재하는지 검사 
	 */
	public boolean existingUser(String userId) throws SQLException {
		String sql = "SELECT count(*) FROM USERINFO WHERE userid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return false;
	}

}
