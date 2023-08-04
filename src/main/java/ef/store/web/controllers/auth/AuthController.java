package ef.store.web.controllers.auth;

import ef.store.web.domains.RefreshToken;
import ef.store.web.domains.Role;
import ef.store.web.domains.User;
import ef.store.web.entities.RoleEntity;
import ef.store.web.enums.ERole;
import ef.store.web.payload.request.LoginRequest;
import ef.store.web.payload.request.SignupRequest;
import ef.store.web.payload.response.JwtResponse;
import ef.store.web.security.jwt.JwtUtils;
import ef.store.web.security.services.UserDetailsImpl;
import ef.store.web.services.refresh_token.RefreshTokenService;
import ef.store.web.services.role.RoleService;
import ef.store.web.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = User.builder()
                .id(signUpRequest.getId())
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .address(signUpRequest.getAddress())
                .status(signUpRequest.getStatus())
                .build();

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleService.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.createNewAccount(user);

        return ResponseEntity.ok("User registered successfully!");
    }

//    @PostMapping("/signout")
//    public ResponseEntity<?> logoutUser() {
//        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principle.toString() != "anonymousUser") {
//            Long userId = ((UserDetailsImpl) principle).getId();
//            refreshTokenService.deleteByUserId(userId);
//        }
//
//        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
//        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
//                .body(new MessageResponse("You've been signed out!"));
//    }

//    @PostMapping("/refreshtoken")
//    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
//        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
//
//        if ((refreshToken != null) && (refreshToken.length() > 0)) {
//            return refreshTokenService.findByToken(refreshToken)
//                    .map(refreshTokenService::verifyExpiration)
//                    .map(RefreshToken::getUser)
//                    .map(user -> {
//                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
//
//                        return ResponseEntity.ok()
//                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                                .body(new MessageResponse("Token is refreshed successfully!"));
//                    })
//                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
//                            "Refresh token is not in database!"));
//        }
//
//        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
//    }

}
