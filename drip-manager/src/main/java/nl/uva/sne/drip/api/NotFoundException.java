package nl.uva.sne.drip.api;

@javax.annotation.Generated(value = "nl.uva.sne.drip.codegen.languages.SpringCodegen", date = "2019-10-09T14:00:37.436Z")

public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
