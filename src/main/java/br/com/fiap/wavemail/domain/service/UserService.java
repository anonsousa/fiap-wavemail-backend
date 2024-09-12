package br.com.fiap.wavemail.domain.service;

import br.com.fiap.wavemail.domain.dto.auth.AuthUserLoginDto;
import br.com.fiap.wavemail.domain.dto.user.UserAddDto;
import br.com.fiap.wavemail.domain.dto.user.UserReturnDto;
import br.com.fiap.wavemail.domain.dto.user.UserUpdateDto;
import br.com.fiap.wavemail.domain.enums.UserRole;
import br.com.fiap.wavemail.domain.model.UserEntity;
import br.com.fiap.wavemail.domain.repository.UserEntityRepository;
import br.com.fiap.wavemail.infra.exceptions.InvalidPasswordException;
import br.com.fiap.wavemail.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public UserReturnDto createUser(UserAddDto user){
        String encryptPassword = new BCryptPasswordEncoder().encode(user.password());

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(encryptPassword);
        userEntity.setRole(UserRole.USER);
        userEntity.setCreatedAt(LocalDateTime.now().withNano(0));

        return new UserReturnDto(userRepository.save(userEntity));
    }

    @Transactional(readOnly = true)
    public UserReturnDto getUserById(UUID id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found!"));
        return new UserReturnDto(userEntity);
    }

    @Transactional(readOnly = true)
    public Page<UserReturnDto> getAllusers(Pageable pageable){
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userEntityPage.map(UserReturnDto::new);
    }

    @Transactional
    public UserReturnDto updateUser(UUID id, String email, UserUpdateDto user){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found!"));

        if (email != null && userEntity.getEmail().equals(email)) {
            if(encoder.matches(user.currentPassword(), userEntity.getPassword()))
            {
                userEntity.setName(user.name());
                userEntity.setEmail(user.email());
                userEntity.setPassword(encoder.encode(user.newPassword()));
                userRepository.save(userEntity);
                return new UserReturnDto(userEntity);

            } else {
                throw new InvalidPasswordException("Invalid Password!");
            }
        }
        throw new ItemNotFoundException("User not found!");
    }

    @Transactional
    public void deleteUser(UUID id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found!"));
        userEntity.setEnabled(false);
        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public boolean verifyUser(AuthUserLoginDto user){
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(user.email());

        if (userEntity.isPresent()) {
            if (encoder.matches(user.password(), userEntity.get().getPassword())) {
                return true;
            }
            return false;
        }

        return false;
    }
}
