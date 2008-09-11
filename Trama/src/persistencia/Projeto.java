/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package persistencia;

import java.util.LinkedList;

public class Projeto {
	
	private LinkedList< DadosMatriz > matrizes;
	private String nome = "";
	
	public LinkedList< DadosMatriz > getMatrizes() {
		return matrizes;
	}
	
	public void setMatrizes( LinkedList< DadosMatriz > matrizes ) {
		this.matrizes = matrizes;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
}
