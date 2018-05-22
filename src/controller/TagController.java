package controller;

import model.TagEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tag")
@CrossOrigin("http://localhost:8081")
public class TagController extends BaseController<TagEntity> {
}
