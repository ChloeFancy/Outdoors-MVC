package controller;

import DAO.Impl.RecommendDAOImpl;
import DAO.Impl.UserDAOImpl;
import DAO.RecommendDAO;
import model.RecommendEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.BasicResponse;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/recommend")
@CrossOrigin("http://localhost:8081")
public class RecommendController extends BaseController<RecommendEntity> {
    @RequestMapping(value = "/getRecSpot", method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse getRecSpot(RecommendEntity recommendEntity, HttpServletRequest request) {
            BasicResponse response = new BasicResponse();
            response.setResCode("-1");
            response.setResMsg("error");
            RecommendDAOImpl dao = new RecommendDAOImpl();
            try {
                response.setResCode("1");
                response.setResMsg("success");
                response.setData(dao.getRecSpot(recommendEntity));
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return response;
    }
}
