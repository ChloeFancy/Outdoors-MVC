package controller;

import model.ContinentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/continent")
@CrossOrigin("http://localhost:8081")
public class ContinentController extends BaseController<ContinentEntity>{

}
