package com.careerit.cb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class AppUtil {

      private AppUtil(){

      }
      public static String convertString(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
         return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
}
