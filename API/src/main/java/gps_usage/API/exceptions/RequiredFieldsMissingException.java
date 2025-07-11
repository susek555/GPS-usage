package gps_usage.API.exceptions;

public class RequiredFieldsMissingException extends RuntimeException {
    public RequiredFieldsMissingException() {
        super("Required fields are missing");
    }
}
