package controller.wish;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import model.dao.WishDAO;
import dto.CourseDTO;

public class GetWishTableController implements Controller{

	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		
		int wish_id= Integer.parseInt(WishIdSessionUtils.getWishId(request.getSession()));
		
		
		List<CourseDTO> tableList = wishDAO.getTable(wish_id, stuId);
			
		request.setAttribute("tableList", tableList);	
		

		return "/user/wish";
		
	}

}
