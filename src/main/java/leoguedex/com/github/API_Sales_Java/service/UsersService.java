package leoguedex.com.github.API_Sales_Java.service;

import leoguedex.com.github.API_Sales_Java.exception.InvalidPasswordException;
import leoguedex.com.github.API_Sales_Java.model.Users;
import leoguedex.com.github.API_Sales_Java.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public UserDetails authenticate(Users usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean passwordOk = passwordEncoder.matches(usuario.getPassword(), userDetails.getPassword());

        if (passwordOk) {
            return userDetails;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users userReceipt = usersRepository.findByLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in dataBase"));
        String[] roles = userReceipt.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(userReceipt.getLogin())
                .password(userReceipt.getPassword())
                .roles(roles)
                .build();
    }

}