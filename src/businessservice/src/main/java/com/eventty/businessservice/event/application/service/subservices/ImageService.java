package com.eventty.businessservice.event.application.service.subservices;

import com.eventty.businessservice.event.application.FileHandler;
import com.eventty.businessservice.event.application.dto.request.ImageUploadRequestDTO;
import com.eventty.businessservice.event.application.dto.response.ImageResponseDTO;
import com.eventty.businessservice.event.domain.entity.EventImageEntity;
import com.eventty.businessservice.event.domain.repository.EventImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final EventImageRepository eventImageRepository;
    private final FileHandler fileHandler;

    public ImageResponseDTO findImageByEventId(Long eventId){
        EventImageEntity eventImageEntity = eventImageRepository.selectEventImageByEventId(eventId);
        //String imageResourceFromStorage = getFileInfo(eventImageEntity.getStoredFilePath());
        Long imageId = eventImageEntity.getId();
        String imagePathFromStorage = eventImageEntity.getStoredFilePath();
        String imageOriginalFileName = eventImageEntity.getOriginalFileName();
        return new ImageResponseDTO(imageId, imagePathFromStorage, imageOriginalFileName);
    }

    public Long createEventImage(Long eventId, MultipartFile image){
        uploadFile(eventId, image);
        return eventId;
    }

    public Long deleteEventImage(Long eventId){
        // DB 삭제
        eventImageRepository.deleteEventImageByEventId(eventId);
        return eventId;
    }

    // 파일 업로드
    private void uploadFile(Long eventId, MultipartFile image) {
        try {
            // 파일 저장
            ImageUploadRequestDTO imageUploadRequestDTO = fileHandler.uploadFile(eventId, image);

            // DB 저장
            eventImageRepository.insertEventImage(imageUploadRequestDTO.toEntity());

        } catch (IOException e) {
            log.error("[Image] File Upload Error : " + e.getMessage());
        }
    }

    // 파일 정보 가져오기
    private String getFileInfo(String filePath){
        try {
            return fileHandler.getFileInfo(filePath);
        }catch (IOException e){
            log.error("[Image] Get File Info Error : " + e.getMessage());
        }
        return null;
    }
}
