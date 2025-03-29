package sample.common.exception;

public class RefreshTokenNotFoundException extends ResourceNotFoundException {

    public RefreshTokenNotFoundException() {
        super(ErrorMessage.REFRESH_TOKEN_NOT_FOUND);
    }

}
