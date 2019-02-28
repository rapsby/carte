package com.o2o.action.server.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.actions.api.App;
import com.o2o.action.server.app.ChefApp;
import com.o2o.action.server.db.MealMenu;
import com.o2o.action.server.repo.DateMenuRepository;
import com.o2o.action.server.repo.MealDetailRepository;

@RestController
public class ChefController {
	private final App testCap;

	@Autowired
	private DateMenuRepository menuRepository;
	@Autowired
	private MealDetailRepository detailRepository;

	public ChefController() {
		testCap = new ChefApp();
	}

	@RequestMapping(value = "/simple", method = RequestMethod.POST)
	public @ResponseBody String simple(@RequestBody String body, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonResponse = null;
		try {
			System.out.println("request : " + body);
			jsonResponse = testCap.handleRequest(body, getHeadersMap(request)).get();
			System.out.println("response : " + jsonResponse);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}

	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public @ResponseBody String simpleGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Call!!");

		/*
		 * MealMenu menu = new MealMenu();
		 * menu.setServiceDate(Calendar.getInstance().getTime());
		 * menu.setDessert("디저트"); menu.setMrice("잡곡밥"); menu.setSalad1("셀러드1");
		 * menu.setSalad2("셀러드2");
		 * 
		 * menu = repository.save(menu);
		 * 
		 * MealDetail detail = new MealDetail(); detail.setMealMenu(menu);
		 * detail.setMealType(MealType.LAUNCH_1); detail.setMenu1("메뉴1");
		 * detail.setMenu2("메뉴2"); detail.setMenu3("메뉴3"); detail.setMenu4("메뉴4");
		 * 
		 * detail = detailRepository.save(detail);
		 */

		Iterable<MealMenu> menus = menuRepository.findAll();
		for (MealMenu dateMenu : menus) {
			System.out.println(dateMenu.getDessert());
		}
		return "Good";
	}

	@RequestMapping(value = "/api/1.0/mealmenu", method = RequestMethod.GET)
	public @ResponseBody Object getMealMenu(HttpServletRequest request, HttpServletResponse response) {
		Iterable<MealMenu> menus = menuRepository.findAll();

		return makeCollection(menus);
	}

	@RequestMapping(value = "/api/1.0/login", method = RequestMethod.GET)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			String userId = (String) httpSession.getAttribute("userId");
		}
	}

	@RequestMapping(value = "/api/1.0//login", method = RequestMethod.POST)
	public void login(@RequestParam(value = "inputID", required = true) String id,
			@RequestParam(value = "inputPassword", required = true) String passwd, HttpServletRequest request,
			HttpServletResponse response) {
		if (id != null && passwd != null && id.length() > 0 && passwd.length() > 0) {
			if (id.trim().equalsIgnoreCase("admin") && passwd.trim().equalsIgnoreCase("1234")) {
				request.getSession().setAttribute("userId", "admin");
				try {
					response.sendRedirect(request.getContextPath() + "/");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/api/1.0/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			httpSession.removeAttribute("userId");
		}
		try {
			response.sendRedirect(request.getContextPath() + "/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static <E> Collection<E> makeCollection(Iterable<E> iter) {
		Collection<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	private Map<String, String> getHeadersMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
