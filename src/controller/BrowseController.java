package controller;

import model.BrowseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/browse")
@CrossOrigin("http://localhost:8081")
public class BrowseController extends BaseController<BrowseEntity> {
}
