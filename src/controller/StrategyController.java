package controller;

import DAO.Impl.FollowDAOImpl;
import DAO.Impl.StrategyDAOImpl;
import DAO.Impl.UserDAOImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.StrategyEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

@Controller
@RequestMapping("/strategy")
@CrossOrigin("http://localhost:8081")
public class StrategyController extends BaseController<StrategyEntity>{

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    StrategyDAOImpl strategyDAO = (StrategyDAOImpl) context.getBean("strategyDAOImpl");

    @RequestMapping(value="/fuzzyQuery",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse search(@RequestParam String keyword){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        JSONArray jsonArray = strategyDAO.findArticlesByKeyword(keyword);
        response.setData(jsonArray);
        if(jsonArray.size()<1){
            response.setResCode("-1");
            response.setResMsg("null");
        }
        return response;
    }

    @RequestMapping(value="/getArticleAndComments",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse getArticleAndComments(@RequestParam int id){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        JSONObject result = strategyDAO.getArticleAndComments(id);
        if(result==null){
            response.setResCode("-1");
            response.setResMsg("null");
        }else{
            response.setData(result);
        }
        return response;
    }

}
