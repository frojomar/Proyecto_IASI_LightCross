package aima.core.environment.lightCross;

import aima.core.search.framework.evalfunc.HeuristicFunction;

/**
 * Heuristica C del Proyecto Akira.
 * Esta heurística se basa en contar el numero de filas totales - filas completas en el
 * caso en el que hay menos filas que columnas. Si no es así, cuenta el numero de columnas totales - columnas completas.
 * Por tanto, contempla como mejor alternativa aquella cuyo numero de filas(/columnas) sin completar es menor 
 * (o, lo que es lo mismo, aquella con más filas(/columnas) completas).
 * 
 * Proyecto IASI 2016-2017.
 * 
 * Alumno:  Francisco Javier Rojo Martín. 2do Curso. GIIIS.
 * 
 * @author javier3rm
 *
 */
public class HeuristicaC implements HeuristicFunction{

	@Override
	public double h(Object state) {
		LightCross lightCross = (LightCross) state;		
		if(lightCross.getNumeroFilas()<lightCross.getNumeroColumnas())
			return lightCross.getNumeroFilas()-lightCross.numeroFilasCompletas();
		else
			return lightCross.getNumeroColumnas()-lightCross.numeroColumnasCompletas();
	}

}
