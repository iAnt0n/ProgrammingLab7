package collection;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Коллекция, содержимым которой управляет программа
 */
public class CityCollection {
    private ConcurrentHashMap<String, City> cityMap;
    private LocalDateTime initTime;

    public CityCollection(ConcurrentHashMap<String, City> cityMap) {
        this.cityMap = cityMap;
        this.initTime=LocalDateTime.now();
    }

    public CityCollection() {
        this.cityMap = new ConcurrentHashMap<>();
        this.initTime=LocalDateTime.now();
    }

    public ConcurrentHashMap<String, City> getCityMap() {
        return cityMap;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

}
