package br.com.cotiinformatica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecuperarSenhaController {

	/* o mapping define o endereço da página no navegador, 
	 * por isso até é chamado de RequestMapping (mapeamento de requisição). 
	 * O ModelAndViewer pega o conteúdo em si da página. */
	
	@RequestMapping(value = "/recuperar-senha") // ROTA (navegação)
	public ModelAndView recuperarSenha() {
		// WEB-INF/view/autenticar.jsp
		ModelAndView modelAndView = new ModelAndView("recuperar-senha");
		return modelAndView;
	}

	
}
