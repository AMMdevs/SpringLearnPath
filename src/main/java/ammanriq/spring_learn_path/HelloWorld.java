package ammanriq.spring_learn_path;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "hello")
public class HelloWorld {
    
    @GetMapping
    public String helloWorld(){
        return "Hello World!";
    }
}
