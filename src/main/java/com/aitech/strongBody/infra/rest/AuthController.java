package com.aitech.strongBody.infra.rest;

import com.aitech.strongBody.application.useCase.user.CreateUserUseCase;
import com.aitech.strongBody.domain.entity.User;
import com.aitech.strongBody.infra.config.security.TokenService;
import com.aitech.strongBody.infra.rest.dto.auth.SignInDto;
import com.aitech.strongBody.infra.rest.dto.shared.TokenDto;
import com.aitech.strongBody.infra.rest.dto.user.CreateUserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    @Autowired
    private final CreateUserUseCase createUserUseCase;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenService tokenService;

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody @Valid SignInDto signInDto) {
        var userNamePassword  =  new UsernamePasswordAuthenticationToken(signInDto.email(), signInDto.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        log.info("AuthController::signIn::Successfully");
        return new TokenDto(token);
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody @Valid CreateUserDto input) {
        var encodedPassword = new BCryptPasswordEncoder().encode(input.password());
        User user = new User(
                input.name(),
                input.email(),
                input.nickname(),
                input.avatarUrl(),
                encodedPassword,
                null);
        this.createUserUseCase.execute(user);
        log.info("AuthController::signUp::Successfully");
    }
}
