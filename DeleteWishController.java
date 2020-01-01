package controller.wish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import model.dao.TakesDAO;
import model.dao.WishDAO;

public class DeleteWishController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	private TakesDAO takesDAO = new TakesDAO();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
	
		int wish_id = Integer.parseInt(WishIdSessionUtils.getWishId(request.getSession()));
				
		wishDAO.deleteWish(wish_id, stuId);
		
		int[] wishIdList = wishDAO.getWishId(stuId);
		if(wishIdList.length == 0) {
			wishDAO.insertCourse("0", 0, 1, stuId);
			wishDAO.setStudentFinalId(1, stuId);
			HttpSession session = request.getSession();
	        session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, 1);
		}
		else if(wish_id == takesDAO.getStuWishId(stuId)){
			wishDAO.setStudentFinalId(wishIdList[0], stuId);
			HttpSession session = request.getSession();
	        session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, wishIdList[0]);
		}
		else {
			HttpSession session = request.getSession();
	        session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, wishIdList.length);
		}

		return "redirect:/user/wish";
	}

}
