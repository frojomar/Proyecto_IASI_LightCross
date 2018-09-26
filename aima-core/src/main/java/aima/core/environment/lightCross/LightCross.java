package aima.core.environment.lightCross;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import aima.core.environment.ochopuzzle.Puzzle;
import aima.core.util.datastructure.Pair;
import aima.core.util.datastructure.XYLocation;

/**
 * Clase LightCross. Contiene los metodos necesarios para realizar las operaciones que necesitará AIMA.
 * Proyecto IASI 2016-2017: Akira.
 * 
 * Alumno:  Francisco Javier Rojo Martín. 2do Curso. GIIIS.
 * 
 * @author javier3rm
 *
 */
public class LightCross {

	static final int VACIO=6; //indica que una casilla aun esta vacia (sin bombilla, sin pared, sin iluminar)
	static final int BOMBILLA=7; //indica que en esa casilla hay una bombilla
	static final int ILUMINADO=8; //indica que esa casilla se encuentra iluminada por una o varias bombillas (2 como maximo)
	static final int PARED=5; //indica que en esa casilla hay una pared (de las que no tienen numeracion, ademas)
	//Las casillas numeradas de 0 a 4 tomarán esos valores, por lo que no tendre una constante para ellas.
	
	/**variable que va a representar el estado del problema*/
	private int lightState [][] ;
	
	
	/*Configuracion que usaré para realizar las pruebas de la clase*/
	private static int[][] TABLERO_ejemplo = {
		{ 5,6,6,6,2 },
		{ 6,0,5,3,6 },
		{ 1,6,1,6,6 },
		{ 6,6,6,6,5 }
		}; 

	/* Contructores */
	
	/**
	 * Constructor por defecto utilizando configuracion inicial del problema para pruebas internas 
	 */
	public LightCross(){
		lightState = new int[TABLERO_ejemplo.length][TABLERO_ejemplo[0].length];
		for(int fila=0;fila<TABLERO_ejemplo.length;fila++){
			for(int columna=0;columna<TABLERO_ejemplo[0].length;columna++){
				lightState[fila][columna]=TABLERO_ejemplo[fila][columna];				
			}
		}
	}
	
	/** 
	 * Constructor parametrizado que recibe una variable que represente el estado inicial del problema 
	 * 
	 * @param estadoInicialLight int[][] que contiene la situacion de un estado. Esta situacion se copiará
	 * 	a la variable 'lightState'. 
	 */
	public LightCross(int[][] estadoInicialLight){
		lightState = new int[estadoInicialLight.length][estadoInicialLight[0].length];
		for(int fila=0;fila<estadoInicialLight.length;fila++){
			for(int columna=0;columna<estadoInicialLight[0].length;columna++){
				lightState[fila][columna]=estadoInicialLight[fila][columna];				
			}
		}
	}
	
	/** 
	 * Metodo que devuelve el estado del problema en un momento dado. Nos da una copia de la 
	 * 	variable 'lightState'.
	 */
	public int[][] getState(){
		int [][] retorno= new int[lightState.length][lightState[0].length];
		for (int fila = 0; fila < lightState.length; fila++) {
			for (int columna = 0; columna < lightState[0].length; columna++) {
				retorno[fila][columna] = lightState[fila][columna];
			}
		}
		
		return retorno;
	}
	
	/**
	 * Metodo que fija un estado para el problema. Copia el estado que se nos pasa por parametro a la 
	 * variable 'lightState'.
	 * 
	 * @param estadoPuzzle int[][] que contiene el estado que queremos que tenga la variable 'lightState'.
	 */
	public void setState( int[][] estadoPuzzle){
		for (int fila = 0; fila < estadoPuzzle.length; fila++) {
			for (int columna = 0; columna < estadoPuzzle[0].length; columna++) {
				lightState[fila][columna]=estadoPuzzle[fila][columna];
			}
		}
	}
	

	/**
	 * Metodo que nos devuelve las posiciones vacias. Esto quiere decir: sin pared (numerada o sin serlo), sin bombilla,
	 * 	sin iluminar.
	 * 
	 * @param posiciones Lista de posiciones (del tipo XYLocation) que servira como parametro de salida para devolver
	 * 	las posiciones vacias que existen en el tablero actual. 
	 */
	public void buscarPosicionesVacias(List<XYLocation> posiciones){
		for(int i=0; i<lightState.length; i++){
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] == VACIO) //se insertaran en la lista de posibles candidatos los que estan en casillas vacias
					posiciones.add(new XYLocation(i,j));
			}
		}
	}
	
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
/////////INICIO METODOS ESPECIFICOS PARA HEURISTICAS /////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
	
	/**
	 * Metodo consultor que nos devuelve el numero de filas del estado actual.
	 * 
	 * @return int que contiene el numero de filas del estado actual.
	 */
	public int getNumeroFilas(){
		return lightState.length;
	}
	
	/**
	 * Metodo consultor que nos devuelve el numero de columnas del estado actual.
	 * 
	 * @return int que contiene el numero de columnas del estado actual.
	 */
	public int getNumeroColumnas(){
		return lightState[0].length;
	}
	
	/**
	 * Metodo que cuenta el numero de posiciones vacias del estado actual.
	 * 
	 * @return devuelve un entero que posee el numero de posiciones vacias del estado actual.
	 */
	public int cuantasPosicionesVacias(){
		int cuantas=0;
		for(int i=0; i<lightState.length; i++){
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] == VACIO) 
					cuantas=cuantas+1;
			}
		}
		return cuantas;
	}
	
	/**
	 * Metodo que cuenta el numero de filas completas del estado actual.
	 * 
	 * @return devuelve un int que posee el numero de filas completas del estado actual.
	 */
	public int numeroFilasCompletas(){
		boolean llena;
		int cuantas=0;
		
		for(int i=0; i<lightState.length; i++){
			llena=true;
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] == VACIO) 
					llena=false;
			}
			if(llena==true){ //si la fila estaba completa..
				cuantas++; //incrementamos el numero de filas completas
			}
		}
		
		return cuantas;
	}
	
	/**
	 * Metodo que cuenta el numero de columnas completas del estado actual.
	 * 
	 * @return devuelve un int que posee el numero de columnas completas del estado actual.
	 */
	public int numeroColumnasCompletas(){
		boolean llena;
		int cuantas=0;
		
		for(int i=0; i<lightState[0].length; i++){
			llena=true;
			for(int j=0; j< lightState.length; j++){
				if(lightState[j][i] == VACIO) 
					llena=false;
			}
			if(llena==true){ //si la columna estaba completa..
				cuantas++; //incrementamos el numero de columnas completas
			}
		}
		
		return cuantas;
	}
	/**
	 * Metodo que nos dice en una casilla numerada (valor distinto de '0') cuantas bombillas le faltan.
	 * 
	 * @param candidata XYLocation que contiene la posicion de la casilla numerada sobre la que se van a comprobar las adyacentes.
	 * 
	 * @return devuelve int con el numero de bombillas que le faltan.
	 */
	private int cuantasBombillasAdyacentesFaltan(XYLocation candidata) {
		int fila= candidata.getXCoOrdinate();
		int columna= candidata.getYCoOrdinate();
		int valor=lightState[fila][columna];
		
		//comprobacion bombilla encima
		if(fila-1>=0){
			if(lightState[fila-1][columna]==BOMBILLA)
				valor--;
		}
		//comprobacion bombilla debajo
		if(fila+1<lightState.length){
			if(lightState[fila+1][columna]==BOMBILLA)
				valor--;
		}
		//comprobacion bombilla izquierda
		if(columna-1>=0){
			if(lightState[fila][columna-1]==BOMBILLA)
				valor--;
		}
		//comprobacion bombilla derecha
		if(columna+1<lightState[0].length){
			if(lightState[fila][columna+1]==BOMBILLA)
				valor--;
		}
		
		return valor;
	}
	
	/**
	 * Metodo que cuenta el numero de bombillas adyacentes a las casillas numeradas (de 1 a 4) 
	 * que quedan por poner en el tablero.
	 * 
	 * @return devuelve un int con el numero de bombillas adyacentes a casillas numeradas que faltan.
	 * 
	 */
	public int numeroBombillasNumeradas(){
		int cuantasBombillas=0;
		for(int i=0; i<lightState.length; i++){
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] >0 && lightState[i][j]<=4){ //si es numerada..
					cuantasBombillas+=this.cuantasBombillasAdyacentesFaltan(new XYLocation(i,j));
				}
			}
		}
		return cuantasBombillas;
	}
	
	/**
	 * Metodo que cuenta el numero de casillas numeradas (de 1 a 4) que quedan por completar en el tablero.
	 * 
	 * @return devuelve un int con el numero de bombillas adyacentes a casillas numeradas que faltan.
	 * 
	 */
	public int numeroCasillasNumeradasIncompletas(){
		int noCompletas=0;
		
		for(int i=0; i<lightState.length; i++){
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] >0 && lightState[i][j]<=4){ //si es numerada..
					if(!casillaNumeradaCompletaAux(new XYLocation(i,j)))//comprobamos si esta completa
						noCompletas++;
				}
			}
		}
		return noCompletas;
	}
	
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
////////// FIN METODOS ESPECIFICOS PARA HEURISTICAS //////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
	
	/**
	 * Metodo que nos dice si existe alguna posicion '0' (bloque numerado con 0) adyacente (encima,
	 * debajo, izquierda, derecha) a la casilla 'candidata'.
	 * 
	 * @param candidata XYLocation que contiene la posicion de la casilla sobre la que se van a comprobar las adyacentes.
	 * 
	 * @return devuelve 'true' si existe una posicion '0' adyacente y 'false' si no existe ninguna.
	 */
	private boolean posicionCeroAdyacente(XYLocation candidata) {
		int fila= candidata.getXCoOrdinate();
		int columna= candidata.getYCoOrdinate();
		
		//comprobacion ningun 0 adyacente encima
		if(fila-1>=0 && lightState[fila-1][columna]==0){
			return true;
		}
		//comprobacion ningun 0 adyacente debajo
		if(fila+1<lightState.length && lightState[fila+1][columna]==0){
			return true;
		}
		//comprobacion ningun 0 adyacente izquierda
		if(columna-1>=0 && lightState[fila][columna-1]==0){
			return true;
		}
		//comprobacion ningun 0 adyacente derecha
		if(columna+1<lightState[0].length && lightState[fila][columna+1]==0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Metodo que nos dice si una casilla numerada (valor distinto de '0') esta ya completa o no. Es decir,
	 * si ya tiene tantas bombillas adyacentes(encima, debajo, izquierda, derecha) como necesita.
	 * 
	 * @param candidata XYLocation que contiene la posicion de la casilla numerada sobre la que se van a comprobar las adyacentes.
	 * 
	 * @return devuelve 'true' si esta completa y 'false' si no lo esta.
	 */
	private boolean casillaNumeradaCompletaAux(XYLocation candidata) {
		int fila= candidata.getXCoOrdinate();
		int columna= candidata.getYCoOrdinate();
		int valor=lightState[fila][columna];
		
		//comprobacion bombilla encima
		if(fila-1>=0){
			if(lightState[fila-1][columna]==BOMBILLA)
				valor--;
		}
		//comprobacion bombilla debajo
		if(fila+1<lightState.length){
			if(lightState[fila+1][columna]==BOMBILLA)
				valor--;
		}
		//comprobacion bombilla izquierda
		if(columna-1>=0){
			if(lightState[fila][columna-1]==BOMBILLA)
				valor--;
		}
		//comprobacion bombilla derecha
		if(columna+1<lightState[0].length){
			if(lightState[fila][columna+1]==BOMBILLA)
				valor--;
		}
		
		if(valor>0)
			return false;
		
		return true;
	}
	
	
	/**
	 * Metodo que nos dice si existe una casilla numerada (valor distinto de '0') adyacente a la casilla que le
	 * estamos pasando. Son adyacentes las posiciones: encima, debajo, izquierda, derecha.
	 * 
	 * @param candidata XYLocation que contiene la posicion de la casilla sobre la que se van a comprobar las adyacentes.
	 * 
	 * @return devuelve 'true' si existe alguna casilla numerada (que ya esta completa) junto a esta casilla 'candidata'.
	 */
	private boolean casillaNumeradaCompleta(XYLocation candidata) {
		int fila= candidata.getXCoOrdinate();
		int columna= candidata.getYCoOrdinate();
		int valor;
		
		//NOTA MUY IMPORTANTE: En el caso de que la casilla valga cuatro, no llamamos a 'casillaCompletaAux', pues no
		//es necesario comprobar si esta completa, pues ya sabemos que no (si no, esta posicion no estaría vacia).
		//comprobacion ninguna casilla numerada completa por encima
		if(fila-1>=0){
			valor=lightState[fila-1][columna];
			if(valor>0 && valor<4) {
				if(casillaNumeradaCompletaAux(new XYLocation(fila-1,columna)))
					return true;
			}
		}
		//comprobacion ninguna casilla numerada completa por debajo
		if(fila+1<lightState.length){
			valor=lightState[fila+1][columna];
			if(valor>0 && valor<4) {
				if(casillaNumeradaCompletaAux(new XYLocation(fila+1,columna)))
					return true;
			}
		}
		//comprobacion ninguna casilla numerada completa por la izquierda
		if(columna-1>=0){
			valor=lightState[fila][columna-1];
			if(valor>0 && valor<4) {
				if(casillaNumeradaCompletaAux(new XYLocation(fila,columna-1)))
					return true;
			}
		}
		//comprobacion ninguna casilla numerada completa por la derecha
		if(columna+1<lightState[0].length){
			valor=lightState[fila][columna+1];
			if(valor>0 && valor<4) {
				if(casillaNumeradaCompletaAux(new XYLocation(fila,columna+1)))
					return true;
			}
		}
		
		return false;
	}
	

	/**
	 * Metodo que nos dice si una casilla vacia es valida o no. Para que lo sea, debe cumplirse que:
	 * 	->No tiene ninguna casilla 0 adyacente
	 *	->No tiene ninguna casilla numerada adyacente ya completa
	 * 	->No hay una bombilla en su fila o columna (siempre y cuando no haya pared de por medio).	
	 * 
	 * @param candidata XYLocation que posee las coordenadas de la posicion a comprobar si es valida o no.
	 * 
	 * @return devuelve 'true' si la posicion es valida y 'false' si no lo es.
	 */
	private boolean candidataValida(XYLocation candidata) {
		
		
		return !posicionCeroAdyacente(candidata) && !casillaNumeradaCompleta(candidata);
		
		//podriamos haber creado un metodo 'filaColumnaSinBombilla(candidata)' que ademas comprobase si 
		//ya habia alguna bombilla en la fila o columna sin una pared en medio, pero no es necesario, pues
		// 'buscarPosicionesVacias' ya nos devuelve las posiciones VACIAS, es decir, SIN ILUMINAR, por lo que
		//ya sabemos que no puede a ver ninguna bombilla de por medio que incumpla esa condicion. 
		//
		//Por tanto,
		// llegados a este punto, no es necesario comprobar esa condicion, pues LA HEMOS COMPROBADO INDIRECTAMENTE.
	}
	
	/**
	 * Metodo que nos devuelve una lista de las posibles posiciones en las que se pueden poner una fuente de luz. Para
	 * que sea valida una casilla, se debe dar que:
	 * 	->Esta vacia.
	 * 	->No tiene ninguna casilla 0 adyacente
	 *	->No tiene ninguna casilla numerada adyacente ya completa
	 * 	->No hay una bombilla en su fila o columna (siempre y cuando no haya pared de por medio).	
	 * 
	 * @return List<XYLocation>, que será una lista con todas las posibles posiciones en las que podemos poner una 
	 * bombilla (cada posicion almacenada como un XYLocation).
	 */
	public List<XYLocation> getPosiblesFuentesLuz() {
		int i=0;
		XYLocation candidata;
		List<XYLocation> posiciones= new ArrayList<XYLocation>();
		//posiciones esta vacia
		
		this.buscarPosicionesVacias(posiciones); 
		//posiciones contiene todas las casillas que se encuentran vacías y sin iluminar.
		
		
		while(i<posiciones.size()){
			candidata=posiciones.get(i);
			if(candidataValida(candidata)) //comprobamos cuales son validas realmente
				i++;
			else
				posiciones.remove(i); //las que no son validas se eliminan
		}
		
		return posiciones;
	}

	/**
	 * Metodo que pone una bombilla en la posicion 'coordenadas'. Al poner la bombilla, ilumina la fila y columna
	 * correspondiente a esta posicion 'coordenadas' hasta que se encuentra una pared o bloque.
	 * 
	 * @param coordenadas XYLocation que contiene la posicion sobre la que vamos a poner la bombilla.
	 */
	public void incluirFuenteLuz(XYLocation coordenadas){
		int fila=coordenadas.getXCoOrdinate();
		int columna=coordenadas.getYCoOrdinate();
		int i;
		
		lightState[fila][columna]=BOMBILLA;
		
		//iluminar columna hasta las paredes más proximas (si existen).
		i=fila;
		while( i>=0 && ( lightState[i][columna]==ILUMINADO || lightState[i][columna]==VACIO || lightState[i][columna]==BOMBILLA ) ){
			if(lightState[i][columna]!=BOMBILLA) //solo es necesaria esta comprobacion por la posicion 'coordenadas'.
				lightState[i][columna]=ILUMINADO;
			i--;
		}
		i=fila;
		while(i<lightState.length && (lightState[i][columna]==ILUMINADO || lightState[i][columna]==VACIO || lightState[i][columna]==BOMBILLA)){
			if(lightState[i][columna]!=BOMBILLA)//solo es necesaria esta comprobacion por la posicion 'coordenadas'.
				lightState[i][columna]=ILUMINADO;
			i++;
		}
		
		//iluminar fila hasta las paredes más proximas (si existen).
		i=columna;
		while(i>=0 && (lightState[fila][i]==ILUMINADO || lightState[fila][i]==VACIO || lightState[fila][i]==BOMBILLA)){
			if(lightState[fila][i]!=BOMBILLA)//solo es necesaria esta comprobacion por la posicion 'coordenadas'.
				lightState[fila][i]=ILUMINADO;
			i--;
		}
		i=columna;
		while(i<lightState[0].length && (lightState[fila][i]==ILUMINADO || lightState[fila][i]==VACIO || lightState[fila][i]==BOMBILLA)){
			if(lightState[fila][i]!=BOMBILLA)//solo es necesaria esta comprobacion por la posicion 'coordenadas'.
				lightState[fila][i]=ILUMINADO;
			i++;
		}
	}
	
	/**
	 * Metodo que comprueba si hemos llegado a un estado objetivo: 
	 * 
	 *	->No hay ninguna casilla vacía.
	 *	->No hay ninguna bombilla adyacente a posicion con 0.
	 *	->Todos los bloques numerados tienen su numero correspondiente de bombillas adyacentes.
	 *
	 * @return devuelve 'true' si estamos en un estado objetivo y 'false' si no lo estamos.
	 */
	public boolean checkFinish() {
		
		for(int i=0; i<lightState.length; i++){
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] == VACIO) //en cuanto encontremos una posicion vacia fin == false
					return false;
			}
		}
		
		//ahora debemos comprobar que se cumplen las condiciones de las casillas numeradas.
		
		if(condicionCasillasNumeradas()==false)//si no se dan condiciones de casillas numeradas
			return false; //devolvemos que no es final
		
		//no es necesario comprobar si hay bombillas al lado de casillas 0, pues no se pueden poner ninguna 
		//ahi al no darnoslo como opcion 'getPosiblesFuentesLuz'.
		
		return true; //si no hay posiciones vacias, habremos terminado.
	}
	
	/**
	 * Metodo que nos dice si se cumple la condicion de las Casillas Numeradas. Esta condicion establece que 
	 * cada casilla numerada tiene que tener el mismo numero de bombillas adyacentes a su valor.
	 * 
	 * @return devuelve 'true' si se cumple la condicion y 'false' si no se cumple.
	 */
	private boolean condicionCasillasNumeradas() {
		
		for(int i=0; i<lightState.length; i++){
			for(int j=0; j< lightState[0].length; j++){
				if(lightState[i][j] >0 && lightState[i][j]<=4){ //si es numerada..
					if(!casillaNumeradaCompletaAux(new XYLocation(i,j)))//comprobamos si esta completa
						return false; //si NO lo esta devolvemos false, pues no se cumple condicion Casillas Numeradas
				}
			}
		}
		return true; //si se cumple las condiciones en todas, decimos que la condicion se cumple en el Estado por tanto.
		
		//no hace falta comprobar si hay casillas con valor 0 con bombilla alrededor, pues nunca se puede poner
		//bombillas adyacentes a estas columnas (se filtran estas posiciones en getPosicionesPosibles mediante
		//la llamada al metodo casillaValida(que llama a posicionCeroAdyacente)).	
	}
	
	
	/**
	 * Metodo que pinta el estado actual. Pinta una matriz donde:
	 *	->0..4 = Casilla numerada.
	 *	->B = Bombilla. 	
	 *	->x = Iluminada.
	 *	-># = Pared.
	 */
	public void printCurrentState(){
		System.out.println();
		
		System.out.print("       ");
		for(int j=0; j<lightState[0].length;j++){
			System.out.print(j+" ");
		}
		System.out.println();
		
		System.out.print("       ");
		for(int j=0; j<lightState[0].length;j++){
			System.out.print("- ");
		}
		System.out.println();
		
		for(int i=0; i<lightState.length; i++){
			System.out.print("    "+i+" |");
			for(int j=0; j<lightState[0].length; j++){
				switch(lightState[i][j]){
					case VACIO:
						System.out.print(" ");
						break;
					case BOMBILLA:
						System.out.print("B");
						break;
					case ILUMINADO:
						System.out.print("x");
						break;
					case PARED:
						System.out.print("#");
						break;
					default:
						System.out.print(lightState[i][j]);
				}
				if(j!=lightState[0].length){System.out.print(" ");}
			}
			System.out.println("| "+i+"    ");
		}
		
		System.out.print("       ");
		for(int j=0; j<lightState[0].length;j++){
			System.out.print("- ");
		}
		System.out.println();
		
		System.out.print("       ");
		for(int j=0; j<lightState[0].length;j++){
			System.out.print(j+" ");
		}
		System.out.println();
		
		System.out.println();
	}
	
	/**
	 * Metodo que implementa el hashCode en funcion del estado del problema.
	 */
	public int hashCode() {
		int result = 17;
		for (int fila = 0; fila < lightState.length; fila++) {
			for (int columna = 0; columna < lightState[0].length; columna++) {
					result += ((result * 37 * lightState[fila][columna])+fila+5)*(columna+7);
			}
		}
		return result;
		}	
	
	/**
	 * Metodo que implementa el metodo equals en funcion del estado del problema.
	 * 
	 * @param o Object con el que se va a comparar nuestro tablero de LightCross.
	 */
	@Override
	public boolean equals(Object o) {
		boolean iguales=true;
		int fila=0;
		int columna=0;	
		//parte com�n a cualquier equals
		if(this == o)
			return true;
		if((o == null) || (this.getClass() != o.getClass()))
			return false;
		//parte espec�fica del equals
		LightCross aState = (LightCross) o;
		while(iguales && fila<lightState.length){
			while(iguales && columna<lightState[0].length){
				iguales=(lightState[fila][columna]==aState.getState()[fila][columna]);
				columna++;
			}
			columna=0;
			fila++;
		}
		return iguales;
	}
	
	/*M�todo que implementa el m�todo toString en funci�n del estado del problema*/

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(" |");
		for (int columna=0;columna<lightState[0].length;columna++)
			buf.append(columna);
		
		buf.append("\n");
		buf.append("  ");
		for (int columna=0;columna<lightState[0].length;columna++)
			buf.append("-");
		
		for (int fila=0;fila<lightState.length;fila++){
			buf.append("\n");
			buf.append(fila);
			buf.append("|");
			for (int columna=0;columna<lightState[0].length;columna++) 
			{ 
				switch(lightState[fila][columna]){
				case VACIO:
					buf.append(" ");
					break;
				case BOMBILLA:
					buf.append("B");
					break;
				case ILUMINADO:
					buf.append("x");
					break;
				case PARED:
					buf.append("#");
					break;
				default:
					buf.append(lightState[fila][columna]);
				}					
			}
		}
	
		buf.append("\n");
		return buf.toString();
	}
	
	/*Main de la clase para realizar pruebas internas*/
	public static void main (String args[]){
		LightCross l= new LightCross();
		List<XYLocation> posiciones= new ArrayList<XYLocation>();
		
		l.printCurrentState();
		
		System.out.println(l.toString());
		
		
		//PRUEBA DE buscarPosicionesVacias
		l.buscarPosicionesVacias(posiciones);
		
		System.out.println("---------------------------");
		for(int i=0; i<posiciones.size(); i++){
			System.out.println(posiciones.get(i).getXCoOrdinate()+" - "+posiciones.get(i).getYCoOrdinate());
		}
		System.out.println("---------------------------");
		
		
		//PRUEBA 1 DE cuantasPosicionesVacias
		if(l.cuantasPosicionesVacias()==12){System.out.println("--PRUEBA 1 DE cuantasPosicionesVacias CORRECTA");}
		else{System.out.println("--PRUEBA 1 DE cuantasPosicionesVacias FALLIDA");}
		
		
		//PRUEBA DE getPosiblesFuentesLuz
		posiciones=l.getPosiblesFuentesLuz();
		
		System.out.println("---------------------------");
		for(int i=0; i<posiciones.size(); i++){
			System.out.println(posiciones.get(i).getXCoOrdinate()+" - "+posiciones.get(i).getYCoOrdinate());
		}
		System.out.println("---------------------------");
		
		
		//PRUEBA DE incluirFuenteLuz
		l.incluirFuenteLuz(new XYLocation(0,2));
		
		l.printCurrentState();
		
		posiciones= new ArrayList<XYLocation>();
		
		l.buscarPosicionesVacias(posiciones);
		
		System.out.println("---------------------------");
		for(int i=0; i<posiciones.size(); i++){
			System.out.println(posiciones.get(i).getXCoOrdinate()+" - "+posiciones.get(i).getYCoOrdinate());
		}
		System.out.println("---------------------------");
		
		posiciones=l.getPosiblesFuentesLuz();
		
		System.out.println("---------------------------");
		for(int i=0; i<posiciones.size(); i++){
			System.out.println(posiciones.get(i).getXCoOrdinate()+" - "+posiciones.get(i).getYCoOrdinate());
		}
		System.out.println("---------------------------");
		System.out.println();
		System.out.println();

		
		//PRUEBA 1 DE checkFinish
		if(l.checkFinish()){
			System.out.println("--PRUEBA 1 DE checkFinish FALLIDA");
		}
		else{
			System.out.println("--PRUEBA 1 DE checkFinish CORRECTA");
		}
		System.out.println();
		System.out.println();

		
		//PRUEBA 2 DE cuantasPosicionesVacias
		if(l.cuantasPosicionesVacias()==9){System.out.println("--PRUEBA 2 DE cuantasPosicionesVacias CORRECTA");}
		else{System.out.println("--PRUEBA 2 DE cuantasPosicionesVacias FALLIDA");}
		System.out.println();
		System.out.println();

		
		//PRUEBA DE getState
		System.out.println("PRUEBA DE getState");
		
		for(int i=0; i<l.getState().length; i++){
			for(int j=0; j<l.getState()[0].length; j++){
				System.out.print(l.getState()[i][j]+" ");
			}
			System.out.println( );
		}

		System.out.println("Comparar con esta:"
				+"\n"+ "5 8 7 8 2"
				+"\n"+ "6 0 5 3 6" 
				+"\n"+ "1 6 1 6 6" 
				+"\n"+ "6 6 6 6 5");
		System.out.println();
		System.out.println();
		System.out.println();

		
		//PRUEBA DE LightCross(int[][])
		System.out.println("PRUEBA DE LightCross(int[][])");
		LightCross l2= new LightCross(l.getState());
		//if(l2.getState().equals(l.getState())){System.out.println("--PRUEBA DE constructor parametrizado CORRECTA");}
		//else{System.out.println("--PRUEBA DE constructor parametrizado FALLIDA");}
		
		for(int i=0; i<l2.getState().length; i++){
			for(int j=0; j<l2.getState()[0].length; j++){
				System.out.print(l2.getState()[i][j]+" ");
			}
			System.out.println( );
		}
		
		System.out.println("Comparar con esta:"
				+"\n"+ "5 8 7 8 2"
				+"\n"+ "6 0 5 3 6" 
				+"\n"+ "1 6 1 6 6" 
				+"\n"+ "6 6 6 6 5");
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		//PRUEBA DE setState
		System.out.println("PRUEBA DE setState");
		int [][] matriz= {
				{ 5,8,8,7,2 },
				{ 8,7,5,3,7 },
				{ 1,8,1,7,8 },
				{ 7,8,8,8,5 }
				}; 
		l.setState(matriz);
		l.printCurrentState();
		System.out.println("Comparar con 'matriz' del main o con esta (es la misma):"
				+"\n"+ "# x x B 2"
				+"\n"+ "x B # 3 B" 
				+"\n"+ "1 x 1 B x" 
				+"\n"+ "B x x x #");
		System.out.println();
		System.out.println();
		System.out.println();
		//if(l.getState().equals(matriz)){System.out.println("--PRUEBA DE setState CORRECTA");}
		//else{System.out.println("--PRUEBA DE setState FALLIDA");}
		
		
		
		//PRUEBA 2 DE checkFinish
		if(!l.checkFinish()){
			System.out.println("--PRUEBA 2 DE checkFinish CORRECTA");
		}
		else{
			System.out.println("--PRUEBA 2 DE checkFinish FALLIDA");
		}
		
		int [][] matriz2= {
				{ 5,8,8,7,2 },
				{ 8,7,5,3,7 },
				{ 1,8,5,7,8 },
				{ 7,8,8,8,5 }
				}; 
		l.setState(matriz2);
		l.printCurrentState();
		
		//PRUEBA 3 DE checkFinish
		if(l.checkFinish()){
			System.out.println("--PRUEBA 3 DE checkFinish CORRECTA");
		}
		else{
			System.out.println("--PRUEBA 3 DE checkFinish FALLIDA");
		}
	}

	
}
