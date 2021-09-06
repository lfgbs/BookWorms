package tqs.es.bookworms.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/bookworms")
@Controller
public class LoginController {

    @GetMapping(value = "/")
    public String homepage(){
        return "Homepage_form";
    }

    @GetMapping(value="/signup")
    public String signUp(){
        return "SignUp_form";
    }

    @GetMapping(value="/login")
    public String LogIn(){
        return "Login_form";
    }
}
