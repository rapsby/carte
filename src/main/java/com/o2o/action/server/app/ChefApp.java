package com.o2o.action.server.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.HelperIntent;
import com.google.api.services.actions_fulfillment.v2.model.AndroidApp;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowse;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowseItem;
import com.google.api.services.actions_fulfillment.v2.model.DialogSpec;
import com.google.api.services.actions_fulfillment.v2.model.ExpectedIntent;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.LinkOutSuggestion;
import com.google.api.services.actions_fulfillment.v2.model.LinkValueSpec;
import com.google.api.services.actions_fulfillment.v2.model.LinkValueSpecLinkDialogSpec;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;

public class ChefApp extends DialogflowApp {

	@ForIntent("Default Welcome Intent")
	public ActionResponse welcome(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		AndroidApp app = new AndroidApp();
		app.setPackageName("kr.o2o.app.android.o2omediaviewer");

		Button learnMoreButton = new Button().setTitle("링크용 버튼")
				.setOpenUrlAction(new OpenUrlAction().setUrl("http://www.daum.net"));// .setAndroidApp(app));
		List<Button> buttons = new ArrayList<>();
		buttons.add(learnMoreButton);
		String text = "This is a basic card.  Text in a basic card can include \"quotes\" and\n"
				+ "  most other unicode characters including emoji \uD83D\uDCF1. Basic cards also support\n"
				+ "  some markdown formatting like *emphasis* or _italics_, **strong** or\n"
				+ "  __bold__, and ***bold itallic*** or ___strong emphasis___ as well as other\n"
				+ "  things like line  \nbreaks";
		responseBuilder.add("This is a basic card")
				.add(new BasicCard().setTitle("This is a title").setSubtitle("This is a subtitle")
						.setFormattedText(text)
						.setImage(new Image().setUrl("https://csnopy.iptime.org/test/IMG_0207.PNG")
								.setAccessibilityText("Image alternate text"))
						.setImageDisplayOptions("CROPPED").setButtons(buttons));

		responseBuilder.addSuggestions(new String[] { "Suggestion A" })
				.add(new LinkOutSuggestion().setDestinationName("링크용 Suggestion").setUrl("http://www.naver.com"));
		ActionResponse response = responseBuilder.build();
		return response;
	}

	@ForIntent("Media Demo")
	public ActionResponse mediaDemo(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		responseBuilder.add("Launch app.");
		
		responseBuilder.add(new HelperIntent() {
			@Override
			public Map<String, Object> getParameters() {
				Map<String, Object> maps = new HashMap<String, Object>();

				Map<String, Object> openUrlAction = new HashMap<String, Object>();
				
				Map<String, Object> androidApp = new HashMap<String, Object>();
				
				Map<String, Object> dialogSpec = new HashMap<String, Object>();
				Map<String, Object> dialogSpeclink = new HashMap<String, Object>();
				dialogSpeclink.put("@type", "type.googleapis.com/google.actions.v2.LinkValueSpec.LinkDialogSpec");
				dialogSpeclink.put("destinationName", "O2O Viewer");
				dialogSpeclink.put("requestLinkReason", "이제 대상 앱 열어 주까?");
				dialogSpec.put("extension", dialogSpeclink);
				
				androidApp.put("packageName", "kr.o2o.app.android.o2omediaviewer");
				
				openUrlAction.put("url", "https://csnopy.iptime.org/android");
				openUrlAction.put("androidApp", androidApp);
				
				maps.put("@type", "type.googleapis.com/google.actions.v2.LinkValueSpec");
				maps.put("openUrlAction", openUrlAction);
				maps.put("dialogSpec", dialogSpec);
				return maps;
			}

			@Override
			public String getName() {
				return "actions.intent.LINK";
			}
		});

		return responseBuilder.build();
	}

	@ForIntent("Get Link Status")
	public ActionResponse linkStatus(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		return responseBuilder.add("Bad").build();
	}
}
