package controller.takes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;

public class ViewSearchController implements Controller {
	private TakesDAO takesDAO = new TakesDAO();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		String cate = request.getParameter("cate");
		String key = request.getParameter("key");
		
		List<CourseDTO> list = takesDAO.getSearchCourse(cate, key);
		
		HttpSession session = request.getSession();
        session.setAttribute(SearchSessionUtils.SEARCH_KEY, list);
        
		return "redirect:/user/takes";
	}
	
}
