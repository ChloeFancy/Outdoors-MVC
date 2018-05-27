package controller;

import DAO.Impl.BaseDAOImpl;
import DAO.Impl.CommentDAOImpl;
import model.BrowseEntity;
import model.CommentEntity;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.unsignFromCookie;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


@Controller
@RequestMapping("/comment")
@CrossOrigin("http://localhost:8081")
public class CommentController extends BaseController<CommentEntity>{

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    CommentDAOImpl commentDAO = (CommentDAOImpl) context.getBean("commentDAOImpl");
    BaseDAOImpl<CommentEntity> commentEntityBaseDAO = (BaseDAOImpl<CommentEntity>) context.getBean("baseDaoImpl");


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

    @RequestMapping(value = "/addComment",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse findByStrategy(@RequestParam String comment, @RequestParam Integer idStrategy, HttpServletRequest httpServletRequest){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");

        UserEntity fromToken = unsignFromCookie.unsign(httpServletRequest);
        if(fromToken==null){
            response.setResCode("-1");
            response.setResMsg("Error");
        }else{
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setContent(comment);
            commentEntity.setIdWriter(fromToken.getId());
            commentEntity.setIdStrategy(idStrategy);
            try{
                commentEntityBaseDAO.insert(commentEntity);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return response;
    }


}
