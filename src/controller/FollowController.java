package controller;

import DAO.Impl.BaseDAOImpl;
import DAO.Impl.CommentDAOImpl;
import com.alibaba.fastjson.JSON;
import model.BrowseEntity;
import model.FollowEntity;
import DAO.Impl.FollowDAOImpl;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.JWT;
import util.unsignFromCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/follow")
@CrossOrigin("http://localhost:8081")
public class FollowController extends BaseController<FollowEntity> {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    FollowDAOImpl dao = (FollowDAOImpl) context.getBean("followDAOImpl");

    BaseDAOImpl<FollowEntity> baseDAO = (BaseDAOImpl<FollowEntity>) context.getBean("baseDaoImpl");
    BaseDAOImpl<FollowEntity> followEntityBaseDAO = (BaseDAOImpl<FollowEntity>) context.getBean("baseDaoImpl");


    @RequestMapping(value="/findFollower",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findFollower(@RequestParam String idFollowed, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        UserEntity userEntity = unsignFromCookie.unsign(request);
        int client = 0;
        if(userEntity!=null){
            client = userEntity.getId();
        }
        try{
            response.setData(dao.findFollower(Integer.parseInt(idFollowed),client));
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

    @RequestMapping(value="/findFollowed",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findFollowed(@RequestParam String idFollower, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        UserEntity userEntity = unsignFromCookie.unsign(request);
        int client = 0;
        if(userEntity!=null){
            client = userEntity.getId();
        }
        try{
            response.setData(dao.findFollowed(Integer.parseInt(idFollower),client));
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

    @RequestMapping(value="/followOne",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse followOne(@RequestParam int idFollowed, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        UserEntity fromToken = unsignFromCookie.unsign(request);
        if(fromToken==null){
            //说明token已失效
            response.setResCode("-2");
            return response;
        }
        int idFollower = fromToken.getId();
        FollowEntity followEntity = new FollowEntity();
        followEntity.setIdFollowed(idFollowed);
        followEntity.setIdFollower(idFollower);

        try{
            baseDAO.insert(followEntity);
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

    @RequestMapping(value="/unfollowOne",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse unfollowOne(@RequestParam int idFollowed, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        FollowDAOImpl dao = new FollowDAOImpl();
        UserEntity fromToken = unsignFromCookie.unsign(request);
        if(fromToken==null){
            //说明token已失效
            response.setResCode("-2");
            return response;
        }
        int idFollower = fromToken.getId();
        FollowEntity followEntity = new FollowEntity();
        followEntity.setIdFollowed(idFollowed);
        followEntity.setIdFollower(idFollower);
        try{
            FollowEntity one =  followEntityBaseDAO.findByQuery(followEntity).get(0);
            System.out.println(JSON.toJSONString(one));
            followEntityBaseDAO.delete(one);

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
