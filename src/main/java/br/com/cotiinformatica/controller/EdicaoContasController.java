package br.com.cotiinformatica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EdicaoContasController {

	@RequestMapping(value = "/admin/edicao-contas")
	public ModelAndView cadastroContas() {
		
		//WEB-INF/views/admin/cadastro-contas.jsp
		ModelAndView modelAndView = new ModelAndView("admin/edicao-contas");
		return modelAndView;
	}	
}