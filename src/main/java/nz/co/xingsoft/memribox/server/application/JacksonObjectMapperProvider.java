package nz.co.xingsoft.memribox.server.application;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

/**
 * 
 * A custom Jackson JSON mapper*
 */
@Component
// tell spring it's a provider (type is determined by the implements)
@Provider
public class JacksonObjectMapperProvider
        implements ContextResolver<ObjectMapper> {

    private ObjectMapper mapper = new ObjectMapper();

    public JacksonObjectMapperProvider() {
        final SerializationConfig serConfig = mapper.getSerializationConfig();
        serConfig.withDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        final DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig.withDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }
}
