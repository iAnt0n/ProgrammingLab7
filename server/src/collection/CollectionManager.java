package collection;

import utils.JsonWriter;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Класс, служащий для операций над коллекцией
 */
public class CollectionManager {
    private CityCollection collection;

    /**
     * инициализирует коллекцию, с которой надо работать
     * @param col коллекция
     */
    public CollectionManager(CityCollection col){
        this.collection=col;
    }

    public CityCollection getCollection() {
        return collection;
    }

    /**
     * Возвращает информацию о коллекции
     * @return строка с информацией о коллекции
     */
    public String info(){
        return "Тип коллекции: " + collection.getCityMap().getClass().toString() + "\n" +
                "Время инициализации: " + collection.getInitTime().toString() + "\n" +
                "Элементов в коллекции: " + collection.getCityMap().size();
    }

    /**
     * Возвращает содержимое коллекции в строковом представлении
     * @return элементы коллекции в строковом представлении
     */
    public String show(){
        if (!collection.getCityMap().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, City> entry : collection.getCityMap().entrySet()) {
                sb.append("Key: ").append(entry.getKey()).append("\n").append("Value: ").append(entry.getValue().toString()).append("\n");
            }
            return sb.toString();
        } else return "Коллекция пуста";
    }

    /**
     * Очищает коллекцию
     */
    public void clear(){
        collection.getCityMap().clear();
    }

    /**
     * Добавляет элемент с указанным ключом в коллекцию
     * @param s ключ
     * @param c объект {@link City}, который нужно добавить в коллекцию
     */
    public void put(String s, City c){
        collection.getCityMap().put(s, c);
    }

    /**
     * Удаляет элемент из коллекции по заданному ключу
     * @param s ключ
     */
    public void remove(String s){
        collection.getCityMap().remove(s);
    }

    /**
     * Сохраняет коллекцию в файл в формате json
     * @param path путь к файлу
     * @throws IOException при ошибке при сохранении в файл
     */
    public void save(String path) throws IOException {
        JsonWriter writer = new JsonWriter();
        writer.write(path, collection.getCityMap());
    }

    /**
     * Возвращает число элементов коллекции с полем governor, равным заданному
     * @param governor объект {@link Human}, с которым нужно сравнивать
     * @return число совпадений
     */
    public long countByGovernor(Human governor){
        return collection.getCityMap().values().stream().filter(x -> x.getGovernor().compareTo(governor) == 0).count();
    }

    /**
     * Удаляет из коллекции все элементы, меньшие заданного
     * @param c объект {@link City}, с которым необходимо сравнивать
     */
    public void removeLower(City c){
        collection.getCityMap().entrySet().removeIf(x -> x.getValue().compareTo(c) < 0);
    }

    /**
     * Удаляет из коллекции все элементы с ключами, меньшими заданного
     * @param s ключ, с которым необходимо сравнивать
     */
    public void removeLowerKey(String s){
        collection.getCityMap().entrySet().removeIf(x -> x.getKey().compareTo(s) < 0);
    }

    /**
     * Заменяет элемент коллекции на заданный, если заданный элемент меньше элемента с указанным ключом
     * @param s ключ, элемент соответсвующий которому необходимо заменить
     * @param c элемент, с которым необходимо сравнить и заменить в случае выполнения условий
     * @return true если замена выполнена, иначе false
     */
    public boolean replaceIfLower(String s, City c){
        if(c.compareTo(collection.getCityMap().get(s))<0){
            collection.getCityMap().put(s, c);
            return true;
        }
        return false;
    }

    /**
     * Выводит один из элементов с наименьшим значениемь поля population
     * @return строковое представление элемента коллекции
     * @throws NoSuchElementException если коллекция пуста
     */
    public String minByPopulation() throws NoSuchElementException{
        StringBuilder sb = new StringBuilder();
        Map.Entry<String, City> elem = collection.getCityMap().entrySet().stream().min(Comparator.comparing(x->x.getValue().getPopulation())).get();
        return sb.append("Key: ").append(elem.getKey()).append("\n").append("Value: ").append(elem.getValue().toString()).toString();
    }

    /**
     * Вывести один из элементов с максимальным значением поля StandardOfLiving
     * @return строковое представление элемента коллекции
     * @throws NoSuchElementException если StandardOfLiving null для всех элементов
     */
    public String max_by_standard_of_living() throws NoSuchElementException {
        StringBuilder sb = new StringBuilder();
        Map.Entry<String, City> elem = collection.getCityMap().entrySet().stream().filter(x-> x.getValue().getStandardOfLiving() != null).max(
                Comparator.comparing(x->x.getValue().getStandardOfLiving().getLevel())).get();
        return sb.append("Key: ").append(elem.getKey()).append("\n").append("Value: ").append(elem.getValue().toString()).toString();
    }
}