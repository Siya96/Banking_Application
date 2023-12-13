package com.safeBankAB.safebankapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.EncryptedUserData;
import com.safeBankAB.safebankapp.model.User;
import com.safeBankAB.safebankapp.repo.EncryptedUserDataRepo;
import com.safeBankAB.safebankapp.repo.UserRepo;

import java.util.Optional;




@Service
public class UserService {

    private final UserRepo userRepo;
    private final EncryptedUserDataRepo encryptedUserDataRepo;

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
    public UserService(UserRepo userRepo, EncryptedUserDataRepo encryptedUserDataRepo) {
        this.userRepo = userRepo;
        this.encryptedUserDataRepo = encryptedUserDataRepo;
    }

    public Pair<User, Status> createUser(String name, String socialSecurityNumber, EncryptedUserData encryptedUserData)  {
        User user;
        Status status;
        if (!isUSerRegistered(name, socialSecurityNumber)) {
            user = new User(name, socialSecurityNumber);
            user.setEncryptedUserData(encryptedUserData);
            status = Status.USER_CREATED;
            userRepo.save(user);
        }
        else {
            user = getUser(name, socialSecurityNumber);
            status = Status.USER_ALREADY_EXISTS;
        }
        return Pair.of(user, status);
    }

    public boolean isUSerRegistered(String name, String socialSecurityNumber) {
        return getUser(name, socialSecurityNumber) != null;
    }
    public User getUser(String name, String socialSecurityNumber) {
        Optional<User> user = userRepo.findByNameAndSocialSecurityNumber(name, socialSecurityNumber);
        return user.orElse(null);
    }


    public UserRepo getUserRepo() {
        return this.userRepo;
    }
}
