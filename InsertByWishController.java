package controller.takes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.dao.TakesDAO;

public class InsertByWishController implements Controller {
	private TakesDAO takesDAO = new TakesDAO();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		String courseId = request.getParameter("courseId");
		int classNum = Integer.parseInt(request.getParameter("classNum"));
		
		
		
		if(takesDAO.restrict(courseId, classNum) == false) {
			request.setAttribute("insertFailed", true);
			request.setAttribute("exception", "수강인원을 초과하였습니다.");
			return "/user/takes";
		}
		
		int check = takesDAO.isInTakes(courseId, stuId);
		
		if(check != 0) {
			request.setAttribute("insertFailed", true);
			request.setAttribute("exception", "이미 신청하였습니다.");
			return "/user/takes";
		}
		if(takesDAO.checkTime(courseId, classNum, stuId) == 1) {
			request.setAttribute("insertFailed", true);
			request.setAttribute("exception", "시간이 중복 됩니다.");
			return "/user/takes";
		}

		takesDAO.updateTakes(courseId, classNum, stuId);
		return "redirect:/user/takes";
	}

}
