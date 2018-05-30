package controller;

import DAO.Impl.BaseDAOImpl;
import DAO.Impl.FollowDAOImpl;
import DAO.Impl.SpotDAOImpl;
import DAO.Impl.StrategyDAOImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.SpotEntity;
import model.StrategyEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

import java.util.List;

@Controller
@RequestMapping("/spot")
@CrossOrigin("http://localhost:8081")
public class SpotController extends BaseController<SpotEntity> {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    SpotDAOImpl spotDAO = (SpotDAOImpl) context.getBean("spotDAOImpl");

    BaseDAOImpl<SpotEntity> spotEntityBaseDAO = (BaseDAOImpl<SpotEntity>)context.getBean("baseDaoImpl");
    StrategyDAOImpl strategyDAO = new StrategyDAOImpl();

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

    @RequestMapping(value="/findSpotAndStrategies",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse findSpotAndStrategies(SpotEntity spotEntity){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try{
            JSONObject data = new JSONObject();

            SpotEntity cur = spotEntityBaseDAO.findById(spotEntity);
            data.put("spot",cur);

            List<JSONObject> strategies = strategyDAO.findStrategyWithWriterName(spotEntity.getId());
            data.put("strategies",strategies);
            response.setData(data);
        }catch(Exception ex){
            ex.printStackTrace();
            response.setResCode("-1");
            response.setResMsg("error");
        }
        return response;
    }
}
