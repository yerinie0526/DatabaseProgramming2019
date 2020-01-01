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
			// �𵨿� �α��� ó���� ����
			UserManager manager = UserManager.getInstance();
			manager.login(userId, password, cate);
	
			// ���ǿ� ����� ���̵� ����
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
			/* UserNotFoundException�̳� PasswordMismatchException �߻� ��
			 * �ٽ� login form�� ����ڿ��� �����ϰ� ���� �޼����� ���
			 */
            request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
            return "/user/login.jsp";			
		}	
    }
}
