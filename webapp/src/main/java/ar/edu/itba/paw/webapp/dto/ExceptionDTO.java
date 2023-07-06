package ar.edu.itba.paw.webapp.dto;

public class ExceptionDTO {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ExceptionDTO fromException(Exception ex) {
        ExceptionDTO dto = new ExceptionDTO();
        dto.message = ex.getMessage();
        return dto;
    }


}
