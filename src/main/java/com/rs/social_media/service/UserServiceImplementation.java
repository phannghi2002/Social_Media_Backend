package com.rs.social_media.service;

import com.rs.social_media.config.jwtProvider;
import com.rs.social_media.exceptions.UserExceptions;
import com.rs.social_media.model.User;
import com.rs.social_media.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName((user.getFirstName()));
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());

        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    @Override
    public User findUserById(Integer userId) throws UserExceptions {

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
       throw new UserExceptions("User not exist with userId " + userId);


    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return  user;

    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws UserExceptions {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        user2.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(user2.getId());

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
    }

    @Override
    public User updateUser(User user, Integer userId) throws UserExceptions{
        Optional<User> user1 = userRepository.findById(userId);

        if(user1.isEmpty()){
            throw new UserExceptions("User not exist id " + userId);
        }
        User oldUser = user1.get();

        if (user.getFirstName()!=null){
            oldUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName()!=null){
            oldUser.setLastName(user.getLastName());
        }
        if (user.getEmail()!=null){
            oldUser.setEmail(user.getEmail());
        }
        if(user.getGender()!=null){
            oldUser.setGender(user.getGender());
        }
//        if(user.getPassword()!=null){
//            oldUser.setPassword(user.getPassword());
//        }

        User updatedUser = userRepository.save(oldUser);
        return updatedUser;
    }

    @Override
    public List<User> searchUser(String query2) {
        return userRepository.searchUser(query2);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        User user = userRepository.findByEmail(email);
        return user;
    }
}
