package th.com.hyweb.mybuddylinebot.entity;

import java.util.List;

import lombok.Data;

@Data
public class Months {
	private String month;
	private List<Days> days;
}
