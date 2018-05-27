package controller;

import DAO.Impl.BaseDAOImpl;
import DAO.Impl.StrategyDAOImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.StrategyEntity;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.unsignFromCookie;

import javax.servlet.http.HttpServletRequest;
import java.rmi.server.ExportException;

@Controller
@RequestMapping("/strategy")
@CrossOrigin("http://localhost:8081")
public class StrategyController extends BaseController<StrategyEntity>{

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    StrategyDAOImpl strategyDAO = new StrategyDAOImpl();
    BaseDAOImpl<StrategyEntity> strategyEntityBaseDAO = (BaseDAOImpl<StrategyEntity>) context.getBean("baseDaoImpl");

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


    @RequestMapping(value="/addStrategy",method={RequestMethod.POST})
    public @ResponseBody
    BasicResponse addStrategy(@RequestParam int idSpot,
                              @RequestParam String title,
                              @RequestParam String content,
                              HttpServletRequest httpServletRequest){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        UserEntity fromToken = unsignFromCookie.unsign(httpServletRequest);
        if(fromToken==null){
            response.setResCode("-1");
            response.setResMsg("null");
        }else{
            try{
                StrategyEntity strategyEntity = new StrategyEntity();
                strategyEntity.setIdWriter(fromToken.getId());
                strategyEntity.setContent(content);
                strategyEntity.setTitle(title);
                strategyEntity.setIdSpot(idSpot);
                strategyEntityBaseDAO.insert(strategyEntity);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return response;
    }


}
