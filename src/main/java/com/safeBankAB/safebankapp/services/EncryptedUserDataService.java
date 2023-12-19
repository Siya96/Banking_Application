package com.safeBankAB.safebankapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.model.EncryptedUserData;
import com.safeBankAB.safebankapp.repo.EncryptedUserDataRepo;

import java.security.SecureRandom;

@Service
public class EncryptedUserDataService {

    private final EncryptedUserDataRepo encryptedUserDataRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 16, new SecureRandom());


    @Autowired
    public EncryptedUserDataService(EncryptedUserDataRepo encryptedUserDataRepo){
        this.encryptedUserDataRepo = encryptedUserDataRepo;
    }

    public EncryptedUserData createEncryptedUserPassword(String password) {
        return new EncryptedUserData(bCryptPasswordEncoder.encode(password));
    }

    public boolean verifyUserPassword(CharSequence cipher, String encodedPassword) {

        return bCryptPasswordEncoder.matches(cipher, encodedPassword);
    }


}
