package controller.wish;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import dto.CourseDTO;
import model.dao.WishDAO;

public class InsertByRecController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		
		String c_id = request.getParameter("c_id");
		int class_num = Integer.parseInt(request.getParameter("class_num"));
		int wish_id = Integer.parseInt(WishIdSessionUtils.getWishId(request.getSession()));
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));		
		int check = wishDAO.isInWish(c_id, class_num, stuId, wish_id);
		
		if(check != 0) {
			request.setAttribute("insertFailed", true);
			request.setAttribute("exception", "이미 신청하였습니다.");
			return "/user/wish";
		}
		else{
			wishDAO.insertCourse(c_id, class_num, wish_id, stuId);
		}

		return "redirect:/user/wish";
		
	}

}
