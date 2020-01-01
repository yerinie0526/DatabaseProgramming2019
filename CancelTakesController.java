package controller.takes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.dao.TakesDAO;

public class CancelTakesController implements Controller {
	private TakesDAO takesDAO = new TakesDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		String courseId = request.getParameter("courseId");
		String classNum = request.getParameter("classNum");
		
		takesDAO.cancelTakes(courseId, Integer.parseInt(classNum), stuId);
		
		return "redirect:/user/takes";
	}
}
