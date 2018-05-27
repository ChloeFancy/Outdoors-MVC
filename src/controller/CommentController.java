package controller;

import DAO.Impl.BaseDAOImpl;
import DAO.Impl.CommentDAOImpl;
import model.BrowseEntity;
import model.CommentEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

import java.lang.reflect.Method;


@Controller
@RequestMapping("/comment")
@CrossOrigin("http://localhost:8081")
public class CommentController extends BaseController<CommentEntity>{

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    CommentDAOImpl commentDAO = (CommentDAOImpl) context.getBean("commentDAOImpl");

    @RequestMapping(value = "/findByStrategy",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findByStrategy(@RequestParam int idStrategy){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
//        CommentDAOImpl commentDAO = new CommentDAOImpl();
        response.setData(commentDAO.findByStrategy(idStrategy));
        return response;
    }
}
