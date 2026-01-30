package com.example.bankcards.security.components;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    public void setJwtToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(8 * 60 * 60); // 8 часов
        response.addCookie(cookie);
    }

    public void setTokensNull(HttpServletResponse response) {
        Cookie userData = new Cookie("UserData", null);
        userData.setPath("/");
        userData.setMaxAge(0);

        Cookie jwtCookie = new Cookie("token", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(userData);
        response.addCookie(jwtCookie);
    }
}
