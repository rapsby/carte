package com.o2o.action.server.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.HelperIntent;
import com.google.actions.api.response.helperintent.SelectionCarousel;
import com.google.api.services.actions_fulfillment.v2.model.AndroidApp;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowse;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowseItem;
import com.google.api.services.actions_fulfillment.v2.model.CarouselSelectCarouselItem;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.LinkOutSuggestion;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;

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
						.setImage(new Image().setUrl("https://actions.o2o.kr/content/img/7LCp2.gif")
								.setAccessibilityText("Image alternate text"))
						.setImageDisplayOptions("CROPPED").setButtons(buttons));

		responseBuilder.addSuggestions(new String[] { "Suggestion A" })
				.add(new LinkOutSuggestion().setDestinationName("링크용 Suggestion").setUrl("http://www.naver.com"));
		ActionResponse response = responseBuilder.build();
		return response;
	}

	@ForIntent("Media demo")
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

	@ForIntent("Find movie")
	public ActionResponse findMovie(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		List<CarouselSelectCarouselItem> items = new ArrayList<CarouselSelectCarouselItem>();
		CarouselSelectCarouselItem item;
		item = new CarouselSelectCarouselItem().setTitle("The Simpsons").setDescription(
				"The Simpsons is an American animated sitcom created by Matt Groening for the Fox Broadcasting Company. The series is a satirical depiction of working-class life, epitomized by the Simpson family, which consists of Homer, Marge, Bart, Lisa, and Maggie. The show is set in the fictional town of Springfield and parodies American culture and society, television, and the human condition.")
				.setOptionInfo(new OptionInfo().setKey("SELECTION_KEY_ONE")
						.setSynonyms(Arrays.asList("synonym 1", "synonym 2", "synonym 3")))
				// .setOpenUrlAction(new
				// OpenUrlAction().setUrl("https://actions.o2o.kr/content/movie/1"))
				.setImage(new Image().setUrl("https://actions.o2o.kr/aog/img/simpsons.png")
						.setAccessibilityText("Image alternate text"));
		items.add(item);
		item = new CarouselSelectCarouselItem().setTitle("안시성(영화)").setDescription(
				"넥스트엔터테인먼트월드가 새로 신설한 회사인 스튜디오앤뉴에서 제작을 맡아 215억 원의 제작비가 투입된 블록버스터 사극으로 고구려-당 전쟁 당시 있었던 안시성 전투를 다루고 있다. 다만 영화 제작사 측에서 언급한 등장인물 소개글들을 보면 정통 사극보다는 퓨전 사극으로 추정된다.")
				.setOptionInfo(new OptionInfo().setKey("SELECTION_KEY_TWO")
						.setSynonyms(Arrays.asList("synonym 1-2", "synonym 2-2", "synonym 3-2")))
				// .setOpenUrlAction(new
				// OpenUrlAction().setUrl("https://actions.o2o.kr/content/movie/2"))
				.setImage(new Image().setUrl("https://actions.o2o.kr/aog/img/ansi.jpg")
						.setAccessibilityText("Image alternate text"));
		items.add(item);
		item = new CarouselSelectCarouselItem().setTitle("아쿠아맨")
				.setDescription("아쿠아맨은 DC 확장 유니버스의 6번째 영화이자 아쿠아맨의 탄생을 그린 해양 판타지 액션, 슈퍼히어로 영화이다.")
				.setOptionInfo(new OptionInfo().setKey("SELECTION_KEY_GOOGLE_PIXEL")
						.setSynonyms(Arrays.asList("synonym 1-3", "synonym 2-3", "synonym 3-3")))
				// .setOpenUrlAction(new
				// OpenUrlAction().setUrl("https://actions.o2o.kr/content/movie/3"))
				.setImage(new Image().setUrl("https://actions.o2o.kr/aog/img/aqua.jpg")
						.setAccessibilityText("Image alternate text"));
		items.add(item);

		responseBuilder.add("검색된 영화입니다.").add(new SelectionCarousel().setItems(items));

		return responseBuilder.build();
	}

	@ForIntent("Find movie 2")
	public ActionResponse findMovie2(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		List<CarouselBrowseItem> items = new ArrayList<CarouselBrowseItem>();
		CarouselBrowseItem item;
		item = new CarouselBrowseItem().setTitle("The Simpsons").setDescription(
				"The Simpsons is an American animated sitcom created by Matt Groening for the Fox Broadcasting Company. The series is a satirical depiction of working-class life, epitomized by the Simpson family, which consists of Homer, Marge, Bart, Lisa, and Maggie. The show is set in the fictional town of Springfield and parodies American culture and society, television, and the human condition.")
				.setOpenUrlAction(new OpenUrlAction().setUrl("https://actions.o2o.kr/content/movie/1"))
				.setImage(new Image().setUrl("https://actions.o2o.kr/aog/img/simpsons.png")
						.setAccessibilityText("Image alternate text"));
		items.add(item);
		item = new CarouselBrowseItem().setTitle("안시성(영화)").setDescription(
				"넥스트엔터테인먼트월드가 새로 신설한 회사인 스튜디오앤뉴에서 제작을 맡아 215억 원의 제작비가 투입된 블록버스터 사극으로 고구려-당 전쟁 당시 있었던 안시성 전투를 다루고 있다. 다만 영화 제작사 측에서 언급한 등장인물 소개글들을 보면 정통 사극보다는 퓨전 사극으로 추정된다.")
				.setOpenUrlAction(new OpenUrlAction().setUrl("https://actions.o2o.kr/content/movie/2"))
				.setImage(new Image().setUrl("https://actions.o2o.kr/aog/img/ansi.jpg")
						.setAccessibilityText("Image alternate text"));
		items.add(item);
		item = new CarouselBrowseItem().setTitle("아쿠아맨")
				.setDescription("아쿠아맨은 DC 확장 유니버스의 6번째 영화이자 아쿠아맨의 탄생을 그린 해양 판타지 액션, 슈퍼히어로 영화이다.")
				.setOpenUrlAction(new OpenUrlAction().setUrl("https://actions.o2o.kr/content/movie/3"))
				.setImage(new Image().setUrl("https://actions.o2o.kr/aog/img/aqua.jpg")
						.setAccessibilityText("Image alternate text"));
		items.add(item);

		responseBuilder.add("검색된 영화입니다.").add(new CarouselBrowse().setItems(items));

		return responseBuilder.build();
	}
}
