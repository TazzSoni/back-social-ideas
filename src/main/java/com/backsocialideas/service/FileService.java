package com.backsocialideas.service;

import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.ProfileImageEntity;
import com.backsocialideas.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final ProfileImageRepository profileImageRepository;

    public ProfileImageEntity getProfileImageById(Long imageId) {
        return profileImageRepository.findById(imageId).orElseThrow(() -> new RecordNotFoundException("Imagem de perfil não encontrada!"));
    }

    public void deleteById(Long id) {
        profileImageRepository.deleteById(id);
    }
}
