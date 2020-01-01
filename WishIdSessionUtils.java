package controller.user;

import javax.servlet.http.HttpSession;

public class WishIdSessionUtils {
	public static final String WISHID_SESSION_KEY = "swishId";

    /* 현재 로그인한 사용자의 ID를 구함 */
    public static String getWishId(HttpSession session) {
        String swishId = String.valueOf(session.getAttribute(WISHID_SESSION_KEY));
        return swishId;
    }

    /* 로그인한 상태인지를 검사 */
    public static boolean hasWishId(HttpSession session) {
        if (!getWishId(session).equals("null")) {
        	System.out.println(getWishId(session));
            return true;
        }
        return false;
    }

}
