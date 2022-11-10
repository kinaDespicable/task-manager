package dev.taskManager.backend.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class Utils {

    public static Map<String,Object> generateResponseBody(HttpStatus status, boolean success){
        Map<String,Object> responseBody = new LinkedHashMap<>();
        responseBody.put("success", success);
        responseBody.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        responseBody.put("status_code", status.value());
        responseBody.put("status", status);
        return responseBody;
    }

    public static void generateResponse(HttpServletResponse response, Object responseBody) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValue(response.getOutputStream(), responseBody);
    }
    public static boolean isAdmin(HttpServletRequest request){
        return request.getRequestURI().contains("/admin/");
    }
}
