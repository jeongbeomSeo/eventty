package com.eventty.businessservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString @Setter
@Getter @NoArgsConstructor
public class ResponseDTO<T> {

    /**
     * 실패 응답
     *
     * {
     *     "errorResponseDTO": {
     *         "code": ""
     *     },
     *     "success": false
     * }
     *
     * 성공 응답
     *
     *  * {
     *  *     "successResponseDTO": {
     *  *         "data": "{
     *              "Field_1" : "Value_1"
     *              ...
     *  *     },
     *  *     "success": true
     *  * }
     *
     *  혹은 비어있는 형태
     */

    private boolean isSuccess = true;
    private ErrorResponseDTO errorResponseDTO;
    private SuccessResponseDTO<T> successResponseDTO;

    private ResponseDTO(ErrorResponseDTO errorResponseDTO) {
        this.isSuccess = false;
        this.errorResponseDTO = errorResponseDTO;
        this.successResponseDTO = null;
    }

    private ResponseDTO(SuccessResponseDTO<T> successResponseDTO) {
        this.isSuccess = true;
        this.errorResponseDTO = null;
        this.successResponseDTO = successResponseDTO;
    }

    private ResponseDTO(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.errorResponseDTO = null;
        this.successResponseDTO = null;
    }

    public static ResponseDTO<Void> of(ErrorResponseDTO errorResponseDTO) {
        return new ResponseDTO<>(errorResponseDTO);
    }

    public static <T> ResponseDTO<T> of(SuccessResponseDTO<T> successResponseDTO) {
        return new ResponseDTO<T>(successResponseDTO);
    }

    public static ResponseDTO<Void> of(boolean isSuccess) {
        return new ResponseDTO<>(isSuccess);
    }
}