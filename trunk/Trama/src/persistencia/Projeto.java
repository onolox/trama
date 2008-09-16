/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package persistencia;

import java.util.LinkedList;

/**
 * Classe que é usada para serializar o projeto em XML e vice-versa.
 * 
 * @author Fabio Marmitt
 */
public class Projeto {
	private LinkedList< DadosMatriz > matrizes;
	private String nome = "";
	
	/**
	 * Usado para buscar a lista de DadosMatriz do projeto.
	 * 
	 * @return lista de DadosMatriz do projeto.
	 */
	public LinkedList< DadosMatriz > getMatrizes() {
		return matrizes;
	}
	
	/**
	 * Usado para colocar a lista de matrizes do projeto.
	 * 
	 * @param matrizes lista de DadosMatriz
	 */
	public void setMatrizes( LinkedList< DadosMatriz > matrizes ) {
		this.matrizes = matrizes;
	}
	
	/**
	 * Usado para buscar o nome do projeto.
	 * 
	 * @return nome do projeto
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Usado pra colocar o nome do projeto.
	 * 
	 * @param nome nome do projeto
	 */
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
}
