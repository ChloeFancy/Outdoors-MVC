package controller;

import DAO.Impl.BaseDAOImpl;
import model.BrowseEntity;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import util.unsignFromCookie;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/browse")
@CrossOrigin("http://localhost:8081")
public class BrowseController extends BaseController<BrowseEntity> {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    BaseDAOImpl<BrowseEntity> dao = (BaseDAOImpl<BrowseEntity>) context.getBean("baseDaoImpl");


    @RequestMapping(value="/updateRecord",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse updateRecord(@RequestParam int idSpot, HttpServletRequest request)
    {
        BasicResponse response = new BasicResponse();
        try{
//            BaseDAOImpl<BrowseEntity> dao = new BaseDAOImpl<>();
            UserEntity userEntity = unsignFromCookie.unsign(request);
            if(userEntity==null){
                response.setResCode("0");
                response.setResMsg("noUser");
                return response;
            }
            int idUser = userEntity.getId();
            BrowseEntity browseEntity = new BrowseEntity();
            browseEntity.setIdSpot(idSpot);
            browseEntity.setIdUser(idUser);

            if(dao.findByQuery(browseEntity).isEmpty()) {//没有存在过
                browseEntity.setCount(1);//很奇怪,数据库里设了默认值但hibernate不会加上
                dao.insert(browseEntity);
                response.setResCode("1");
                response.setResMsg("Success");
                response.setData(dao.findByQuery(browseEntity).get(0));
            }
            else {//计数加一
                browseEntity=dao.findByQuery(browseEntity).get(0);
                browseEntity.setCount(browseEntity.getCount()+1);
                dao.update(browseEntity);
                response.setResCode("1");
                response.setResMsg("Success");
                response.setData(dao.findByQuery(browseEntity).get(0));
            }
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("Error");
            e.printStackTrace();
        }
        return response;

    }
}
