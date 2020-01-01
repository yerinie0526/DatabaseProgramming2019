package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.takes.SearchSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;

public class PMoveTableController implements Controller {
	
private TakesDAO takesDAO = new TakesDAO();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		String profId = UserSessionUtils.getLoginUserId(request.getSession());
		
		HttpSession session = request.getSession();
		if(SearchSessionUtils.hasSearchKey(session))
			session.removeAttribute(SearchSessionUtils.SEARCH_KEY);
		
		List<CourseDTO> resultList = takesDAO.getProTTable(profId);

		request.setAttribute("resultList", resultList);
		return "/user/ptable.jsp";
	}

}
