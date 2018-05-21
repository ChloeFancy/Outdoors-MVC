package controller;

import model.ManagerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
@CrossOrigin("http://localhost:8081")
public class ManagerController extends BaseController<ManagerEntity> {
}
