package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.entity.ApplicationUser;
import com.dhinesh.tvservice.exception.ConflictException;
import com.dhinesh.tvservice.model.UserModel;
import com.dhinesh.tvservice.repository.ApplicationUserRepository;
import com.dhinesh.tvservice.security.ApplicationUserDetails;
import com.dhinesh.tvservice.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ApplicationUser> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s not found",username));
        }

        return new ApplicationUserDetails(user.get());
    }

    /**
     * Creates user from userModel bean if username doesn't exist
     *
     * @param userModel
     * @return
     */
    public void createUser(UserModel userModel){

        String username = userModel.getUsername();

        if(userRepository.existsById(username)){

            throw new ConflictException(String.format("Username %s already Exists",username));
        }

        // Create User
        ApplicationUser applicationUser = Utility.createUserFromModel(userModel,passwordEncoder);
        userRepository.save(applicationUser);

    }
}
