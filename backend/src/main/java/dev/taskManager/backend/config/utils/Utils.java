package dev.taskManager.backend.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class Utils {

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
