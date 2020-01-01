package controller.wish;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;
import model.dao.WishDAO;

public class PMoveWishController implements Controller{
	
	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		String profId = UserSessionUtils.getLoginUserId(request.getSession());
		List<CourseDTO> pwishList = wishDAO.getProWTable(profId);
 
		request.setAttribute("pwishList", pwishList);
		return "/wish/pwish.jsp";
	}

}
