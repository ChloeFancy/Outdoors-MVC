package controller;

import model.CommentEntity;
import model.ContinentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.BasicResponse;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/continent")
@CrossOrigin("http://localhost:8081")
public class ContinentController extends BaseController<ContinentEntity>{

}
