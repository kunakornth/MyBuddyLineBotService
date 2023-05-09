package th.com.hyweb.mybuddylinebot.entity;

import java.util.List;

import lombok.Data;

@Data
public class CalendarFireStore {
	private String year;
	private List<Months> months;
}
