package ncb.phd.pucv.cuckooSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
//import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ncb.phd.pucv.cuckooSearch.dbscan.DBSCANClusterer;
import ncb.phd.pucv.cuckooSearch.dbscan.DBSCANClusteringException;
import ncb.phd.pucv.cuckooSearch.dbscan.DistanceMetricPunto;

//import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Nicolás Caselli Benavente Phd Informática PUCV
 */
public class CuckooSearch extends Thread{

	private static int cantNidos, cantNidosInicial; //número de población de nidos(soluciones)
	private static float probDescubrimiento, probDescubrimientoInicial ;  //Probabilidad de descubrir el nido(solución) por la especie dueña de este.
	private static int numIteraciones; //Número de iteraciones del ciclo de búsqueda.
	private static String tipoBinarizacion, tipoDiscretizacion, idArchivo;
	private static float nidosDecimales[][], nidosBinarios[][];
	private static int costos[];
	private static int restricciones[][];
	private static int cantidadFilas, cantidadColumnas;
	private static float bestFit, bestFitAnterior;
	private static long tiempoInicioEjecucion, tiempoInicioGlobal, tiempoTermino;
	private static long semilla;
	private static boolean fs, ss;
	private static Map<Integer, String> mNombresInstancias = new HashMap<Integer, String>(); 
	private static Map<Integer, Integer> mBestFistInstancias = new HashMap<Integer, Integer>();
    /**
     * {@code logger} = Log de errores
     */
    private static final Logger logger = Logger.getLogger(CuckooSearch.class.getName());
	 /**
     * {@code rows} = Filas de M {@code cols} = Columnas de M
     */
    private static int rows, cols;
    /**
     * {@code rs} = Restricciones {@code rc} = Restricciones cubiertas
     * {@code cc} = Contador de costos {@code cr} = Contador de restricciones
     * {@code fc} = Contador de fitness {@code tf} = Total fitness
     */
    private static int rs, rc, cc, cr;
	/**
	 * {@code input} = ubicacion del archivo SCP {@code output} = Nombre y
	 * ubicacion del archivo de resultados
	 */
	private String inPut;
	/**
	 * {@code outPut} = ubicacion del archivo SCP {@code output} = Nombre y
	 * ubicacion del archivo de resultados
	 */
	private String outPut;

	public long getSemilla() {
		return semilla;
	}

	public void setSemilla(long semilla) {
		this.semilla = semilla;
	}

	public String getInPut() {
		return inPut;
	}

	public void setInPut(String inPut) {
		this.inPut = inPut;
	}

	public String getOutPut() {
		return outPut;
	}

	public void setOutPut(String outPut) {
		this.outPut = outPut;
	}

	public long getTiempoInicioEjecucion() {
		return tiempoInicioEjecucion;
	}

	public void setTiempoInicioEjecucion(long tiempoInicioEjecucion) {
		this.tiempoInicioEjecucion = tiempoInicioEjecucion;
	}

	public long getTiempoInicioGlobal() {
		return tiempoInicioGlobal;
	}

	public void setTiempoInicioGlobal(long tiempoInicioGlobal) {
		this.tiempoInicioGlobal = tiempoInicioGlobal;
	}

	public long getTiempoTermino() {
		return tiempoTermino;
	}

	public void setTiempoTermino(long tiempoTermino) {
		this.tiempoTermino = tiempoTermino;
	}

	public String getTipoBinarizacion() {
		return tipoBinarizacion;
	}

	CuckooSearch(int cant_Nidos, float probabilidad,  int numeroiteraciones,
						Map<Integer, String> mInstanciasAprocesar, 
						Map<Integer, Integer> mMejoresFitsInstancias,
						String discretizacion,
						String binarizacion,
						int [] vectorCostos,
						int [] matrizRestricciones[],
						int numFilas,
						int numColumnas) { //Constructor
		cantNidos = cant_Nidos;
		cantNidosInicial = cant_Nidos;
		probDescubrimientoInicial = probabilidad;
		probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		numIteraciones = numeroiteraciones;
		tipoDiscretizacion = discretizacion;
		tipoBinarizacion = binarizacion;
		mNombresInstancias = mInstanciasAprocesar;
		mBestFistInstancias = mMejoresFitsInstancias;
		nidosDecimales = null;
		costos = vectorCostos;
		restricciones = matrizRestricciones;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		cantidadColumnas = numColumnas;
		cantidadFilas = numFilas;
		

	}
	
	CuckooSearch(int cant_Nidos, float probabilidad,  int numeroiteraciones,Map<Integer, String> mInstanciasAprocesar, Map<Integer, Integer> mMejoresFitsInstancias, String discretizacion, String binarizacion) { //Constructor
		cantNidos = cant_Nidos;
		cantNidosInicial = cant_Nidos;
		probDescubrimientoInicial = probabilidad;
		probDescubrimiento = probabilidad;
//		num_dimensiones = 0;
		numIteraciones = numeroiteraciones;
		tipoDiscretizacion = discretizacion;
		tipoBinarizacion = binarizacion;
		mNombresInstancias = mInstanciasAprocesar;
		mBestFistInstancias = mMejoresFitsInstancias;
		nidosDecimales = null;
		costos = null;
		restricciones = null;
		bestFit = 0;
		tiempoInicioEjecucion = 0;
		tiempoInicioGlobal = System.currentTimeMillis();;
		tiempoTermino = 0;
		semilla = new Date().getTime();
		

	}

	public void setBestFit (int bestFit)
	{
		this.bestFit = bestFit;
	}

	public float getBestFit() {
		return this.bestFit;
	}

	public void setInput(String input) {
		this.inPut = input;
	}

	public void setOutput(String output) {
		this.outPut = output;
	}

	public void setTipoBinarizacion(String tipoBinarizacion) {
		this.tipoBinarizacion = tipoBinarizacion;
	}

	public int getNum_nidos() {
		return cantNidos;
	}
	public void setNum_nidos(int num_nidos) {
		this.cantNidos = num_nidos;
	}
	public float getProbDescubrimiento() {
		return probDescubrimiento;
	}
	public void setProbDescubrimiento(float probDescubrimiento) {
		this.probDescubrimiento = probDescubrimiento;
	}

	public int getNumIteraciones() {
		return numIteraciones;
	}
	public void setNumIteraciones(int n_iter_total) {
		this.numIteraciones = n_iter_total;
	}
	public float[][] getNidos() {
		return nidosDecimales;
	}
	public void setNidos(float[][] nidos) {
		this.nidosDecimales = nidos;
	}
	public int[] getCostos() {
		return costos;
	}
	public void setCostos(int[] costos) {
		this.costos = costos;
	}
	public int[][] getRestricciones() {
		return restricciones;
	}
	public void setRestricciones(int[][] restricciones) {
		this.restricciones = restricciones;
	}
	/*Este método inicializa las soluciones de manera binaria y aleatoria*/
	private float[][] inicializacionSoluciones() throws FileNotFoundException, IOException {
		Random rnd = new Random(System.nanoTime());
		float nest[][] = new float[this.cantNidos][this.cantidadColumnas];
		for (int i = 0; i < this.cantNidos; i++) {
			for (int j = 0; j < this.cantidadColumnas; j++) {
				nest[i][j] = rnd.nextFloat();
			}
//			nest[i] = this.evaluarRestriccionesYRepara(nest[i], restricciones, costos);
		}
		return nest;
	}
	
	/**
	 * Esta función recorre la lista de restricciones, y encuentra la columna que tenga el menos valor de ponderación (Costo columna / suma de 1's de dicha columna)
	 * @param arrayList arreglo con restricciones a evaluar
	 * @param restricciones
	 * @param costos
	 * @return
	 */
	private Integer ColumnaMenorCosto(ArrayList<Integer> arrayList, int restricciones[][], int costos[]) {
		double nValor = 0;
		double nValorTemp = 0;
		int nFila = 0;
		int cont = 0;

		for (Integer nColumna : arrayList) {
			double sum = 0;
			for (int i = 0; i < restricciones.length; i++) {
				if (restricciones[i][nColumna] == 1) {
					sum++;
				}
			}
			if (cont == 0) {
				nValor = costos[nColumna] / sum;
				nValorTemp = costos[nColumna] / sum;
				nFila = nColumna;
			} else {
				nValorTemp = costos[nColumna] / sum;
			}
			if (nValorTemp < nValor) {
				nValor = nValorTemp;
				nFila = nColumna;
			}
			cont++;
		}

		return nFila;
	}

	private float[] evaluarRestriccionesYRepara(float nest[], int restricciones[][], int costos[]) {
		/*int sumatoria=0;
         for(int i=0;i<restricciones.length;i++){
         for(int j=0;j<this.num_dimensiones;j++){
         if(sumatoria==0){
         sumatoria = sumatoria + (restricciones[i][j]*nidos[j]);
         }
         }

         if(sumatoria == 0){ //MÉTODO CLÁSICO LLENANDO CON 1 DE IZQ A DERECHA
         for(int k=0;k<this.num_dimensiones;k++){
         if(restricciones[i][k] == 1){
         nidos[k]=1;
         sumatoria=0;
         break;
         }
         }
         }else{
         sumatoria=0;
         }

         }*/
		//MÉTODO VITOLI
		//valida que cada columna sea cubierta por almenos un valor de alguna fila del nido
		boolean bComprobacion = false;
		for (int i = 0; i < restricciones.length; i++) {
			bComprobacion = false;
			for (int j = 0; j < restricciones[i].length; j++) {
				if (nest[j] * restricciones[i][j] == 1) {
					bComprobacion = true;
					break;
				}
			}
			if (bComprobacion == false) {
				break;
			}
		}


		if (bComprobacion == false) {		//Si existe alguna restricción qu eno se cumpla, caemos acá
			Map<Integer, ArrayList<Integer>> aListColRestr = new HashMap<Integer, ArrayList<Integer>>(); //lista de columnas restricciones, con su respectivo índice
			Map<Integer, ArrayList<Integer>> aListFilRestr = new HashMap<Integer, ArrayList<Integer>>();	//lista de filas restricciones, con su respectivo índice
			ArrayList<Integer> aListU = new ArrayList<Integer>(); //lista que contemdrá todas las filas que violan resticcion
			ArrayList<Integer> aListW = new ArrayList<Integer>();

			//Este for, es el simil al "find" de matlab, que devuelve los índices del vector cuyos valores no son ceros
			for (int i = 0; i < restricciones.length; i++) { //recorre, por cada fila, todas las columnas, y aquellas que son restrictivas las guarda
				aListU.add(i);
				ArrayList<Integer> aListTemp = new ArrayList<Integer>();
				for (int j = 0; j < restricciones[i].length; j++) {
					if (restricciones[i][j] == 1) {
						aListTemp.add(j);
					}
				}
				aListColRestr.put(i, aListTemp);//para la fila i, todas las columnas con 1--> asigna lista de índices de columnas con valor 1 en la restricción i
			}

			for (int j = 0; j < restricciones[0].length; j++) { //recorre, por cada columna, todas las filas, y aquellas que son restrictivas las guarda
				ArrayList<Integer> aListTemp = new ArrayList<Integer>();
				for (int i = 0; i < restricciones.length; i++) {
					if (restricciones[i][j] == 1) {
						aListTemp.add(i);
					}
				}
				aListFilRestr.put(j, aListTemp);//para la columna j, todas las filas con 1 --> asigna lista de índices de filas con valor 1 en la restricción i
			}

			for (int i = 0; i < restricciones.length; i++) {
				for (int j = 0; j < restricciones[i].length; j++) {
					if (nest[j] * restricciones[i][j] == 1) {	//si en el nido j no viola restricción, se elimina el id de la fila de la lista 
						if (aListU.contains((Object) i)) {
							aListU.remove((Object) i);
						}
						break;
					}
				}
			}
			HashSet<Integer> hashSet = new HashSet<Integer>(aListU);
			aListU.clear();
			aListU.addAll(hashSet);

			//ACÁ COMIENZA LA REPARACIÓN DEL NIDO

			while (!aListU.isEmpty()) { //MIENTRAS QUEDEN COLUMNAS POR CORREGIR
				int nFila = 0;
				for (Integer fila : aListU) {
					nFila = fila;
					break;
				}
				Integer nColumnSel = ColumnaMenorCosto(aListColRestr.get(nFila), restricciones, costos); //busca la columna de mayor ajuste (la que tenga mas opciones de ser reemplazada)
				nest[nColumnSel] = 1;

				for (Integer nFilaDel : aListFilRestr.get(nColumnSel)) {
					if (aListU.contains((Object) nFilaDel)) {
						aListU.remove((Object) nFilaDel);	//DADO QUE CORREGÍ ARRIBA, QUITO LA FILA DE LA LISTA --> borra la fila completa, pues tiene otra columna que la resuelve
					}
				}
			}
			//LUEGO DE CORREGIR, VALIDAMOS CUÁNTAS FILAS QUEDAN SIN RESTRICCION POR CADA COLUMNA
			int contFila = 0;
			for (int i = 0; i < restricciones.length; i++) {
				contFila = 0;
				for (int j = 0; j < restricciones[i].length; j++) {
					if (nest[j] * restricciones[i][j] == 1) {
						contFila++;
					}
				}
				aListW.add(contFila); //se agregan tantos elementos como filas con 1 existan en el nido
			}

			aListU = null;
			ArrayList<Integer> aNumRow = null;
			boolean bComp = false;
			for (int j = nest.length - 1; j >= 0; j--) {
				bComp = false;
				aNumRow = new ArrayList<Integer>();
				if (nest[j] == 1) {
					for (int i = 0; i < restricciones.length; i++) {
						if (restricciones[i][j] == 1) {
							if (aListW.get(i) >= 2) { //si la fila tiene más de dos alternativas, se guarda su índice
								aNumRow.add(i);			//agrega el número de la fila al arreglo
								bComp = true;
							} else {
								bComp = false;
								break;
							}
						}
					}

					if (bComp) {
						for (Integer i : aNumRow) {//para todas aquellas filas que tenían más de una solución, se les resta una solución
							aListW.set(i, aListW.get(i) - 1);
						}
						nest[j] = 0;//y el valor del nido se deja en cero (chanchamente a cero)
						//System.out.print("cambiando el valor del nido en la posicion:" +j+" \n");

					}
				}
			}
		}
		return nest;
	}
	/**
	 * Funcion que deja el valor del fitnes en 10^10
	 * @param fitness
	 */
	private void inicializacionFitness(float fitness[]) {
		for (int i = 0; i < this.cantNidos; i++) {
			fitness[i] = (float) Math.pow((double) 10, (double) 10);
		}
	}

	private static double erf(double z) {

		double q = 1.0 / (1.0 + 0.5 * Math.abs(z));

		double ans = 1 - q * Math.exp(-z * z - 1.26551223
				+ q * (1.00002368
						+ q * (0.37409196
								+ q * (0.09678418
										+ q * (-0.18628806
												+ q * (0.27886807
														+ q * (-1.13520398
																+ q * (1.48851587
																		+ q * (-0.82215223
																				+ q * (0.17087277))))))))));

		return z >= 0 ? ans : -ans;
	}
	
	private float[][] transferFunctionPoblacion(float[][] decimalNest, Random rnd, String tipoBinarizacion) throws FileNotFoundException, IOException {
		float[][] f = new float[cantNidos][cantidadColumnas];
		for (int i = 0; i < cantNidos; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
//				switch (tipoBinarizacion){
//					case "sshape1":
//						f[i][j] = (float) (1/(1+Math.pow(Math.E,-2*f[i][j])));
//						
//					case "sshape2":
//						f[i][j] = (float) (1 / (1 + Math.pow(Math.E, -f[i][j] )));
//						
//					case "sshape3":
//						f[i][j] = (float) (1/(1+Math.pow(Math.E,-1*f[i][j]/2)));
//						
//					case "sshape4":
//						f[i][j] = (float) (1/(1+Math.pow(Math.E,-1*f[i][j]/3)));
//			
//					case "vshape1":
//						f[i][j] = (float) Math.abs(erf((Math.sqrt(Math.PI) / 2) * f[i][j] ));
//			
//					case "vshape2":
//						f[i][j] = Math.abs((float)Math.tan(f[i][j] ));
//	//					return rnd.nextFloat() <= Math.abs((float)Math.tan(f)) ? 1:0;
//			
//					case "vshape3":
//						f[i][j] = (float) Math.abs(f[i][j]/Math.sqrt(1+Math.pow(f[i][j] , 2)));
//			
//					case "vshape4":
//						f[i][j] = (float) Math.abs(f[i][j]/Math.sqrt(1+Math.pow(f[i][j] , 2)));
//			
//					case "vshape5":
//						f[i][j] = (float) Math.abs(2/Math.PI*Math.atan(Math.PI/2*f[i][j] ));        	
//					case "basic":
//						f[i][j] = 0.5 <= f[i][j] ? 0 : 1;
//					default:
//						f[i][j] = 0;
//				}
				f[i][j] = 0.5 <= decimalNest[i][j] ? 1 : 0;
			}
		}			
		return f;

	}
	
	private double transferFunction(float f, Random rnd, String tipoBinarizacion) throws FileNotFoundException, IOException {
		
		switch (tipoBinarizacion){
			case "sshape1":
				return (1/(1+Math.pow(Math.E,-2*f)));
				
			case "sshape2":
				return (1 / (1 + Math.pow(Math.E, -f)));
				
			case "sshape3":
				return (1/(1+Math.pow(Math.E,-1*f/2)));
				
			case "sshape4":
				return (1/(1+Math.pow(Math.E,-1*f/3)));
	
			case "vshape1":
				return Math.abs(erf((Math.sqrt(Math.PI) / 2) * f));
	
			case "vshape2":
				return Math.abs((float)Math.tan(f));
//				return rnd.nextFloat() <= Math.abs((float)Math.tan(f)) ? 1:0;
	
			case "vshape3":
				return Math.abs(f/Math.sqrt(1+Math.pow(f, 2)));
	
			case "vshape4":
				return Math.abs(f/Math.sqrt(1+Math.pow(f, 2)));
	
			case "vshape5":
				return Math.abs(2/Math.PI*Math.atan(Math.PI/2*f));        	
			case "basic":
				return 0.5 <= f ? 1 : 0;
			default:
				return 0;

		}

	}

	
	private float[][] discretizaPoblacion(float x[][], Random rnd, String tipoDiscretizacion) throws FileNotFoundException, IOException {

		double alpha = 0.4;
		for (int i = 0; i < cantNidos; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				/*
				 * switch (tipoDiscretizacion){ case "standard": x[i][j] = rnd.nextFloat() <=
				 * x[i][j] ? 1 : 0; case "complement": x[i][j] = rnd.nextFloat() <= x[i][j] ?
				 * discretizacion(1-x[i][j] , rnd, "standard" ) : 0; case "staticProbability":
				 * x[i][j] = alpha >= x[i][j] ? 0 : (alpha < x[i][j] && x[i][j] <= ((1 + alpha)
				 * / 2)) ? discretizacion(x[i][j], rnd, "standard" ) : 1; case "elitist":
				 * x[i][j] = rnd.nextFloat() < x[i][j] ? discretizacion(x[i][j], rnd, "standard"
				 * ) : 0; default: x[i][j] = 0;
				 * 
				 * }
				 */
				x[i][j] =  rnd.nextFloat() <= x[i][j]  ? 1 : 0;
			}
		}
		return x;

	}
	
	private int discretizacion(double x, Random rnd, String tipoDiscretizacion) throws FileNotFoundException, IOException {
		double alpha = 0.4;
		switch (tipoDiscretizacion){
			case "standard":
				return rnd.nextFloat() <= x ? 1 : 0;
			case "complement":
				return rnd.nextFloat() <= x ? discretizacion(1-x, rnd,  "standard" ) : 0;
			case "staticProbability":
				return alpha >= x ? 0 : (alpha < x && x <= ((1 + alpha) / 2)) ? discretizacion(x, rnd, "standard" ) : 1;
			case "elitist":
				return rnd.nextFloat() < x ? discretizacion(x, rnd, "standard" ) : 0;
			default:
				return 0;

		}

	}

	/**
	 * Evalua la F.O. en todos los nidos, y devuelve el índice del nido con mejor fitnes entre todos
	 * @param nidos
	 * @param nuevosnidos
	 * @param fitness
	 * @param costos
	 * @param asd jamaz se usa...
	 * @return
	 */
	private int obtenerMejorNido(float nidos[][], float nuevosnidosDecimales[][],float nuevosNidosBinarios[][], float fitness[], int costos[], String asd) {
		int mejorposicion = -1;
		float costo = 0;
		float aux = 1000000000;
		for (int i = 0; i < this.cantNidos; i++) { //evaluando todos los "nuevos nidos" en la f.o y comparandolos con el anterior!
			for (int j = 0; j < this.cantidadColumnas; j++) {
				costo = costo + this.funcionObjetivo(nuevosNidosBinarios[i][j], j, costos);
			}

			if (costo < fitness[i]) {
				for (int k = 0; k < this.cantidadColumnas; k++) {
					nidos[i][k] = nuevosnidosDecimales[i][k];
				}
				fitness[i] = costo;
			}
			costo = 0;
		}
		for (int i = 0; i < fitness.length; i++) { //obteniendo el mejor nido
			if (aux > fitness[i]) {
				aux = fitness[i];
				mejorposicion = i;
			}
		}
		return mejorposicion;
	}
	/**
	 * Genera de manera aleatorea los  valores para los nidos, para cada valor los binariza, luego repara los valores que violan restricciones.    
	 * @param nidos
	 * @param posicionmejor
	 * @param restricciones
	 * @param costos
	 * @param fitness
	 * @param rnd
	 * @param alfa
	 * @param tipoBinarizacion
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private float[][] generarNuevasSoluciones(float nidos[][], int posicionmejor, int restricciones[][], int costos[], float fitness[], Random rnd, float alfa, String tipoBinarizacion) throws FileNotFoundException, IOException {
		int solucionCorrecta = 0;
		float nuevosnidos[][] = new float[this.cantNidos][this.cantidadColumnas];
		float beta = (float) 3 / 2;
		float sigma;
		float[] paso = new float[this.cantidadColumnas];
		float[] tamanoPaso = new float[this.cantidadColumnas];
		float[] u = new float[this.cantidadColumnas];
		float[] v = new float[this.cantidadColumnas];
		//sigma = (float) Math.pow(((Gamma.gamma((float)1+beta)*Math.sin(Math.PI*beta/2)))/(Gamma.gamma(((float)1+beta)/2)*beta*Math.pow(2,(float)(beta-1)/2)),(float)(1/beta));//(1/beta));               
		sigma = (float) 0.69657;
		for (int i = 0; i < this.cantidadColumnas; i++) {
			u[i] = (float) (rnd.nextGaussian() * sigma);
			v[i] = (float) rnd.nextGaussian();
			paso[i] = (float) (float) u[i] / (float) Math.abs(v[i]);
			paso[i] = (float) Math.pow(paso[i], 2);
			paso[i] = (float) Math.cbrt(paso[i]);
		}
		for (int i = 0; i < this.cantNidos; i++) {
			for (int j = 0; j < this.cantidadColumnas; j++) {
				float valor;
				if(i > nidos.length-1)
				{
					 valor = rnd.nextFloat();
					
				}else {
					 valor = (float) (nidos[i][j] + ((float) ((float) alfa * (float) paso[j]) * (nidos[posicionmejor][j] - nidos[i][j]) * rnd.nextGaussian()));
				}
				
				if (valor < 0) { //LOWER BOUNDS
					nuevosnidos[i][j] = 0;
				} else if (valor > 1) {//UPPER BOUNDS
					nuevosnidos[i][j] = 1;
				} else {
//					nuevosnidos[i][j] = this.discretizacion(this.transferFunction(valor, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
					nuevosnidos[i][j] = valor;
				}
				//}else{
				//    nuevosnidos[i][j] = nidos[i][j];
				//}
			}
//			this.evaluarRestriccionesYRepara(nuevosnidos[i], restricciones, costos);
		}
		return nuevosnidos;
	}
	/**
	 * similar a generarNuevasSoluciones, crea nidos nuevos de maner aleatorea, binariza luego repara.
	 * @param nidos
	 * @param posicionmejor
	 * @param restricciones
	 * @param rnd
	 * @param alfa
	 * @param costos
	 * @param tipoBinarizacion
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private float[][] nidosVacios(float nidos[][], int posicionmejor, int restricciones[][], Random rnd, float alfa, int costos[], String tipoBinarizacion) throws FileNotFoundException, IOException {
		float nuevosnidos[][] = new float[this.cantNidos][this.cantidadColumnas];
		for (int i = 0; i < this.cantNidos; i++) {
			int filaRandom1 = rnd.nextInt(this.cantNidos);
			int filaRandom2 = rnd.nextInt(this.cantNidos);


			if (rnd.nextFloat() > this.probDescubrimiento) { //si la especie dueña del nido descubre el huevo ajeno, se va a otro nido.
				for (int j = 0; j < this.cantidadColumnas; j++) {
					float valor = (float) (nidos[i][j] + ((float) rnd.nextFloat() * (nidos[filaRandom1][j] - nidos[filaRandom2][j])));
					if (valor < 0) { //LOWER BOUNDS
						nuevosnidos[i][j] = 0;
					} else if (valor > 1) {//UPPER BOUNDS
						nuevosnidos[i][j] = 1;
					} else {
//						nuevosnidos[i][j] = this.discretizacion(this.transferFunction(valor, rnd, tipoBinarizacion), rnd, tipoDiscretizacion);
						nuevosnidos[i][j] = valor;
					}
				}
//				this.evaluarRestriccionesYRepara(nuevosnidos[i], restricciones, costos);
			} else {
				for (int j = 0; j < this.cantidadColumnas; j++) {
					nuevosnidos[i][j] = nidos[i][j];
				}
			}
		}
		return nuevosnidos;
	}

	private float funcionObjetivo(float b, int dimension, int costos[]) {
		return b * costos[dimension];
	}

	private float funcionObjetivo(float nido[], int costos[]) {
		int costo = 0;
		for (int i=0; i< costos.length; i++)
			costo += nido[i]*costos[i];
		return costo;
	}
	/* funciones relacionadas al manejo de instancias y escritura de resultados
	 * en archivos planos, para utilizarlos posteriormente
	 * */
	
	/**
	 * Función que procesa línea de archivo unicos de librería OR
	 * Cada línea que lee la convierte según el formato de dicha instancia, logrando 
	 * asignar los valores a las variables de restricciones, filas, columnas y costos.
	 * @param line línea de archivo unicost a procesar
	 */
	public static void ProcesarLinea(String line) {
        String[] values = line.trim().split(" ");
        if (fs) {
            cantidadFilas = Integer.parseInt(values[0]);
            cantidadColumnas = Integer.parseInt(values[1]);
            restricciones = new int[cantidadFilas][cantidadColumnas];
            costos = new int[cantidadColumnas];
//System.out.print("numeroColumnas:" +numeroColumnas);
            fs = false;
        } else {
            if (ss) {
                for (int i = 0; i < values.length; i++) {
                    costos[cc++] = Integer.parseInt(values[i]);
                }
                ss = cc < cantidadColumnas;
            } else {
                if (rc == 0) {
                    rc = Integer.parseInt(values[0]);
                } else {
                    for (int i = 0; i < values.length; i++) {
                        restricciones[rs][Integer.parseInt(values[i]) - 1] = 1;
                        cr++;
                    }
                    if (rc <= cr) {
                        rs++;
                        rc = 0;
                        cr = 0;
                    }
                }
            }
        }
    }
	
	/**
	 * Función que inicia la lectura de archivo unicos, resive la ruta y el nombre de la instancia.
	 * @param rutaYnombreInstancia
	 */
	public static void LeeArchivoInstancia(String rutaYnombreInstancia) {
	    	fs = true;
	        ss = true;

	        rs = 0;
	        rc = 0;
	        cc = 0;
	        cr = 0;
	        BufferedReader br = null;
	        String line;

	        try {
	            br = new BufferedReader(new FileReader(rutaYnombreInstancia));
	            while ((line = br.readLine()) != null) {
	                ProcesarLinea(line);
	            }
	        } catch (IOException ex) {
	            logger.log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (br != null) {
	                    br.close();
	                }
	            } catch (IOException ex) {
	                logger.log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	  
	
	 private static String Write(int b, Object o) {
	        String line = "";
	        for (int i = 0; i < b - String.valueOf(o).length(); i++) {
	            line += " ";
	        }
	        return (line + o + " |");
	    }
	/**
	 * escribe los resultados de cada ejecución en @filename	
	 * @param ejecucion es el número de la ejecucion
	 * @param fileName es el archivo dónde se escriben los resultados
	 * @param knowFitInstancia es el mejor resultado conocido de dicha instancia
	 * @param nombreInstancia es el nombre del archivo OR utilizado
	 */
	private static void Save(int ejecucion, int iteracion, String fileName, int knowFitInstancia, String nombreInstancia) {
//			|Nº EJECUCION | NIDOS | BINARIZACION  |ITERACIONES|  SEMILLA     | MEJOR FITNESS |    FITNESS FOUND| TIME |\n");
	        BufferedWriter bufferedWriter = null;
	        File file;
	        String line = "|";

	        try {
	            file = new File(fileName);
	            boolean exists = file.exists();

	            if (!exists) {
	                (new File(file.getParent())).mkdirs();
	            }

	            bufferedWriter = new BufferedWriter(new FileWriter(file, true));

	            try (PrintWriter pw = new PrintWriter(bufferedWriter)) {
	                if (!exists) {
	                    line += Write(2, "#") + 
	                    		Write(9, "#ITER") + 
	                    		Write(9, "#IterMAX") + 
	                    		Write(5, "Nest") + 
	                    		Write(11, "%Desc") + 
	                    		Write(13, "semilla") + 
	                    		Write(8, "BestFit");
	                    
	                    line += Write(8, "GottedFit") + 
	                    		Write(5, "RDP") +
	                    		Write(11, "instancia") + 
	                    		Write(5, "time");
	                    pw.println(line);
	                    line = "";
	                    for (int i = 0; i < 97; i++) {
	                        line += "-";
	                    }
	                    pw.println(line);
	                    line = "|";
	                }

	                line += Write(2, ejecucion) +  
	                		Write(5, iteracion) + 
	                		Write(5, numIteraciones) +
	                		Write(5, cantNidos) + 
	                		Write(11, probDescubrimiento) + 
	                		Write(13, semilla) + 
	                		Write(8, knowFitInstancia) +
	                		Write(8, bestFit) +
	                		Write(5, String.format("%.2f", (bestFit- knowFitInstancia)/knowFitInstancia*100));
	                line += Write(11, nombreInstancia) +  
	                		Write(5, (String.format("%.2g%n", (System.currentTimeMillis()-tiempoInicioEjecucion) / (1000f))).trim());
	                pw.println(line);
	            }
	        } catch (IOException ex) {
	            logger.log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bufferedWriter != null) {
	                    bufferedWriter.close();
	                }
	            } catch (IOException ex) {
	                logger.log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	
	@Override
	public String toString() {
		return ("Numero de nidos:" + this.cantNidos + "\n" + "Numero de dimensiones:" + this.cantidadColumnas + "\n" + "Operador probabilidad:" + this.probDescubrimiento + "\n" + "Numero de Iteraciones:" + this.numIteraciones + "\n");
	}

	private int[][] clusterizaDbscanSoluciones(float[][] poblacionSoluciones, float[] vectoFitnessSoluciones )
	{
		
		Map <Integer, List<Punto>> mClusterPuntos = new HashMap <Integer, List<Punto>>();
		Map <Integer, Float> mPromFitnessCluster = new HashMap <Integer, Float>();
		ArrayList<Punto> puntosRuido = new ArrayList<Punto>();
		
		int[][] solucionClusterizada = new int[poblacionSoluciones.length][cantidadColumnas];
		List<Punto> puntos = new ArrayList<Punto>();
//		float pesoTransicion = 0.2F;

//		 System.out.print("\nCantidad de elementos en la solucion: "+ solucion.length);
//		for (int fil = 0; fil< poblacionSoluciones.length; fil++) {
//
//			Float[] solucion = new Float[cantidadColumnas];
//			for (int col = 0; col< poblacionSoluciones[0].length; col++) 
//				solucion[col] = poblacionSoluciones[fil][col];				
//			
//			Punto p = new Punto(solucion, fil, 0, vectoFitnessSoluciones[fil]);
//			puntos.add(p);
//			
//		}
		for  (int col = 0; col< poblacionSoluciones[0].length; col++) {

			Float[] dimensionSolucion = new Float[poblacionSoluciones.length];
			for (int fil = 0; fil< poblacionSoluciones.length; fil++)
				dimensionSolucion[fil] = poblacionSoluciones[fil][col];				
			
			Punto p = new Punto(dimensionSolucion, 0, col); //ya no le pasamos el fitness, pues ahora no va el vector solucion, sin o que el vector de la dimension
			puntos.add(p);
			
		}
		int cantPuntos = puntos.size();
		
		if (cantPuntos < 2 ) {
			System.out.print("\n Nada que clusterizar, retornando\n");
			return solucionClusterizada;
		}
		
//		int minElementosEnCluster = (int) (puntos.size()*0.1f/100);
		// Se sugiere setear el valor del minElemen como LOG(n);
		int minElementosEnCluster = 3;
//		int minElementosEnCluster = (int) Math.log10(cantPuntos);
//		System.out.println(minElementosEnCluster+" es el Log(cantPuntos)");
//        double maxDistance = (double)(puntos.size()*0.0000095d/100);
//        double epsilonValue = 5.0d;
		double epsilonValue = getEpsilonValueKnn(minElementosEnCluster, puntos);

        
        
        DBSCANClusterer<Punto> clusterer = null;
        try {
            clusterer = new DBSCANClusterer<Punto>(puntos, minElementosEnCluster, epsilonValue, new DistanceMetricPunto());
        } catch (DBSCANClusteringException e1) {
            System.out.println("Should not have failed on instantiation: " + e1);
        }
        
        ArrayList<ArrayList<Punto>> result = null;
        
        try {
    		System.out.print("\n");

            result = clusterer.performClustering(); //-->clusteriza los puntos
            System.out.print("\n");
            puntosRuido = clusterer.getNoiseValues(); //obtiene puntos ruido
            probDescubrimiento = (float)puntosRuido.size()/cantPuntos;
    		System.out.println("Probabilidad de descubrimiento seteada en: " +probDescubrimiento);

//            int conteoCluster = 0, sumFitCluster;

          //Recorremos los clusteres para obtener el promedio y asignar peso
//            for (ArrayList<Punto> arrayPnt: result) {
//            	conteoCluster++;
//            	sumFitCluster = 0;
//            	mClusterPuntos.put(conteoCluster, arrayPnt);
//            	for (int dim = 0; dim < arrayPnt.size(); dim++) {
//            		sumFitCluster += arrayPnt.get(dim).getFitnessSolucion();
//            	}
//            	mPromFitnessCluster.put(conteoCluster, (float)sumFitCluster/arrayPnt.size());
//            }
//            conteoCluster++;
//        	sumFitCluster = 0;
//            for(Punto pntRuido:puntosRuido){
//            	sumFitCluster += pntRuido.getFitnessSolucion();
//            	
//            }
//        	mPromFitnessCluster.put(conteoCluster, (float)sumFitCluster/puntosRuido.size());
//
//            //mostramos los promedios para análisis:
//            for(Map.Entry<Integer, Float> entry: mPromFitnessCluster.entrySet())
//    		{
//            	if (entry.getKey() == conteoCluster) {
//            		System.out.println("cluster: " + entry.getKey() + " Promedio Fitness: "+ entry.getValue()+" Cant Puntos: "+puntosRuido.size()+"\n");
//            		System.out.println("Fitness en cluster:\n");
//            		for(Punto pntRuido:puntosRuido){
//            			System.out.println(pntRuido.getFitnessSolucion()+" ");
//                    }
//            		
//            	}else {
//            		System.out.println("cluster: " + entry.getKey() + " Promedio Fitness: "+ entry.getValue()+" Cant Puntos: "+mClusterPuntos.get(entry.getKey()).size()+"\n");
//            		System.out.println("Fitness en cluster:\n");
//            		for (ArrayList<Punto> arrayPnt: result) {
//                    	
//                    	for (int dim = 0; dim < arrayPnt.size(); dim++) {
//                    		System.out.println(arrayPnt.get(dim).getFitnessSolucion()+" ");
//                    	}
//                    }
//            	}
//
//    		}
            
        } catch (DBSCANClusteringException e) {
            System.out.println("Should not have failed while performing clustering: " + e);
            
        }
		return solucionClusterizada;
	}
	
	private double getEpsilonValueKnn(int kValue, List<Punto> puntos) {
		//Copiamos los puntos a evaluar para compararlos entre si
		List<Punto> lPuntosRecorrer = new ArrayList<>(puntos);
		double meanTotalPuntos = 0;
		
		for(Punto ptoOrigen:puntos)
		{
			//creamos una lista que contendrá las distancias del punto evaluado con el resto
			List<Double> distancias = new ArrayList<Double>();
			
			for(Punto ptoDestino:lPuntosRecorrer){
				if (!ptoOrigen.equals(ptoDestino))
					distancias.add(ptoOrigen.distanciaEuclideana(ptoDestino));
			}
			//Ordenamos la lista de menor a mayor, para sacar el promedio de los k primeros elementos
			Collections.sort(distancias);
			//calculamos el promedio de los k-primeros valores
			double meanPuntoOrigen = 0;
			
			for(int i = 0; i<kValue; i++) {
				meanPuntoOrigen += distancias.get(i);
//				System.out.println(meanPuntoOrigen + "");
			}
			meanPuntoOrigen = meanPuntoOrigen/kValue;
			
			//sumamos el promedio del punto al promedio total
			meanTotalPuntos += meanPuntoOrigen;
		}
		meanTotalPuntos = meanTotalPuntos/puntos.size();
		System.out.println("kvalue: "+ meanTotalPuntos);
		return 	meanTotalPuntos;
	}

	@Override
	public  void run() {
		int repActual = 1;//Integer.parseInt(args[0]);
		int posicionmejor,aux = 0, i=0, contIncrementoNidos= 1, contDecrementoNidos=1;
		float nuevosnidos[][] = null;

		float alfa = (float) 0.01;
		try {
//			 System.out.print("en Clase Cuckoo:\n NumeroFilas:"+ numeroFilas +"numeroCol"+ numeroColumnas + "costos:"+costos.length);

			for (int inst =1; inst <= mNombresInstancias.size(); inst++) 
			{
//				esperarXsegundos(new Random().nextInt(5));
	            LeeArchivoInstancia("resources/input/" + mNombresInstancias.get(inst));
			 	System.out.print("Archivo: "+mNombresInstancias.get(inst)+"\nFilas: "+cantidadFilas+"\nColumnas:"+cantidadColumnas+"\n\n");
			 	System.out.print("|Nº EJEC |   ITER  | ITER MAX |  NIDOS |    PROB DESC    |  SEMILLA     | MEJOR FITNESS |   FIT. FOUND |  RDP  | TIME |\n");
	            System.out.print("|__________________________________________________________________________________________________________________________\n");
	            idArchivo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
				for (int numEjecucion = 1; numEjecucion <= 31; numEjecucion++)
				{
		
					int mejorIteracion = 0;
					Random rnd;
					rnd = new Random();
					
					tiempoInicioEjecucion = System.currentTimeMillis();
					//	        System.out.print("EXPERIMENTO ID:" + id + "\n\n\n");
					semilla = new Date().getTime();
					rnd.setSeed(semilla);
					
					
					probDescubrimiento =probDescubrimientoInicial ; 
					cantNidos = cantNidosInicial;
					
					float fitness[] = new float[cantNidos];
		
					nidosDecimales = this.inicializacionSoluciones(); //Inicializar nidos o soluciones || con valores aleatoreos 
					/******************************************************************************
					 * 	INICIO SCP operators: valores a binarios y eurística de reparación de SCP
					 ******************************************************************************/
					//aplicamos funcion de transferencia
					nidosBinarios = transferFunctionPoblacion(nidosDecimales, rnd, tipoBinarizacion);
					//reparamos solucion
					for(int fila = 0; fila <nidosDecimales.length;fila++)
						nidosBinarios[fila] = evaluarRestriccionesYRepara(nidosBinarios[fila], restricciones, costos);

					
					inicializacionFitness(fitness); //Inicializar el vector con las mejores soluciones para los nidos i-ésimos || laprimera vez todos los fitness en 10^10
					posicionmejor = this.obtenerMejorNido(nidosDecimales, nidosDecimales, nidosBinarios, fitness, costos, null);
					//hasta aquí solo inicializó la primera vez de evaluaciones, con valores aleatorios y fitness altísimos para poder dejar los valores iniciales.
		
		
					int auxPosicion = posicionmejor;
					bestFit = fitness[posicionmejor];
					i = 1;
		
					
					while (i <= numIteraciones) { //Aqui comienza a iterar el algoritmo CS.
						nuevosnidos = generarNuevasSoluciones(nidosDecimales, posicionmejor, restricciones, costos, fitness, rnd, alfa, tipoBinarizacion); //Aqui se generarán nuevas soluciones aplicando LF                                                          
						bestFitAnterior = bestFit;

						/******************************************************************************
						 * 	INICIO SCP operators: valores a binarios y eurística de reparación de SCP
						 ******************************************************************************/
						//aplicamos funcion de transferencia
						nidosBinarios = transferFunctionPoblacion(nuevosnidos, rnd, tipoBinarizacion);
						//aplicamos eurística para reparar solucion
						for(int fila = 0; fila <nuevosnidos.length;fila++)
							nidosBinarios[fila] = evaluarRestriccionesYRepara(nidosBinarios[fila], restricciones, costos);
						/*****************************************************************************
						 * 	FIN SCP operators: valores a binarios y eurística de reparación de SCP
						 *****************************************************************************/
						
						posicionmejor = obtenerMejorNido(nidosDecimales, nuevosnidos, nidosBinarios, fitness, costos, "levy");
						
		
						if (posicionmejor != auxPosicion && fitness[posicionmejor] != bestFit) {
							auxPosicion = posicionmejor;
							bestFit = fitness[posicionmejor];
							mejorIteracion = i;
						}
						
						nuevosnidos = nidosVacios(nidosDecimales, posicionmejor, restricciones, rnd, alfa, costos, tipoBinarizacion);
						
						/******************************************************************************
						 * 	INICIO SCP operators: valores a binarios y eurística de reparación de SCP
						 ******************************************************************************/
						//aplicamos funcion de transferencia
						nidosBinarios = transferFunctionPoblacion(nuevosnidos, rnd, tipoBinarizacion);
						//reparamos solucion
						for(int fila = 0; fila <nuevosnidos.length;fila++)
							nidosBinarios[fila] = evaluarRestriccionesYRepara(nidosBinarios[fila], restricciones, costos);
						/*****************************************************************************
						 * 	FIN SCP operators: valores a binarios y eurística de reparación de SCP
						 *****************************************************************************/
						
						posicionmejor = obtenerMejorNido(nidosDecimales, nuevosnidos, nidosBinarios, fitness, costos, "p(a)");
						if (posicionmejor != auxPosicion && fitness[posicionmejor] != bestFit) {
							auxPosicion = posicionmejor;
							bestFit = fitness[posicionmejor];
							mejorIteracion = i;
						}
						/*******************************************************************
						 * APLICAMOS ML PARA RESTABLECER PARAMETROS
						 ******************************************************************/
						if (i%100 == 0)
						{
							System.out.print( "\n");

							clusterizaDbscanSoluciones(nidosDecimales,  fitness);
							if (probDescubrimiento == 0)
								probDescubrimiento = 0.25f;
							else if(probDescubrimiento >0.4f)
								probDescubrimiento = 0.45f;
							else if (probDescubrimiento <0.1f)
								probDescubrimiento = 0.1f;
							
							System.out.print("|   " +String.format("%02d", numEjecucion)+"   |   " +String.format("%04d", i)+"  |   " + numIteraciones +"   |    " + cantNidos + "  |    " + String.format("%.8f", probDescubrimiento)
									+"   |"+ semilla + " |     "+     mBestFistInstancias.get(inst)     
									+"       |      "+ (int)bestFit+ "     |  "+ String.format("%.2f", (bestFit- mBestFistInstancias.get(inst))/mBestFistInstancias.get(inst)*100)
									+ " |  " + (float)(System.currentTimeMillis()-tiempoInicioEjecucion)/1000+"|\n");
							/* guardamos resultados en archivo plano*/
			                Save(numEjecucion, i,
			                		"resources/output/Salida_DBSCAN_autoPA_"+idArchivo +"_" + mNombresInstancias.get(inst),
			                		mBestFistInstancias.get(inst), 
			                		mNombresInstancias.get(inst) );
			                if(bestFit == bestFitAnterior)
			                {
			                	//incrementamos el contador para aumentar cont
			                	contIncrementoNidos++;
			                }else if(bestFit > bestFitAnterior)
			                {
			                	//incrementamos contador para achicar pob
			                	contDecrementoNidos++;
			                }
			                
			                if(contIncrementoNidos == 4) {
			                	cantNidos +=5; contIncrementoNidos =0;
			                	
			                	float fitnessNuevos[] = new float[cantNidos];
			                	System.arraycopy(fitness, 0, fitnessNuevos, 0, fitness.length);
			                	for (int col = fitness.length; col < cantNidos; col++)
			                		fitnessNuevos[col] = (float) Math.pow((double) 10, (double) 10);
			                	fitness = new float[cantNidos];
			                	fitness = fitnessNuevos.clone();
			                	
			                	
			                	float nuevosNidosFloat[][] = new float[cantNidos][cantidadColumnas];
			                	for (int fila = 0; fila<nidosDecimales.length; fila++) {
			                		System.arraycopy(nidosDecimales[fila], 0, nuevosNidosFloat[fila], 0, cantidadColumnas);
			                	}
			                	nidosDecimales = new float[cantNidos][cantidadColumnas];
			                	nidosDecimales = nuevosNidosFloat.clone();
			                	
			                	
			                }
			                if(contDecrementoNidos == 2 && cantNidos >=15)
			                	cantNidos -=5; contDecrementoNidos = 0;
						}
						if (i==1){
							System.out.print("|   " +String.format("%02d", numEjecucion)+"   |   " +String.format("%04d", i)+"  |   " + numIteraciones +"   |    " + cantNidos + "  |    " + String.format("%.8f", probDescubrimiento)
							+"   |"+ semilla + " |     "+     mBestFistInstancias.get(inst)     
							+"       |      "+ (int)bestFit+ "     |  "+ String.format("%.2f", (bestFit- mBestFistInstancias.get(inst))/mBestFistInstancias.get(inst)*100)
							+ " |  " + (float)(System.currentTimeMillis()-tiempoInicioEjecucion)/1000+"|\n");
							/* guardamos resultados en archivo plano*/
			                Save(numEjecucion, i,
			                		"resources/output/Salida_DBSCAN_autoPA_"+idArchivo +"_" + mNombresInstancias.get(inst),
			                		mBestFistInstancias.get(inst), 
			                		mNombresInstancias.get(inst) );
						}
						
						/*******************************************************************
						 * FIN: APLICAMOS ML PARA RESTABLECER PARAMETROS
						 ******************************************************************/
						if(bestFit <= mBestFistInstancias.get(inst)) {
							numEjecucion=31;break;}
						i++;
					}
					
					//termino de iteraciones, validando y mostrando resultados
					float fit = 0;
					for (i = 0; i < cantidadColumnas; i++) {
						fit = fit + funcionObjetivo(nidosBinarios[posicionmejor][i], i, costos);
					}
					//	        System.out.print("\n\nMEJOR FITNESS:" + fit + " EN LA POSICION:" + posicionmejor + "\n");
					//	        for (i = 0; i < CS.num_dimensiones; i++) {
					//	            System.out.print(nidos[posicionmejor][i] + "\t");
					//	        }
					aux = 0;
					int sumatoria = 0;
					//	        System.out.print("\n\nCOMPROBANDO RESTRICCIONES...\n");
					for (i = 0; i < restricciones.length; i++) {
						for (int j = 0; j < cantidadColumnas; j++) {
							if (restricciones[i][j] == 1 && nidosBinarios[posicionmejor][j] == 1) {
								sumatoria++;
								break;
							}
						}
					}
					if (sumatoria == restricciones.length) {
						//	            System.out.print("\n\nTODAS LAS RESTRICCIONES HAN SIDO CUBIERTAS, TOTAL:" + restricciones.length + "\n");
					} else {
						System.out.print("\n\nERROR EN PASO DE UNA O MAS RESTRICCION\n");
					}
					tiempoTermino = System.currentTimeMillis();
					System.out.print("|   " +String.format("%02d", numEjecucion)+"   |   " +String.format("%04d", numIteraciones)+"  |   " + numIteraciones +"   |    " + cantNidos + "  |    " + String.format("%.8f", probDescubrimiento)
									+"   |"+ semilla + " |     "+     mBestFistInstancias.get(inst)     
									+"       |      "+ (int)bestFit+ "     |  "+ String.format("%.2f", (bestFit- mBestFistInstancias.get(inst))/mBestFistInstancias.get(inst)*100)
									+ " |  " + (float)(System.currentTimeMillis()-tiempoInicioEjecucion)/1000+"|\n");
					
					/* guardamos resultados en archivo plano*/
					Save(numEjecucion, i,
	                		"resources/output/Salida_DBSCAN_autoPA_"+idArchivo +"_" + mNombresInstancias.get(inst),
	                		mBestFistInstancias.get(inst), 
	                		mNombresInstancias.get(inst) );
	                
				}
				System.out.println("Ejecuciones terminadas en: "+ (tiempoTermino-tiempoInicioGlobal)/3600000 +":"+ ((tiempoTermino-tiempoInicioGlobal)%3600000)/60000 +":"+ ((tiempoTermino-tiempoInicioGlobal)%3600000)%60000 );
			}

			//CS.generarExcel(numerofilas,numerocolumnas,numeroIteraciones,fitness[posicionmejor],fitness[posicionPeor],fBinarizacion,id,archivo.getName().replace(".txt", ""),repActual,tiempoInicio,mejorIteracion);
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

	private void esperarXsegundos(int segundos) {
		try {
			System.out.println("Esperando por"+(segundos*1000)+"\n");
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}
