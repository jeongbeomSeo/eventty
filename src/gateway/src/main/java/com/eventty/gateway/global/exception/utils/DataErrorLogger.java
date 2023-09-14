package com.eventty.gateway.global.exception.utils;

import com.eventty.gateway.global.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class DataErrorLogger {

    public void logging(JwtException e) {
        Object causedErrorData = e.getCausedErrorData();

        // 기본생성자일 경우
        if(e.getFields() == null && causedErrorData == null)
            return;

        StringBuffer sb = new StringBuffer("Input Data Details :\n");
        for(String field : e.getFields()){
            sb.append(field).append("=");
            try {
                // causedErrorData가 DTO or Entity 일 경우 해당 필드값 매핑
                Field fieldName = causedErrorData.getClass().getDeclaredField(field);
                fieldName.setAccessible(true);
                sb.append(fieldName.get(causedErrorData));
            }catch (Exception error){
                // causedErrorData가 Wrapper Class일 경우 causedErrorData 매핑
                sb.append(causedErrorData);
            }
            sb.append("\n");
        }
        log.error(sb.toString());
    }
}
