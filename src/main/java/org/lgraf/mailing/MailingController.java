package org.lgraf.mailing;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
class MailingController {

    private final MailingService service;


    MailingController( MailingService service ) {
        this.service = service;
    }


    @GetMapping( "/mailings" )
    String findAll( Model model ) {
        var mailings = service.findAll();
        model.addAttribute( "mailings", mailings );

        return "mailing/mailings";
    }


    @GetMapping( "/mailings/{id}" )
    String find( @PathVariable Long id, Model model ) {
        var mailing = service.find( id );
        model.addAttribute( "mailing", mailing );

        return "mailing/mailing";
    }


    @PostMapping( "/mailings" )
    String create( @RequestParam String name, Model model, HttpServletRequest req ) {
        service.create( name );

        var mailings = service.findAll();
        model.addAttribute( "mailings", mailings );

        return "mailing/mailings";
    }

}
