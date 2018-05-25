package uk.co.cd15.sentrydemo.controllers;

import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SentryController {

    @GetMapping("/sentry")
    public String sentry(@RequestParam(name="breadcrumb", required=false, defaultValue= "false") boolean breadcrumb,
                         @RequestParam(name="error", required=false, defaultValue= "false") boolean error,
                         Model model) {

        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("error", error);


        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail("spring-boot@sentry.io").build()
        );

        // Add extra data to future events in this context.
//        Sentry.getContext().addExtra("environment", "thing");

        // Add an additional tag to future events in this context.
//        Sentry.getContext().addTag("tagName", "tagValue");


        if(breadcrumb){
            // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
            Sentry.getContext().recordBreadcrumb(
                    new BreadcrumbBuilder().setMessage("User made an action").build()
            );
        }


        /*
        This sends a simple event to Sentry using the statically stored instance
        that was created in the ``main`` method.
        */
//        Sentry.capture("This is a test");


        if (error){

        }
        try {
            throw new NullPointerException("Unable to find value");
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }

        return "sentry";
    }

}
