package th.com.hyweb.mybuddylinebot.controller;

import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Collections.singletonList;

import java.util.ArrayList;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import th.com.hyweb.mybuddylinebot.entity.LineUsers;
import th.com.hyweb.mybuddylinebot.flex.NotificationFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.flex.UpdateFlexMessageSupplier;
import th.com.hyweb.mybuddylinebot.service.LineUsersService;


@Slf4j
@LineMessageHandler
public class LineBotController {
	
	 @Autowired
	 private LineMessagingClient lineMessagingClient;
	 
	 @Autowired
	 private LineUsersService lineusersservice;
	 
	 String TextUnknow[] = {"บอทไม่เข้าใจสิ่งนี้ โปรดเลือกเมนูใหม่","บอทไม่โอเคกับสิ่งนี้ โปรดเลือกเมนูใหม่","บอทปวดหัวกับสิ่งนี้ โปรดเลือกเมนูใหม่","บอทไม่ถูกใจสิ่งนี้ โปรดเลือกเมนูใหม่"};
	 List<String> notilist= new ArrayList<String>(); 
	 
	 @EventMapping
	 public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
	       TextMessageContent message = event.getMessage();
	       handleTextContent(event.getReplyToken(), event, message);
	 }
	 
	 private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception {
	        final String text = content.getText();
	        List<LineUsers> list = lineusersservice.getLineUserList();
	        String id ="";
	        for(int i=0;i<list.size();i++) {
    			for(int j=0;j<notilist.size();j++) {
    				if(notilist.get(j).equals(list.get(i).getId())) {
    					id =  list.get(i).getId();
    				}
    			}
    		}
	        if(notilist.size()>0 && !id.equals("")) {
        		String personname ="";
        		String personid ="";
        		String persontext ="";
        		for(int i=0;i<list.size();i++) {
        			for(int j=0;j<notilist.size();j++) {
        				if(notilist.get(j).equals(list.get(i).getId())) {
        					personname = list.get(i).getName();
        					personid =  list.get(i).getId();
        					persontext =text;
        					notilist.remove(j);
        				}
        			}
        		}
        		for(int i=0;i<list.size();i++) {
        			if(!personid.equals(list.get(i).getId())) {
        				this.push(list.get(i).getId(), new NotificationFlexMessageSupplier().get(personname, persontext));
        			}
        			
        		}
        		
        		this.reply(replyToken,  new TextMessage("ทำรายการสำเร็จแล้ว"));
			
	        }
	        else {
	        	switch (text) {
	            case "profile": {
	                log.info("Invoking 'profile' command: source:{}",
	                         event.getSource());
	                final String userId = event.getSource().getUserId();
	                
	                break;
	            }
	            case "ลางาน": {
	            	this.reply(replyToken,  new TextMessage("สามารถเลือกการลาผ่าน Richmenu ได้เลย"));
	                break;
	            }
	            case "Notification": {
	            	String userId = event.getSource().getUserId();
	            	if(notilist.size()==0) {
	            		notilist.add(userId);
	            		this.replyText(replyToken,"บอกข้อมูลที่ต้องการแจ้งเตือน");
	            		break;
	            	}
	            	
	            }
	       
	            default:
	   	    	 	Random rand = new Random();
	   	    	 	int ran = rand.nextInt(TextUnknow.length);
	   	    	 	this.reply(replyToken,  new TextMessage(TextUnknow[ran]));
	   	    	 	break;
	        }
	        }
	        
	    }
	 private void reply(@NonNull String replyToken, @NonNull Message message) {
	        reply(replyToken, singletonList(message));
	    }

	    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
	        reply(replyToken, messages, false);
	    }

	    private void reply(@NonNull String replyToken,
	                       @NonNull List<Message> messages,
	                       boolean notificationDisabled) {
	        try {
	            BotApiResponse apiResponse = lineMessagingClient
	                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
	                    .get();
	            log.info("Sent messages: {}", apiResponse);
	        } catch (InterruptedException | ExecutionException e) {
	            throw new RuntimeException(e);
	        }
	    }


	    private void replyText(@NonNull String replyToken, @NonNull String message) {
	        if (replyToken.isEmpty()) {
	            throw new IllegalArgumentException("replyToken must not be empty");
	        }
	        if (message.length() > 1000) {
	            message = message.substring(0, 1000 - 2) + "……";
	        }
	        this.reply(replyToken, new TextMessage(message));
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
