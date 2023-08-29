package com.eventty.userservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    USER_INFO_INSERT    (HttpStatus.OK.value(), "SignUp successful"),
    USER_INFO_FIND_BY_ID(HttpStatus.OK.value(), "User information find successful"),
    USER_INFO_UPDATE    (HttpStatus.OK.value(), "User information update successful");

    private final int status;
    private final String message;
}
