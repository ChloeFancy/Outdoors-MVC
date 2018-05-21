package controller;

import model.CommentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
@CrossOrigin("http://localhost:8081")
public class CommentController extends BaseController<CommentEntity>{

}
