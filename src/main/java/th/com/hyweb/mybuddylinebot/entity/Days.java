package th.com.hyweb.mybuddylinebot.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Days {
	private String type;
	private String name;
	private Date  startdatetime;
	private Date enddatetime;
	private String location;
	private String description;
	private Date date;
	private List<String> personlist;
}
