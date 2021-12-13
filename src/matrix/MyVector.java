package matrix;

import java.io.Serializable;

public class MyVector implements Serializable {

    Integer[] vector;
    Integer len;

    public MyVector(Integer[] vector) {
        this.vector = vector;
        this.len = this.vector.length;
    }

    public void setElem(int index, int value) {
        vector[index] = value;
    }

    public Integer getElem(int index) {
        return vector[index];
    }

    public Integer getLen() {
        return len;
    }
}
