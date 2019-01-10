package org.nuaa.b730401.softwarereliability.core.exception;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/10 19:45
 */
public class NetworkException extends Exception{

    private String message;
    private String code;

    public NetworkException(String s) {
        super(s);
        this.message = s;
    }

    public NetworkException(String s, String code) {
        super(s);
        this.message = s;
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
