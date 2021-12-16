package beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@FacesConverter("beans.TimeConverter")
public class TimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext,
                              UIComponent component, String value) {
        try {
            OffsetDateTime time = OffsetDateTime.parse(value);
            return time;
        }catch (DateTimeParseException e) {
            FacesContext.getCurrentInstance().addMessage("input-form:time_input", new FacesMessage("Неверный формат", "Неверный формат"));
            throw new ConverterException();
        }
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, Object value) {
        return value.toString();
    }
}
