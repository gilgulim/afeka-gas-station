package loging;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.Date;


public class CustomLogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		
		StringBuffer buf = new StringBuffer();
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		buf.append(dateFormat.format(date));
		buf.append(" ");
		buf.append(record.getLevel());
		buf.append(":\t");
		buf.append(formatMessage(record));
		buf.append("\r\n");
		
		return buf.toString();
	}

}
