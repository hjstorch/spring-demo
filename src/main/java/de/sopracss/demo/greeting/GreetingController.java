package de.sopracss.demo.greeting;

import de.sopracss.demo.DemoProperties;
import de.sopracss.demo.quote.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice(assignableTypes = GreetingController.class)
@Validated
@Tag(name = "Greeting", description = "Greeting users")
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

    @RequestMapping(value = {"/greeting","/greeting/"}, produces = "text/plain")
    public String contentnotset(Model model) {
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

    @GetMapping(value = "/greetingRest", produces = "text/plain")
    @ResponseBody
    @Operation(summary = "Get a greeting", description = "Get a greeting for a name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "BadRequest", content = @Content(mediaType = "text/plain")),
    })
    public ResponseEntity<String> errorContent(@RequestParam(name = "myname", required = false)
                                                   @Schema(example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
                                                   String name,
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
