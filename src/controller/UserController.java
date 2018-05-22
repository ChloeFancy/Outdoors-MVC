package controller;


import DAO.Impl.BaseDAOImpl;
import model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
@CrossOrigin("http://localhost:8081")
public class UserController extends BaseController<UserEntity>{
    @RequestMapping(value="/modifyPassword",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse modifyPassWord(UserEntity userEntity,
                                 @RequestParam String newPassword, @RequestParam String confirmPassword,
                                 HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");//用户密码不一致
        response.setResMsg("Error");
        BaseDAOImpl<UserEntity> dao = new BaseDAOImpl<>();
        try{
            response.setData(userEntity=dao.findOne(userEntity));
            if(newPassword.equals(confirmPassword)) {
                request.setCharacterEncoding("utf-8");
                userEntity.setPassword(newPassword);
                //System.out.println("new:"+newPassword+confirmPassword);
                dao.update(userEntity);
                response.setData(userEntity);

                response.setResCode("1");//修改成功
                response.setResMsg("success");
                return response;
            }
            else {
                response.setResCode("-2");//两次密码不一致
                response.setResMsg("error");
                return response;
            }
        }catch(Exception ex){
            response.setResCode("-1");
            response.setResMsg("Error");
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
