package bl;

public interface ErrorNotifierHandler {
	
	public enum ErrorType{
		LowFuel,
		GasStationEmpty,
	}
	
	void notifyError(ErrorType errType);
}
