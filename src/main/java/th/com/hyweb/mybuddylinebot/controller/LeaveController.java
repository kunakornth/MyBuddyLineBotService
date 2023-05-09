package th.com.hyweb.mybuddylinebot.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.message.TextMessage;

import lombok.extern.log4j.Log4j2;
import th.com.hyweb.mybuddylinebot.base.ServicesBase;
import th.com.hyweb.mybuddylinebot.entity.LineUsers;
import th.com.hyweb.mybuddylinebot.flex.ApproveFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.flex.ApproveNewFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.flex.UpdateFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.model.CallTemplateModel;
import th.com.hyweb.mybuddylinebot.model.Notiperson;
import th.com.hyweb.mybuddylinebot.service.LineUsersService;

@CrossOrigin
@RestController
@Log4j2
public class LeaveController extends ServicesBase{

	@Autowired
	private LineUsersService lineusersservice;

	
	@PostMapping(value="/notiApprovePerson")
	public String notiseven(@RequestBody Notiperson request) throws IOException, InterruptedException, ExecutionException  {
		List<LineUsers> list = lineusersservice.getLineUserList();
		for(int i=0;i<list.size();i++) {
		
			if(list.get(i).getId().equals(request.getLineid())) {
				if(request.getType().equals("Notification")) {
					this.push(list.get(i).getId(), new ApproveFlexMessageSupplier().get());
				}
				else if(request.getType().equals("New")) {
					this.push(list.get(i).getId(), new ApproveNewFlexMessageSupplier().get());
				}
				else if(request.getType().equals("Text")) {
					this.push(list.get(i).getId(), new TextMessage(request.getMessage()));
					
				}
				
			}
		}
		
		return "Success";
	}
}
