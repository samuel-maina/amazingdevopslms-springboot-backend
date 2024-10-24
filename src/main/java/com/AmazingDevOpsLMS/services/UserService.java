package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.exceptions.EmailAlreadyRegisteredException;
import com.AmazingDevOpsLMS.exceptions.ResourceNotFoundException;
import com.AmazingDevOpsLMS.exceptions.UserActivationException;
import com.AmazingDevOpsLMS.model.Image;
import com.AmazingDevOpsLMS.model.LuluSMSClient;
import com.AmazingDevOpsLMS.model.LuluSMSResponseObject;
import com.AmazingDevOpsLMS.model.Role;
import com.AmazingDevOpsLMS.model.Roles;
import com.AmazingDevOpsLMS.model.Student;

import com.AmazingDevOpsLMS.model.User;
import com.AmazingDevOpsLMS.model.UserRole;
import com.AmazingDevOpsLMS.repositories.ImageRepository;
import com.AmazingDevOpsLMS.repositories.RoleRepository;
import com.AmazingDevOpsLMS.repositories.UserRepository;
import com.AmazingDevOpsLMS.repositories.UserRolesRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.bytebuddy.utility.RandomString;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * class handles the auto-activation and auto-deactivation of user accounts
 * based on time set
 *
 * 03-10-2022
 *
 * @author Samuel Maina
 * @version 1.0
 */
@Service
@EnableScheduling
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private LuluSMSClient SMSClient;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Saves users
     *
     * @param user User object/instance
     * @return scheduled user
     */
    public User saveUser(User user, Roles role) {
        if (role.ordinal() == 0) {

        }
        Optional<User> user_ = userRepository.getUserByEmail(user.getEmail());
        if (user_.isPresent()) {
            throw new EmailAlreadyRegisteredException("");
        }

        Random random = new Random();
        String code = String.format("%04d", random.nextInt(10000));
        user.setActivationCode(Integer.parseInt(code));
        User temp = userRepository.save(user);
        UserRole userRole = new UserRole();
        Role role_ = roleRepository.findById(role.ordinal()).get();
        userRole.setUser(temp);
        userRole.setRoles(role_);
        userRolesRepository.save(userRole);

        return temp;
    }

    /**
     * retrieves all users
     *
     * @return list of users
     */
    public Iterable getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * finds user by id
     *
     * @param userId
     * @return user object
     */
    public User findUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResourceNotFoundException("user not found");
        }
    }

    /**
     * sets the activation and deactivation status of an account
     */
    /**
     * schedules an account
     *
     * @param scheduledAccountActivator time object [activation and deactivation
     * time]
     * @param userId unique user identifier
     * @return ScheduledAccountActivator object
     */
    /**
     * update a user object
     *
     * @param user object
     * @return updated user object
     */
    public User update(User user, String email) {
        User user_ = findUserById(email);
        user_.setAddress(user.getAddress());
        user_.setFirstname(user.getFirstname());
        user_.setLastname(user.getLastname());
        user_.setPhone(user.getPhone());
        return userRepository.save(user_);
    }

    public User updateProfile(User user, String email) {
        Image image = user.getImage();
        image.setId(RandomString.make(64));
        Image tempImage = imageRepository.save(image);
        User user_ = findUserById(email);
        user_.setImage(tempImage);
        user_.setAddress(user.getAddress());
        user_.setFirstname(user.getFirstname());
        user_.setLastname(user.getLastname());
        user_.setPhone(user.getPhone());
        return userRepository.save(user_);
    }

    public void activateUser(User user) {
        User user_ = userRepository.getUserByEmail(user.getEmail()).get();
        if (user_.getActivationCode() == user.getActivationCode()) {
            user_.setEnabled(true);
            userRepository.save(user_);
        } else {
            throw new UserActivationException("");
        }
    }

    public void deActivateUser(String id) {
        User user = findUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    public boolean getStatus(String id) {
        return findUserById(id).isEnabled();
    }

    public List<User> findUsersByRole(String roleId) {
        return userRolesRepository.findUsersByRole(roleId);
    }

    public int getUserCount() {
        return userRepository.getUserCount();
    }

    public int getActiveUserCount() {
        return userRepository.getActiveUserCount();
    }
}
