package com.tightpocket.authservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tightpocket.authservice.models.UserInfoDTO;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDTO> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, UserInfoDTO data) {
        byte[] returnValue = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            returnValue = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    @Override
    public void close() {}
}
