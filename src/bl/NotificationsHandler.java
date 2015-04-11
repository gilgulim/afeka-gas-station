package bl;

public interface NotificationsHandler {
	
	public enum NotifyType{
		warnLowFuel,
		errFuelRepositoryEmpty,
		infoFinishedFuelRep,
		infoFuelingRepStatus,
	}
	
	void notificationHandle(NotifyType errType, Object data);
}
