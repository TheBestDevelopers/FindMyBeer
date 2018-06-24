package com.thebestdevelopers.find_my_beer.DAO;

import com.sun.deploy.util.SessionState;
import com.thebestdevelopers.find_my_beer.DTO.GetUsernameDTO;
import com.thebestdevelopers.find_my_beer.model.ClientEntity;
import com.thebestdevelopers.find_my_beer.model.PhotosEntity;
import com.thebestdevelopers.find_my_beer.model.RoleEntity;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.ClientRepository;
import com.thebestdevelopers.find_my_beer.repository.PhotoRepository;
import com.thebestdevelopers.find_my_beer.repository.RoleRepository;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula
 *              Grzegorz Nowak - naprawiono usuwanie uzytkownika - 22.05.2018
 *              Grzegorz Nowak - naprawiono modyfikacje hasla - 22.05.2018
 *              Jakub Pisula - dodanie getUsername
 */
@Service
public class UserDaoImpl implements UserDao{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ClientRepository clientRepository;


    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);
        return(hashed_password);
    }

    @Override
    public UserEntity createUser(String username, String password, String role) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(hashPassword(password));
        userEntity = userRepository.save(userEntity);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        roleEntity.setUserId(userEntity.getUserId());
        roleEntity = roleRepository.save(roleEntity);
        roleEntity.setUserByUserId(userEntity);
        userEntity.setRoleByUserId(roleEntity);
        PhotosEntity photosEntity = new PhotosEntity();
        photosEntity.setPath("");
        photosEntity = photoRepository.save(photosEntity);
        if(role.equals("client")){
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setClientId((int) userEntity.getUserId());
            clientEntity.setPhotoId(photosEntity.getPhotoId());
            clientRepository.save(clientEntity);
        }

        return userEntity;
    }

    @Override
    public Boolean changeUserPassword(String username, String password, String newPassword) {
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if(pwEncoder.matches(password, userEntity.getPassword())) {
            userEntity.setPassword(hashPassword(newPassword));
            userRepository.save(userEntity);
            return true;
        }
        else
            return false;
    }

    @Override
    public Boolean deleteUser(String username, String password) {
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if(pwEncoder.matches(password, userEntity.getPassword())) {
            roleRepository.deleteById(userEntity.getUserId());
            userRepository.deleteById(userEntity.getUserId());
            return true;
        }
        else
            return false;
    }

    @Override
    public GetUsernameDTO getUsername(int id) {
        return new GetUsernameDTO(userRepository.findByUserId(id).getUsername());
    }
}
