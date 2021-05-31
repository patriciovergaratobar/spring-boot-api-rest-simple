package cl.pvergara.apirest.app.exception;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

	public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
