package controller.takes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;
import model.dao.WishDAO;

public class MoveTakesController implements Controller {
	private TakesDAO takesDAO = new TakesDAO();
	private WishDAO wishDAO = new WishDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }

		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		int wishId = takesDAO.getStuWishId(stuId);
		List<CourseDTO> wishList = wishDAO.getTable(wishId, stuId);
		
		List<CourseDTO> takesList = takesDAO.getTable(stuId);
		
		List<CourseDTO> searchList = null;
		if (SearchSessionUtils.getSearchKey(request.getSession()) != null) {
			searchList = SearchSessionUtils.getSearchKey(request.getSession());
        }
        
		request.setAttribute("wishList", wishList);
		request.setAttribute("takesList", takesList);
		request.setAttribute("searchList", searchList);
		return "/takes/takes.jsp";
	}
	

}
