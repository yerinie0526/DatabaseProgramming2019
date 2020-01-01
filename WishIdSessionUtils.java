package controller.user;

import javax.servlet.http.HttpSession;

public class WishIdSessionUtils {
	public static final String WISHID_SESSION_KEY = "swishId";

    /* ���� �α����� ������� ID�� ���� */
    public static String getWishId(HttpSession session) {
        String swishId = String.valueOf(session.getAttribute(WISHID_SESSION_KEY));
        return swishId;
    }

    /* �α����� ���������� �˻� */
    public static boolean hasWishId(HttpSession session) {
        if (!getWishId(session).equals("null")) {
        	System.out.println(getWishId(session));
            return true;
        }
        return false;
    }

}
