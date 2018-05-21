package controller;

import model.CountryEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/country")
@CrossOrigin("http://localhost:8081")
public class CountryController extends BaseController<CountryEntity>{

}
