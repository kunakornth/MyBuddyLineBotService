package th.com.hyweb.mybuddylinebot.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.ResourceDescriptor.History;
import com.google.gson.Gson;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import th.com.hyweb.mybuddylinebot.entity.CalendarFireStore;
import th.com.hyweb.mybuddylinebot.entity.Days;
import th.com.hyweb.mybuddylinebot.entity.Event;
import th.com.hyweb.mybuddylinebot.entity.Holiday;
import th.com.hyweb.mybuddylinebot.entity.LineUsers;
import th.com.hyweb.mybuddylinebot.entity.Months;
import th.com.hyweb.mybuddylinebot.entity.WaitingApprove;
import th.com.hyweb.mybuddylinebot.flex.ApproveFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.flex.UpdateFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.model.CallTemplateModel;
import th.com.hyweb.mybuddylinebot.service.CalendarService;
import th.com.hyweb.mybuddylinebot.service.HolidaysService;
import th.com.hyweb.mybuddylinebot.service.LineUsersService;
import th.com.hyweb.mybuddylinebot.service.TierService;
import th.com.hyweb.mybuddylinebot.service.WaitingApproveService;




@CrossOrigin
@RestController
@Log4j2
public class MessageController {

	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@Autowired
	private LineUsersService lineusersservice;
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private WaitingApproveService waitingapproveservice;
	
	@Autowired
	private Gson gson;
	
	
	@GetMapping(value="/notiseven")
	public String notiseven() throws IOException, InterruptedException, ExecutionException  {
		List<LineUsers> list = lineusersservice.getLineUserList();
		notimeeting(list);
		notiwaitingapprove(list);
		notiReport(list);
		return "Success";
	}
	
	private void notiReport(List<LineUsers> list) {
		Calendar date = Calendar.getInstance();
		if(date.get(Calendar.DATE) == 1) {
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getName().equals("Mint")) {
					this.push(list.get(i).getId(), Arrays.asList(
							new TextMessage("Report ประจำเดือน"),
							new TextMessage("https://docs.google.com/spreadsheets/d/1bnsUd6TW_OB2zxaXAmnVbcFsU0beOxD0bPC0Xt53ZNg/edit?usp=sharing")
							));	
				}
				
			}
			
		}
	}
	
	
	@PostMapping(value="/callNotiMeetingEvent")
	public String callTemplateMeetingEvent(@RequestBody CallTemplateModel request) throws InterruptedException, ExecutionException, IOException {
		List<LineUsers> list = lineusersservice.getLineUserList();
		
		String Summary = request.getSummary().substring(0,request.getSummary().indexOf("( "));
		String name = request.getSummary().substring(request.getSummary().indexOf("( ")+2,request.getSummary().indexOf(" )"));
		String location = request.getLocation();
		String start = request.getStartdatetime().substring(0,16);
		String end =request.getEnddatetime().substring(0,16);
		String desc =null;
		String datetime = "วันที่  "+start.substring(8,10)+"-"+start.substring(5,7)+"-"+start.substring(0,4)
		+" เวลา "+ start.substring(11,16)
		+" ถึง วันที่  "+end.substring(8,10)+"-"+end.substring(5,7)+"-"+end.substring(0,4)
		+" เวลา "+ end.substring(11,16);
		try {
			desc = request.getDescription();
			if(desc.length() ==0) {
				desc = "ไม่มีรายละเอียด";
			}
		}
		catch(Exception e) {
			desc = " ";
		}
		
	
		StringBuilder sb = new StringBuilder();
		if(!name.equals("ทุกคน")) {
			for(int j=0;j<list.size();j++) {
				if(name.contains(list.get(j).getName())) {
//					this.push(list.get(j).getName(), new UpdateFlexMessageSupplier().get(request.getLinename(),Summary,location,desc,sb.toString(),datetime));
				}
			}
		}
		else {
			sb.append("ทุกคน");
		}
		if(name.equals("ทุกคน")) {
//			for(int j=0;j<listline.length;j++) {
////				 new UpdateFlexMessageSupplier().get(linename,Summary,location,date,name,desc)
//				this.push(listline[j][0], new UpdateFlexMessageSupplier().get(request.getLinename(),Summary,location,datetime,sb.toString(),desc));
//			}
		}
		else {
//			for(int j=0;j<listline.length;j++) {
//				if(name.contains(listline[j][1])) {
//					this.push(listline[j][0], new UpdateFlexMessageSupplier().get(request.getLinename(),Summary,location,datetime,sb.toString(),desc));
//					
//				}
//			}
		}
		return null;
	}
	
	
	
	private void notimeeting(List<LineUsers> list) throws InterruptedException, ExecutionException {
		
		Calendar date = Calendar.getInstance();
		CalendarFireStore calend = calendarService.getCalendarSearch(Integer.toString(date.get(Calendar.YEAR)));
		List<Days> events = new ArrayList<Days>();
		List<Days> dayinmonth = new ArrayList<Days>();
		String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		for(int i =0 ;i<calend.getMonths().size();i++) {
				if(calend.getMonths().get(i).getMonth().equals(months[date.get(Calendar.MONTH)]) ) {
					dayinmonth = calend.getMonths().get(i).getDays();
					break;
				}
		}
		List<Days> days = new ArrayList<Days>();
		for(int i=0 ;i<dayinmonth.size();i++) {
			if(dayinmonth.get(i).getType().equals("Meeting") || dayinmonth.get(i).getType().equals("Event")) {
				
				
				days.add(dayinmonth.get(i));
				
			}
		}
		
		for(int j=0;j<days.size();j++) {
			int startday = days.get(j).getStartdatetime().getDate();
//			log.debug(startday+"=="+date.get(Calendar.DATE));
			if(startday==date.get(Calendar.DATE) ) {
				events.add(days.get(j));
			}
		}
		if(events.size()>0) {
			for(int i=0;i<events.size();i++) {
				String title = events.get(i).getName();
				String location = events.get(i).getLocation();
				String description = events.get(i).getDescription();
				Date start  = events.get(i).getStartdatetime();
				Date end = events.get(i).getEnddatetime();
				List<String> personlist = events.get(i).getPersonlist();
				
				String day=formatStartEndDate(start,end);
				String personliststr ="";
				for(int j= 0;j<list.size();j++) {
					for(int l=0;l<personlist.size();l++) {
						if(list.get(j).getName().equals(personlist.get(l))) {
							this.push(list.get(i).getId(), new UpdateFlexMessageSupplier().get(title,location,description,personliststr,day));
							break;
						}
					}
				}

			}
		}
	}
	
	private void notiwaitingapprove(List<LineUsers> list) throws InterruptedException, ExecutionException {
		List<WaitingApprove> wa=waitingapproveservice.getWaitingApproveSearch();
		List<WaitingApprove> head = new ArrayList<WaitingApprove>();
		List<WaitingApprove> leader = new ArrayList<WaitingApprove>();
		for(int i =0;i<wa.size();i++) {
			if(wa.get(i).getTieruser().equals("normal")){
				leader.add(wa.get(i));
			}
			else if(wa.get(i).getTieruser().equals("normalplus") || wa.get(i).getTieruser().equals("leader")) {
				head.add(wa.get(i));
			}
		}
		for(int i =0 ;i<list.size();i++) {
			if(list.get(i).getTier().equals("leader") &&  leader.size()>0 ) {
//				push
				this.push(list.get(i).getId(), new ApproveFlexMessageSupplier().get());
				break;
			}
			else if( list.get(i).getTier().equals("head") && head.size()>0) {
				//push
				this.push(list.get(i).getId(), new ApproveFlexMessageSupplier().get());
				break;
			}
		}
	}
	
	private String formatStartEndDate(Date start,Date end) {
		String resp="วันนี้เวลา ";
		String starttime = start.getHours()+":"+start.getMinutes();
		String endtime = end.getHours()+":"+end.getMinutes();
		resp = resp+ starttime + " ถึง "+ endtime;
		return resp;
	}
	
	private void push(String linelId ,@NonNull Message message) {
        push( linelId , Collections.singletonList(message));
    }
	private void push(String linelId , @NonNull List<Message> messages) {
	       final PushMessage pushMessage = new PushMessage(linelId, messages);
	        try {
	            BotApiResponse response = lineMessagingClient.pushMessage(pushMessage).get();
	        } catch (InterruptedException | ExecutionException e) {
	            throw new RuntimeException(e);
	        }
	    }

    
}
