package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.dao.*;
import dto.*;

public class TakesDAO {
   public TakesDAO() { // 생성자
      // JDBC 드라이버 로딩 및 등록
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (ClassNotFoundException ex) {
         ex.printStackTrace();
      }
   }

   // 재사용가능
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
   
   public int checkTime(String courseId, int classNum, int stuId) {
	   Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      
	      String query = "select count(*) count "
	      		+ "from course_dinfo "
	      		+ "where c_id = ? and class_num = ? and (day_week, times) in (select distinct d.day_week dday_week, d.times dtimes "
	      		+ "from takes t, course_dinfo d "
	      		+ "where t.c_id = d.c_id and t.class_num = d.class_num and t.stu_id = ?) ";
	    		  		    		                             
	    		                                                                   	    		                                              	      
	      conn = getConnection();
	      try {
	         stmt = conn.prepareStatement(query);
	         stmt.setString(1, courseId);
	         stmt.setInt(2, classNum);
	         stmt.setInt(3, stuId);
	         rs = stmt.executeQuery();
	         
	         if(rs.next()) {
	        	 if(rs.getInt("count") > 0) {
	        		 return 1;
	        	 }
	         }
	                     
	      } catch (SQLException ex) {
	         ex.printStackTrace();
	      }finally {      // 자원 반납
	         if (rs != null) 
	            try { 
	               rs.close(); 
	            } catch (SQLException ex) { ex.printStackTrace(); }
	         if (stmt != null) 
	            try { 
	               stmt.close(); 
	            } catch (SQLException ex) { ex.printStackTrace(); }
	         if (conn != null) 
	            try { 
	               conn.close(); 
	            } catch (SQLException ex) { ex.printStackTrace(); }
	      }   
	      
	      
	      return 0;
   }
   
   public List<CourseDTO> getProTTable(String profId) {
         
         Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            List<CourseDTO> list = new ArrayList<CourseDTO>();
            
            String query = "select c.c_id id, c.enroll_stu enroll, c.cname name, c.class_num cnum, d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2, c.credit credit, p.pname pname "
                     + "from course c, course_dinfo d1, course_dinfo d2, prof p "
                     + "where c.c_id = c.c_id and c.class_num = c.class_num and c.c_id = d1.c_id and c.class_num = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num "
                     + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
                        + "(c.c_id, c.class_num) in (select c_id, class_num "
                        +  "from course_dinfo "
                        +  "group by c_id, class_num "
                        +  "having count(*) = 1 ))"
                     + "and d1.times >= d2.times and p.prof_id = c.prof_id and c.prof_id = ?";
               
                  
            conn = getConnection();
            try {
               stmt = conn.prepareStatement(query);
               stmt.setString(1, profId);
               rs = stmt.executeQuery();
               while (rs.next()) {
                  CourseDTO cdto = new CourseDTO();                  
                  cdto.setCourseId(rs.getString("id"));
                  cdto.setCourseName(rs.getString("name"));
                  cdto.setClassNum(rs.getInt("cnum"));
                  cdto.setCredit(rs.getInt("credit"));
                  cdto.setClassroom1(rs.getString("room1"));
                  cdto.setClassroom2(rs.getString("room2"));
                  cdto.setDayWeek1(rs.getString("week1"));
                  cdto.setDayWeek2(rs.getString("week2"));
                  cdto.setTime1(rs.getInt("time1"));
                  cdto.setTime2(rs.getInt("time2"));
                  cdto.setpName(rs.getString("pname"));
                  cdto.setEnroll(rs.getInt("enroll"));
                  cdto.setTotal(getTotal(cdto.getCourseId(), cdto.getClassNum()));
                  list.add(cdto);
               }
               
               
               
            } catch (SQLException ex) {
               ex.printStackTrace();
            }finally {      // 자원 반납
               if (rs != null) 
                  try { 
                     rs.close(); 
                  } catch (SQLException ex) { ex.printStackTrace(); }
               if (stmt != null) 
                  try { 
                     stmt.close(); 
                  } catch (SQLException ex) { ex.printStackTrace(); }
               if (conn != null) 
                  try { 
                     conn.close(); 
                  } catch (SQLException ex) { ex.printStackTrace(); }
            }   
            return list;
         
      }

   public int getStuWishId(int stuId) {
      // TODO Auto-generated method stub
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Connection conn = null;
      int wishId = 1;

      conn = getConnection();
      String query = "select final_wish " + "from student " + "where stu_id=?";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, stuId);
         rs = stmt.executeQuery();
         if (rs.next()) {

            wishId = rs.getInt("final_wish");

         }
         return wishId;

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (conn != null) { // 전역변수로 빼서 메인에서 클로즈해도 됨.
            try {
               conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }

      return wishId;
   }

   public int updateTakes(String courseId, int classNum, int stuId) {
      // TODO Auto-generated method stub
      PreparedStatement stmt = null;
      PreparedStatement stmt2 = null;
      Connection conn = null;
      int wishId = getStuWishId(stuId);
      int result;

      conn = getConnection();

      String query = "delete from wish " + "where c_id=? and class_num=? and stu_id=? and wish_id=?";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, courseId);
         stmt.setInt(2, classNum);
         stmt.setInt(3, stuId);
         stmt.setInt(4, wishId);

         stmt.executeUpdate();

         result = insertCourseTakes(courseId, classNum, stuId);

         // 위시, 수강 다시 가져오기

         return result;

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (conn != null) { // 전역변수로 빼서 메인에서 클로즈해도 됨.
            try {
               conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
      return -2;
   }

   public int cancelTakes(String courseId, int classNum, int stuId) {
      // TODO Auto-generated method stub
      PreparedStatement stmt = null;

      Connection conn = null;

      conn = getConnection();

      String query = "delete from takes " + "where c_id=? and class_num=? and stu_id=?";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, courseId);
         stmt.setInt(2, classNum);
         stmt.setInt(3, stuId);
         int result = stmt.executeUpdate();

         if (result > -1)
            return result;

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (conn != null) { // 전역변수로 빼서 메인에서 클로즈해도 됨.
            try {
               conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }

      return -1;
   }

   public List<CourseDTO> getTable(int stuId) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      List<CourseDTO> list = new ArrayList<CourseDTO>();
      String query = "select w.c_id id, c.cname name, c.enroll_stu enroll, w.class_num cnum, d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2, c.credit credit, p.pname pname "
            + "from takes w, course c, course_dinfo d1, course_dinfo d2, prof p "
            + "where w.c_id = c.c_id and w.class_num = c.class_num and c.c_id = d1.c_id and c.class_num = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num "
            + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
               + "(w.c_id, w.class_num) in (select c_id, class_num "
               +  "from course_dinfo "
               +  "group by c_id, class_num "
               +  "having count(*) = 1 ))"
            + "and d1.times >= d2.times and p.prof_id = c.prof_id and stu_id=?";

      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, stuId);

         rs = stmt.executeQuery();

         while (rs.next()) {
            CourseDTO cdto = new CourseDTO();
            cdto.setCourseId(rs.getString("id"));
            cdto.setCourseName(rs.getString("name"));
            cdto.setClassNum(rs.getInt("cnum"));
            cdto.setCredit(rs.getInt("credit"));
            cdto.setClassroom1(rs.getString("room1"));
            cdto.setClassroom2(rs.getString("room2"));
            cdto.setDayWeek1(rs.getString("week1"));
               cdto.setDayWeek2(rs.getString("week2"));
               cdto.setTime1(rs.getInt("time1"));
               cdto.setTime2(rs.getInt("time2"));
               cdto.setpName(rs.getString("pname"));
               cdto.setEnroll(rs.getInt("enroll"));
            cdto.setTotal(getTotal(cdto.getCourseId(), cdto.getClassNum()));
            list.add(cdto);
         }

      } catch (SQLException ex) {
         ex.printStackTrace();
      } finally { // 자원 반납
         if (rs != null)
            try {
               rs.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         if (stmt != null)
            try {
               stmt.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         if (conn != null)
            try {
               conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
      }
      return list;
   }

   public List<CourseDTO> getSearchCourse(String cate, String key) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      List<CourseDTO> list = new ArrayList<CourseDTO>();

      String query = "select c.c_id id, c.cname name, c.enroll_stu enroll, c.class_num cnum, d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2, c.credit credit, p.pname pname "
            + "from course c, course_dinfo d1, course_dinfo d2, prof p "
            + "where c.c_id = d1.c_id and c.class_num = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num  "
            + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
               + "(c.c_id, c.class_num) in (select c_id, class_num "
               +  "from course_dinfo "
               +  "group by c_id, class_num "
               +  "having count(*) = 1 ))"
            + "and d1.times >= d2.times and p.prof_id = c.prof_id and ";

      if (cate.equals("학수번호")) {
         query += "c.c_id like ?";
      } else if (cate.equals("교수")) {
         query += "p.pname like ?";

      } else if (cate.equals("학과")) {
         query += "c.dname like ?";

      } else if (cate.equals("과목명")) {
         query += "c.cname like ?";

      }

      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, "%" +key+"%");
         rs = stmt.executeQuery();
         while (rs.next()) {
            CourseDTO cdto = new CourseDTO();
            cdto.setCourseId(rs.getString("id"));
            cdto.setCourseName(rs.getString("name"));
            cdto.setClassNum(rs.getInt("cnum"));
            cdto.setCredit(rs.getInt("credit"));
            cdto.setClassroom1(rs.getString("room1"));
            cdto.setClassroom2(rs.getString("room2"));
            cdto.setDayWeek1(rs.getString("week1"));
            cdto.setDayWeek2(rs.getString("week2"));
            cdto.setTime1(rs.getInt("time1"));
            cdto.setTime2(rs.getInt("time2"));
            cdto.setpName(rs.getString("pname"));
            cdto.setEnroll(rs.getInt("enroll"));
            cdto.setTotal(getTotal(cdto.getCourseId(), cdto.getClassNum()));
            list.add(cdto);
         }

      } catch (SQLException ex) {
         ex.printStackTrace();
      } finally { // 자원 반납
         if (rs != null)
            try {
               rs.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         if (stmt != null)
            try {
               stmt.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         if (conn != null)
            try {
               conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
      }

      return list;

   }

   public int getTotal(String courseId, int classNum) {
      // TODO Auto-generated method stub
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Connection conn = null;
      int total = 0;

      conn = getConnection();

      String query = "select count(*) as total " + "from takes " + "where c_id=? and class_num=? ";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, courseId);
         stmt.setInt(2, classNum);
         rs = stmt.executeQuery();
         if (rs.next()) {
            total = rs.getInt("total");
         }

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (conn != null) { // 전역변수로 빼서 메인에서 클로즈해도 됨.
            try {
               conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
      return total;
   }

   public int insertCourseTakes(String courseId, int classNum, int stuId) {
      // TODO Auto-generated method stub
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Connection conn = null;

      conn = getConnection();

      String query = "insert into takes " + "values(?,?,?)";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, stuId);
         stmt.setString(2, courseId);
         stmt.setInt(3, classNum);

         int result = stmt.executeUpdate();

      } catch (SQLException ex) {
         ex.printStackTrace();
      } finally { // 자원 반납
         if (rs != null)
            try {
               rs.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         if (stmt != null)
            try {
               stmt.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }

         if (conn != null)
            try {
               conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
      }

      return 0;
   }

   // 수강테이블에 없으면 0, 있으면 0보다 큰 숫자 반환
   public int isInTakes(String courseId, int stuId) {
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Connection conn = null;
      int countCourse = 0;

      conn = getConnection();

      String query = "select count(*) as countCourse " + "from takes " + "where c_id=? and stu_id=? ";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, courseId);
         stmt.setInt(2, stuId);
         rs = stmt.executeQuery();
         if (rs.next()) {
            countCourse = rs.getInt("countCourse");
         }

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (conn != null) { // 전역변수로 빼서 메인에서 클로즈해도 됨.
            try {
               conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
      return countCourse;

   }

   public boolean restrict(String courseId, int classNum) {
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Connection conn = null;
      int enroll = 0;
      conn = getConnection();

      String query = "select enroll_stu " + "from course " + "where c_id=? and class_num=? ";

      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, courseId);
         stmt.setInt(2, classNum);
         rs = stmt.executeQuery();

         if (rs.next()) {
            enroll = rs.getInt("enroll_stu");

         }

         int total = getTotal(courseId, classNum);

         if (total < enroll) {
            return true;
         }

      } catch (SQLException ex) {
         ex.printStackTrace();
      } finally { // 자원 반납
         if (rs != null)
            try {
               rs.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         if (stmt != null)
            try {
               stmt.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }

         if (conn != null)
            try {
               conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
      }

      return false;
   }
   
//   public boolean onemore(String courseId, int classNum, int stuId) {
//	      PreparedStatement stmt = null;
//	      ResultSet rs = null;
//	      Connection conn = null;
//	      int enroll = 0;
//	      conn = getConnection();
//
//	      String query = "select count(*) as total " + "from takes t, course c, course_dinfo " 
//	    		  + "where t.c_id=c.c_id and t.class_num=c.class_num and c.c_id=d.c_id and c.class_num=d.class_num "
//	    		  + "and (?,?,?,?) in (select and stu_id=?";
//
//	      try {
//	         stmt = conn.prepareStatement(query);
//	         stmt.setString(1, courseId);
//	         stmt.setInt(2, classNum);
//	         rs = stmt.executeQuery();
//
//	         if (rs.next()) {
//	            enroll = rs.getInt("enroll_stu");
//
//	         }
//
//	         int total = getTotal(courseId, classNum);
//
//	         if (total < enroll) {
//	            return true;
//	         }
//
//	      } catch (SQLException ex) {
//	         ex.printStackTrace();
//	      } finally { // 자원 반납
//	         if (rs != null)
//	            try {
//	               rs.close();
//	            } catch (SQLException ex) {
//	               ex.printStackTrace();
//	            }
//	         if (stmt != null)
//	            try {
//	               stmt.close();
//	            } catch (SQLException ex) {
//	               ex.printStackTrace();
//	            }
//
//	         if (conn != null)
//	            try {
//	               conn.close();
//	            } catch (SQLException ex) {
//	               ex.printStackTrace();
//	            }
//	      }
//
//	      return false;
//	   }
//   
//   public List<CourseDTO> getCourseInfo(int stuId, String c_id, int classNum) {
//	      // TODO Auto-generated method stub
//	      Connection conn = null;
//	      PreparedStatement stmt = null;
//	      ResultSet rs = null;
//	      List<CourseDTO> list = new ArrayList<CourseDTO>();
//	      String query = "select d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2 "
//	            + "from takes w, course c, course_dinfo d1, course_dinfo d2 "
//	            + "where w.c_id = c.c_id and w.class_num = c.class_num and c.c_id = d1.c_id and c.class_num = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num "
//	            + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
//	               + "(w.c_id, w.class_num) in (select c_id, class_num "
//	               +  "from course_dinfo "
//	               +  "group by c_id, class_num "
//	               +  "having count(*) = 1 ))"
//	            + "and d1.times >= d2.times and stu_id=? and w.c_id=? and w.class_num=? ";
//
//	      conn = getConnection();
//	      try {
//	         stmt = conn.prepareStatement(query);
//	         stmt.setInt(1, stuId);
//	         stmt.setString(2, c_id);
//	         stmt.setInt(3, classNum);
//
//	         rs = stmt.executeQuery();
//
//	         while (rs.next()) {
//	            CourseDTO cdto = new CourseDTO();
//	           
//	            cdto.setDayWeek1(rs.getString("week1"));
//	              cdto.setDayWeek2(rs.getString("week2"));
//	              cdto.setTime1(rs.getInt("time1"));
//	              cdto.setTime2(rs.getInt("time2"));
//	             
//	            
//	            list.add(cdto);
//	         }
//
//	      } catch (SQLException ex) {
//	         ex.printStackTrace();
//	      } finally { // 자원 반납
//	         if (rs != null)
//	            try {
//	               rs.close();
//	            } catch (SQLException ex) {
//	               ex.printStackTrace();
//	            }
//	         if (stmt != null)
//	            try {
//	               stmt.close();
//	            } catch (SQLException ex) {
//	               ex.printStackTrace();
//	            }
//	         if (conn != null)
//	            try {
//	               conn.close();
//	            } catch (SQLException ex) {
//	               ex.printStackTrace();
//	            }
//	      }
//	      return list;
//	   }

}