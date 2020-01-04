package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.*;
import dto.CourseDTO;

public class WishDAO {
   
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
   
   
   public List<CourseDTO> getProWTable(String profId) {
      
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
               cdto.setTotal(getWishTotal(cdto.getCourseId(), cdto.getClassNum()));
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
   //위시테이블에 없으면 0, 있으면 0보다 큰 숫자 반환
   
    public int isInWish(String courseId, int classNum, int stuId, int wishId) {
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Connection conn = null;
       int countCourse = 1;
       
       conn = getConnection();
       
       String query = "select count(*) as countCourse " +
                   "from wish " +
                   "where c_id=? and class_num=? and stu_id=? and wish_id= ?";
                   
       
       try {
          stmt = conn.prepareStatement(query);
          stmt.setString(1, courseId);
          stmt.setInt(2, classNum);
          stmt.setInt(3, stuId);
          stmt.setInt(4, wishId);
          rs = stmt.executeQuery();
          if(rs.next()) {
             countCourse = rs.getInt("countCourse");
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
       return countCourse;
       
    }

   
    public List<CourseDTO> getSearchedCourse(String cate, String key) {
        // TODO Auto-generated method stub
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CourseDTO> list = new ArrayList<CourseDTO>();

        String query = "select c.c_id id, c.enroll_stu enroll, c.cname name, c.class_num cnum, d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2, c.credit credit, p.pname pname "
              + "from course c, course_dinfo d1, course_dinfo d2, prof p "
              + "where c.c_id = d1.c_id and c.class_num = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num  "
              + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
                 + "(c.c_id, c.class_num) in (select c_id, class_num "
                 +  "from course_dinfo "
                 +  "group by c_id, class_num "
                 +  "having count(*) = 1 ))"
              + "and d1.times >= d2.times and p.prof_id = c.prof_id and ";

        if (cate.equals("학수번호")) {
           query += "c.c_id  like ?";
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
              cdto.setEnroll(rs.getInt("enroll"));
              cdto.setpName(rs.getString("pname"));
              cdto.setTotal(getWishTotal(cdto.getCourseId(), cdto.getClassNum()));
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

   
    public int[] getWishId(int stuId) {
        // TODO Auto-generated method stub
         PreparedStatement stmt = null;
         ResultSet rs = null;
         Connection conn = null;
         int[] wishlist = null;
         conn = getConnection();
        
        String query = "select count(distinct wish_id) countwishid "
              + "from wish "
              + "where stu_id = ?";
        
        String query2 = "select distinct wish_id "
              + "from wish "
              + "where stu_id = ? "
              + "order by wish_id ";
        
         try {
              stmt = conn.prepareStatement(query);
              stmt.setInt(1, stuId);
             
              rs = stmt.executeQuery();
           
              int n = 0;
              if(rs.next()) {
                 n = rs.getInt("countwishid");
              }
              
              wishlist = new int[n];
              
              stmt.close();
            stmt = conn.prepareStatement(query2);
            stmt.setInt(1, stuId);    
            
            rs = stmt.executeQuery();
            
            int i = 0;
            while(rs.next()) {
                    wishlist[i] = rs.getInt("wish_id");
                    i++;
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
           return wishlist;
        
        
        
     }

   
   public List<CourseDTO> getStudentWish(int id) {
      // TODO Auto-generated method stub
      return null;
      //getTable과 비슷해서 안만드는 걸로 
   }
   

   
      public int getWishTotal(String courseId, int classNum) {
         // TODO Auto-generated method stub
         PreparedStatement stmt = null;
         ResultSet rs = null;
         Connection conn = null;
         int total = 0;
         
         conn = getConnection();
         
         String query = "select count(*) as total " +
                     "from wish join student on wish.stu_id = student.stu_id " +
                     "where wish.wish_id = student.final_wish and c_id=? and class_num=? ";
                     
         
         try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseId);
            stmt.setInt(2, classNum);
            rs = stmt.executeQuery();
            if(rs.next()) {
               total = rs.getInt("total");
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
         return total;
      }

   
   public void setStudentFinalId(int fwish, int stuId) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      String query = "update student "
            + "set final_wish = ? " 
            + "where stu_id = ? ";
      
      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, fwish);
         stmt.setInt(2, stuId);
         stmt.executeUpdate();
                     
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
      
   }

   
   public void insertWish(int wishNum, int stuId) {
      // TODO Auto-generated method stub
	   
      
   }
   
   

   
   public void deleteWish(int wishNum, int stuId) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      String query = "delete from wish "
            + "where stu_id = ? and wish_id = ? ";
      
      
      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, stuId);
         stmt.setInt(2, wishNum);
         stmt.executeUpdate();
                     
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
      

      
   }
   
   

   
   public int insertCourse(String courseId, int classNum, int wishNum, int stuId) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
     
      
      String query = "insert into wish "
            + "values(?, ?, ?, ? ,?)";
      
      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, stuId);
         stmt.setString(2, courseId);
         stmt.setInt(3, classNum);
         stmt.setInt(4, wishNum);
         stmt.setInt(5, 100);
         stmt.executeUpdate();
                     
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

   
   public void deleteCourse(String courseId, int classNum, int wishNum, int stuId) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      String query = "delete from wish "
            + "where c_id = ? and class_num = ? and stu_id = ? and wish_id = ? ";
      
      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setString(1, courseId);
         stmt.setInt(2, classNum);
         stmt.setInt(3, stuId);
         stmt.setInt(4, wishNum);
         stmt.executeUpdate();
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
      
      
   }

   
   public void setOrder(int order, int wishNum, String courseId, int stuId, int classNum) {
	      // TODO Auto-generated method stub
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      
	      String query = "update wish "
	            + "set orders = ? "
	            + "where stu_id = ? and c_id = ? and wish_id = ? and class_num = ?";
	      
	      conn = getConnection();
	      try {
	         stmt = conn.prepareStatement(query);
	         stmt.setInt(1, order);
	         stmt.setInt(2, stuId);
	         stmt.setString(3, courseId);
	         stmt.setInt(4, wishNum);
	         stmt.setInt(5, classNum);
	         stmt.executeUpdate();
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
	      
	   }

   
   public List<CourseDTO> getTable(int wishNum, int stuId) {
      // TODO Auto-generated method stub
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      List<CourseDTO> list = new ArrayList<CourseDTO>();
      
      String query = "select w.c_id id, c.enroll_stu enroll, c.cname name, c.class_num cnum, d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2, c.credit credit, p.pname pname, w.orders orders "
              + "from wish w, course c, course_dinfo d1, course_dinfo d2, prof p "
              + "where w.c_id = c.c_id and w.class_num = c.class_num and c.c_id = d1.c_id and c.class_num = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num  "
              + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
                 + "(c.c_id, c.class_num) in (select c_id, class_num "
                 +  "from course_dinfo "
                 +  "group by c_id, class_num "
                 +  "having count(*) = 1 )) "
              + "and d1.times >= d2.times and p.prof_id = c.prof_id and w.stu_id = ? and w.wish_id = ? "
              + "order by orders ";

         
            
      conn = getConnection();
      try {
         stmt = conn.prepareStatement(query);
         stmt.setInt(1, stuId);
         stmt.setInt(2, wishNum);
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
            cdto.setTotal(getWishTotal(cdto.getCourseId(), cdto.getClassNum()));
            cdto.setOrder(rs.getInt("orders"));
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

   
   public List<CourseDTO> getRecCourse(int wishNum, int stuId) {
	      // TODO Auto-generated method stub
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      Statement stmt2 = null;
	      ResultSet rs = null;
	      List<CourseDTO> list = new ArrayList<CourseDTO>();
	      
	      String query = "create or replace view recView as "
	            + "select distinct d.c_id cid, d.class_num cnum, cname, course.prof_id pid, course.credit credit "
	            + "from course_dinfo d, course "
	            + "where course.c_id = d.c_id and course.class_num = d.class_num and (d.c_id, d.class_num) not in (select c_id, class_num "
	            + "                                      from course_dinfo d "
	            + "                                      where (day_week, times) in (select distinct d.day_week dday_week, d.times dtimes "
	            + "                                                                  from wish w, course_dinfo d "
	            + "                                                                  where w.c_id = d.c_id and w.class_num = d.class_num and w.stu_id = " + stuId + " and w.wish_id = " + wishNum + "))";
	      
	      String query2 = "select c.cid id, c.cname name, c.cnum cnum, d1.day_week week1, d2.day_week week2, d1.times time1, d2.times time2, d1.classroom room1, d2.classroom room2, c.credit credit, p.pname pname "
	              + "from recView c, course_dinfo d1, course_dinfo d2, prof p "
	              + "where c.cid = d1.c_id and c.cnum = d1.class_num and d1.c_id = d2.c_id and d1.class_num = d2.class_num  "
	              + "and (not(d1.day_week = d1.day_week and d1.times = d2.times and d1.classroom = d2.classroom) or " 
	                 + "(c.cid, c.cnum) in (select c_id, class_num "
	                 +  "from course_dinfo "
	                 +  "group by c_id, class_num "
	                 +  "having count(*) = 1 ))"
	              + "and d1.times >= d2.times and p.prof_id = c.pid";
	      
	      
	      
	      conn = getConnection();
	      try {
	         stmt2 = conn.createStatement();
	         stmt2.executeQuery(query);
	         stmt2.close();
	         
	         stmt = conn.prepareStatement(query2);
	      
	     
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
	            cdto.setTotal(getWishTotal(cdto.getCourseId(), cdto.getClassNum()));
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

}