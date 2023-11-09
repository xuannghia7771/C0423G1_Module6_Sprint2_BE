package com.example.dawine.controller;

import com.example.dawine.config.JwtTokenUtil;
import com.example.dawine.dto.AppUserDto;
import com.example.dawine.dto.AppUserLoginDto;
import com.example.dawine.model.user.AppUser;
import com.example.dawine.model.user.JwtResponse;
import com.example.dawine.service.user.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String LOGIN_FAILED = "Đăng nhập thất bại";


    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody AppUserLoginDto appUserDto, BindingResult bindingResult) {
        new AppUserLoginDto().validate(appUserDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(LOGIN_FAILED, HttpStatus.UNAUTHORIZED);
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUserDto.getUserName(), appUserDto.getPassword()));
        } catch (DisabledException e) {
            return new ResponseEntity<>("Tài khoản của bạn đã bị vô hiệu hóa", HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(LOGIN_FAILED, HttpStatus.UNAUTHORIZED);
        }
        AppUser appUser = appUserService.findByUsername(appUserDto.getUserName());
        UserDetails userDetails = appUserService.loadUserByUsername(appUser.getUserName());

        String jwtToken = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok().body(new JwtResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody AppUserDto appUserDto, BindingResult bindingResult) {
        new AppUserDto().validate(appUserDto, bindingResult);
        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream()
                    .map(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return new ResponseEntity<>(errorMap, HttpStatus.OK);
        }
        AppUser appUser = appUserService.findByUsername(appUserDto.getUserName());
        if (appUser != null) {
            return new ResponseEntity<>("Tên đăng nhập đã tồn tại", HttpStatus.OK);
        } else {
            AppUser appUser1 = new AppUser();

            appUser1.setUserName(appUserDto.getUserName());
            appUser1.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
            appUser1.setEmail(appUserDto.getEmail());
            Boolean checkAddNewAppUser = appUserService.createNewAppUser(appUser1, "ROLE_ADMIN");
            if (!Boolean.TRUE.equals(checkAddNewAppUser)) {
                return new ResponseEntity<>("Đăng kí thất bại, vui lòng chờ trong giây lát",
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(appUser1, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getUser/{name}")
    public ResponseEntity<Object> getUser(@PathVariable String name) {
        AppUser appUser = appUserService.findByUsername(name);
        if (appUser != null) {
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy user", HttpStatus.NOT_FOUND);
    }

}