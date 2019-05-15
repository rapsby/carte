package com.o2o.action.server.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@Autowired
	private HttpServletRequest context;

	@GetMapping("/")
	public String mainIndex(Model model) {
		System.out.println("Haha.");

		return "index";
	}

	@GetMapping("/manager")
	public String managerIndex(Model model) {
		return "manager";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/junghyo")
	public String junghyo(Model model) {
		return "junghyo";
	}

	/*
	 * 세션이 유지될 때 새로고침을 하면 count 값 증가 count 값이 없을 때는 1을 채워주고 있을 때는 증가시킴
	 */
	@GetMapping("/study1")
	public String study1(Model model) {
		HttpSession session = context.getSession();
		if (session != null) {
			Object obj = session.getAttribute("count");
			if (obj == null) {
				session.setAttribute("count", 1);
				System.out.println("1");
			}

			else {
				int count = (int) obj;
				count++;
				session.setAttribute("count", count);
				System.out.println(count);
			}
			System.out.println(session);
		}

		return "study1";
	}
	
	@GetMapping("/study2")
	public String study2(Model model) {
		
		return "study2";
	}
}
