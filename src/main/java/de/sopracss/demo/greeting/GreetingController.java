package de.sopracss.demo.greeting;

import de.sopracss.demo.DemoProperties;
import de.sopracss.demo.quote.QuoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice(assignableTypes = GreetingController.class)
@Validated
public class GreetingController {

    private final QuoteService quoteService;

    private final DemoProperties demoProperties;

    private final String greeting;

    public GreetingController(
            QuoteService quoteService,
            DemoProperties demoProperties,
            @Value("${demo.greeting}") String greeting
    ) {
        this.quoteService = quoteService;
        this.demoProperties = demoProperties;
        this.greeting = greeting;
    }

    @RequestMapping("/greeting/")
    public String contentno(Model model) {
        throw new NullPointerException("No name provided");
    }

    @RequestMapping("/greeting/{myname}")
    public String content(@PathVariable(name = "myname") String name, Model model) {
        model.addAttribute("username", name);
        model.addAttribute("greeting", this.greeting);
        model.addAttribute("quote", this.getQuote());
        return "greeting";
    }

    private String getQuote() {
        return this.quoteService.getQuote();
    }

    @GetMapping("/greetingRest")
    @ResponseBody
    public ResponseEntity<String> errorContent(@RequestParam(name = "myname", required = false) String name,
                                               @RequestAttribute(name = "myname", required = false) String goodname,
                                               @RequestAttribute(name = "badname", required = false) boolean badname
    ) {
        if(null == name){
            return ResponseEntity.notFound().build();
        }
        if(badname) {
            name = goodname;
        }
        return ResponseEntity.ok().body(demoProperties.getGreeting() + " " + name);
    }



    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
