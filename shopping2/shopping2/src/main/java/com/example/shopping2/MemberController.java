package com.example.shopping2;

import com.example.shopping2.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

  //  private final JwtTokenUtils jwtTokenUtils;
  //  private final
}
