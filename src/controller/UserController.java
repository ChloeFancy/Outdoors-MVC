package controller;


import DAO.Impl.BaseDAOImpl;
import DAO.Impl.UserDAOImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.JWT;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
@CrossOrigin("http://localhost:8081")
public class UserController extends BaseController<UserEntity>{
    @RequestMapping(value="/modifyPassword",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse modifyPassWord(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpServletRequest request) {

        BasicResponse response = new BasicResponse();
        response.setResCode("-1");//用户密码不一致
        response.setResMsg("Error");

        //获取token，还原userEntity
        UserEntity fromToken = null;
        Cookie[] cookie = request.getCookies();
        for (int i = 0; i < cookie.length; i++) {
            Cookie cook = cookie[i];
            System.out.println(cook.getName());
            if(cook.getName().equalsIgnoreCase("usertoken")){ //获取键
//                System.out.println(cook.getValue().toString());

                fromToken = JWT.unsign(cook.getValue().toString(),UserEntity.class);
//                System.out.println(JSON.toJSONString(fromToken));
            }
        }
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
        BaseDAOImpl<UserEntity> dao = new BaseDAOImpl<>();
        try{
            fromToken.setPassword(newPassword);
            dao.update(fromToken);
            response.setResCode("1");//修改成功
            response.setResMsg("success");
            return response;
//            response.setData(userEntity=dao.findOne(userEntity));
//            if(newPassword.equals(confirmPassword)) {
//                request.setCharacterEncoding("utf-8");
//                userEntity.setPassword(newPassword);
//                //System.out.println("new:"+newPassword+confirmPassword);
//                dao.update(userEntity);
//                response.setData(userEntity);
//
//                response.setResCode("1");//修改成功
//                response.setResMsg("success");
//                return response;
//            }
//            else {
//                response.setResCode("-2");//两次密码不一致
//                response.setResMsg("error");
//                return response;
//            }
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
            UserDAOImpl userDAO = new UserDAOImpl();
            UserEntity result = userDAO.login(userEntity);
            if(result!=null){
                //生成用户登录的token标记登录状态
                String token = JWT.sign(result, 30L * 24L * 3600L * 1000L);
                UserEntity test = JWT.unsign(token,UserEntity.class);
                System.out.println(JSON.toJSONString(test));
                if (token != null) {
                    System.out.println(token);
                    JSONObject tokenObj = new JSONObject();
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
    BasicResponse isLogin(@RequestParam String token,HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            UserEntity userEntity = JWT.unsign(token,UserEntity.class);
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
