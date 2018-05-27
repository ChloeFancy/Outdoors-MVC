package controller;

import DAO.Impl.FollowDAOImpl;
import DAO.Impl.SpotDAOImpl;
import DAO.Impl.StrategyDAOImpl;
import com.alibaba.fastjson.JSONArray;
import model.SpotEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

@Controller
@RequestMapping("/spot")
@CrossOrigin("http://localhost:8081")
public class SpotController extends BaseController<SpotEntity> {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    SpotDAOImpl spotDAO = (SpotDAOImpl) context.getBean("spotDAOImpl");

    @RequestMapping(value="/fuzzyQuery",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse search(@RequestParam String keyword){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        JSONArray jsonArray = spotDAO.findSpotsNameLike(keyword);
        response.setData(jsonArray);
        if(jsonArray.size()<1){
            response.setResCode("-1");
            response.setResMsg("null");
        }
        return response;
    }
}
