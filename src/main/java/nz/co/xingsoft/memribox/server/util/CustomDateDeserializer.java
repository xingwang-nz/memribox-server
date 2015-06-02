package nz.co.xingsoft.memribox.server.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class CustomDateDeserializer
        extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(final JsonParser jsonParser, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        final ObjectCodec oc = jsonParser.getCodec();
        final JsonNode node = oc.readTree(jsonParser);

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return formatter.parse(node.getTextValue());
        } catch (final ParseException e) {
            return null;
        }

    }

}
