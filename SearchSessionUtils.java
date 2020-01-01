package controller.takes;

import java.util.List;

import javax.servlet.http.HttpSession;

import dto.CourseDTO;

public class SearchSessionUtils {
	public static final String SEARCH_KEY = "searchKey";
	
	public static List<CourseDTO> getSearchKey(HttpSession session) {
        List<CourseDTO> searchKey = (List<CourseDTO>)session.getAttribute(SEARCH_KEY);
        return searchKey;
    }
	
	public static boolean hasSearchKey(HttpSession session) {
		
        if (getSearchKey(session) == null) {
            return false;
        }
        return true;
    }
}
