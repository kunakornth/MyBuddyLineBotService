package th.com.hyweb.mybuddylinebot.entity;

import lombok.Data;

@Data
public class LineUsers {
	private String id;
	private String name;
	private String tier;
	private String fullname;
	private int annualleavehour;
	private int annualleaveuse;
	private int sickleaveuse;
	
	
}
