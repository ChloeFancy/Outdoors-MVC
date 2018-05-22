package controller;

import DAO.Impl.BaseDAOImpl;
import model.BrowseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.BasicResponse;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/browse")
@CrossOrigin("http://localhost:8081")
public class BrowseController extends BaseController<BrowseEntity> {
    @RequestMapping(value="/updateRecord",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse updateRecord(BrowseEntity browseEntity,HttpServletRequest request)
    {
        BasicResponse response=new BasicResponse();
        BaseDAOImpl<BrowseEntity> dao = new BaseDAOImpl<>();
        try{
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
            response.setData(browseEntity);
            e.printStackTrace();
        }
        return response;

    }
}
