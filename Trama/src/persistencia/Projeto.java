/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package persistencia;

import java.util.LinkedList;

/**
 * Classe que é usada como entidade para armazenamento de {@link DadosMatriz} de um projeto.<br>
 * Também é utlizada para armazenar o nome do projeto.
 * 
 * @author Fabio Marmitt
 */
public class Projeto {
	
	/** Lista de {@link DadosMatriz} de um projeto */
	private LinkedList< DadosMatriz > matrizes;
	/** Nome do projeto */
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
