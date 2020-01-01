package controller.wish;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.takes.SearchSessionUtils;
import dto.CourseDTO;
import model.dao.WishDAO;

public class ViewWSearchController implements Controller {
	
	private WishDAO wishDAO = new WishDAO();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String cate = request.getParameter("cate");
		String key = request.getParameter("key");

		List<CourseDTO> list = wishDAO.getSearchedCourse(cate, key);
		
		HttpSession session = request.getSession();
        session.setAttribute(SearchSessionUtils.SEARCH_KEY, list);

		return "redirect:/user/wish";
		
	}


}
