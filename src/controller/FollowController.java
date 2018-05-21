package controller;

import DAO.Impl.BaseDAOImpl;
import model.FollowEntity;
import DAO.Impl.FollowDAOImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.BasicResponse;

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
}
