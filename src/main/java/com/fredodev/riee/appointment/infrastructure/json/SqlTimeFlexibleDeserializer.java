package com.fredodev.riee.appointment.infrastructure.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

public class SqlTimeFlexibleDeserializer extends JsonDeserializer<Time> {

    @Override
    public Time deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken token = parser.currentToken();

        if (token == JsonToken.VALUE_NUMBER_INT) {
            return parseHour(parser.getIntValue());
        }

        if (token == JsonToken.VALUE_STRING) {
            String rawValue = parser.getText();
            if (rawValue == null || rawValue.isBlank()) {
                return null;
            }

            String value = rawValue.trim();
            if (value.matches("^\\d{1,2}$")) {
                return parseHour(Integer.parseInt(value));
            }
            if (value.matches("^\\d{1,2}:\\d{2}$")) {
                return Time.valueOf(LocalTime.parse(value + ":00"));
            }
            if (value.matches("^\\d{1,2}:\\d{2}:\\d{2}$")) {
                return Time.valueOf(LocalTime.parse(value));
            }
        }

        throw new IOException("horaCita debe ser numero de hora o texto con formato HH:mm o HH:mm:ss");
    }

    private Time parseHour(int hour) throws IOException {
        if (hour < 0 || hour > 23) {
            throw new IOException("horaCita debe estar entre 0 y 23");
        }
        return Time.valueOf(LocalTime.of(hour, 0));
    }
}
