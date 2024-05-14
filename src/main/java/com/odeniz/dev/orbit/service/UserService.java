package com.odeniz.dev.orbit.service;

import com.odeniz.dev.orbit.configration.JWTUserDetail;
import com.odeniz.dev.orbit.entity.User;
import com.odeniz.dev.orbit.model.UserDetailResponse;
import com.odeniz.dev.orbit.repository.UserRepository;
import com.odeniz.dev.orbit.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<ApiResponse<UserDetailResponse>> getInfo(Authentication authentication) {

        JWTUserDetail userDetail = (JWTUserDetail) authentication.getPrincipal();
        if (userDetail != null){
            Optional<User> user = userRepository.findById(userDetail.getId());
            if (user.isPresent()){
                UserDetailResponse userDetailResponse = new UserDetailResponse(user.get());
                return new ResponseEntity<>(ApiResponse.create(userDetailResponse), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(ApiResponse.error(), HttpStatus.OK);
    }

    public UserDetailResponse getUserProfileInformation(Long userId){
        User user =userRepository.findById(userId).orElse(null);
        if (user != null){
            return new UserDetailResponse(user);
        }
        return null;
    }
}
