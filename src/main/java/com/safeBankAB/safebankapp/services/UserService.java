package com.safeBankAB.safebankapp.services;

import com.safeBankAB.safebankapp.DataTransferObjects.UserDTO;
import com.safeBankAB.safebankapp.httpRequestInput.UserCredentialsInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.entities.EncryptedUserData;
import com.safeBankAB.safebankapp.model.entities.User;
import com.safeBankAB.safebankapp.repo.UserRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final EncryptedUserDataService encryptedUserDataService;

    /*
     Constructor-injection:
     1) The dependencies are clearly identified.
        There is no way to forget one when testing,
        or instantiating the object in any other circumstance (like creating the bean instance explicitly in a config class).
     2) The dependencies can be final, which helps with robustness and thread-safety.
     3) You don't need reflection to set the dependencies. InjectMocks is still usable, but not necessary.
        You can just create mocks by yourself and inject them by simply calling the constructor
     */
    @Autowired
    public UserService(UserRepo userRepo, EncryptedUserDataService encryptedUserDataService) {
        this.userRepo = userRepo;
        this.encryptedUserDataService = encryptedUserDataService;
    }

    @Transactional
    public UserDTO createUser(String name, String socialSecurityNumber, EncryptedUserData encryptedUserData)  {
        User user;
        if (getUser(name, socialSecurityNumber).isEmpty()) {
            user = new User(name, socialSecurityNumber);
            user.setEncryptedUserData(encryptedUserData);
            return new UserDTO(userRepo.save(user), Status.USER_CREATED);
        }
        else {
            return new UserDTO(getUser(name, socialSecurityNumber).get(), Status.USER_ALREADY_EXISTS);
        }
    }

    public Status verifyUser(UserCredentialsInput userCredentialsInput) {
        Optional<User> user = getUser(userCredentialsInput.getName(), userCredentialsInput.getSocialSecurityNumber());
        if(user.isPresent()) {
            EncryptedUserData encryptedUserData = user.get().getEncryptedUserData();
            if(encryptedUserDataService.verifyUserPassword(userCredentialsInput.getPassword(), encryptedUserData.getEncryptedPassword())) {
                return Status.SUCCESSFUL_AUTHENTICATION;
            }
            return Status.FAILED_AUTHENTICATION;
        }
        else {
            return Status.USER_DOES_NOT_EXIST;
        }
    }

    public Optional<User> getUser(String name, String socialSecurityNumber) {
            return userRepo.findByNameAndSocialSecurityNumber(name,socialSecurityNumber);
    }

}
