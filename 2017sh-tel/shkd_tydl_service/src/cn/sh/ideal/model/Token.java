package cn.sh.ideal.model;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author ScienJus
 * @date 2017-5-2.
 */
public class Token {
 
    //用户id
    private long userId;
 
    //随机生成的uuid
    private String token;
 
    public Token(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
 
    public long getUserId() {
        return userId;
    }
 
    public void setUserId(long userId) {
        this.userId = userId;
    }
 
    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
}
