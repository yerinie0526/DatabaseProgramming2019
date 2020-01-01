package controller.wish;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.dao.WishDAO;

public class GetWishIdController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
	
		int[] wishIdList = wishDAO.getWishId(stuId);
		
		request.setAttribute("wishIdList", wishIdList);	

		return "redirect:/user/wish";
	}

}
