package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.takes.SearchSessionUtils;
import controller.user.UserSessionUtils;
import dto.ProfDTO;
import dto.StudentDTO;
import model.dao.UserDAO;

public class PMoveMainController implements Controller{

	private UserDAO userDAO = new UserDAO();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int profId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		
		ProfDTO dto = userDAO.getProfInfo(profId);
		request.setAttribute("profInfo", dto);
		
		return "/user/pmenu.jsp";
	}

}
