import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.text.WordUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;



public class CalculaValores {

	
	public static void main(String args[]) throws IOException{
		 
		CalculaValores anc = new CalculaValores();
		anc.limpeza(new File("C:/Users/Omar/Desktop/TodosSemDivisaoEstado.txt"));

		
		
	}
	
	public void limpeza(File file) throws IOException{
		
		CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()),';');
		CSVWriter writer = new CSVWriter(new FileWriter("C:/Users/Omar/Desktop/values.csv"), ';', CSVWriter.NO_QUOTE_CHARACTER);

		
		 String line[] = reader.readNext(); //separador
		 
         try{
        	 line = reader.readNext(); //cabecalho
        	 ArrayList<String> l = new ArrayList<String>(Arrays.asList(line));
 			 l.add("Valor_total");
 			 l.add("Estado_U");
 			 l.add("Cidade_U");
        	 writer.writeNext(l.toArray(new String[l.size()]));
        	 line = reader.readNext(); //primeira linha
        	 
         }
         catch(Exception e){
        	 e.printStackTrace();
         }
         String lastLine[] = line.clone();
         String lastid = line[0];
		 String id, estadoUnico = "1", cidadeUnica = "1";
		 
		 double oldA = -1;
		 double oldP = -1;
		 double oldT = -1;
		 
		 double antes = 0;
		 double pos = 0;
		 double total = 0;
		 
		 double totalToSave = 0;
		 
     	do {
     		
     		id = line[0];

     		if(!line[3].contains("em")&&!line[3].isEmpty())
     			antes = Double.parseDouble(line[3]);
     		else antes = 0;
     		if(!line[4].contains("em")&&!line[4].isEmpty())
     			pos = Double.parseDouble(line[4]);
     		else pos = 0;
     		if(!line[5].isEmpty())
     			total = Double.parseDouble(line[5]);
     		else total = 0;
     		
     		if (id.equals(lastid)){
     			
     			if(antes!=oldA)
     				oldA = antes;
     			if(pos!=oldP)
     				oldP = pos;
     			if(total!=oldT)
     				oldT = total;    	
     			
     			lastLine = line.clone();
     		}else{ 
     			//SE O ID FOR DIFERENTE, VOCE MUDOU DE EMPREENDIMENTO, CALCULAR TUDO BASEADO NA ULTIMA LINHA
     			//
         		if(line[0].equals("1337"))
         			System.out.println(line[1]);
         		if(oldT==0)
         			totalToSave = oldA + oldP;
     			else
     				totalToSave = oldT;
         		
         		//Se houver regime diferenciado valor total eh zero
         		if(lastLine[17].contains("RDC"))
         			totalToSave = 0;
     			
     			if(lastLine[6].length()>2)
     				estadoUnico = "0";
     			if(lastLine[7].contains(",") || lastLine[7].isEmpty())
     				cidadeUnica = "0";
     			
     			//Toda maiuscula, 
     			if(lastLine[7].toUpperCase().equals(lastLine[7])){
     				lastLine[7] = lastLine[7].toLowerCase();
     				lastLine[7]  = WordUtils.capitalize(lastLine[7] ,',',' ','/');			       		
     			}
     			else
     				lastLine[7]  = WordUtils.capitalize(lastLine[7] ,',',' ','/');			
     			
     			
     			lastLine[8]  = WordUtils.capitalizeFully(lastLine[8] ,',','(', ' ');
     			
     			ArrayList<String> l = new ArrayList<String>(Arrays.asList(lastLine));
     			l.add(totalToSave+"");
     			l.add(estadoUnico);
     			l.add(cidadeUnica);
				writer.writeNext(l.toArray(new String[l.size()]));
				
				
				cidadeUnica = "1";
				estadoUnico = "1";
     			oldA = antes;
     			oldP = pos;
     			oldT = total;
     			 
     			antes = 0;
     			pos = 0;
     			total = 0;
     			
     			if(!line[3].contains("em")&&!line[3].isEmpty())
         			antes = Double.parseDouble(line[3]);
         		if(!line[4].contains("em")&&!line[4].isEmpty())
         			pos = Double.parseDouble(line[4]);
         		if(!line[5].isEmpty())
         			total = Double.parseDouble(line[5]);
         		
         		lastid = id;
         		lastLine = line.clone();
     		}
     					
     	} while ((line = reader.readNext()) != null);
		
     	
     	//For last Entry
     		if(total==0)
				total = antes + pos;
			
			if(lastLine[6].length()>2)
				estadoUnico = "1";

			if(lastLine[7].contains(","))
				cidadeUnica = "1";
			
			ArrayList<String> l = new ArrayList<String>(Arrays.asList(lastLine));
			l.add(total+"");
			l.add(estadoUnico);
			l.add(cidadeUnica);
			writer.writeNext(l.toArray(new String[l.size()]));

     	
		reader.close();
		writer.close();
		
	}

	

}
