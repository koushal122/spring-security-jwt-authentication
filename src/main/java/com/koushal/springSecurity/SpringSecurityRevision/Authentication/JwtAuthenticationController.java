package com.koushal.springSecurity.SpringSecurityRevision.Authentication;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class JwtAuthenticationController {
    //This will encode the authentication to Jwt token
    private final JwtEncoder jwtEncoder;
    //dependency injected by spring
    public JwtAuthenticationController(JwtEncoder jwtEncoder){
        this.jwtEncoder=jwtEncoder;
    }

    //This endpoint will send the JWT token here Jwt Response is our custom class, for now here we are only adding
    //token later we can add any attribute we want to send.
    @PostMapping(value = "/authenticate")
    public JwtResponse authenticate(Authentication authentication){
        return new JwtResponse(createToken(authentication));
    }

    //This method will create token from authentication
    private String createToken(Authentication authentication) {
        var claims= JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60*10))
                .claim("scope",createScope(authentication))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    //here we are adding all the roles of user with space between it.
    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }
}
//here we can have custom attributes in jwt response.
record JwtResponse(String token) {}
