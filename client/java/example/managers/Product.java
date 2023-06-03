package example.managers;

import java.io.Serializable;

public class Product implements Serializable {
    String result;
    Boolean success;

    public Product(String result, Boolean success) {
        this.result = result;
        this.success = success;
    }


    public String getResult() {
        return result;
    }

    public Boolean isSuccess() {
        return success;
    }

}