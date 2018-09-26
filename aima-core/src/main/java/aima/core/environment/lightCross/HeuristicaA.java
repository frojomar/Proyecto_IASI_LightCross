package aima.core.environment.lightCross;

import aima.core.search.framework.evalfunc.HeuristicFunction;

/**
 * Heuristica A del Proyecto Akira.
 * Esta heurística se basa en contar el numero de casillas vacias en el tablero actual y hacer la raiz cuadrada de esta. Con
 * esta operacion conseguimos saber una estimacion de las bombillas que nos pueden quedar por poner (nos quedamos con el entero
 * más pequeño proximo a la raiz del numero de casillas vacias).
 * 
 * Proyecto IASI 2016-2017.
 * 
 * Alumno:  Francisco Javier Rojo Martín. 2do Curso. GIIIS.
 * 
 * @author javier3rm
 *
 */
public class HeuristicaA implements HeuristicFunction{

	@Override
	public double h(Object state) {
		LightCross lightCross = (LightCross) state;		
		return (double)(int)Math.sqrt(lightCross.cuantasPosicionesVacias());
	}

}
