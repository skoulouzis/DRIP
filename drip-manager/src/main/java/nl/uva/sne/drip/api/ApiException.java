package nl.uva.sne.drip.api;

@javax.annotation.Generated(value = "nl.uva.sne.drip.codegen.languages.SpringCodegen", date = "2019-10-09T14:00:37.436Z")

public class ApiException extends Exception{
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
