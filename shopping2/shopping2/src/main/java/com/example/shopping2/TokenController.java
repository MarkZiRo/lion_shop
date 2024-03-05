package com.example.shopping2;

import com.example.shopping2.dto.MemberSignupDto;
import com.example.shopping2.dto.MemberSignupResponseDto;
import com.example.shopping2.entity.CustomUserDetails;
import com.example.shopping2.jwt.JwtRequestDto;
import com.example.shopping2.jwt.JwtResponseDto;
import com.example.shopping2.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final JwtTokenUtils jwtTokenUtils;  // jwt
    private final UserDetailsManager manager;  // service
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/issue")
    private JwtResponseDto issue(@RequestBody JwtRequestDto dto)
    {
        if(!manager.userExists(dto.getUsername()))
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        UserDetails userDetails = manager.loadUserByUsername(dto.getUsername());

        if(!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        log.info(userDetails.toString());
        String jwt = jwtTokenUtils.generateToken(userDetails);
        log.info(jwt);
        JwtResponseDto responseDto = new JwtResponseDto();
        responseDto.setToken(jwt);
        return responseDto;
    }

    @GetMapping("/validate")
    public Claims validateToken(@RequestParam("token") String token)
    {
        if(!jwtTokenUtils.validate(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return jwtTokenUtils.parseClaims(token);
    }

    @GetMapping("/profile")
    public String myProfile(Authentication authentication,
                             Model model)
    {
        model.addAttribute("username", authentication.getName());
        log.info(authentication.getName());
//        log.info(((User) authentication.getPrincipal()).getPassword());

        return "abcdefg";
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Validated MemberSignupDto memberSignupDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        manager.createUser(CustomUserDetails.builder()
                .username(memberSignupDto.getId())
                .password(memberSignupDto.getPassword())
                .build());

        UserDetails userDetails = manager.loadUserByUsername(memberSignupDto.getId());

        MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto();
        memberSignupResponseDto.setId(userDetails.getUsername());
        memberSignupResponseDto.setPassword(userDetails.getPassword());

        return new ResponseEntity(memberSignupResponseDto, HttpStatus.CREATED);
    }

}
