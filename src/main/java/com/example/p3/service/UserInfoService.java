package com.example.p3.service;

import com.example.p3.entity.UserInfo;
import com.example.p3.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.p3.Validation.*;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByName(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfo userInfo) {
        if(!isValidEmail(userInfo.getEmail())) throw new RuntimeException("Mail id not valid");
        if(!isValidNameLength(userInfo.getName())) throw new RuntimeException("Input name length not valid");

        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }


    public UserInfo getUser(int userId) {
       UserInfo userInfo=repository.findById(userId);
       if(userInfo==null) return null;
       return userInfo;
    }

    public UserInfo editUser(int userId) {
        UserInfo userInfo=repository.findById(userId);
        if(userInfo==null) return null;
        return repository.save(userInfo);
    }

    public String deleteUser(int userId) {
        UserInfo userInfo=repository.findById(userId);
            if(userInfo==null) return null;
      repository.delete(userInfo);
        return "Delete Successfully";
    }


}
