package th.com.hyweb.mybuddylinebot.model;

import lombok.Data;

@Data
public class CallTemplateModel {
	private String linename;
	private String summary;
	private String location;
	private String description;
	private String startdatetime;
	private String enddatetime;
	
}
