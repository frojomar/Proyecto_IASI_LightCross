package aima.core.environment.lightCross;

import aima.core.search.framework.evalfunc.HeuristicFunction;

/**
 * Heuristica B del Proyecto Akira.
 * Esta heurística se basa en contar el numero de casillas vacias entre el Min[num.columnas, num.filas].
 * 
 * Proyecto IASI 2016-2017.
 * 
 * Alumno:  Francisco Javier Rojo Martín. 2do Curso. GIIIS.
 * 
 * @author javier3rm
 *
 */
public class HeuristicaB implements HeuristicFunction{

	@Override
	public double h(Object state) {
		LightCross lightCross = (LightCross) state;		
		
		int divisor= Math.min(lightCross.getNumeroFilas(), lightCross.getNumeroColumnas());
		
		return ( lightCross.cuantasPosicionesVacias()/divisor );
	}

}
