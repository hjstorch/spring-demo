package de.sopracss.demo.greeting;

import de.sopracss.demo.DemoProperties;
import de.sopracss.demo.quote.QuoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice(assignableTypes = GreetingController.class)
@Validated
public class GreetingController {

    private final QuoteService quoteService;

    private final GreetingService greetingService;

    private final DemoProperties demoProperties;

    public GreetingController(
            QuoteService quoteService, GreetingService greetingService,
            DemoProperties demoProperties
    ) {
        this.quoteService = quoteService;
        this.greetingService = greetingService;
        this.demoProperties = demoProperties;
    }

    @GetMapping(value = {"/greeting",}, produces = "text/plain")
    public String greetingParam(@RequestParam(value = "myname", defaultValue = "Welt", required = false) String name) {
        return getGreetingAndQuote(name);
    }

    @GetMapping({"/greeting/{myname}","/greeting/"})
    public String greetingPath(@PathVariable(name = "myname", required = false) String name) {
        return getGreetingAndQuote(name);
    }

    private String getGreetingAndQuote(String name) {
        return this.greetingService.getGreeting(name) + " Quote of the Day: " + this.getQuote();
    }

    private String getQuote() {
        return this.quoteService.getQuote();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
