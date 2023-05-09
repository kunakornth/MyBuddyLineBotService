package th.com.hyweb.mybuddylinebot.flex;

import static java.util.Arrays.asList;

import java.util.function.Supplier;

import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

public class NotificationFlexMessageSupplier implements Supplier<FlexMessage>{

	@Override
	public FlexMessage get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FlexMessage get(String name, String message) {
		 //final Image heroBlock = createHeroBlock();
       final Box bodyBlock = createBodyBlock(name,message);
//       final Box footerBlock = createFooterBlock(date);

       final Bubble bubbleContainer = Bubble.builder()
             //  .hero(heroBlock)
               .body(bodyBlock)
//               .footer(footerBlock)
               .build();
       return new FlexMessage("การแจ้งเตือน", bubbleContainer);
	}

	  private Box createBodyBlock(String name, String message) {
	    	
	        final Text title = Text.builder()
	                .text("คุณ "+name+" ได้แจ้งเตือนคุณ")
	                .weight(Text.TextWeight.BOLD)
	                .size(FlexFontSize.XL)
	                .build();
	       
	        final Box info = createInfoBox(message);
	        

	        return Box.builder()
	                .layout(FlexLayout.VERTICAL)
	                .contents(asList(title, info))
	                .build();
	    }

	  private Box createInfoBox(String message) {

	    	final Box who = Box.builder()
	                .layout(FlexLayout.BASELINE)
	                .spacing(FlexMarginSize.SM)
	                .contents(asList(
	                        Text.builder()
	                            .text("ข้อความ")
	                            .color("#aaaaaa")
	                            .size(FlexFontSize.SM)
	                            .flex(2)
	                            .build(),
	                            Text.builder()
	                            .text(message)
	                            .wrap(true)
	                            .color("#666666")
	                            .flex(5)
	                            .build()
	                            )).build();
	        
	     
	        return Box.builder()
	                .layout(FlexLayout.VERTICAL)
	                .margin(FlexMarginSize.LG)
	                .spacing(FlexMarginSize.SM)
	                .contents(asList(who))
	                .build();
	    }


}
