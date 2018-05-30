package util;

import model.UserEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class unsignFromCookie {
    public static UserEntity unsign(HttpServletRequest request){
        UserEntity fromToken = null;
        Cookie[] cookie = request.getCookies();
        for (int i = 0; i < cookie.length; i++) {
            Cookie cook = cookie[i];
            if(cook.getName().equalsIgnoreCase("usertoken")){ //获取键
                fromToken = JWT.unsign(cook.getValue().toString(),UserEntity.class);
            }
        }
        return fromToken;
    }
}
