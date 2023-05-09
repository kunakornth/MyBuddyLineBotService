package th.com.hyweb.mybuddylinebot.flex;
/*
 * Copyright 2018 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.function.Supplier;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Icon;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

public class UpdateFlexMessageSupplier implements Supplier<FlexMessage> {
	
    public FlexMessage get(String titlename,String location,String description,String person , String date) {
//        final Image heroBlock =
//                Image.builder()
//                     .url(URI.create("https://example.com/cafe.jpg"))
//                     .size(ImageSize.FULL_WIDTH)
//                     .aspectRatio(ImageAspectRatio.R20TO13)
//                     .aspectMode(ImageAspectMode.Cover)
//                     .action(new URIAction("label", URI.create("http://example.com"), null))
//                     .build();

        final Box bodyBlock = createBodyBlock(titlename,location,description,person,date);
//        final Box footerBlock = createFooterBlock();
        final Bubble bubble =
                Bubble.builder()
//                      .hero(heroBlock)
                      .body(bodyBlock)
//                      .footer(footerBlock)
                      .build();

        return new FlexMessage("แจ้งเตือนนัด", bubble);
    }

//    private Box createFooterBlock() {
//        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
//        final Button callAction = Button
//                .builder()
//                .style(ButtonStyle.LINK)
//                .height(ButtonHeight.SMALL)
//                .action(new URIAction("CALL", URI.create("tel:000000"), null))
//                .build();
//        final Separator separator = Separator.builder().build();
//        final Button websiteAction =
//                Button.builder()
//                      .style(ButtonStyle.LINK)
//                      .height(ButtonHeight.SMALL)
//                      .action(new URIAction("WEBSITE", URI.create("https://example.com"), null))
//                      .build();
//
//        return Box.builder()
//                  .layout(FlexLayout.VERTICAL)
//                  .spacing(FlexMarginSize.SM)
//                  .contents(asList(spacer, callAction, separator, websiteAction))
//                  .build();
//    }

    private Box createBodyBlock(String titlename, String location, String description, String person, String date) {
        final Text title =
                Text.builder()
                    .text("นัดเรื่อง "+titlename)
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.SM)
                    .build();

//        final Box review = createReviewBox();

        final Box info = createInfoBox(location,description,date,person);

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, info))
                  .build();
    }

    private Box createInfoBox(String location, String description, String date, String person) {
    	final Box who = Box.builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("มีนัด")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(2)
                            .build(),
                            Text.builder()
                            .text(person)
                            .wrap(true)
                            .color("#666666")
                            .flex(5)
                            .build()
                            )).build();
    	final Box place = Box.builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("สถานที่")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(2)
                            .build(),
                            Text.builder()
                            .text(location)
                            .wrap(true)
                            .color("#666666")
                            .flex(5)
                            .build()
                  
                )).build();
        final Box desc = Box.builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("รายละเอียด")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(2)
                            .build(),
                            Text.builder()
                            .text(description)
                            .wrap(true)
                            .color("#666666")
                            .flex(5)
                            .build()
                  
                )).build();
        final Box time = Box.builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder().text("วัน")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(2)
                            .build(),
                        Text.builder()
                            .text(date)
                            .wrap(true)
                            .color("#666666")
                            .size(FlexFontSize.SM)
                            .flex(5)
                            .build()
                )).build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .margin(FlexMarginSize.LG)
                .spacing(FlexMarginSize.SM)
                .contents(asList(who,place, time,desc))
                .build();
    }

    private Box createReviewBox() {
        final Icon goldStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://example.com/gold_star.png")).build();
        final Icon grayStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://example.com/gray_star.png")).build();
        final Text point =
                Text.builder()
                    .text("4.0")
                    .size(FlexFontSize.SM)
                    .color("#999999")
                    .margin(FlexMarginSize.MD)
                    .flex(0)
                    .build();

        return Box.builder()
                  .layout(FlexLayout.BASELINE)
                  .margin(FlexMarginSize.MD)
                  .contents(asList(goldStar, goldStar, goldStar, goldStar, grayStar, point))
                  .build();
    }

	@Override
	public FlexMessage get() {
		// TODO Auto-generated method stub
		return null;
	}
}