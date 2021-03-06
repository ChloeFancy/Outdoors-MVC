package controller;

import DAO.Impl.BaseDAOImpl;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.BasicResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JWT;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Controller
@CrossOrigin("http://localhost:8081")
public abstract class BaseController<T> {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    BaseDAOImpl<T> baseDAO = (BaseDAOImpl<T>) context.getBean("baseDaoImpl");

    @RequestMapping(value="/add",method={RequestMethod.POST})
    public @ResponseBody
    BasicResponse add(T t, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");

//        BaseDAOImpl<T> baseDAO = new BaseDAOImpl<>();
        try{
            request.setCharacterEncoding("utf-8");
            if(baseDAO.insert(t).equals("success")){
                response.setResCode("1");
                response.setResMsg("success");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

//    @RequestMapping(value="/login",method = {RequestMethod.POST})
//    public @ResponseBody
//    BasicResponse login(T t, HttpServletRequest request) {
//        BasicResponse response = new BasicResponse();
//        response.setResCode("-1");
//        response.setResMsg("Error");
//        try{
//            BaseDAOImpl<T> baseDAO = new BaseDAOImpl<>();
//            T result = baseDAO.findOne(t);
//            if(result!=null){
//                //生成用户登录的token标记登录状态
//                String token = JWT.sign(t, 30L * 24L * 3600L * 1000L);
//                if (token != null) {
//                    JSONObject tokenObj = new JSONObject();
//                    tokenObj.put("token",token);
//                    response.setData(tokenObj);
//                }
//                response.setResCode("1");
//                response.setResMsg("success");
//            }
//            return response;
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return response;
//    }

    @RequestMapping(value="/logout",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse logout(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            request.getSession().removeAttribute("session_id");
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value="/findById",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findById(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(baseDAO.findById(t));
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/findAll",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findAll(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(baseDAO.findList(t));
            response.setResMsg(baseDAO.countAll(t)+"");
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/findOne",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findOne(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            T tmp = baseDAO.findOne(t);
            response.setData(tmp);
            if(tmp==null){
                //fail
                response.setResCode("0");
            }else{
                //success
                response.setResCode("1");
                response.setResMsg("success");
            }
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/update",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse update(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            request.setCharacterEncoding("utf-8");
            baseDAO.update(t);
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/delete",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse delete(T t,HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            baseDAO.delete(t);
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            response.setResCode("-1");
            response.setResMsg("Error");
            ex.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value="/findByQuery",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findByQuery(T t,HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(baseDAO.findByQuery(t));
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            response.setResCode("-1");
            response.setResMsg("Error");
            ex.printStackTrace();
        }
        return response;
    }
    @RequestMapping(value="/test",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse test(T t){
        BasicResponse response = new BasicResponse();
        response.setData(t);
        return response;
    }

}
