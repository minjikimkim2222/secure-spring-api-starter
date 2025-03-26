package sample.common.exception;

public enum ErrorMessage {
    // global
    INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", "g001"),

    // auth
    UNAUTHORIZED("권한이 없습니다", "a001");

    private final String message;
    private final String code;

    ErrorMessage(String message, String code){
        this.message = message;
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public String getMessage() {
        return message;
    }
}
