package loging;
import java.lang.reflect.Field;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import com.sun.media.jfxmedia.logging.Logger;


public class CustomFilter implements Filter{

	private Object parentClass;
	private String compareField;
	private Object compareFieldValue;
	
	public CustomFilter(Object parentClass, String compareField, Object compareFieldValue) {
		this.parentClass = parentClass;
		this.compareField = compareField;
		this.compareFieldValue = compareFieldValue;
	}
	
	@Override
	public boolean isLoggable(LogRecord record) {
		
		try {
			
			String parentClassName = parentClass.getClass().getName();
			String sourceClassName;
			Field field;
			Object fieldValue = null;
			
			if(record.getParameters()!= null){
				
				sourceClassName = record.getParameters()[0].getClass().getName();
				
				field = record.getParameters()[0].getClass().getDeclaredField(compareField);
				field.setAccessible(true);
				fieldValue = field.get(record.getParameters()[0]);

				if(sourceClassName == parentClassName){ 
					if (fieldValue !=null){
						if(fieldValue.equals(compareFieldValue)){
							return true;		
						}else{
							return false;
						}
						
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
			
		
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
				
		
	}
	

}
