package controller;

import DAO.Impl.StrategyDAOImpl;
import DAO.Impl.UserDAOImpl;
import com.alibaba.fastjson.JSONArray;
import model.StrategyEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

@Controller
@RequestMapping("/strategy")
@CrossOrigin("http://localhost:8081")
public class StrategyController extends BaseController<StrategyEntity>{

    @RequestMapping(value="/fuzzyQuery",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse search(@RequestParam String keyword){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        StrategyDAOImpl strategyDAO = new StrategyDAOImpl();
        JSONArray jsonArray = strategyDAO.findArticlesByKeyword(keyword);
        response.setData(jsonArray);
        if(jsonArray.size()<1){
            response.setResCode("-1");
            response.setResMsg("null");
        }
        return response;
    }
}
