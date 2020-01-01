package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.takes.SearchSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;

public class MoveTableController implements Controller {
   private TakesDAO takesDAO = new TakesDAO();
   
   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      // TODO Auto-generated method stub
      if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
      int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
      
      HttpSession session = request.getSession();
      if(SearchSessionUtils.hasSearchKey(session))
         session.removeAttribute(SearchSessionUtils.SEARCH_KEY);
      
      HttpSession session1 = request.getSession();
      if(WishIdSessionUtils.hasWishId(request.getSession())) {
    	  request.getSession().removeAttribute(WishIdSessionUtils.WISHID_SESSION_KEY);
      }
      
      List<CourseDTO> resultList = takesDAO.getTable(stuId);
      String[] nameList = {"월", "화", "수", "목", "금"};


      request.setAttribute("resultList", resultList);
      request.setAttribute("nameList", nameList);
      return "/user/table.jsp";
   }

}