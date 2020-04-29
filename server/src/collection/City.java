package collection;

import exceptions.InvalidFieldException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, экземпляры которого хранятся в коллекции
 */
public class City implements Comparable, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private Climate climate; //Поле может быть null
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле не может быть null
    private String user;
    private static int maxId = 1;
    private static final long serialVersionUID=29082002L;

    public City(String name,
                Coordinates coordinates,
                float area,
                Long population,
                Float metersAboveSeaLevel,
                Climate climate,
                Government government,
                StandardOfLiving standardOfLiving,
                Human governor){
        this.name=name;
        this.coordinates=coordinates;
        this.area=area;
        this.population=population;
        this.metersAboveSeaLevel=metersAboveSeaLevel;
        this.climate=climate;
        this.government=government;
        this.standardOfLiving=standardOfLiving;
        this.governor=governor;
        this.creationDate = LocalDateTime.now();
        checkFields();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public float getArea() {
        return area;
    }

    public Long getPopulation() {
        return population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Climate getClimate() {
        return climate;
    }

    public Government getGovernment() {
        return government;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setMaxNewId(){
        this.id = maxId;
        maxId++;
    }

    private void checkFields(){
        if (name==null||name.isEmpty()){
            throw new InvalidFieldException("Ошибка в поле объекта: поле name");
        }
        if (coordinates==null){
            throw new InvalidFieldException("Ошибка в поле объекта: поле coordinates");
        }
        if (area<0){
            throw new InvalidFieldException("Ошибка в поле объекта: поле area");
        }
        if (population <= 0){
            throw new InvalidFieldException("Ошибка в поле объекта: поле population");
        }
        if(metersAboveSeaLevel==null){
            throw new InvalidFieldException("Ошибка в поле объекта: поле metersAboveSeaLevel");
        }
        if (government==null){
            throw new InvalidFieldException("Ошибка в поле объекта: поле government");
        }
        if (governor==null){
            throw new InvalidFieldException("Ошибка в поле объекта: поле governor");
        }
    }
    public void setUser(String user){
        this.user=user;
    }
    public String getUser(){
        return user;
    }
    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof City)) {
            throw new ClassCastException();
        }
        City c = (City) o;
        return (int) name.compareToIgnoreCase(c.name);
    }

    @Override
    public String toString() {
        return
                "\nОбъект City с названием "+getName()+
                        "\nID "+getId()+
                        "\n"+getCoordinates().toString()+
                        "\n Время создания этого объекта     "+getCreationDate().toLocalDate()+"  "+getCreationDate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))+
                        "\n Площадь города                   "+getArea()+
                        "\n Население города                 "+getPopulation()+
                        "\n Высота над уровнем моря          "+getMetersAboveSeaLevel()+
                        "\n Климат, преобладающий в городе   "+getClimate()+
                        "\n Устройство власти в городе       "+getGovernment()+
                        "\n Уровень жизни в городе           "+getStandardOfLiving()+
                        "\n"+getGovernor().toString();
    }
}

