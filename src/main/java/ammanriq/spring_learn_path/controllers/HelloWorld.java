package ammanriq.spring_learn_path.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "hello")
public class HelloWorld {
    private String name = null;

    @GetMapping("world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping
    public String helloName() {
        if (name == null) {
            return "Not name found.";
        } else {
            return "Hello " + name;
        }
    }

    @PutMapping
    public void setName(@RequestBody String name) {
        this.name = name;
    }

    @DeleteMapping
    public void deleteName() {
        name = null;
    }


}
