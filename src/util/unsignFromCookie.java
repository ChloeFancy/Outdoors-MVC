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
            System.out.println(cook.getName());
            if(cook.getName().equalsIgnoreCase("usertoken")){ //获取键
//                System.out.println(cook.getValue().toString());
                fromToken = JWT.unsign(cook.getValue().toString(),UserEntity.class);
//                System.out.println(JSON.toJSONString(fromToken));
            }
        }
        return fromToken;
    }
}
