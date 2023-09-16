package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.domain.entity.CsrfTokenEntity;
import com.eventty.authservice.domain.exception.CsrfTokenNotFoundException;
import com.eventty.authservice.domain.repository.CsrfRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CSRFTokenProvider {

    private static final int TOKEN_BYTE_LENGTH = 16;  // 16 bytes = 128 bits
    private final CsrfRepository csrfRepository;

    public String update(Long userId) {
        String newCsrfToken = generateToken();

        Optional<CsrfTokenEntity> csrfTokenEntityOpt = getCsrfTokenByUserId(userId);

        // 사실 앞선 로직에서 검사해서 불필요한 로직이긴 함.
        if (csrfTokenEntityOpt.isEmpty())
            throw new CsrfTokenNotFoundException(userId);

        CsrfTokenEntity csrfTokenEntity = csrfTokenEntityOpt.get();
        csrfTokenEntity.setName(newCsrfToken);

        csrfRepository.save(csrfTokenEntity);

        return newCsrfToken;
    }

    public String save(Long userId) {
        String newCsrfToken = generateToken();

        CsrfTokenEntity csrfTokenEntity = CsrfTokenEntity.builder()
                .userId(userId)
                .name(newCsrfToken)
                .build();

        csrfRepository.save(csrfTokenEntity);

        return newCsrfToken;
    }

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_BYTE_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }

    public Optional<CsrfTokenEntity> getCsrfTokenByUserId(Long userId) {
        return csrfRepository.findByUserId(userId);
    }
}
