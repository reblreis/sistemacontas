package br.com.cotiinformatica.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.dtos.UsuarioDTO;
import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.models.ConsultaContasModel;
import br.com.cotiinformatica.repositories.ContaRepository;

@Controller
public class ConsultaContasController {

	@Autowired // inicialização automática (injeção de dependência)
	private ContaRepository contaRepository;

	@RequestMapping(value = "/admin/consulta-contas")
	public ModelAndView cadastroContas(HttpServletRequest request) {

		// WEB-INF/views/admin/cadastro-contas.jsp MÉTODO PARA ABRIR A PÁGINA
		ModelAndView modelAndView = new ModelAndView("admin/consulta-contas");

		modelAndView.addObject("model", new ConsultaContasModel());

		ConsultaContasModel model = new ConsultaContasModel();

		try {

			UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute("usuario");

			Calendar calendar = Calendar.getInstance();
			Date dataIni = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1)
					.getTime();
			Date dataFim = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).getTime();

			model.setDataIni(new SimpleDateFormat("yyyy-MM-dd").format(dataIni));
			model.setDataFim(new SimpleDateFormat("yyyy-MM-dd").format(dataFim));

			List<Conta> contas = contaRepository.findByUsuarioAndData(usuarioDto.getIdUsuario(), dataIni, dataFim);
			modelAndView.addObject("contas", contas);
		} catch (Exception e) {
			modelAndView.addObject("mensagem", "Falha ao consultar contas: " + e.getMessage());
		}

		modelAndView.addObject("model", model);

		return modelAndView;
	}

	@RequestMapping(value = "/admin/consultar-contas", method = RequestMethod.POST)
	public ModelAndView consultarContas(ConsultaContasModel model, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("admin/consulta-contas");

		try {

			List<Conta> contas = obterConsultaDeContas(model, request);
			modelAndView.addObject("contas", contas);
		} catch (Exception e) {
			modelAndView.addObject("mensagem", "Falha ao consultar contas: " + e.getMessage());
		}

		modelAndView.addObject("model", model);
		return modelAndView;
	}

	private List<Conta> obterConsultaDeContas(ConsultaContasModel model, HttpServletRequest request) throws Exception {

		// capturar os dados do usuário gravado em sessão
		UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute("usuario");

		// capturar as datas selecionadas no formulário
		Date dataIni = new SimpleDateFormat("yyyy-MM-dd").parse(model.getDataIni());
		Date dataFim = new SimpleDateFormat("yyyy-MM-dd").parse(model.getDataFim());

		// consultar as contas do usuário no banco de dados
		return contaRepository.findByUsuarioAndData(usuarioDto.getIdUsuario(), dataIni, dataFim);
	}
}