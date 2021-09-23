package br.com.alura.forum.model;

import lombok.NoArgsConstructor;

/**
 * Representa uma situação de um tópico do fórum.
 * 
 * @author Rômulo Machado Flores <romuloflores@gmail.com>
 */
@NoArgsConstructor
public enum TopicStatus {

	/**
	 * Fechado.
	 */
	CLOSED,
	/**
	 * Solucionado.
	 */
	SOLVED,
	/**
	 * Sem Resposta.
	 */
	UNANSWERED,
	/**
	 * Não Solucionado.
	 */
	UNSOLVED;
}