package controller;


import DAO.Impl.BaseDAOImpl;
import DAO.Impl.StrategyDAOImpl;
import DAO.Impl.UserDAOImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.BrowseEntity;
import model.FollowEntity;
import model.StrategyEntity;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.JWT;
import util.unsignFromCookie;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
@CrossOrigin("http://localhost:8081")
public class UserController extends BaseController<UserEntity>{

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDAOImpl userDAO = (UserDAOImpl) context.getBean("userDAOImpl");

    BaseDAOImpl<UserEntity> userEntityBaseDAO = (BaseDAOImpl<UserEntity>) context.getBean("baseDaoImpl");

    BaseDAOImpl<StrategyEntity> strategyEntityBaseDAO = (BaseDAOImpl<StrategyEntity>) context.getBean("baseDaoImpl");

    BaseDAOImpl<FollowEntity> followEntityBaseDAO = (BaseDAOImpl<FollowEntity>) context.getBean("baseDaoImpl");


    @RequestMapping(value="/fuzzyQuery",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse search(@RequestParam String keyword,HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        UserEntity userEntity = unsignFromCookie.unsign(request);
        JSONArray jsonArray = userDAO.findUsersNameLike(userEntity,keyword);
        response.setData(jsonArray);
        if(jsonArray.size()<1){
            response.setResCode("-1");
            response.setResMsg("null");
        }
        return response;
    }

    @RequestMapping(value="/modifyPassword",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse modifyPassWord(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpServletRequest request) {

        BasicResponse response = new BasicResponse();
        response.setResCode("-1");//用户密码不一致
        response.setResMsg("Error");

        //获取token，还原userEntity
        UserEntity fromToken = unsignFromCookie.unsign(request);
        if(fromToken==null){
            //说明token已失效
            response.setResCode("-2");
            return response;
        }
        if(!fromToken.getPassword().equals(oldPassword)){
            //旧密码不正确
            response.setResCode("-3");
            return response;
        }

        //如果未失效且旧密码正确
        try{
            fromToken.setPassword(newPassword);
            userEntityBaseDAO.update(fromToken);
            response.setResCode("1");//修改成功
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            response.setResCode("-1");
            response.setResMsg("Error");
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/login",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse login(UserEntity userEntity, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            UserEntity result = userDAO.login(userEntity);
            if(result!=null){
                //生成用户登录的token标记登录状态
                String token = JWT.sign(result, 30L * 24L * 3600L * 1000L);
                UserEntity test = JWT.unsign(token,UserEntity.class);
                if (token != null) {
                    System.out.println(token);
                    JSONObject tokenObj = new JSONObject();
                    tokenObj.put("userId",result.getId());
                    tokenObj.put("token",token);
                    response.setData(tokenObj);
                }
                response.setResCode("1");
                response.setResMsg("success");
            }
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    //验证用户是否处于登录状态
    @RequestMapping(value="/isLogin",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse isLogin(HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            UserEntity userEntity = unsignFromCookie.unsign(request);
            if(userEntity!=null){
                //仍处于登录状态
                response.setResCode("1");
                response.setResMsg("success");
            }
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    //注册
    @RequestMapping(value="/logon",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse logon(UserEntity userEntity, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");//用户已存在
        response.setResMsg("Error");
        try{
            if(!userDAO.hasUser(userEntity)) {//如果没有出现过
                userDAO.insert(userEntity);
                response.setResCode("1");
                response.setResMsg("Success");
                response.setData(userDAO.findByQuery(userEntity).get(0));
                return response;
            }

        }catch(Exception ex){
            response.setData(userEntity);
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/findUserDetail",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findUserDetail(UserEntity userEntity, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");//用户已存在
        response.setResMsg("Error");
        try{
            StrategyEntity tmp = new StrategyEntity();
            tmp.setIdWriter(userEntity.getId());
            JSONObject result = new JSONObject();
            result.put("strategies",strategyEntityBaseDAO.findByQuery(tmp));

            FollowEntity followEntity = new FollowEntity();
            followEntity.setIdFollower(userEntity.getId());
            result.put("follows",followEntityBaseDAO.findByQuery(followEntity));

            followEntity.setIdFollower(null);
            followEntity.setIdFollowed(userEntity.getId());
            result.put("followers",followEntityBaseDAO.findByQuery(followEntity));

            result.put("user",userEntityBaseDAO.findById(userEntity));

            response.setData(result);
            response.setResCode("1");
            return response;
        }catch(Exception ex){
            response.setData(userEntity);
            ex.printStackTrace();
        }
        return response;
    }

    //备注：子类中的requestMapping不能和父类重名
//    @RequestMapping(value="/findAll",method = {Req uestMethod.GET})
//    public @ResponseBody
//    BasicResponse findAll(HttpServletRequest request) {
//        BasicResponse response = new BasicResponse();
//        response.setResCode("-1");
//        response.setResMsg("Error");
//        UserDAOImpl userDAO = new UserDAOImpl();
//        try{
//            response.setData(userDAO.findAllUser());
//            response.setResCode("1");
//            response.setResMsg("success");
//            return response;
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }
//>>>>>>> 4e6eeb9b08802365bf1bd9c270bc03fc3823aa86

//    备注：子类中的requestMapping不能和父类重名
//    @RequestMapping(value="/login",method = {RequestMethod.GET})
//    public @ResponseBody
//    BasicResponse login(UserEntity userEntity,HttpServletRequest request) {
//        BasicResponse response = new BasicResponse();
//        response.setResCode("-1");
//        response.setResMsg("Error");
//        UserDAOImpl userDAO = new UserDAOImpl();
//        try{
//            userDAO.login(userEntity,response);
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return response;
//    }
}
