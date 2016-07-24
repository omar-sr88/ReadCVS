

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CorrigeQuoteEmDataEID {

	static String cabecalho = "idn_empreendimento;idn_digs;dsc_titulo;val_2011_2014;val_pos_2014;investimento_total;sig_uf;txt_municipios;txt_executores;dsc_orgao;idn_estagio;dat_ciclo;dat_selecao;dat_conclusao_revisada;val_lat;val_long;emblematica;observacao";
	
	public static void main(String args[]) throws IOException{
			 
		CorrigeQuoteEmDataEID anc = new CorrigeQuoteEmDataEID();
		anc.listFilesForFolder(new File("/Users/Omar/Desktop/Dados PAC - Separados por estado"));

		
		
	}
	
	public void listFilesForFolder(final File folder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	
	        	corrigePontoEVirgulaNoTexto(new File(fileEntry.getAbsolutePath()));
	          
	            
	        }
	    }
	}
	
	public void corrigePontoEVirgulaNoTexto(File file) throws IOException {

		/*
		Reader fr = new FileReader(file);
		System.out.println(file.getName());
	    BufferedReader br = new BufferedReader(fr);
	    while(br.ready()) {   	
	    		String line = br.readLine();
	    		int num =  numberOfSemiColons(line);
	    	    if(num>17 ){
	    	    	
	    	    	int diff = num-17;
	    	    	int secondPos =0 ;
	    	    	for(int i = 0; i<line.length();i++){
	    	    		
	    	    		if(line.charAt(i)==';'){
	    	    			secondPos++;
	    	    			if(secondPos==2){
	    	    				secondPos = i;
	    	    				break;
	    	    			}
	    	    		} 	    			
	    	    	}
	    	    	
	    	    	
	    	    	StringBuilder lineB = new StringBuilder(line);
	    	    	for (int i = secondPos+1;i<line.length() && diff!=0 ;i++){
	    	    		if(line.charAt(i)==';'){
	    	    			lineB.setCharAt(i, ',');
	    	    			diff--;
	    	    		}
	    	    		
	    	    		
	    	    	}
	    	       
	    	    	
	    	    	System.out.println(line.substring(0, line.indexOf(';')+1) + " --- Numer" + num);
	    	    }
	    	    		    	     			    		
	    }
	    
	    fr.close();
		br.close();
		
		
	*/
		CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()),';');
		
		File tempFile = File.createTempFile("buffer", ".tmp"); 
		CSVWriter writer = new CSVWriter(new FileWriter(tempFile), ';', CSVWriter.NO_QUOTE_CHARACTER);
		
		String [] nextLine;
		nextLine = reader.readNext(); //le e escreve cabecalho

	    writer.writeNext(nextLine);    
	    System.out.println(file.getName());
	    int c = 0;
	    while ((nextLine = reader.readNext()) != null) {
	        //nextLine[] is an array of values from the line
	        if(!nextLine[4].isEmpty()){
	        	
	        	if(!nextLine[4].contains("em revisao") && !nextLine[4].contains("em revisão"))
	        	{
	        		
	        		try {
	        		Double.parseDouble(nextLine[4]);
	        		}
		        	catch(Exception e){
		        		System.out.println(nextLine[0] + " -----" + nextLine[4]);
		        		
		        	}
	        	}
	        }
	      if(nextLine.length>18){
	    	  c++;
	      }
	        
	        //writer.writeNext(nextLine);
	    }
	    
	    
	    //System.out.println(c);
	    reader.close();
	    writer.close();
	    //Finally replace the original file.
	   // if (file.exists()) file.delete(); 	
	    //  tempFile.renameTo(file);
	}

	public static int numberOfSemiColons(String line){
		int result = 0;
		for(int i = 0;i<line.length();i++){
			if(line.charAt(i)== ';')
				result++;
		}
		return result;
	}
	
}
