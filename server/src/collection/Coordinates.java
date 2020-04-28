package collection;

import exceptions.InvalidFieldException;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Значение поля должно быть больше -773, Поле не может быть null
    private Double y; //Максимальное значение поля: 644, Поле не может быть null
    private static final long serialVersionUID=2908L;

    public Coordinates(Integer x, Double y){
        this.x = x;
        this.y = y;
        checkFields();
    }

    @Override
    public String toString() {
        return "Координаты:"+
                "\n     Х: " + getX().toString()+
                "\n     Y: " + getY().toString();
    }

    public Double getY() {
        return y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    private void checkFields(){
        if (x<=-773){
            throw new InvalidFieldException("Ошибка в исходном файле Json: поле coordinates.x");
        }
        if (y>=664){
            throw new InvalidFieldException("Ошибка в исходном файле Json: поле coordinates.y");
        }
    }
}
