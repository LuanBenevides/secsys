package br.com.modelar.sislogmobile.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {

	private Integer status;
	private LocalDateTime dataHora;
	private String titulo;
	private List<Campo> campo;
	
	@Getter
	@AllArgsConstructor
	public static class Campo {
		
		private String nome;
		private String mensagem;
	}
}
