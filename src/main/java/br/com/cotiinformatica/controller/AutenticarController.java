package br.com.cotiinformatica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // anotação do java para funcionar como

public class AutenticarController {

	// modelandview = rota

	@RequestMapping(value = "/") // ROTA (navegação)
	public ModelAndView autenticar() {
		// WEB-INF/view/autenticar.jsp
		ModelAndView modelAndView = new ModelAndView("autenticar");
		return modelAndView;
	}

}
