package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Livro;

@FacesConverter(forClass = Livro.class)
public class LivroConverter implements Converter{
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Livro) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Livro) {
			Livro entity = (Livro) value;
			if (entity != null && entity instanceof Livro && entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}
	
}
