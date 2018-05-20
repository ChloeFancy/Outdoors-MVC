package controller;


import model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@CrossOrigin("http://localhost:8081")
public class UserController extends BaseController<UserEntity>{


    //备注：子类中的requestMapping不能和父类重名
//    @RequestMapping(value="/findAll",method = {RequestMethod.GET})
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
