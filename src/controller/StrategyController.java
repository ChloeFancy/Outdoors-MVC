package controller;

import model.StrategyEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/strategy")
@CrossOrigin("http://localhost:8081")
public class StrategyController extends BaseController<StrategyEntity>{


}
