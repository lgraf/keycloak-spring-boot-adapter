package org.lgraf.user;

import java.io.IOException;
import java.util.Base64;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
class ProfileController {

    @GetMapping( "/profile" )
    String index( Model model, KeycloakAuthenticationToken keycloakToken ) {
        AccessToken token = keycloakToken.getAccount().getKeycloakSecurityContext().getToken();

        model.addAttribute( "preferredUsername", token.getPreferredUsername() );
        model.addAttribute( "email", token.getEmail() );
        model.addAttribute( "givenName", token.getGivenName() );
        model.addAttribute( "familyName", token.getFamilyName() );
        model.addAttribute( "token", getTokenPayload( keycloakToken ) );

        return "profile";
    }


    private String getTokenPayload( KeycloakAuthenticationToken token ) {

        String accessToken = token.getAccount().getKeycloakSecurityContext().getTokenString();
        String payload = accessToken.split( "[.]" )[1];
        String decoded = new String( Base64.getDecoder().decode( payload ) );

        return prettify( decoded );
    }


    private String prettify( String json ) {
        ObjectMapper mapper = new ObjectMapper();

        String prettyJson;
        try {
            Object jsonObject = mapper.readValue( json, Object.class );
            prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString( jsonObject );
        }
        catch( IOException x ) {
            throw new IllegalArgumentException( "invalid json string!", x );
        }

        return prettyJson;
    }

}
