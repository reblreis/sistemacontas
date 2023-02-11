package br.com.cotiinformatica.repositories;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.enums.TipoConta;

public class ContaRepository {

	// atributo
	private JdbcTemplate jdbcTemplate;

	// construtor
	public ContaRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void create(Conta conta) throws Exception {

		String query = "insert into conta(nome, valor, data, tipo, observacoes, idusuario) values(?,?,?,?,?,?)";

		Object[] params = { conta.getNome(), conta.getValor(),
				java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(conta.getData())),
				conta.getTipo() == TipoConta.RECEBER ? 1 : conta.getTipo() == TipoConta.PAGAR ? 2 : 0,
				conta.getObservacoes(), conta.getUsuario().getIdUsuario() };

		jdbcTemplate.update(query, params);
	}

	public void update(Conta conta) throws Exception {

		String query = "update conta set nome=?, valor=?, data=?, tipo=?, observacoes=? where idconta=?";

		Object[] params = { conta.getNome(), conta.getValor(),
				java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(conta.getData())),
				conta.getTipo() == TipoConta.RECEBER ? 1 : conta.getTipo() == TipoConta.PAGAR ? 2 : 0,
				conta.getObservacoes(), conta.getIdConta() };

		jdbcTemplate.update(query, params);
	}

	public void delete(Conta conta) throws Exception {

		String query = "delete from conta where idconta=?";

		Object[] params = { conta.getIdConta() };

		jdbcTemplate.update(query, params);
	}

	public List<Conta> findByUsuarioAndData(Integer idUsuario, Date dataIni, Date dataFim) throws Exception {

		String query = "select * from conta where idusuario=? and data between ? and ? order by data";

		Object[] params = { idUsuario, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dataIni)),
				java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dataFim)) };

		List<Conta> lista = jdbcTemplate.query(query, params, new RowMapper<Conta>() {

			@Override
			public Conta mapRow(ResultSet rs, int rowNum) throws SQLException {

				Conta conta = new Conta();

				try {
					conta.setIdConta(rs.getInt("idconta"));
					conta.setNome(rs.getString("nome"));
					conta.setValor(rs.getDouble("valor"));
					conta.setData(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data")));
					conta.setTipo(rs.getInt("tipo") == 1 ? TipoConta.RECEBER
							: rs.getInt("tipo") == 2 ? TipoConta.PAGAR : null);
					conta.setObservacoes(rs.getString("observacoes"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				return conta;
			}
		});

		return lista;
	}

	public Conta findById(Integer idConta, Integer idUsuario) throws Exception {

		String query = "select * from conta where idconta=? and idusuario=?";

		Object[] params = { idConta, idUsuario };

		List<Conta> lista = jdbcTemplate.query(query, params, new RowMapper<Conta>() {

			@Override
			public Conta mapRow(ResultSet rs, int rowNum) throws SQLException {

				Conta conta = new Conta();

				try {
					conta.setIdConta(rs.getInt("idconta"));
					conta.setNome(rs.getString("nome"));
					conta.setValor(rs.getDouble("valor"));
					conta.setData(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data")));
					conta.setTipo(rs.getInt("tipo") == 1 ? TipoConta.RECEBER
							: rs.getInt("tipo") == 2 ? TipoConta.PAGAR : null);
					conta.setObservacoes(rs.getString("observacoes"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				return conta;
			}
		});

		if (lista.size() == 1)
			return lista.get(0);
		else
			return null;
	}

		public double sumByUsuarioAndData(Integer idUsuario, Date dataIni, Date dataFim, Integer tipo) throws Exception {
		
		String query = "select sum(valor) from conta where idusuario=? and data between ? and ? and tipo = ?";
		
		Object[] params = {
			idUsuario,
			java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dataIni)),
			java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dataFim)),
			tipo
		};
				
		BigDecimal valor = jdbcTemplate.queryForObject(query, params, BigDecimal.class);
		
		if(valor != null)
			return valor.doubleValue();
		else
			return 0;
	}
}