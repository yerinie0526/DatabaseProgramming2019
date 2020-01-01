package controller.wish;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import controller.user.WishIdSessionUtils;
import dto.CourseDTO;
import model.dao.WishDAO;

public class SetOrderController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/form";
        }
	
		
		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
		System.out.println("hi1");
		int wish_id = Integer.parseInt(WishIdSessionUtils.getWishId(request.getSession()));
		System.out.println("hi3");
		String[] orders = request.getParameterValues("orders");
		System.out.println("hi2");
		System.out.print(orders.length);
		
		List<CourseDTO> wishList = wishDAO.getTable(wish_id, stuId);
		
	      for (int i = 0; i < orders.length; i++) {
	          int order = Integer.parseInt(orders[i]);
	          if(order >= 100) {
	             request.setAttribute("insertFailed", true);
	             request.setAttribute("exception", "우선순위 설정은 최대 99 입니다.");
	             return "/user/wish";
	          }
	       }
	       for (int i = 0; i < orders.length; i++) {
	          int order = Integer.parseInt(orders[i]);
	          if (orders[i].contentEquals(""))
	             order = 100;
	          System.out.print(order);
	          String c_id = wishList.get(i).getCourseId();
	          int classNum = wishList.get(i).getClassNum();
	          wishDAO.setOrder(order, wish_id, c_id, stuId, classNum);
	       }

		return "redirect:/user/wish";
	}

}
