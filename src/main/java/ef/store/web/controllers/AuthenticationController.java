package ef.store.web.controllers;

import ef.store.web.domains.User;
import ef.store.web.dtos.auth.AuthenticationDto;
import ef.store.web.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@RequestBody User user) throws Exception {
        return new ResponseEntity<AuthenticationDto>(authenticationService.register(user), HttpStatus.OK);
    }

}
