package nz.co.xingsoft.memribox.server.business.exception;

import nz.co.xingsoft.memribox.server.business.dto.ErrorCode;

public class BusinessLogicException
        extends Exception {

    final ErrorCode errorCode;

    public BusinessLogicException(final ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BusinessLogicException(final ErrorCode errorCode, final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public BusinessLogicException(final ErrorCode errorCode, final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessLogicException(final ErrorCode errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessLogicException(final ErrorCode errorCode, final Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
