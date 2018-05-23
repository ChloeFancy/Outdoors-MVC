package controller;

import DAO.Impl.BaseDAOImpl;
import model.FollowEntity;
import DAO.Impl.FollowDAOImpl;
import model.UserEntity;
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
    @RequestMapping(value="/findFollower",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findFollower(FollowEntity followed, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        FollowDAOImpl dao = new FollowDAOImpl();
        try{
            response.setData(dao.findFollower(followed));
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
    BasicResponse findFollowed(FollowEntity follower, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        FollowDAOImpl dao = new FollowDAOImpl();
        try{
            response.setData(dao.findFollowed(follower));
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
        BaseDAOImpl<FollowEntity> dao = new BaseDAOImpl();
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
            dao.insert(followEntity);
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
        FollowDAOImpl dao = new FollowDAOImpl();
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
            dao.unfollow(followEntity);
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
