package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        // model.addAttribute: 컨트롤러에서 템플릿으로 데이터를 전달하는 방법
        // "data"="hello!" 저장 (고정값)
        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // http://localhost:8080/hello-mvc?name=spring
        // -> 모델이 "name"="spring" 저장
        model.addAttribute("name", name);
        return "hello-template";
    }
}
