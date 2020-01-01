package controller.wish;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import model.dao.WishDAO;

public class InsertWishController implements Controller {
	private WishDAO wishDAO = new WishDAO(); 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		int[] wishIdList = wishDAO.getWishId(stuId);
		if(wishIdList.length > 20) {
			request.setAttribute("insertFailed", true);
			request.setAttribute("exception", "���ø���Ʈ�� 20������ �߰��� �� �ֽ��ϴ�.");
			return "/user/wish";
		}
		
		wishDAO.insertCourse("0", 0, wishIdList[wishIdList.length-1] + 1, stuId);
		
		HttpSession session = request.getSession();
        session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, wishIdList[wishIdList.length-1] + 1);
		return "redirect:/user/wish";
	}

}
