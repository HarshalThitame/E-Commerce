package com.ecom.config;

import com.ecom.entity.Enum.UserRole;
import com.ecom.entity.User;
import com.ecom.repositories.UserRepository;
import com.ecom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        if(user.getUserRole() == null)
        {
            user.setUserRole(UserRole.USER);
        }
        User save = userService.createUser(user);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        }
        catch (AuthenticationException e) {
            logger.error(e.getMessage());
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status",false);

            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        JwtResponse jwtResponse = new JwtResponse(token,userDetails.getUsername(),role);
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) this.userDetailsService.loadUserByUsername(principal.getName());
        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow();
    }

}
