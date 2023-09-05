package com.eventty.businessservice.common.config;

import com.eventty.businessservice.common.Enum.ErrorCode;
import com.eventty.businessservice.common.annotation.ApiErrorCode;
import com.eventty.businessservice.common.annotation.ApiSuccessData;
import com.eventty.businessservice.common.response.ErrorResponseDTO;
import com.eventty.businessservice.common.response.ResponseDTO;
import com.eventty.businessservice.common.response.SuccessResponseDTO;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final String VERSION = "v0.0.1";

    // 제목
    private final String TITLE = "\"EVENT SERVER API 명세서\"";

    // 제목 밑에 들어갈 설명
    private final String DESCRIPTION = "EVENTTY - EVENT SERVER API 명세서 입니다. \n행사에 관련된 정보만 담겨 있습니다.";

    /**
     * Swagger API 설정
     * @param
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo());
    }

    // Swagger title, version등 화면에 출력될 기본정보 setting
    private Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/jeongbeomSeo/eventty");
        license.setName("eventty");

        return new Info()
                .version(VERSION)
                .title(TITLE)
                .description(DESCRIPTION)
                .license(license);
    }
    // ----------------------------------------------------------------
    /**
     * 커스텀 어노테이션 적용
     */
    @Bean
    public OperationCustomizer customOperationCutomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            // 커스텀 어노테이션 값 불러오기
            ApiErrorCode apiErrorCodeExample = handlerMethod.getMethodAnnotation(ApiErrorCode.class);
            ApiSuccessData apiSuccessData = handlerMethod.getMethodAnnotation(ApiSuccessData.class);

            // 전처리
            operation.setResponses(new ApiResponses());

            // @ApiSuccessData가 있을 경우 실행
            if (apiSuccessData != null){
                generateSuccessResponseDoc(operation, apiSuccessData);
            }

            // @ApiErrorCode가 있을 경우 실행
            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseDoc(operation, apiErrorCodeExample.value());
            }

            return operation;
        };
    }

    /**
     * DTO.class를 기반으로한 성공 응답 예시 문서화
     * @param operation
     * @param apiSuccessData
     * @param <T>
     */
    private <T> void generateSuccessResponseDoc(Operation operation, ApiSuccessData apiSuccessData){
        Class<?> responseDTO = apiSuccessData.value();
        String status = apiSuccessData.stateCode();

        ApiResponses responses = operation.getResponses();
        ApiResponse response = new ApiResponse();
        Content content = new Content();
        MediaType mediaType = new MediaType();

        // 이건 try catch를 사용하지 않고 어떻게 쓸지 잘 모르겠어요..!
        // 아시는 분은 말씀 주세요..@
        Example successExample = new Example();
        try{
            successExample.setValue(ResponseDTO.of(SuccessResponseDTO.of(responseDTO.getConstructor().newInstance())));
        }catch(Exception e) {
            successExample.setValue(new ResponseDTO());
        }
        mediaType.addExamples("ResponseDTO", successExample);

        response.setDescription(HttpStatus.valueOf(Integer.parseInt(status)).toString());
        response.setContent(content.addMediaType("application/json", mediaType));

        responses.addApiResponse(status, response);
        operation.setResponses(responses);
    }

    /**
     * ErrorCode(EnumClass)를 기반으로한 오류 응답 문서화
     * @param operation
     * @param errorCodes
     * @param <T>
     */
    private <T> void generateErrorCodeResponseDoc(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        for(ErrorCode errorCode : errorCodes){
            ApiResponse apiResponse = Optional.ofNullable(responses.get(String.valueOf(errorCode.getStatus()))).orElseGet(ApiResponse::new);
            Content content = Optional.ofNullable(apiResponse.getContent()).orElseGet(Content::new);
            MediaType mediaType = content.getOrDefault("application/json", new MediaType());

            Example example = new Example();
            example.setDescription(errorCode.getMessage());
            example.setValue(ResponseDTO.of(ErrorResponseDTO.of(errorCode)));
            mediaType.addExamples(errorCode.name(), example);

            apiResponse.setDescription(HttpStatus.valueOf(errorCode.getStatus()).toString());
            apiResponse.setContent(content.addMediaType("application/json", mediaType));

            responses.addApiResponse(String.valueOf(errorCode.getStatus()), apiResponse);
        }
    }
}