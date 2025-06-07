package HttpHandlers;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH.mm dd.MM.yy");


    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(FORMAT.format(value));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String dateString = in.nextString();
        return dateString != null ? LocalDateTime.parse(dateString, FORMAT) : null;
    }
}

