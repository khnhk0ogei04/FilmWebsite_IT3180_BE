package com.example.filmxxx.service;

import com.example.filmxxx.exception.EmailException;
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

    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Integer MIN_PASSWORD_LENGTH = 6;

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

        if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new UserException.InvalidPasswordException("Password must be at least 6 characters long.");
        }

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

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new UserException.UsernameAlreadyExistsException(userDTO.getUsername());
        }

        if (!EMAIL_PATTERN.matcher(userDTO.getEmail()).matches()) {
            throw new UserException.InvalidEmailFormatException("Email must contain at least 1 uppercase letter.");
        }

        if (userDTO.getPassword().length() < MIN_PASSWORD_LENGTH){
            throw new UserException.InvalidPasswordException("Password's length must contains at least 6 characters");
        }

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // default value:
        RoleEntity role = roleRepository.findById(2L)
                        .orElseThrow(() -> new RoleException.RoleNotFoundException(2L));

        userEntity.setRole(role);
        userEntity.setStatus(1);
        userEntity.setAccountBalance(0L); // default account balance is 0
        userEntity.setOrderCount(0L); // default order count is 0
        userEntity.setCreatedDate(LocalDateTime.now());
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public void resetPassword(String username) throws EmailException.ErrorSendEmailException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            UserEntity userEntity = user.get();

            String resetToken  = UUID.randomUUID().toString();
            String email = userEntity.getEmail();
            userEntity.setResetToken(resetToken);
            userEntity.setTokenExpiration(LocalDateTime.now().plusMinutes(10));
            userRepository.save(userEntity);

            String subject = "Password Reset";
            String message = """
            <div style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <h2 style="color: #007BFF; text-align: center;">Password Reset</h2>
                <p>Dear <b>%s</b>,</p>
                <p>We received a request to reset your password. If you made this request, please use the OTP below and click the link to reset your password:</p>
                <p><b>Your OTP:</b> <span style="color: red; font-weight: bold;">%s</span></p>
                <p>
                    <a href="http://localhost:3000/reset-password/%s" 
                       style="background-color: #007BFF; color: white; padding: 10px 20px; text-decoration: none; font-weight: bold; border-radius: 5px;">
                        Reset Password
                    </a>
                </p>
                <hr style="margin: 20px 0; border: 0; border-top: 1px solid #ddd;" />
                <p style="font-size: 14px; color: #555;">
                    If you didn’t request this, please ignore this email or contact support.
                </p>
            </div>
        """.formatted(userEntity.getFullName(), resetToken, resetToken);

            emailService.sendMessage("khanh8a04ytb@gmail.com", email, subject, message);
        } else {
            throw new EmailException.ErrorSendEmailException("Error when send email");
        }
    }

    public void updatePasswordWithToken(String resetToken, String newPassword) {
        System.out.println(resetToken + " " + newPassword);
        if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new UserException.InvalidPasswordException("Password must be at least 6 characters long.");
        }
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
            // Update new accountBalance
            Long newAccountBalance = userEntity.getAccountBalance() + amount;
            userEntity.setAccountBalance(newAccountBalance);
            userEntity.setModifiedDate(LocalDateTime.now());
            UserEntity savedUser = userRepository.save(userEntity);
            return modelMapper.map(savedUser, UserDTO.class);
        } else {
            throw new UserException.UserNotFoundException(id);
        }
    }
}
