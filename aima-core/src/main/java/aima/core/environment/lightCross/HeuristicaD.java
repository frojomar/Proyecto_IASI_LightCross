package aima.core.environment.lightCross;

import aima.core.search.framework.evalfunc.HeuristicFunction;
/**
 * Heuristica D del Proyecto Akira.
 * Esta heurística se basa en contar el numero de bombillas adyacentes a casillas numeradas que faltan
 * por poner.
 * 
 * Proyecto IASI 2016-2017.
 * 
 * Alumno:  Francisco Javier Rojo Martín. 2do Curso. GIIIS.
 * 
 * @author javier3rm
 *
 */
public class HeuristicaD implements HeuristicFunction{

	@Override
	public double h(Object state) {
		LightCross lightCross = (LightCross) state;		
				
		return lightCross.numeroBombillasNumeradas();
	}

}
