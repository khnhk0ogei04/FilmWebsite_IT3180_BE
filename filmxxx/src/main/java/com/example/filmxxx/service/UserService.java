package com.example.filmxxx.service;

import com.example.filmxxx.repository.UserRepository;
import com.example.filmxxx.dto.UserDTO;
import com.example.filmxxx.entity.RoleEntity;
import com.example.filmxxx.entity.UserEntity;
import com.example.filmxxx.exception.RoleException;
import com.example.filmxxx.exception.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private com.example.filmxxx.repository.roleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Long getTotalTicketsByUserId(Long userId) {
            return userRepository.countTicketsByUserId(userId);
    }

    // Get all users in one page:
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    // Get users (pageable)
    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    Long totalTicketsByUserId = userRepository.countTicketsByUserId(user.getId());
                    userDTO.setOrderCount(totalTicketsByUserId);
                    return userDTO;
                });
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            if (passwordEncoder.matches(currentPassword, userEntity.getPassword())) {
                userEntity.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(userEntity);
                return true;
            }
        }
        return false;
    }

    public UserDTO getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()){
            UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);

            Long orderCounts = userRepository.countTicketsByUserId(id);
            userDTO.setOrderCount(orderCounts);
            return userDTO;
        } else {
            throw new UserException.UserNotFoundException(id);
        }
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new UserException.UsernameAlreadyExistsException(userDTO.getUsername());
        }

        if (!EMAIL_PATTERN.matcher(userDTO.getEmail()).matches()) {
            throw new UserException.InvalidEmailFormatException(userDTO.getEmail());
        }

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // default value:
        RoleEntity role = roleRepository.findById(2L)
                        .orElseThrow(() -> new RoleException.RoleNotFoundException(2L));

        userEntity.setRole(role);
        userEntity.setAccountBalance(0L); // default account balance is 0
        userEntity.setOrderCount(0L); // default order count is 0
        userEntity.setCreatedDate(LocalDateTime.now());
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public void resetPassword(String username){
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            UserEntity userEntity = user.get();

            String resetToken  = UUID.randomUUID().toString();
            String email = userEntity.getEmail();
            userEntity.setResetToken(resetToken);
            userEntity.setTokenExpiration(LocalDateTime.now().plusMinutes(10));
            userRepository.save(userEntity);

            String subject = "Password Reset";
            String message = "Click here to reset your password: "
                    + "http://localhost:3000/reset-password/" + resetToken;
            emailService.sendMessage("khanh8a04ytb@gmail.com", email, subject, message);
        } else {
            throw new RuntimeException("Error when send email");
        }
    }

    public void updatePasswordWithToken(String resetToken, String newPassword) {
        System.out.println(resetToken + " " + newPassword);
        Optional<UserEntity> userOptional = userRepository.findByResetToken(resetToken);
        if (userOptional.isEmpty()) {
            throw new UserException.InvalidTokenException("Token is not valid.");
        }

        UserEntity user = userOptional.get();
        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new UserException.ExpiredTokenException("Token is expired");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiration(null);
        userRepository.save(user);
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()){
            UserEntity userEntity = user.get();

            userEntity.setFullName(userDTO.getFullname());
            userEntity.setEmail(userDTO.getEmail());

            if (!EMAIL_PATTERN.matcher(userDTO.getEmail()).matches()) {
                throw new UserException.InvalidEmailFormatException(userDTO.getEmail());
            }

            userEntity.setPhone(userDTO.getPhone());
            userEntity.setCity(userDTO.getCity());
            userEntity.setAvatar(userDTO.getAvatar());

            UserEntity updatedUser = userRepository.save(userEntity);
            return modelMapper.map(updatedUser, UserDTO.class);
        } else {
            throw new UserException.UserNotFoundException(id);
        }
    }

    public UserDTO addBalance(Long id, Long amount){
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()){
            UserEntity userEntity = user.get();
            userEntity.setAccountBalance(userEntity.getAccountBalance() + amount);
            userEntity.setModifiedDate(LocalDateTime.now());

            UserEntity savedUser = userRepository.save(userEntity);
            return modelMapper.map(savedUser, UserDTO.class);
        } else {
            throw new UserException.UserNotFoundException(id);
        }
    }
}
