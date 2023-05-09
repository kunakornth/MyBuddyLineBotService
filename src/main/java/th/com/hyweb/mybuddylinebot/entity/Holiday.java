package th.com.hyweb.mybuddylinebot.entity;

import java.util.List;

import lombok.Data;

@Data
public class Holiday {

	private String year;
	private List<Days> Apr;
	private List<Days> Dec;
	private List<Days> Aug;
	private List<Days> Feb;
	private List<Days> Jan;
	private List<Days> Jul;
	private List<Days> Jun;
	private List<Days> Mar;
	private List<Days> May;
	private List<Days> Nov;
	private List<Days> Oct;
	private List<Days> Sep;
	private List<Months> months;
}
