package br.com.cotiinformatica.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {

	public AuthenticationFilter() {

	}

	public void destroy() {

	}

	//Método executado sempre que uma página/rota é acessada no sistema
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//verificando se o usuário esta acessando uma página/rota contida em /admin
		if(req.getServletPath().contains("admin/")) {
			
			//verificar se o usuário não está autenticado (sessão)
			if(req.getSession().getAttribute("usuario") == null) {
				
				//redirecionar para a página inicial do sistema (autenticar)
				resp.sendRedirect("/sistemacontas/");
			}			
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}