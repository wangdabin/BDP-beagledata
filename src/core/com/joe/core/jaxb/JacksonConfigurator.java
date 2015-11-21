package com.joe.core.jaxb;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.joe.core.annotation.InitResource;
import com.joe.core.annotation.RestResource;
import com.joe.core.annotation.SecurityResource;

@SecurityResource(name = "json_support")
@InitResource(name = "json_support")
@RestResource(name = "json_support")
@Provider
@Produces(value = {MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_JSON})
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {

    private ObjectMapper mapper = new ObjectMapper();

    public JacksonConfigurator() {
//        SerializationConfig serConfig = mapper.getSerializationConfig();
//        serConfig.setDateFormat(new SimpleDateFormat(<my format>));
//        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
//        deserializationConfig.setDateFormat(new SimpleDateFormat(<my format>));
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
    }

    @Override
    public ObjectMapper getContext(Class<?> clazz) {
        return mapper;
    }

}