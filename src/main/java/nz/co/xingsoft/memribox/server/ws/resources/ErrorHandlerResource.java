package nz.co.xingsoft.memribox.server.ws.resources;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response.Status;

import nz.co.xingsoft.memribox.server.business.dto.ErrorCode;
import nz.co.xingsoft.memribox.server.business.dto.ErrorResponse;
import nz.co.xingsoft.memribox.server.business.exception.BusinessLogicException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.JResponse;

@Component
public class ErrorHandlerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerResource.class);

    public JResponse<?> processException(final String action, final Throwable t) {
        LOGGER.error("{} failed: {}", action, t.getMessage(), t);

        final ErrorResponse response = new ErrorResponse();

        if (t instanceof BusinessLogicException) {

            response.setErrorCode(((BusinessLogicException) t).getErrorCode());
            response.setMessage(((BusinessLogicException) t).getMessage());

            return JResponse.status(Status.BAD_REQUEST).entity(response).build();
        } else if (t instanceof ValidationException) {
            response.setErrorCode(ErrorCode.VALIDATION_FAILED);

            final StringBuilder msgBuilder = new StringBuilder();
            if (t instanceof ConstraintViolationException) {
                final Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) t).getConstraintViolations();
                msgBuilder.append("Invalid fields - ");

                boolean first = true;
                for (final ConstraintViolation<?> violation : violations) {
                    msgBuilder.append(!first ? ", " : "").append(violation.getMessage());
                    first = false;
                }
            } else {
                msgBuilder.append(t.getMessage());
            }

            // TODO: populate message
            response.setMessage(msgBuilder.toString());

            return JResponse.status(Status.BAD_REQUEST).entity(response).build();
        } else {
            // response.setErrorCode(ErrorCode.INTERNAL_SERVER_ERROR);
            // response.setMessage(t.getMessage());
            return JResponse.status(Status.INTERNAL_SERVER_ERROR).entity(t.getMessage()).build();
        }
    }
}
