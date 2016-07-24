import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
 
public class SeparaEstadosPac {
 
  public static void main(String[] args) throws IOException {
 
	SeparaEstadosPac obj = new SeparaEstadosPac();
	
	/*try {
		obj.limpaErroQuotePontoVirgula(new File("/Users/Omar/Desktop/Dados PAC/"));
		}
		catch (Exception e){
			e.printStackTrace();
		}*/
	
	obj.listFilesForFolder(new File("/Users/Omar/Desktop/Dados PAC"));
	
	
	
  }
 
  public void separaEstados(File file) {
 
	String[] line;

	String id = "";
	String estados = "";
	String[] siglas ;
	String modeloEntrada = "?1;?tipo;?nome;?2;?3;?4;?5";
	String entrada = "" ;
	
	try {
		//criando leitor do csv
		CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()),';');
		
		//criando escritor de csv em arquivo temporario
		File tempFile = File.createTempFile("buffer", ".tmp"); 
	    CSVWriter writer = new CSVWriter(new FileWriter(tempFile), ';', CSVWriter.NO_QUOTE_CHARACTER);
		
	    line = reader.readNext(); //le e escreve cabecalho
	    writer.writeNext(line);
		while ((line = reader.readNext()) != null) {
			
			id = line[0];
			estados = line[6];
			siglas = estados.split(" ");
			entrada = modeloEntrada;
			
			//Tratamento
			if(estados.isEmpty())
				continue;
			
			if(estados.length() <= 2){
				writer.writeNext(line);
			}
			else {
						
				//REGRA: Se antes ou depois estiverem preenchidos, TOTAL nao sera preenchido
				//		 se total estiver preenchidos, outros sao vazios
				
				String antes, depois , total;
				double valAntes[] = new double[siglas.length];
				double valDepois[] = new double[siglas.length];
				double valTotal[] = new double[siglas.length];
				boolean usarTotal = false;
				antes = line[3];
				depois = line[4];
				total = line[5];
				StringBuilder sb = new StringBuilder();
				List<String> municipios = Arrays.asList(line[7].split(","));
				
				
				
				//Testar se val antes e depois estao em revisao
				if(!antes.isEmpty())	
					if(isNumeric(antes)){
						Arrays.fill(valAntes, (int)Double.parseDouble(antes) / siglas.length);
						valAntes[0] = valAntes[0] + Double.parseDouble(antes) % siglas.length;
					}
				
				if(!depois.isEmpty())
					if(isNumeric(depois)){
						Arrays.fill(valDepois,(int)Double.parseDouble(depois)/ siglas.length);
						valDepois[0] = valDepois[0] + Double.parseDouble(depois) % siglas.length;
					}
				

				if(!total.isEmpty())
					if(isNumeric(total)){
						Arrays.fill(valTotal,(int)Double.parseDouble(total) / siglas.length);
						valTotal[0] = valTotal[0] + Double.parseDouble(total) % siglas.length;
						usarTotal = true;
					}
				
				
				//Linha inicial eh toda copiada com algumas modificacoes; linha editada para proximas linhas
				
				
				//cria linhas extras para cada pais
				for(int i = 0;i<siglas.length;i++){
					sb = new StringBuilder();
		
					for (String par : municipios){
						if(par.contains(siglas[i])){
							sb.append(par.substring(0,par.lastIndexOf('/')));
							sb.append(",");
						}	
					}
					if(sb.length()>0)
						sb.deleteCharAt(sb.length()-1);
					
					if(i == 0){
						if(!usarTotal){
						line[3] = antes.isEmpty()?"":isNumeric(antes)?valAntes[0]+"":"em revisao";
						line[4] = depois.isEmpty()?"":isNumeric(depois)?valDepois[0]+"":"em revisao";
						line[5] = valTotal[0]==0?"":valTotal[0]+"";
						line[6] = siglas[0];
						}
						else{
							line[3] = "";
							line[4] = "";
							line[5] = valTotal[0]+"";
							line[6] = siglas[0];
						}
						line[7] = sb.toString();
						writer.writeNext(line);
						continue;
					}
					
					entrada = modeloEntrada.replace("?1", id);
					entrada = entrada.replace("?5", siglas[i]);
					
					if(!usarTotal){
						entrada = entrada.replace("?2", antes.isEmpty()?"":isNumeric(antes)?valAntes[i]+"":"em revisao");
						entrada = entrada.replace("?3",  depois.isEmpty()?"":isNumeric(depois)?valDepois[i]+"":"em revisao");
						entrada = entrada.replace("?4", "");
						
					}else{
						entrada = entrada.replace("?2", "");
						entrada = entrada.replace("?3", "");
						entrada = entrada.replace("?4", valTotal[i]+"");
					}
					
					entrada = entrada.replace("?tipo",line[1]);
					entrada = entrada.replace("?nome",line[2]);
					entrada = entrada + ";" + sb.toString();		
					writer.writeNext(entrada.split(";"));
							
				}
			}
		
		}
		writer.close();
		reader.close();
		
		String arquivo = file.getAbsolutePath().substring(0, file.getAbsolutePath().length()-4) + "_editado.csv";
		File novo = new File(arquivo);
		//if (file.exists()) 
	    //	 file.delete();
   	
	    tempFile.renameTo(novo);
	    
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ArrayIndexOutOfBoundsException e){
		String t = e.getMessage();
		e.printStackTrace();
	} catch (StringIndexOutOfBoundsException e){
		String t = e.getMessage();
		e.printStackTrace();
	}finally{
		System.out.println(file.getName() + "done");
	}
  }
  
  public void listFilesForFolder(final File folder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	
	       	     separaEstados(new File(fileEntry.getAbsolutePath()));
	          
	            
	        }
	    }
	}

  public void limpaQuotes(File folder) throws IOException{
	  for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	limpaQuotes(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	
	        	CSVReader reader = new CSVReader(new FileReader(fileEntry.getAbsolutePath()),';');
	        	//criando escritor de csv em arquivo temporario
	        	File tempFile = File.createTempFile("buffer", ".tmp"); 
	        	System.out.println(tempFile.getAbsolutePath());
	            CSVWriter writer = new CSVWriter(new FileWriter(tempFile), ';', CSVWriter.NO_QUOTE_CHARACTER);
	        	
	            String line[] = reader.readNext(); //le e escreve cabecalho
	            writer.writeNext(line);
	        	while ((line = reader.readNext()) != null) {
	        		
	        		line[2] = line[2].replace("'", "");
	        		line[2] = line[2].replace("\"", "");
	        		writer.writeNext(line);
	        		
	        		
	        	}
	        	writer.close();
	        	reader.close();
	        	if (fileEntry.exists()) 
	        		fileEntry.delete();
	        	
	            tempFile.renameTo(fileEntry);
	          
	            
	        }
	    }
  }
  
  
  public void limpaErroQuotePontoVirgula(File folder) throws IOException{
	  for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	
	       	   
	    File tempFile = File.createTempFile("buffer", ".tmp");
	    FileWriter fw = new FileWriter(tempFile);
	    
	    System.out.println("Temp file : " + tempFile.getAbsolutePath());

	    Reader fr = new FileReader(fileEntry);
	    BufferedReader br = new BufferedReader(fr);
	    Pattern pattern = Pattern.compile("\\d;\"[A-Z]");
	    Pattern pattern2 = Pattern.compile("\";");
	    Matcher matcher ,matcher2;
	    int count =0;
	    
	    while(br.ready()) {   	
	    		String line = br.readLine();
	    		matcher =  pattern.matcher(line);
	    	    matcher2 = pattern2.matcher(line);
	    	    
	    		if(matcher.find()&&matcher2.find()){
	    			
	    			String editing = line.substring(matcher.end()-2, matcher2.start()+1);
	    			editing = editing.replace(';', ',');
	    			editing = editing.replace('"',' ');
	    			//System.out.println(editing);
	    			System.out.println(line.replace(line.substring(matcher.end()-2, matcher2.start()+1), editing));
	    			fw.write(line.replace(line.substring(matcher.end()-2, matcher2.start()+1), editing) + "\n"); 
	    			count++;
	    		}
	    		else{
	    			fw.write(line + "\n");
	    		}
	    }
	    System.out.println(count);
	    br.close();
	    fw.close();
	    if (fileEntry.exists()) 
	    	fileEntry.delete();
    	
	    tempFile.renameTo(fileEntry);
	           
	        }
	    }
	    
  }
  
  
  
  public static boolean isNumeric(String str)
  {
      return str.matches("[+-]?\\d*(\\.\\d+)?");
  }
 
  
  
}