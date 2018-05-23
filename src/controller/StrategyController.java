package controller;

import DAO.Impl.StrategyDAOImpl;
import DAO.Impl.UserDAOImpl;
import model.StrategyEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

@Controller
@RequestMapping("/strategy")
@CrossOrigin("http://localhost:8081")
public class StrategyController extends BaseController<StrategyEntity>{

    @RequestMapping(value="/search",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse search(@RequestParam String keyword){
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");//用户密码不一致
        response.setResMsg("Error");
        StrategyDAOImpl strategyDAO = new StrategyDAOImpl();
        response.setData(strategyDAO.findArticlesByKeyword(keyword));
        return response;
    }
}
