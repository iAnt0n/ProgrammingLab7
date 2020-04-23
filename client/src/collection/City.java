package collection;

import exceptions.InvalidFieldException;

import java.io.Serializable;

/**
 * Класс, экземпляры которого хранятся в коллекции
 */
public class City implements Comparable, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private float area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private Climate climate; //Поле может быть null
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле не может быть null
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

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof City)) {
            throw new ClassCastException();
        }
        City c = (City) o;
        return (int) (this.area - c.getArea());
    }
}

