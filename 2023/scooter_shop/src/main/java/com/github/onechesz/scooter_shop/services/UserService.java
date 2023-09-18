package com.github.onechesz.scooter_shop.services;

import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTIO;
import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTOO;
import com.github.onechesz.scooter_shop.entities.UserEntity;
import com.github.onechesz.scooter_shop.repositories.UserRepository;
import com.github.onechesz.scooter_shop.security.UserDetailsImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Contract(pure = true)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByName(username);

        if (userEntityOptional.isEmpty())
            throw new UsernameNotFoundException("Пользователь с таким именем не найден.");

        return new UserDetailsImpl(userEntityOptional.get());
    }

    public Optional<UserEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Transactional
    public void save(@NotNull UserDTIO userDTIO) {
        userRepository.save(new UserEntity(userDTIO.getName(), new BCryptPasswordEncoder().encode(userDTIO.getPassword()), "ROLE_USER", new BigDecimal(500)));
    }

    public List<UserDTOO> findAll() {
        return userRepository.findAll().stream().map(UserEntity::convertToUserDTOO).toList();
    }

    public Optional<UserDTOO> findById(int id) {
        return userRepository.findById(id).map(UserEntity::convertToUserDTOO);
    }

    @Transactional
    public void editRole(int id, String role) {
        userRepository.editRole(id, role);
    }

    @Transactional
    public void replenishBalance(int id, BigDecimal sum) {
        userRepository.findById(id).ifPresent(userEntity -> {
            userEntity.setBalance(userEntity.getBalance().add(sum));
            userRepository.save(userEntity);
        });
    }
}
