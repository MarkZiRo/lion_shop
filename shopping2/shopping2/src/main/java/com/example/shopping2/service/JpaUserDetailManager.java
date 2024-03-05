package com.example.shopping2.service;

import com.example.shopping2.Repository.UserRepository;
import com.example.shopping2.entity.CustomUserDetails;
import com.example.shopping2.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
//@RequiredArgsConstructor
@Service
public class JpaUserDetailManager implements UserDetailsManager {

    private final UserRepository userRepository;

    public JpaUserDetailManager(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        createUser(CustomUserDetails.builder()
                .username("11")
                .password(passwordEncoder.encode("12"))
                .email("abc@gmail.com")
                .phone("010")
                .name("asdf")
                .nickname("jack")
                .age(33)
                .authorities("ROLE_GUEST")
                .build());

        createUser(CustomUserDetails.builder()
                .username("12")
                .password(passwordEncoder.encode("13"))
                .email("11@gmail.com")
                .phone("011")
                .nickname("df")
                .name("axc")
                .age(32)
                .authorities("ROLE_USER")
                .build());

        createUser(CustomUserDetails.builder()
                .username("13")
                .password(passwordEncoder.encode("14"))
                .email("11@gmail.com")
                .phone("011")
                .nickname("df")
                .name("axc")
                .age(32)
                .authorities("ROLE_ENTERPRISE")
                .build());

        createUser(CustomUserDetails.builder()
                .username("14")
                .password(passwordEncoder.encode("15"))
                .email("11@gmail.com")
                .phone("011")
                .nickname("df")
                .name("axc")
                .age(32)
                .authorities("ROLE_ADMIN")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);

        UserEntity userEntity = optionalUser.get();

        return CustomUserDetails.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .name(userEntity.getName())
                .nickname(userEntity.getNickname())
                .age(userEntity.getAge())
                .authorities(userEntity.getAuthorities())
                .build();

    }

    @Override
    public void createUser(UserDetails user) {

        if(this.userExists(user.getUsername()))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);

        try{
            CustomUserDetails userDetails = (CustomUserDetails)user;
            UserEntity newUser = UserEntity.builder()
                    .username(userDetails.getUsername())
                    .password(userDetails.getPassword())
                    .email(userDetails.getEmail())
                    .phone(userDetails.getPhone())
                    .name(userDetails.getName())
                    .nickname(userDetails.getNickname())
                    .age(userDetails.getAge())
                    .authorities(userDetails.getRawAuthorities())
                    .build();

             userRepository.save(newUser);
        }catch (ClassCastException e)
        {
            log.error("fail cast {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean userExists(String username) {

        boolean abc = userRepository.existsByUsername(username);
        log.info(String.valueOf(abc));
        return userRepository.existsByUsername(username);
    }


    @Override
    public void updateUser(UserDetails user) {
         throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void deleteUser(String username) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
