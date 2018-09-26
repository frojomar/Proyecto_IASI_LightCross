package aima.core.environment.lightCross;

import aima.core.search.framework.evalfunc.HeuristicFunction;
/**
 * Heuristica E del Proyecto Akira.
 * Esta heurística es la más similar al pensamiento humano cuando se juega a este juego. Lo primero que intentamos
 * es ir completando todas las casillas numeradas que hay en el tablero. Una vez que las hemos completado, lo que
 * intentamos es ir poniendo bombillas en posiciones vacias de manera que iluminemos el maximo de casillas con 
 * cada bombilla puesta (esto se consigue empezando por poner las bombillas en los cruces posibles,...)
 * 
 * 
 * Para poder hacer emular estas dos condiciones, lo que haré es realizar una operacion sobre el numero
 *  de casillas que devuelve cada condicion, en la que el numero de casillas numeradas sin completar sea más
 *  importante (esto lo conseguire introduciendo un distinto indice para multiplicar a cada uno).
 * 
 * Proyecto IASI 2016-2017.
 * 
 * Alumno:  Francisco Javier Rojo Martín. 2do Curso. GIIIS.
 * 
 * @author javier3rm
 *
 */
public class HeuristicaE implements HeuristicFunction{

	@Override
	public double h(Object state) {
		LightCross lightCross = (LightCross) state;		
		double condicion1= 0.7; //indice de la condicion de bombillas adyacentes en casillas numeradas sin poner.
		double condicion2= 0.3; //indice de la condicion de bombillas vacias sin poner.
		double resultado;
		
		condicion1= condicion1 * lightCross.numeroBombillasNumeradas();
		condicion2= condicion2 * Math.sqrt(lightCross.cuantasPosicionesVacias());
		
		resultado= ( condicion1 + condicion2 ); //juntamos el resultado de las dos, que nos dara un numero
												//pasos intermedio al que proporcionan las dos condiciones,
												//siendo más proximo al de la condicion1 por el indice de 
												//multiplicacion.
		
		return resultado;
	}

}