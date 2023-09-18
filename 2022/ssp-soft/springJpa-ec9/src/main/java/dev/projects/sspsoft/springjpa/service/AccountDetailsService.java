package dev.projects.sspsoft.springjpa.service;

import dev.projects.sspsoft.springjpa.domain.RoleAuthority;
import dev.projects.sspsoft.springjpa.domain.RoleEntity;
import dev.projects.sspsoft.springjpa.domain.TokenPair;
import dev.projects.sspsoft.springjpa.domain.UserEntity;
import dev.projects.sspsoft.springjpa.repository.RoleRepository;
import dev.projects.sspsoft.springjpa.repository.UserRepository;
import dev.projects.sspsoft.springjpa.util.JwtTokenUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    public AccountDetailsService(PasswordEncoder passwordEncoder,
                                 JwtTokenUtil jwtUtil,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void registerNewUser(String login, String password) {
        RoleEntity USER_ROLE = roleRepository.findByName("USER");
        UserEntity newUser = new UserEntity(login, "1234@gmail.com", passwordEncoder.encode(password), Instant.now(), Set.of(USER_ROLE));
        userRepository.save(newUser);
    }

    public List<UserEntity> getAllUsersInformation() {
        return userRepository.findAll();
    }

    public TokenPair generateTokenPair(String username) throws UsernameNotFoundException {
        UserDetails user = loadUserByUsername(username);

        return jwtTokenUtil.generateToken(user.getUsername());
    }

    public TokenPair regenerateTokenPair(String accessToken) {
        return jwtTokenUtil.regenerateTokenPair(accessToken);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findAll()
                .stream()
                .filter(entity -> entity.getLogin().equals(username))
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

        Set<RoleAuthority> rolesAuthorities = userEntity.getPublicUserRoles()
                .stream()
                .map(RoleAuthority::new)
                .collect(Collectors.toSet());

        return new User(userEntity.getLogin(), userEntity.getPassword(), rolesAuthorities);
    }

    public void deleteUserByToken(String accessToken) {
        String username = jwtTokenUtil.getUsernameFromToken(accessToken);
        UserEntity candidate = getUserByUsername(username);
        userRepository.delete(candidate);
    }
}
