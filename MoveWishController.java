package controller.wish;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.takes.SearchSessionUtils;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import dto.CourseDTO;
import model.dao.TakesDAO;
import model.dao.WishDAO;

public class MoveWishController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	private TakesDAO takesDAO = new TakesDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
		System.out.println("ohoh");
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		int wishId = takesDAO.getStuWishId(stuId);
		System.out.println("wishId:" + wishId);
		if(WishIdSessionUtils.hasWishId(request.getSession()) == false) {
			HttpSession session = request.getSession();
			wishId = takesDAO.getStuWishId(stuId);
			System.out.println("wishId2:" + wishId);
			session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, wishId);
		}
		System.out.println("wishId3:" + wishId);
		if(request.getParameter("w_id") != null) {
			wishId = Integer.parseInt(request.getParameter("w_id"));
			HttpSession session = request.getSession();
	        session.setAttribute(WishIdSessionUtils.WISHID_SESSION_KEY, wishId);
		}
		System.out.println("wishId4:" + wishId);
		wishId = Integer.parseInt(WishIdSessionUtils.getWishId(request.getSession()));
		System.out.println("wishId5:" + wishId);
		
		List<CourseDTO> wishList = wishDAO.getTable(wishId, stuId);
		String[] nameList = {"월", "화", "수", "목", "금"};

		List<CourseDTO> searchList = null;
		if (SearchSessionUtils.getSearchKey(request.getSession()) != null) {
			searchList = SearchSessionUtils.getSearchKey(request.getSession());
        }
		int[] wishIdList = wishDAO.getWishId(stuId);
		
		request.setAttribute("wishIdList", wishIdList); 
		request.setAttribute("wishList", wishList);
		request.setAttribute("searchList", searchList);
		request.setAttribute("selectWishId", wishId);
		request.setAttribute("selectFWishId", takesDAO.getStuWishId(stuId));
		request.setAttribute("nameList", nameList);
		return "/wish/wish.jsp";
	}

}
