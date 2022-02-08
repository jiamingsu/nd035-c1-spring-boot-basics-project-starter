package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public int createCredential(Credential credential) {
        encryptCredential(credential);
        return credentialMapper.insert(credential);
    }

    public List<Credential> listAllCredentials(Integer userId) {
        List<Credential> credentials = credentialMapper.listAllCredentials(userId);
        credentials.forEach(this::decryptCredential);
        return credentials;
    }

    public int updateCredential(Credential credential) {
        encryptCredential(credential);
        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(Integer userId, Integer credentialId) {
        return credentialMapper.deleteCredential(userId, credentialId);
    }

    private String createKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private void encryptCredential(Credential credential) {
        String key = createKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setKey(key);
        credential.setPassword(encryptedPassword);
    }

    private void decryptCredential(Credential credential) {
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        // return decryptedPassword using key field
        credential.setKey(decryptedPassword);
    }
}
