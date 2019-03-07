package com.o2o.action.server.app;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionCarousel;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.o2o.action.server.db.MealMenu;
import com.o2o.action.server.repo.DateMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChefApp extends DialogflowApp {

	private DateMenuRepository menuRepository;

	public void setMenuRepository(DateMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@ForIntent("Menu")
	public ActionResponse process(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		//responseBuilder.add("좋아좋아");

		List<MealMenu> menus = menuRepository.findByServiceDate(new Date());
		if (menus.size() > 0) {
			MealMenu menu = menus.get(0);
			menu.getSalad1();
			responseBuilder.add("셀러드는 " + menu.getSalad1() +" 입니다.");
		} else {
			responseBuilder.add("등록된 메뉴가 없습니다.");
		}
		return responseBuilder.build();
	}
}
