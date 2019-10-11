package nl.uva.sne.drip.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiException extends Exception {

    static ResponseEntity<String> handleExceptions(ApiException ex) {
        switch (ex.getCode()) {
            case 404:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case 409:
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            default:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }
    private int code;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
