package loging;
import java.lang.reflect.Field;
import java.util.logging.Filter;
import java.util.logging.LogRecord;


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
			Field field;
			
			field = parentClass.getClass().getDeclaredField(compareField);
			field.setAccessible(true);
			Object fieldValue = field.get(parentClass);
			
			String sourceClassName = record.getSourceClassName(); 
			
			if(sourceClassName == parentClassName){ 
				if (fieldValue.equals(compareFieldValue)){
					return true;		
				}	
			}else{
				return false;
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
