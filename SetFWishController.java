package controller.wish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import model.dao.WishDAO;

public class SetFWishController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
	
		int fwish = Integer.parseInt(request.getParameter("fwish"));
	
		wishDAO.setStudentFinalId(fwish, stuId);
		HttpSession session = request.getSession();
        session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, fwish);

		return "/user/wish";
	}

}
