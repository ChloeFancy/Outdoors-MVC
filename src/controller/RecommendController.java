package controller;

import model.RecommendEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recommend")
@CrossOrigin("http://localhost:8081")
public class RecommendController extends BaseController<RecommendEntity> {
}
