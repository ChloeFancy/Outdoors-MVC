package controller;

import DAO.Impl.RecommendDAOImpl;
import DAO.Impl.UserDAOImpl;
import model.RecommendEntity;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.unsignFromCookie;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/recommend")
@CrossOrigin("http://localhost:8081")
public class RecommendController extends BaseController<RecommendEntity> {

    private ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    private RecommendDAOImpl recommendDAO = (RecommendDAOImpl) context.getBean("recommendDAOImpl");

    @RequestMapping(value = "/getRecSpot", method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse getRecSpot(HttpServletRequest request) {
            BasicResponse response = new BasicResponse();
            response.setResCode("-1");
            response.setResMsg("error");

            UserEntity fromToken = unsignFromCookie.unsign(request);

            try {
                RecommendEntity recommendEntity = new RecommendEntity();
                if(fromToken!=null){
                    recommendEntity.setIdUser(fromToken.getId());
                    response.setData(recommendDAO.getRecSpot(recommendEntity));
                }else{
                    response.setData(recommendDAO.getMostPopularSpots());
                }

                response.setResCode("1");
                response.setResMsg("success");
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return response;
    }
}
