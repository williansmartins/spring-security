package br.com.impacta.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.impacta.model.Categoria;
import br.com.impacta.model.Despesa;

@Controller
@RequestMapping("/despesa")
public class DespesaController {

	@RequestMapping(value="/inserir", method=RequestMethod.POST)  
	@ResponseBody
	public Despesa inserir(@RequestBody Despesa entrada) {
		return entrada;
	}

	@RequestMapping(value="/buscar", method=RequestMethod.GET)  
	@ResponseBody
	public Despesa buscar() {
		Despesa despesa = new Despesa();
		despesa.setCategoria(Categoria.ALIMENTACAO);
		despesa.setCodigo(1l);
		despesa.setData(new Date());
		despesa.setDescricao("descricao");
		despesa.setObservacoes("observacoes");
		despesa.setValor(new BigDecimal(123.45));
		return despesa;
	}

	public void atualizar() {

	}

	public void deletar() {

	}
}
