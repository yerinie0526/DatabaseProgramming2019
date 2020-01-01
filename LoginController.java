package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.dao.WishDAO;
import model.service.UserManager;

public class LoginController implements Controller {
	//private UserDAO userDAO = new UserDAO();
	private WishDAO wishDAO = new WishDAO();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String cate = request.getParameter("cate");
		
		try {
			// 모델에 로그인 처리를 위임
			UserManager manager = UserManager.getInstance();
			manager.login(userId, password, cate);
	
			// 세션에 사용자 이이디 저장
			HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, userId);
            
            if(cate.equals("student")) {
            	int[] wishIdList = wishDAO.getWishId(Integer.parseInt(userId));
        		if(wishIdList.length == 0) {
        			wishDAO.insertCourse("0", 0, 1, Integer.parseInt(userId));
        			
        		}
            	return "redirect:/user/menu";	
            }

            return "redirect:/user/pmenu";
            
		} catch (Exception e) {
			/* UserNotFoundException이나 PasswordMismatchException 발생 시
			 * 다시 login form을 사용자에게 전송하고 오류 메세지도 출력
			 */
            request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
            return "/user/login.jsp";			
		}	
    }
}
