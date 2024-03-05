package com.example.shopping2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {


    private String id;

    private String username;
    private String password;

    private String email;
    private String phone;
    private String nickname;
    private String name;
    private int age;

    private String authorities;

    public String getRawAuthorities()
    {
        return this.authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        return Arrays.stream(authorities.split(","))
//                .sorted()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        String[] rawAuthorities = authorities.split(",");

        for (String rawAuthority : rawAuthorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(rawAuthority));
        }

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
