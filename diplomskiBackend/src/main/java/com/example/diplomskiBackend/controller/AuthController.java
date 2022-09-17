package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.TokenDTO;
import com.example.diplomskiBackend.security.request.JwtLoginRequest;
import com.example.diplomskiBackend.security.token.TokenUtils;
import com.example.diplomskiBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody JwtLoginRequest authenticationRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String token = tokenUtils.generateToken(userDetails);
            TokenDTO tokenDTO = new TokenDTO(token);
            return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            System.out.println("NOT FOUND");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (DisabledException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
