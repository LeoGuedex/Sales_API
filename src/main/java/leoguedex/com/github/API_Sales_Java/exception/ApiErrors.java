package leoguedex.com.github.API_Sales_Java.exception;

import lombok.Getter;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String errors){
        this.errors = Arrays.asList(errors);
    }

}
