package br.com.cotiinformatica.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.cotiinformatica.entities.Usuario;

public class UsuarioRepository {

	// atributo
	private JdbcTemplate jdbcTemplate;

	// método construtor
	public UsuarioRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void create(Usuario usuario) throws Exception { // controlador
															// gravar o usuário no banco de dados
		String query = "insert into usuario (nome, email, senha) values(?,?, md5(?))"; // md5 = criptografia
		Object[] params = { usuario.getNome(), usuario.getEmail(), usuario.getSenha() };

		jdbcTemplate.update(query, params);
	}

	public void update(Integer idUsuario, String novaSenha) throws Exception { // alterando e atualizando a nova senha do usuário
		String query = "update usuario set senha = md5(?) where idusuario = ?"; // md5 = criptografia sempre UPDATE com
																				// WHERE
		Object[] params = { novaSenha, idUsuario };

		jdbcTemplate.update(query, params);
	}

	public Usuario findByEmail(String email) throws Exception { // controlador

		String query = "select * from usuario where email = ?";
		Object[] params = { email };

		List<Usuario> lista = jdbcTemplate.query(query, params, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Usuario usuario = new Usuario();

				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));

				return usuario;
			}
		});

		if (lista.size() == 1) // verificando se 1 usuário foi encontrado
			return lista.get(0); // retornando os dados do usuário encontrado
		else
			return null; // retornando vazio
	}

	public Usuario findByEmailAndSenha(String email, String senha) throws Exception { // controlador
																						// consulta por usuário por
																						// email e senha
		String query = "select * from usuario where email = ? and senha = md5(?)";
		Object[] params = { email, senha };

		List<Usuario> lista = jdbcTemplate.query(query, params, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Usuario usuario = new Usuario();

				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));

				return usuario;
			}
		});

		if (lista.size() == 1) // verificando se 1 usuário foi encontrado
			return lista.get(0); // retornando os dados do usuário encontrado
		else
			return null; // retornando vazio
	}
}