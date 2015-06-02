package nz.co.xingsoft.memribox.server.ws.config;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

/**
 * Jackson is underlying JSON provider, define a Context resolver to provide custom date marshal/unmarshal in ObjectMapper
 * 
 */
@Component
@Provider
@Produces("application/json")
public class JacksonContextResolver
        implements ContextResolver<ObjectMapper> {

    private static final SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    private ObjectMapper mapper = new ObjectMapper();

    public JacksonContextResolver() {
        final SerializationConfig serConfig = mapper.getSerializationConfig();
        serConfig.withDateFormat(JSON_DATE_FORMAT);

        final DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig.withDateFormat(JSON_DATE_FORMAT);

        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(JSON_DATE_FORMAT);
    }

    @Override
    public ObjectMapper getContext(final Class<?> arg0) {
        return mapper;
    }

}
