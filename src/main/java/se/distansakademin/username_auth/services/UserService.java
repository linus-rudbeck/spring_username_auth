package se.distansakademin.username_auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.distansakademin.username_auth.models.User;
import se.distansakademin.username_auth.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){

        if(user.getPassword() != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            var passwordHash = encoder.encode(user.getPassword());
            user.setPassword(passwordHash);
        }

        userRepository.save(user);
    }

    public boolean usernameExists(String username){
        var user = userRepository.findByUsername(username);
        return (user != null);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }
}
