package th.com.hyweb.mybuddylinebot.base;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class ServicesBase {
	
	@Autowired
	protected Gson gson;
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	protected void push(String linelId ,@NonNull Message message) {
        push( linelId , Collections.singletonList(message));
    }
	protected void push(String linelId , @NonNull List<Message> messages) {
	       final PushMessage pushMessage = new PushMessage(linelId, messages);
	        try {
	            BotApiResponse response = lineMessagingClient.pushMessage(pushMessage).get();
	        } catch (InterruptedException | ExecutionException e) {
	            throw new RuntimeException(e);
	        }
	    }


}
