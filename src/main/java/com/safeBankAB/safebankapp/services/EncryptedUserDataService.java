package com.safeBankAB.safebankapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Constants;
import com.safeBankAB.safebankapp.model.EncryptedUserData;
import com.safeBankAB.safebankapp.repo.EncryptedUserDataRepo;
import com.safeBankAB.safebankapp.utilities.AES256;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptedUserDataService {

    private final EncryptedUserDataRepo encryptedUserDataRepo;


    @Autowired
    public EncryptedUserDataService(EncryptedUserDataRepo encryptedUserDataRepo) {
        this.encryptedUserDataRepo = encryptedUserDataRepo;
    }

    public EncryptedUserData createEncryptedUserPassword(String password) {

        SecretKey key = AES256.generateKey();
        IvParameterSpec iv = AES256.generateIv();
        String encryptedPassword = AES256.encrypt(Constants.AES_CDC_PKCS5PADDING_ALGORITHM , password, key, iv);

        return new EncryptedUserData(encryptedPassword, Base64.getEncoder().encodeToString(key.getEncoded()), Base64.getEncoder().encodeToString(iv.getIV()));

    }

    public String decryptEncryptedUserPassword(String cipher, String secretKey, String iv) {

        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, Constants.AES_STANDARD);

        byte[] decodedIv = Base64.getDecoder().decode(iv);
        IvParameterSpec originalIv = new IvParameterSpec(decodedIv, 0, decodedIv.length);

        String originalPassword = AES256.decrypt(Constants.AES_CDC_PKCS5PADDING_ALGORITHM, cipher, originalKey, originalIv);
        return originalPassword;
    }

}
