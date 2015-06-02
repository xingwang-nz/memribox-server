package nz.co.xingsoft.memribox.server.business.exception;

import nz.co.xingsoft.memribox.server.business.dto.ErrorCode;

public class StoryDataNotFoundException
        extends BusinessLogicException {

    public StoryDataNotFoundException(final ErrorCode errorCode, final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(errorCode, message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public StoryDataNotFoundException(final ErrorCode errorCode, final String message, final Throwable cause) {
        super(errorCode, message, cause);
        // TODO Auto-generated constructor stub
    }

    public StoryDataNotFoundException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
        // TODO Auto-generated constructor stub
    }

    public StoryDataNotFoundException(final ErrorCode errorCode, final Throwable cause) {
        super(errorCode, cause);
        // TODO Auto-generated constructor stub
    }

    public StoryDataNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
        // TODO Auto-generated constructor stub
    }

}
