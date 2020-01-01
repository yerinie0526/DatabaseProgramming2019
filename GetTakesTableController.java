//package controller.takes;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import controller.Controller;
//import controller.user.UserSessionUtils;
//import dto.CourseDTO;
//import model.dao.TakesDAO;
//
//public class GetTakesTableController implements Controller {
//	
//	private TakesDAO takesDAO = new TakesDAO();
//	@Override
//	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// TODO Auto-generated method stub
//		if (!UserSessionUtils.hasLogined(request.getSession())) {
//            return "redirect:/user/form";
//        }
//		
//		int stuId = Integer.parseInt(UserSessionUtils.getLoginUserId(request.getSession()));
//			
//		List<CourseDTO> tableList = takesDAO.getTable(stuId);
//		
//		request.setAttribute("tableList", tableList);	
//
//		return "redirect:/user/wish";
//		
//	}
//
//
//}
