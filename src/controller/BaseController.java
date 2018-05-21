package controller;

import DAO.Impl.BaseDAOImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.BasicResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Controller
@CrossOrigin("http://localhost:8081")
public abstract class BaseController<T> {
    @RequestMapping(value="/add",method={RequestMethod.POST})
    public @ResponseBody
    BasicResponse add(T t, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");

        BaseDAOImpl<T> baseDAO = new BaseDAOImpl<>();
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

    @RequestMapping(value="/login",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse login(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            BaseDAOImpl<T> baseDAO = new BaseDAOImpl<>();
            T result = baseDAO.findOne(t);
            if(result!=null){
                Method m = t.getClass().getMethod("getId");
                String value = m.invoke(t).toString();
                request.getSession().setAttribute("session_id",value);

                response.setResCode("1");
                response.setResMsg("success");
            }
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

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
        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(admin.findById(t));
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
        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(admin.findList(t));
            response.setResMsg(admin.countAll(t)+"");
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
        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            T tmp = admin.findOne(t);
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
        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            request.setCharacterEncoding("utf-8");
            admin.update(t);
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
        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            admin.delete(t);
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
    BasicResponse findByQuery(T t) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(admin.findByQuery(t));
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

}
