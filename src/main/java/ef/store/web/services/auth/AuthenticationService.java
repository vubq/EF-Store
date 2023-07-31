package ef.store.web.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import ef.store.web.config.auth.JwtTokenUtils;
import ef.store.web.domains.Token;
import ef.store.web.domains.User;
import ef.store.web.dtos.auth.AuthenticationDto;
import ef.store.web.dtos.auth.RegisterDto;
import ef.store.web.enums.TokenType;
import ef.store.web.services.token.TokenService;
import ef.store.web.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final TokenService tokenService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenUtils jwtTokenUtils;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationDto register(User userRegister) {
        User user = this.userService.createNewAccount(userRegister);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getListRole().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
        var jwtToken = this.jwtTokenUtils.generateToken(userDetails);
        var refreshToken = this.jwtTokenUtils.generateRefreshToken(userDetails);
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .userId(user.getId())
                .build();
        this.tokenService.createToken(token);
        return AuthenticationDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

//    public AuthenticationDto authenticate(LoginDto login) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        login.getUserName(),
//                        login.getPassword()
//                )
//        );
//        var user = userService.findByUserName(login.getUserName())
//                .orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
//        return AuthenticationDto.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenService.save(token);
//    }
//
//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenService.findAllValidTokenByUser(user.getId()).get();
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenService.saveAll(validUserTokens);
//    }
//
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userName;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userName = jwtService.extractUsername(refreshToken);
//        if (userName != null) {
//            var user = this.userService.findByUserName(userName).orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationDto.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }

}
