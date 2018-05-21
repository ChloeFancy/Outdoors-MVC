package controller;

import model.SpotEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spot")
@CrossOrigin("http://localhost:8081")
public class SpotController extends BaseController<SpotEntity> {

}
