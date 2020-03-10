package ncb.phd.pucv.cuckooSearch;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Main {
	public static List<String> instancias;
	public static int costos[];
	public static int restricciones[][];
	public static int numeroFilas, numeroColumnas;
	public static Map<Integer, String> mNombresInstancias = new HashMap<Integer, String>(); 
	public static Map<Integer, Integer> mBestFistInstancias = new HashMap<Integer, Integer>();
	public static Map<Integer, String> mTiposDeBinarizacion  = new HashMap<Integer, String>(); 
	public static int cantNidos = 25;
	public static float probDescub = (float)0.25;
	public static int numeroIteraciones = 2500; 
	public static int cantInstancias = 2;
	public static String binarizacion = "standard";
	public static boolean fs, ss;
	 /**
     * {@code rows} = Filas de M {@code cols} = Columnas de M
     */
    public static int rows, cols;
    /**
     * {@code rs} = Restricciones {@code rc} = Restricciones cubiertas
     * {@code cc} = Contador de costos {@code cr} = Contador de restricciones
     * {@code fc} = Contador de fitness {@code tf} = Total fitness
     */
    public static int rs, rc, cc, cr;
    /**
	
    /**
     * {@code logger} = Log de errores
     */
    private static final Logger logger = Logger.getLogger(CuckooSearch.class.getName());
	
	public static int cargaInstancias(int cantidadInstancias)
	{
		Map<Integer, String> mInstanciasNombres = new HashMap<Integer, String>(); //lista de columnas restricciones, con su respectivo índice
		Map<Integer, Integer> mInstanciasBestFit = new HashMap<Integer, Integer>(); //lista de columnas restricciones, con su respectivo índice
        
//		mInstanciasNombres.put(1,"mscp46.txt"); mInstanciasBestFit.put( 1,560);
//		mInstanciasNombres.put(1,"mscpc5.txt"); mInstanciasBestFit.put( 1,215);
		int cantInstancia = 1;
		mInstanciasNombres.put(cantInstancia,"mscp41.txt"); mInstanciasBestFit.put( cantInstancia,429);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp42.txt"); mInstanciasBestFit.put( cantInstancia,512);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp43.txt"); mInstanciasBestFit.put( cantInstancia,516);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp44.txt"); mInstanciasBestFit.put( cantInstancia,494);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp45.txt"); mInstanciasBestFit.put( cantInstancia,512);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp46.txt"); mInstanciasBestFit.put( cantInstancia,560);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp47.txt"); mInstanciasBestFit.put( cantInstancia,430);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp48.txt"); mInstanciasBestFit.put( cantInstancia,492);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp49.txt"); mInstanciasBestFit.put( cantInstancia,641);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp410.txt"); mInstanciasBestFit.put( cantInstancia,514);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscp51.txt"); mInstanciasBestFit.put( cantInstancia,253);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp52.txt"); mInstanciasBestFit.put( cantInstancia,302);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp53.txt"); mInstanciasBestFit.put( cantInstancia,226);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp54.txt"); mInstanciasBestFit.put( cantInstancia,242);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp55.txt"); mInstanciasBestFit.put( cantInstancia,211);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp56.txt"); mInstanciasBestFit.put( cantInstancia,213);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp57.txt"); mInstanciasBestFit.put( cantInstancia,293);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp58.txt"); mInstanciasBestFit.put( cantInstancia,288);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp59.txt"); mInstanciasBestFit.put( cantInstancia,279);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp510.txt"); mInstanciasBestFit.put( cantInstancia,265);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp61.txt"); mInstanciasBestFit.put( cantInstancia,138);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp62.txt"); mInstanciasBestFit.put( cantInstancia,146);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp63.txt"); mInstanciasBestFit.put( cantInstancia,145);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp64.txt"); mInstanciasBestFit.put( cantInstancia,131);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscp65.txt"); mInstanciasBestFit.put( cantInstancia,161);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpa1.txt"); mInstanciasBestFit.put( cantInstancia,253);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpa2.txt"); mInstanciasBestFit.put( cantInstancia,252);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpa3.txt"); mInstanciasBestFit.put(cantInstancia,232);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpa4.txt"); mInstanciasBestFit.put(cantInstancia,234);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpa5.txt"); mInstanciasBestFit.put( cantInstancia,236);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpb1.txt"); mInstanciasBestFit.put( cantInstancia,69);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpb2.txt"); mInstanciasBestFit.put( cantInstancia,76);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpb3.txt"); mInstanciasBestFit.put( cantInstancia,80);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpb4.txt"); mInstanciasBestFit.put(cantInstancia,79);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpb5.txt"); mInstanciasBestFit.put( cantInstancia,72);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpc1.txt"); mInstanciasBestFit.put( cantInstancia,227);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpc2.txt"); mInstanciasBestFit.put( cantInstancia,219);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpc3.txt"); mInstanciasBestFit.put( cantInstancia,243);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpc4.txt"); mInstanciasBestFit.put( cantInstancia,219);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpc5.txt"); mInstanciasBestFit.put( cantInstancia,215);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpd1.txt"); mInstanciasBestFit.put( cantInstancia,60);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpd2.txt"); mInstanciasBestFit.put( cantInstancia,66);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpd3.txt"); mInstanciasBestFit.put( cantInstancia,72);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpd4.txt"); mInstanciasBestFit.put( cantInstancia,62);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpd5.txt"); mInstanciasBestFit.put(cantInstancia,61);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnre1.txt"); mInstanciasBestFit.put( cantInstancia,29);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnre2.txt"); mInstanciasBestFit.put( cantInstancia,30);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnre3.txt"); mInstanciasBestFit.put( cantInstancia,27);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnre4.txt"); mInstanciasBestFit.put( cantInstancia,28);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnre5.txt"); mInstanciasBestFit.put( cantInstancia,28);
//		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrf1.txt"); mInstanciasBestFit.put( cantInstancia,14);
		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrf2.txt"); mInstanciasBestFit.put( cantInstancia,15);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrf3.txt"); mInstanciasBestFit.put( cantInstancia,14);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrf4.txt"); mInstanciasBestFit.put( cantInstancia,14);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrf5.txt"); mInstanciasBestFit.put( cantInstancia,13);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrg1.txt"); mInstanciasBestFit.put( cantInstancia,176);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrg2.txt"); mInstanciasBestFit.put( cantInstancia,154);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrg3.txt"); mInstanciasBestFit.put( cantInstancia,166);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrg4.txt"); mInstanciasBestFit.put( cantInstancia,168);
//		cantInstancia++;
//		mInstanciasNombres.put(cantInstancia,"mscpnrg5.txt"); mInstanciasBestFit.put( cantInstancia,168);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrh1.txt"); mInstanciasBestFit.put( cantInstancia,63);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrh2.txt"); mInstanciasBestFit.put( cantInstancia,63);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrh3.txt"); mInstanciasBestFit.put( cantInstancia,59);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrh4.txt"); mInstanciasBestFit.put( cantInstancia,58);
		cantInstancia++;
		mInstanciasNombres.put(cantInstancia,"mscpnrh5.txt"); mInstanciasBestFit.put( cantInstancia,55);
//        
		System.out.println("Instancias cargadas: "+cantInstancia);
        
        for (int i=1;i<=cantidadInstancias;i++) {
        	mBestFistInstancias.put(i, mInstanciasBestFit.get(i));
        	mNombresInstancias.put(i,  mInstanciasNombres.get(i));
        }
        	
		return 0;
	}
	
	public static int cargaDatosDeInstancia(String rutaYnombreInstancia) throws FileNotFoundException, IOException
	{
		System.out.print("COMENZANDO A LEER EL ARCHIVO...\n\n");
        String ruta = rutaYnombreInstancia;
        
        String linea = "";
        List<String> elementos = null;
        File archivo = new File(ruta);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        int numElem = 0, i = 0,  k = 0, auxFichero = 0, flag = 0, aux=0;
//        
//        //LECTURA FICHEROS UNICOST
//        
        try {
	        while (aux == 0) { //leyendo dimensiones de la matriz de restriccion.
	            linea = br.readLine().trim();
	            elementos = Arrays.asList(linea.split(" "));
	            numeroFilas = Integer.parseInt(elementos.get(0));
	            numeroColumnas = Integer.parseInt(elementos.get(1));
	            costos = new int[numeroColumnas];
	            System.out.print("Archivo: "+ruta+"\nFilas: "+numeroFilas+"\nColumnas:"+numeroColumnas+"\n\n");
	            
	            while (numElem != 1) { //llenando el vector de costos asociados a cada columna/dimensión/variable.
	                linea = br.readLine().trim();
	                elementos = Arrays.asList(linea.split(" "));
	                numElem = elementos.size();
	                for (int j = i; j < costos.length && k != elementos.size() && numElem != 1; j++) {
	                    costos[j] = Integer.parseInt(elementos.get(k));
	                    k++;
	                }
	                if (numElem == 1) {
	                    flag = Integer.parseInt(elementos.get(0));
	                }
	                k = 0;
	                i += elementos.size();
	                
	            }
	            System.out.print("costos cargados OK\n");
	            k = 0;
	            linea = br.readLine().trim();
	            elementos = Arrays.asList(linea.split(" "));
	            restricciones = new int[numeroFilas][numeroColumnas];
	            System.out.print("inicio carga Restricciones...\n elementos a cargar : "+elementos+"\n");
	            System.out.print("flag: "+flag+"\nauxFichero: "+auxFichero+"\n");
	            System.out.print("flag: "+elementos.size()+"\n");
	            
	            for (i = 0; i < numeroFilas; i++) { // llenando matriz de restricciones
	                while (flag != auxFichero) {
	                    auxFichero = auxFichero + elementos.size();
	                    
	                    while (k < elementos.size()) {
	                        restricciones[i][Integer.parseInt(elementos.get(k)) - 1] = 1;
	                        k++;
	                        
	                    }
	                    k = 0;
	                    if ((linea = br.readLine()) != null) {
	                        elementos = Arrays.asList(linea.trim().split(" "));
	                    }
//	                    System.out.print(" "+flag+"!="+auxFichero+"?\n");
	                    return 0;
	                }
	                System.out.print("Fila: " +i +" cargada OK\n");
		            
	                auxFichero = 0;
	                flag = Integer.parseInt(elementos.get(0));
	                if ((linea = br.readLine()) != null) {
	                    elementos = Arrays.asList(linea.trim().split(" "));
	                }
	                for (int j = 0; j < numeroColumnas; j++) {
	                    if (restricciones[i][j] != 1) {
	                        restricciones[i][j] = 0;
	                    }
	                }
	
	            }
	            aux = 1;
	        }
	        System.out.println("Archivo cargado correctamente");
	        
            //CS.generarExcel(numerofilas,numerocolumnas,numeroIteraciones,fitness[posicionmejor],fitness[posicionPeor],fBinarizacion,id,archivo.getName().replace(".txt", ""),repActual,tiempoInicio,mejorIteracion);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return 0;
	}
	
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
    
	public static void ProcesarLinea(String line) {
	        String[] values = line.trim().split(" ");

	        if (fs) {
	            numeroFilas = Integer.parseInt(values[0]);
	            numeroColumnas = Integer.parseInt(values[1]);
	            restricciones = new int[numeroFilas][numeroColumnas];
	            costos = new int[numeroColumnas];
	            fs = false;
	        } else {
	            if (ss) {
	                for (int i = 0; i < values.length; i++) {
	                    costos[cc++] = Integer.parseInt(values[i]);
	                }
	                ss = cc < numeroColumnas;
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

	public static int cargaTiposDeBinarizaciones (int cantidadBinarizaciones)
	{
		
		Map<Integer, String> mBinarizaciones = new HashMap<Integer, String>(); //lista de columnas restricciones, con su respectivo índice
		mBinarizaciones.put(1, "sshape1");
		mBinarizaciones.put(2, "sshape2");
		mBinarizaciones.put(3, "sshape3");
		mBinarizaciones.put(4, "sshape4");
		mBinarizaciones.put(5, "vshape1");
		mBinarizaciones.put(6, "vshape2");
		mBinarizaciones.put(7, "vshape3");
		mBinarizaciones.put(8, "vshape4");
		mBinarizaciones.put(9, "vshape5");
		mBinarizaciones.put(10, "basic");
		mBinarizaciones.put(11, "standard");
		mBinarizaciones.put(12, "complement");
		mBinarizaciones.put(13, "staticProbability");
		mBinarizaciones.put(14, "elitist");

		for (int i = 1; i<=cantidadBinarizaciones; i++) {
			mTiposDeBinarizacion.put(i,  mBinarizaciones.get(i));
		}
		return 0;
	}
	
    private static String Write(int b, Object o) {
        String line = "";
        for (int i = 0; i < b - String.valueOf(o).length(); i++) {
            line += " ";
        }
        return (line + o + " |");
    }
	
	private static void Save(int ejecucion, String fileName,int cantNidos, CuckooSearch cs_scp, int knowFitInstancia, String nombreInstancia) {
//		|Nº EJECUCION | NIDOS | BINARIZACION  |ITERACIONES|  SEMILLA     | MEJOR FITNESS |    FITNESS FOUND| TIME |\n");
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
                    		Write(5, "Nest") + 
                    		Write(13, "Binarizacion") + 
                    		Write(6, "%Desc") + 
                    		Write(5, "#Iter") + 
                    		Write(13, "semilla") + 
                    		Write(8, "BestFit");
                    
                    line += Write(8, "GotFit") + 
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
                		Write(5, cantNidos) + 
                		Write(13, cs_scp.getTipoBinarizacion()) + 
                		Write(6, cs_scp.getProbDescubrimiento()) + 
                		Write(5, cs_scp.getNumIteraciones()) + 
                		Write(13, cs_scp.getSemilla()) + 
                		Write(8, knowFitInstancia) +
                		Write(8, cs_scp.getBestFit());
                line += Write(11, nombreInstancia) +  
                		Write(5, (String.format("%.2g%n", (cs_scp.getTiempoTermino()-cs_scp.getTiempoInicioEjecucion()) / (1000f))).trim());
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
	
	public static void main(String[] args) throws FileNotFoundException, IOException 
	 {
		 int cantNidos = 15;
		 float probDescub = (float)0.25;
		 int numeroIteraciones = 5000; 
//		 int cantInstancias = 65, cantTiposBinarizaciones = 14;
		 int cantInstancias = 9, cantTiposBinarizaciones = 1;
		 
//		 String binarizacion = "standard";
		 String nombreArchivoSalida = "resources/input/";
		 try {
			 
			 
		System.out.println("Instancias a procesar: "+cantInstancias);

//		 cargaTiposDeBinarizaciones(cantTiposBinarizaciones);
		 cargaInstancias(cantInstancias);

		 cargaTiposDeBinarizaciones(cantTiposBinarizaciones);
		 
//		 Scanner in = new Scanner(System.in);
//		 System.out.println("\nIngresa la Discretizacion a utilizar: ");
//		 String tipoDiscretizacion = in.nextLine();
//		 
//		 System.out.println("\nIngresa la Binarizacion a utilizar: ");
//		 String tipoBinarizacion = in.nextLine();
//		 ExecutorService exec = Executors.newFixedThreadPool(2);
		 System.out.print("inicio: "+mTiposDeBinarizacion.size()+"\n");
		 
			 CuckooSearch CS_SCP1 = new CuckooSearch(cantNidos, 
            												probDescub,  
            												numeroIteraciones,
            												mNombresInstancias,
            												mBestFistInstancias,
            												"",
            												"",
            												costos,
            												restricciones,
            												numeroFilas,
            												numeroColumnas);
			 

			  CS_SCP1.start();

		 }catch (Exception e) {
			 e.printStackTrace();
		 }
		 
    }

}
