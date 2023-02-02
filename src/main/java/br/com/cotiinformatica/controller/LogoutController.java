package br.com.cotiinformatica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {

		// apagando os dados do usuário gravadi em sessão
		request.getSession().removeAttribute("usuario");

		// redirecionar para apágina de autenticação
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		return modelAndView;

	}

}