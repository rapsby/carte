package com.o2o.action.server.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionCarousel;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.AndroidApp;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowse;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowseItem;
import com.google.api.services.actions_fulfillment.v2.model.CarouselSelectCarouselItem;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.LinkOutSuggestion;
import com.google.api.services.actions_fulfillment.v2.model.ListSelectListItem;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;
import com.google.api.services.dialogflow_fulfillment.v2.model.Context;
import com.google.api.services.dialogflow_fulfillment.v2.model.QueryResult;

public class ChefApp extends DialogflowApp {

	@ForIntent("Default Welcome Intent")
	public ActionResponse welcome(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		AndroidApp app = new AndroidApp();
		app.setPackageName("kr.o2o.app.android.o2omediaviewer");
	
		Button learnMoreButton = new Button().setTitle("This is a Button")
				.setOpenUrlAction(new OpenUrlAction().setUrl("https://csnopy.iptime.org/android/movie"));//.setAndroidApp(app));
		List<Button> buttons = new ArrayList<>();
		buttons.add(learnMoreButton);
		String text = "This is a basic card.  Text in a basic card can include \"quotes\" and\n"
				+ "  most other unicode characters including emoji \uD83D\uDCF1. Basic cards also support\n"
				+ "  some markdown formatting like *emphasis* or _italics_, **strong** or\n"
				+ "  __bold__, and ***bold itallic*** or ___strong emphasis___ as well as other\n"
				+ "  things like line  \nbreaks";
		responseBuilder.add("This is a basic card").add(new BasicCard().setTitle("This is a title")
				.setSubtitle("This is a subtitle").setFormattedText(text)
				.setImage(
						new Image().setUrl("https://csnopy.iptime.org/test/IMG_0207.PNG").setAccessibilityText("Image alternate text"))
				.setImageDisplayOptions("CROPPED").setButtons(buttons));

		responseBuilder.addSuggestions(new String[] { "Suggestion chips", "suggestion 1", "suggestion 3" }).add(
				new LinkOutSuggestion().setDestinationName("Suggestion link").setUrl("https://assistant.google.com"));
		ActionResponse response = responseBuilder.build();
		return response;
	}

	@ForIntent("Media Demo1")
	public ActionResponse mediaDemo(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		List<Button> buttons = new ArrayList<>();
		AndroidApp app = new AndroidApp();
		app.setPackageName("kr.o2o.app.android.o2omediaviewer");

		String url2 = "intent://scan/#Intent;scheme=naversearchapp;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=com.nhn.android.search;end";
		// String url =
		// "intent://scan/#Intent;scheme=zxing;package=com.google.zxing.client.android;end";
		String url = "https://csnopy.iptime.org/android/movie/a.mp4";

		Button learnMoreButton = new Button().setTitle("This is a Button")
				.setOpenUrlAction(new OpenUrlAction().setUrl(url));
		buttons.add(learnMoreButton);

		if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
			return responseBuilder
					.add("Sorry, try ths on a screen device or select the phone surface in the simulator.").build();
		}
		List<CarouselBrowseItem> items = new ArrayList<>();
		CarouselBrowseItem item;
		item = new CarouselBrowseItem().setTitle("The Simpsons").setDescription(
				"The Simpsons is an American animated sitcom created by Matt Groening for the Fox Broadcasting Company. The series is a satirical depiction of working-class life, epitomized by the Simpson family, which consists of Homer, Marge, Bart, Lisa, and Maggie. The show is set in the fictional town of Springfield and parodies American culture and society, television, and the human condition.")
				.setOpenUrlAction(new OpenUrlAction().setUrl("http://csnopy.iptime.org/android/movie/1"))
				.setFooter("O2O Viewer").setImage(new Image().setUrl("https://csnopy.iptime.org/img/simpsons.png")
						.setAccessibilityText("Image alternate text"));
		items.add(item);
		item = new CarouselBrowseItem().setTitle("안시성(영화)").setDescription(
				"넥스트엔터테인먼트월드가 새로 신설한 회사인 스튜디오앤뉴에서 제작을 맡아 215억 원의 제작비가 투입된 블록버스터 사극으로 고구려-당 전쟁 당시 있었던 안시성 전투를 다루고 있다. 다만 영화 제작사 측에서 언급한 등장인물 소개글들을 보면 정통 사극보다는 퓨전 사극으로 추정된다.")
				.setOpenUrlAction(new OpenUrlAction().setUrl("http://csnopy.iptime.org/android/movie/2"))
				.setFooter("O2O Viewer").setImage(new Image().setUrl("https://csnopy.iptime.org/img/ansi.jpg")
						.setAccessibilityText("Image alternate text"));
		items.add(item);
		item = new CarouselBrowseItem().setTitle("아쿠아맨")
				.setDescription("아쿠아맨은 DC 확장 유니버스의 6번째 영화이자 아쿠아맨의 탄생을 그린 해양 판타지 액션, 슈퍼히어로 영화이다.")
				.setOpenUrlAction(new OpenUrlAction().setUrl("http://csnopy.iptime.org/android/movie/3"))
				.setFooter("O2O Viewer").setImage(new Image().setUrl("https://csnopy.iptime.org/img/aqua.jpg")
						.setAccessibilityText("Image alternate text"));
		items.add(item);

		responseBuilder.add("검색된 영화입니다.").add(new CarouselBrowse().setItems(items));
		// responseBuilder.addSuggestions(new String[]{"심슨", "안시성", "아쿠아맨"});

		return responseBuilder.build();
	}

	@ForIntent("Default Welcome Intent - custom")
	public ActionResponse welcome2(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		responseBuilder.add("This is a browse carousel");
		return responseBuilder.build();
	}

	int[] retCount = { 3, 2, 1, 1, 1, 2, 5 };
	String[] resultTitle = { "손흥민 경기", "박지성 경기", "스카이케슬 영화", "킹덤 드라마", "발리 여행지", "액션 영화", "K쇼핑 핫딜" };
	String[][] titles = { { "토 2월 23일 - 번리 VS 토트넘", "수 2월 27일 - 첼시 VS 토트넘", "토 3월 2일 - 토트넘 VS 아스날" },
			{ "박지성 05-06시즌 EPL 인생경기!! 박지성 버스", "06~07시즌 박지성선수 활약 모음!! 굉장한 선수!!" }, { "SKY 캐슬" }, { "킹덤(드라마)" },
			{ "[ACP801]발리클럽메드" }, { "어벤저스: 시크릿 에이전트", "A-특공대" },
			{ "LG 퓨리케어 공기청정기 AS122VDS (실버/스마트씽큐)", "삼성전자 신제품!큐브 공기청정기 (47㎡)", "[탑보이] 심플 슬라브 헨리넥 7부 티셔츠 (RT411)",
					"[탑보이] 베이직 오버핏 맨투맨 티셔츠 (MD109)", "[방송]촉촉한 오징어 24미(3미X8팩)" } };

	String[][] descs = {
			{ "2019년 2월 23일 토요일 12:30. 터프무어 스타디움, 번리, 영국", "2019년 2월 27일 수요일 20:00. 스탬퍼드 브리지 스타디움, 런던, 영국",
					"2019년 3월 2일 토요일 12:30. 웸블리 스타디움, 런던, 영국" },
			{ "박지성선수가 캐리한 경기!", "감탄밖에 나오지않네요... 구독하기하셔서 더 많은 박지성선수 영상 보러오세요~" },
			{ "2018년 11월 23일부터 2019년 2월 2일까지 방영된 JTBC 금토드라마. \\n" + "\\n"
					+ "'대한민국 최고의 명문사립' 주남대학 교수들이 모여 사는 유럽풍의 타운하우스 'SKY 캐슬'을 배경으로 남편은 왕으로, 제 자식은 천하제일 왕자와 공주로 키우고 싶은 명문가 출신 사모님들의 처절한 욕망을 샅샅이 들여다보는 블랙코미디 스릴러 드라마." },
			{ "넷플릭스에서 제작한 한국 드라마. 조선 시대 배경의 좀비 미스터리 스릴러이다. 김은희 작가가 극본을, 김성훈 감독이 연출을 맡았고, 2019년 1월 25일 시즌 1이 공개되었다. 회당 제작비는 약 20억원(200만 달러)로, 2019년 기준, 미국 외 국가의 넷플릭스 오리지널 작품 중 가장 많은 제작비가 투입된 작품이다. 본래 8부작으로 기획되어 있었으나, 내부 시사회에서 반응이 좋자 6부작으로 축소해 시즌제로 변경했고 시즌 2 제작이 확정되었다. 시즌 1의 연출을 맡은 김성훈 감독은 시즌 2 초반까지 연출하고, 나머지 분량은 박인제 감독이 연출할 예정이다." },
			{ "독특한 전경과 이국적인 아름다움, 화려하고 큰 규모의 발리 빌리지에 오셔서 오래된 인도네시아 발리섬만의 전통과 자연을 느껴보세요!!" },
			{ "어벤저스: 시크릿 에이전트는 만화가 마크 밀러의 《시크릿 서비스》(The Secret Service)를 원작으로 한 <어벤저스 시리즈>의 첫 번째 실사 미국 영화다. 감독 매튜 본이 킥애스에 이어 아이콘 코믹스 사의 작품을 영화화한 두 번째 작품이다.",
					"최고의 실력을 자랑하던 특공대가 돌연 자취를 감춘 지 1년. 누구도 해결할 수 없고, 아무도 도울 수 없는 문제들을 해결하기 위해 최고의 해결사 A-특공대가 되어 다시 돌아왔다! 비상한 두뇌 회전의 소유자 한니발의 기상천외한 작전 지휘 아래, 작업의 달인 멋쟁이의 수려한 외모와 화려한 언변으로 실마리를 풀면두려움을 모르는 짐승 파이터 B.A가 선방을 날리고똘끼 충만한 천재 돌+I 파일럿, 머독의 화려한 공중전이 펼쳐진다!2010년 6월, 가 무조건 해치운다!\\n"
							+ "개봉일: 2010년 6월 10일 (대한민국)" },
			{ "K쇼핑가 286,600원", "1,000,000원 -> K쇼핑가 750,000원", "K쇼핑가 17,800원", "K쇼핑가 22,800원",
					"40,900원 -> K쇼핑가 39,900원" } };
	String[][] images = { { "1-1.jpg", "1-2.jpg", "1-3.jpg" }, { "2-1.jpg", "2-2.jpg" }, { "3-1.jpg" }, { "4-1.jpg" },
			{ "5-1.jpg" }, { "6-1.jpg", "6-2.png" }, { "7-1.jpg", "7-2.png", "7-3.jpg", "7-4.jpg", "7-5.jpg" } };

	@ForIntent("Reservation")
	public ActionResponse skyLifeReservation(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		QueryResult qr = request.getWebhookRequest().getQueryResult();

		String eTime = null;

		String title = "????";
		int idx = -1;
		String eSports = null;
		String eDrama = null;
		String eRegion = null;
		String eGenre = null;

		if (qr != null) {
			Map<String, Object> params = qr.getParameters();
			eTime = (String) params.get("Ent_Time");
			eSports = (String) params.get("Ent_Sports");
			eDrama = (String) params.get("Ent_drama");
			eRegion = (String) params.get("Ent_region");
			eGenre = (String) params.get("Ent_genre");
		}

		System.out.println(eTime);
		if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
			// return responseBuilder.add("미안, try ths on a screen device or select the
			// phone surface in the simulator.")
			// .build();
		}

		if ((eSports == null || eSports.length() <= 0) && (eDrama == null || eDrama.length() <= 0)
				&& (eRegion == null || eRegion.length() <= 0) && (eGenre == null || eGenre.length() <= 0)) {
			return responseBuilder.add("요청을 알기가 어렵습니다..").build();
		}

		if (qr.getAllRequiredParamsPresent()) {
			if (eSports != null && eSports.length() > 0) {
				if (eSports.equals("손흥민")) {
					idx = 0;
				} else if (eSports.equals("박지성")) {
					idx = 1;
				} else {
					idx = -1;
				}
			} else if (eDrama != null && eDrama.length() > 0) {
				if (eDrama.equals("스카이케슬")) {
					idx = 2;
				} else if (eDrama.equals("킹덤")) {
					idx = 3;
				} else {
					idx = -1;
				}
			} else if (eRegion != null && eRegion.length() > 0) {
				if (eRegion.equals("발리")) {
					idx = 4;
				} else {
					idx = -1;
				}
			} else if (eGenre != null) {
				if (eGenre.equals("액션") && eGenre.length() > 0) {
					idx = 5;
				} else {
					idx = -1;
				}
			}
		} else {
			return responseBuilder.add("요청을 알기가 어렵습니다...").build();
		}

		List<ListSelectListItem> items = new ArrayList<>();
		if (idx >= 0) {
			title = resultTitle[idx];

			if (retCount[idx] >= 2) {
				for (int i = 0; i < retCount[idx]; i++) {
					ListSelectListItem item = new ListSelectListItem();
					item.setTitle(titles[idx][i]).setDescription(descs[idx][i])
							.setOptionInfo(new OptionInfo().setKey("KEY_" + idx + "_" + i))
							.setImage(new Image().setUrl("https://csnopy.iptime.org/img/" + images[idx][i])
									.setAccessibilityText("Image alternate text" + idx));
					items.add(item);
				}
				if (items.size() > 0) {
					return responseBuilder.add(title).add(new SelectionList().setTitle(title).setItems(items)).build();
				}

			} else if (retCount[idx] == 1) {
				title = resultTitle[idx];
				Button learnMoreButton = new Button().setTitle("link")
						.setOpenUrlAction(new OpenUrlAction().setUrl("https://csnopy.iptime.org"));
				List<Button> buttons = new ArrayList<>();
				buttons.add(learnMoreButton);
				responseBuilder.add(title)
						.add(new BasicCard().setTitle(title).setSubtitle(titles[idx][0]).setFormattedText(descs[idx][0])
								.setImage(new Image().setUrl("https://csnopy.iptime.org/img/" + images[idx][0])
										.setAccessibilityText("Image alternate text"))
								.setImageDisplayOptions("CROPPED")); // .setButtons(buttons)

				return responseBuilder.build();
			}
		}

		return responseBuilder.add(title + "관련 자료를 찾기 어렵습니다.").build();
	}

	@ForIntent("Reservation - custom")
	public ActionResponse skyLifeReservation2(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		// reservation-followup
		QueryResult qr = request.getWebhookRequest().getQueryResult();

		String eTime = null;
		String eSports = null;
		String eDrama = null;
		String eRegion = null;
		String eGenre = null;

		int idx = -1;
		for (Context context : qr.getOutputContexts()) {
			if (context.getName().endsWith("reservation-followup")) {
				Map<String, Object> params = context.getParameters();
				eTime = (String) params.get("Ent_Time");
				eSports = (String) params.get("Ent_Sports");
				eDrama = (String) params.get("Ent_drama");
				eRegion = (String) params.get("Ent_region");
				eGenre = (String) params.get("Ent_genre");

				if (qr.getAllRequiredParamsPresent()) {
					System.out.println("??" + qr.getAllRequiredParamsPresent() + "," + eSports + "," + eDrama + ","
							+ eRegion + "," + eGenre);
					if (eSports != null && eSports.length() > 0) {
						if (eSports.equals("손흥민")) {
							idx = 0;
						} else if (eSports.equals("박지성")) {
							idx = 1;
						} else {
							idx = -1;
						}
					} else if (eDrama != null && eDrama.length() > 0) {
						if (eDrama.equals("스카이케슬")) {
							idx = 2;
						} else if (eDrama.equals("킹덤")) {
							idx = 3;
						} else {
							idx = -1;
						}
					} else if (eRegion != null && eRegion.length() > 0) {
						if (eRegion.equals("발리")) {
							idx = 4;
						} else {
							idx = -1;
						}
					} else if (eGenre != null) {
						if (eGenre.equals("액션") && eGenre.length() > 0) {
							idx = 5;
						} else {
							idx = -1;
						}
					}
					System.out.println(idx);
				} else {
					return responseBuilder.add("요청을 알기가 어렵습니다...").build();
				}
				break;
			}
		}

		if (idx >= 0) {
			responseBuilder.add("성공.");
			return responseBuilder.add(new SimpleResponse().setDisplayText(resultTitle[idx] + " 예약하였습니다")
					.setTextToSpeech(resultTitle[idx] + " 예약하였습니다")).build();
		} else {
			responseBuilder.add("예약실패 하였습니다. 대상을 선택해주세요.");
			return responseBuilder.build();
		}
	}

	@ForIntent("Shopping")
	public ActionResponse skyLifeShopping(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		QueryResult qr = request.getWebhookRequest().getQueryResult();

		String eTime = null;

		String title = "????";
		int idx = -1;
		String eShopping = null;

		if (qr != null) {
			Map<String, Object> params = qr.getParameters();
			eShopping = (String) params.get("Ent_Shopping");
		}

		System.out.println(eTime);
		if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
			// return responseBuilder.add("미안, try ths on a screen device or select the
			// phone surface in the simulator.")
			// .build();
		}

		if (eShopping == null || eShopping.length() <= 0) {
			return responseBuilder.add("요청을 알기가 어렵습니다..").build();
		}

		if (qr.getAllRequiredParamsPresent()) {
			if (eShopping != null && eShopping.length() > 0) {
				idx = 6;
			} else {
				idx = -1;
			}
		} else {
			return responseBuilder.add("요청을 알기가 어렵습니다...").build();
		}

		List<ListSelectListItem> items = new ArrayList<>();
		if (idx >= 0) {
			title = resultTitle[idx];

			if (retCount[idx] >= 2) {
				for (int i = 0; i < retCount[idx]; i++) {
					ListSelectListItem item = new ListSelectListItem();
					item.setTitle(titles[idx][i]).setDescription(descs[idx][i])
							.setOptionInfo(new OptionInfo().setKey("KEY_" + idx + "_" + i))
							.setImage(new Image().setUrl("https://csnopy.iptime.org/img/" + images[idx][i])
									.setAccessibilityText("Image alternate text" + idx));
					items.add(item);
				}
				if (items.size() > 0) {
					return responseBuilder.add(title).add(new SelectionList().setTitle(title).setItems(items)).build();
				}

			} else if (retCount[idx] == 1) {
				title = resultTitle[idx];
				Button learnMoreButton = new Button().setTitle("link")
						.setOpenUrlAction(new OpenUrlAction().setUrl("https://csnopy.iptime.org"));
				List<Button> buttons = new ArrayList<>();
				buttons.add(learnMoreButton);
				responseBuilder.add(title)
						.add(new BasicCard().setTitle(title).setSubtitle(titles[idx][0]).setFormattedText(descs[idx][0])
								.setImage(new Image().setUrl("https://csnopy.iptime.org/img/" + images[idx][0])
										.setAccessibilityText("Image alternate text"))
								.setImageDisplayOptions("CROPPED")); // .setButtons(buttons)

				return responseBuilder.build();
			}
		}

		return responseBuilder.add(title + "관련 자료를 찾기 어렵습니다.").build();
	}

	@ForIntent("Shopping - custom")
	public ActionResponse skyLifeShopping2(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		String selectedItem = request.getSelectedOption();
		// reservation-followup
		QueryResult qr = request.getWebhookRequest().getQueryResult();

		String title = "????";

		if (selectedItem != null && selectedItem.startsWith("KEY_6_")) {
			String[] ids = selectedItem.split("_");
			System.out.println(ids[2]);
			int idx = Integer.parseInt(ids[2]);
			title = titles[6][idx];
			responseBuilder.add("성공.");
			return responseBuilder
					.add(new SimpleResponse().setDisplayText(title + " 주문하였습니다").setTextToSpeech(title + " 주문하였습니다"))
					.build();
		} else {
			return responseBuilder.add(title + "내부 오류 입니다.").build();
		}
	}

	@ForIntent("Support")
	public ActionResponse skyLifeSupportNumber(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		QueryResult qr = request.getWebhookRequest().getQueryResult();
		String selectedItem = request.getSelectedOption();

		System.out.println(selectedItem);

		List<CarouselSelectCarouselItem> items = new ArrayList<>();
		CarouselSelectCarouselItem item;
		item = new CarouselSelectCarouselItem().setTitle("리모컨 사용 방법").setDescription("SkyLife-TV 통합 리모콘 사용방법")
				.setOptionInfo(new OptionInfo().setKey("SELECTION_KEY_ONE").setSynonyms(Arrays.asList("리모컨")));
		items.add(item);
		item = new CarouselSelectCarouselItem().setTitle("비밀번호 변경 방법")
				.setOptionInfo(new OptionInfo().setKey("SELECTION_KEY_TWO").setSynonyms(Arrays.asList("비밀번호")))
				.setDescription("비밀번호를 변경 방법은?");
		items.add(item);

		String voiceStatus = null;
		if (qr != null) {
			Map<String, Object> params = qr.getParameters();
			voiceStatus = (String) params.get("Ent_audio");
		}

		String oriText = "어떤 부분을 도와드릴까요?";
		String outSSML = oriText;
		if (voiceStatus != null) {
			if (voiceStatus.equals("빠르게")) {
				outSSML = "<speak><prosody rate=\"fast\">빠르게 말합니다. " + oriText + "</prosody></speak>";
			} else if (voiceStatus.equals("느리게")) {
				outSSML = "<speak><prosody rate=\"slow\">느리게 말합니다. " + oriText + "</prosody></speak>";
			} else if (voiceStatus.equals("작게")) {
				outSSML = "<speak><prosody volume=\"soft\">작게 말합니다." + oriText + "</prosody></speak>";
			} else if (voiceStatus.equals("크게")) {
				outSSML = "<speak><prosody volume=\"loud\">크게 말합니다." + oriText + "</prosody></speak>";
			}
		}

		responseBuilder.add(outSSML).add(new SelectionCarousel().setItems(items));

		return responseBuilder.build();
	}

	@ForIntent("Support - select.number")
	public ActionResponse skyLifeSupportNumber2(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		QueryResult qr = request.getWebhookRequest().getQueryResult();
		String selectedItem = request.getSelectedOption();

		System.out.println(selectedItem);
		if (selectedItem.equalsIgnoreCase("SELECTION_KEY_ONE")) {
			System.out.println("1");
			responseBuilder.add(new SimpleResponse()
					.setDisplayText("TV통합 리모콘 설정 방법 1. [확인 ○] + [선호채널] or [확인 ○] + [조용히] (동시에 누름) → LED ON"
							+ " 2. [채널+] or [채널-] key를 천천히 TV화면이 꺼질때까지 누른다. (LED가 깜박임)"
							+ " 3. TV 화면이 꺼지면 [확인 ○] 누름 → 완료(LED 3회 점멸하며 꺼짐) ")
					.setTextToSpeech("다음과 같이 시도해 보시기 바랍니다."));
		} else {
			responseBuilder.add(new SimpleResponse()
					.setDisplayText("비밀번호가 아이들이나 청소년에게 알려 졌을 경우를 대비하여 부모님들이 원하실 때 임의로 비밀번호를 변경할 수 있습니다."
							+ "* 비밀번호 변경 방법 - 안드로이드 UHD 수신기 : TV홈 → 설정 → 자녀안심 → 비밀번호 변경"
							+ "- 일반 UHD 수신기 : 메뉴 → 설정 → 자녀안심설정 → 비밀번호 변경" + "- HD 수신기 : 메뉴 → 사용자 메뉴 → 기능설정 → 비밀번호 설정"
							+ "※ 기타 수신기의 경우 고객센터(1588-3002)를 통해 확인해주시기 바랍니다."
							+ "이 비밀번호는 전체 EPG에서 사용되기 때문에 꼭 기억해 주시기 바랍니다.")
					.setTextToSpeech("다음과 같이 시도해 보시기 바랍니다."));
		}
		return responseBuilder.build();
	}

	@ForIntent("Joke")
	public ActionResponse makeJoke(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		responseBuilder.add("아하하하하");

		return responseBuilder.build();
	}
}
