package com.infogain.igconverge.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date>
{
    @Override
    public Date deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

    	DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String date = jsonparser.getText();
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

	

}
