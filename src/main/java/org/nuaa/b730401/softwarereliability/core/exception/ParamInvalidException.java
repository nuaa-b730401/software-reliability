package org.nuaa.b730401.softwarereliability.core.exception;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/10/17 12:27
 */
public class ParamInvalidException extends Exception {

    private String message;
    private String code;

    public ParamInvalidException(String s) {
        super(s);
        this.message = s;
    }

    public ParamInvalidException(String s, String code) {
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
