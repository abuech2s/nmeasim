package sim.data.ais;

import java.util.Calendar;
import java.util.GregorianCalendar;

import lombok.Getter;

public class ETA {
	
	@Getter int month;
	@Getter int day;
	@Getter int hour;
	@Getter int minute;
	
	public ETA(int position, int totalPoints, long timeInterval) {
		long currentTime = System.currentTimeMillis();
		int pointsTogo = totalPoints - position;
		long timeTogo =  timeInterval * pointsTogo * 1_000; // in [ms]
		
		currentTime = currentTime + timeTogo;
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(currentTime);
		
		this.month = c.get(Calendar.MONTH) + 1;
		this.day = c.get(Calendar.DAY_OF_MONTH);
		this.hour = c.get(Calendar.HOUR_OF_DAY);
		this.minute = c.get(Calendar.MINUTE);
	}
	
}
