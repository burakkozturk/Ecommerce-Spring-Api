package emekpazari.project.service;

import emekpazari.project.entity.User;
import emekpazari.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Şifreyi güvenli bir şekilde saklayın
        return userRepository.save(user);
    }
}
