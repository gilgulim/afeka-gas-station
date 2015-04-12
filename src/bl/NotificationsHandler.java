package bl;

public interface NotificationsHandler {
	
	public enum NotifyType{
		WARNING_LOW_FUEL,
		WARNING_FUEL_REP_EMPTY,
		INFO_FUEL_REP_DONE_FUELING,
		INFO_FUEL_REP_STATUS,
	}
	
	void notificationHandle(NotifyType errType, Object data);
}
