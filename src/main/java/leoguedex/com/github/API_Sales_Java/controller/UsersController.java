package leoguedex.com.github.API_Sales_Java.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import leoguedex.com.github.API_Sales_Java.exception.InvalidPasswordException;
import leoguedex.com.github.API_Sales_Java.model.Users;
import leoguedex.com.github.API_Sales_Java.model.dto.CredencialDto;
import leoguedex.com.github.API_Sales_Java.model.dto.TokenDto;
import leoguedex.com.github.API_Sales_Java.security.jwt.JwtService;
import leoguedex.com.github.API_Sales_Java.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User create with successfully"),
            @ApiResponse(code = 400, message = "Validation error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Users saveUsers(@RequestBody @Valid Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersService.save(users);
    }

    @PostMapping("/auth")
    @ApiOperation(value = "Validate user access")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User create with successfully"),
            @ApiResponse(code = 400, message = "Validation error"),
    })
    @ResponseStatus(HttpStatus.OK)
    public TokenDto authenticate(@RequestBody CredencialDto credencialDto) {
        try {
            Users users = Users.builder()
                    .login(credencialDto.getLogin())
                    .password(credencialDto.getPassword())
                    .build();
            usersService.authenticate(users);
            String token = jwtService.generateToken(users);
            return new TokenDto(users.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
