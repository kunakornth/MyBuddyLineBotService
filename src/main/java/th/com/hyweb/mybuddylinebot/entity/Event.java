package th.com.hyweb.mybuddylinebot.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Event {
	private String type;
	private String name;
	private Date  startdatetime;
	private Date enddatetime;
	private String location;
	private String description;
}
