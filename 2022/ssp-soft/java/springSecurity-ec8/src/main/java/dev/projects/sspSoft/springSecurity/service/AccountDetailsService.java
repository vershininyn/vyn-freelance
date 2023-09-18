package dev.projects.sspSoft.springSecurity.service;

import dev.projects.sspSoft.springSecurity.domain.TokenPair;
import dev.projects.sspSoft.springSecurity.domain.UserEntity;
import dev.projects.sspSoft.springSecurity.domain.UserRoleEntity;
import dev.projects.sspSoft.springSecurity.util.JwtTokenUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDetailsService implements UserDetailsService {
    private static final UserRoleEntity ADMIN_ROLE = new UserRoleEntity("ADMIN");
    private static final UserRoleEntity USER_ROLE = new UserRoleEntity("USER");

    private static final List<UserEntity> USERS = new ArrayList<>();

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

//    @Autowired
    public AccountDetailsService(PasswordEncoder passwordEncoder, JwtTokenUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtUtil;
    }

    @PostConstruct
    public void setUpService() {
        USERS.add(new UserEntity("javainuse", passwordEncoder.encode("password0"), "1234@gmail.com", List.of(USER_ROLE), LocalDateTime.now()));
        USERS.add(new UserEntity("login1", passwordEncoder.encode("password1"), "12345@gmail.com", List.of(USER_ROLE), LocalDateTime.now()));
        USERS.add(new UserEntity("admin", passwordEncoder.encode("admin"), "admin@gmail.com", List.of(ADMIN_ROLE, USER_ROLE), LocalDateTime.now()));
    }

    public void registerNewUser(String login, String password) {
        USERS.add(new UserEntity(login, passwordEncoder.encode(password), "1234@gmail.com", List.of(USER_ROLE), LocalDateTime.now()));
    }

    public List<UserEntity> getAllUsersInformation() {
        return USERS;
    }

    public TokenPair generateTokenPair(String username) throws UsernameNotFoundException {
        UserDetails user = loadUserByUsername(username);

        return jwtTokenUtil.generateToken(user.getUsername());
    }

    public TokenPair regenerateTokenPair(String accessToken) {
        return jwtTokenUtil.regenerateTokenPair(accessToken);
    }

    public UserEntity getUserByUsername(String username) {
        return USERS.stream()
                .filter(user -> user.getLogin().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserEntity getUserByAccessToken(String token) throws UsernameNotFoundException {
        final String username = jwtTokenUtil.getUsernameFromToken(token);

        return getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserByUsername(username);

        return new User(userEntity.getLogin(), userEntity.getPassword(), userEntity.getRoles());
    }

    public void deleteUserByToken(String accessToken) {
        String username = jwtTokenUtil.getUsernameFromToken(accessToken);

        USERS.remove(username);
    }
}
