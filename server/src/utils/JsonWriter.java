package utils;

import collection.City;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

/**
 * Класс, отвечающий за сериализацию объектов и запись их в файл
 */
public class JsonWriter {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Записывает коллекцию в файл
     * @param path путь к файлу
     * @param collection коллекция, которую необходимо записать
     * @throws IOException при ошибке при работе с файлом
     */
    public void write(String path, LinkedHashMap<String, City> collection) throws IOException {
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(path))) {
            printWriter.print(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(collection));
        }
    }
}