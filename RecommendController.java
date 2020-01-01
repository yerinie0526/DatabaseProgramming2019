package controller.wish;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;
import model.dao.WishDAO;

public class RecommendController implements Controller{
	
	private WishDAO wishDAO = new WishDAO();
	private TakesDAO takesDAO = new TakesDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		int wish_id = takesDAO.getStuWishId(stuId);
		if (WishIdSessionUtils.hasWishId(request.getSession()) == true) {
			String wishId = WishIdSessionUtils.getWishId(request.getSession());
			wish_id = Integer.parseInt(wishId);
			//System.out.println("me");
        }
				
		
		List<CourseDTO> recList = wishDAO.getRecCourse(wish_id, stuId);
		request.setAttribute("recList", recList);

		return "/user/wish";
		
	}

}
