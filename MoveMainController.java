package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.takes.SearchSessionUtils;
import dto.StudentDTO;
import model.dao.UserDAO;

public class MoveMainController implements Controller {
	
	private UserDAO userDAO = new UserDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		HttpSession session = request.getSession();
		if(SearchSessionUtils.hasSearchKey(session)) {
			session.removeAttribute(SearchSessionUtils.SEARCH_KEY);
		}
		
		
		StudentDTO dto = userDAO.getStudentInfo(stuId);
		request.setAttribute("studentInfo", dto);
		return "/user/menu.jsp";
	}

}
