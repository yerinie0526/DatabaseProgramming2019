package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.takes.*;
import controller.wish.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
       // user
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/user/form", new ForwardController("/user/login.jsp"));
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/logout", new LogoutController());
        //mappings.put("/user/infoList", new ListUserController("/user/menu.jsp"));

        // takes
        mappings.put("/takes/search", new ViewSearchController());
        mappings.put("/takes/search/insert", new InsertBySearchController());
        mappings.put("/takes/wish/insert", new InsertByWishController());
        mappings.put("/takes/list/delete", new CancelTakesController());
        //mappings.put("/wish/flist", new ShowFWishController()); //MoveMainController에서 해줌
        //mappings.put("/takes/list", new ShowTakesController());
        //밑의 GetWishTableController 와 같음
                
        // wish
        //mappings.put("/wish/list", new ShowWishController());
        mappings.put("/wish/list/delete", new DeleteCourseController());
        mappings.put("/wish/recommend", new RecommendController());
        mappings.put("/wish/recommend/insert", new InsertByRecController());
        mappings.put("/wish/set_orders", new SetOrderController());
        mappings.put("/wish/finalwish", new SetFWishController());
        mappings.put("/wish/wishNumList", new GetWishIdController());
        mappings.put("/wish/delete", new DeleteWishController());
        mappings.put("/wish/insert", new InsertWishController());
        mappings.put("/wish/search", new ViewWSearchController());
        mappings.put("/wish/search/insert", new InsertCourseController());
        
        mappings.put("/wish/table", new GetWishTableController());
        //mappings.put("/takes/table", new GetTakesTableController());
        
        // move   
        mappings.put("/user/takes", new MoveTakesController());
        mappings.put("/user/wish", new MoveWishController());
        mappings.put("/user/table", new MoveTableController());
        mappings.put("/user/menu", new MoveMainController());
        
        mappings.put("/user/ptakes", new PMoveTakesController());
        mappings.put("/user/pwish", new PMoveWishController());
        mappings.put("/user/ptable", new PMoveTableController());
        mappings.put("/user/pmenu", new PMoveMainController());
        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {   
       // 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}