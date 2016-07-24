package limpeza;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class ClearIDs {
	
	
	public static void main(String args[]) throws IOException{
		
		//File folder = new File("/Users/Omar/Desktop/links_csv");
		 
		ClearIDs anc = new ClearIDs();
		//anc.listFilesForFolder(folder);
		
		//anc.replaceInFile(new File("/Users/Omar/Desktop/links_csv.csv"));
		
		anc.replaceWithCSVReader(new File("/Users/Omar/Desktop/broken_links.csv"));
		//File file = new File("/Users/Omar/Desktop/Dados Prefeitos - Modificados/consulta_cand_2008/consulta_cand_2008_AC.txt");
		
		/*CSVReader reader = new CSVReader(new FileReader("/Users/Omar/Desktop/Dados Prefeitos - Modificados/consulta_cand_2008/consulta_cand_2008_AC.txt"),';');
	    String [] nextLine;
	    while ((nextLine = reader.readNext()) != null) {
	        //nextLine[] is an array of values from the line
	        System.out.println(nextLine[41]);
	    }
	    reader.close();*/
	}
	
	public void listFilesForFolder(final File folder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	
	       	     replaceInFile(new File(fileEntry.getAbsolutePath()));
	          
	            
	        }
	    }
	}
	
	public void replaceInFile(File file) throws IOException {

	    File tempFile = File.createTempFile("buffer", ".tmp");
	    FileWriter fw = new FileWriter(tempFile);
	    
	    System.out.println("Temp file : " + tempFile.getAbsolutePath());
	    String id;
	    Reader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    while(br.ready()) {   	
	    	
	    		String line = br.readLine();
	    		int space = line.indexOf(" ");
	    		if(space != -1){
	    			id = line.substring(1, space);
	    	    String novo = line.substring(space+1, line.length());
	    	    novo = novo.replace("\"\"", "\"");
	    	    System.out.println(novo);   
	    	    if(novo.charAt(novo.length()-1) != '"'){
	    	    	novo = novo+"\"";
	    	    }
	    	    
	    	    if(hasOddQuotes(novo)){
	    	    	if(novo.charAt(novo.length()-1) == '"' && novo.charAt(novo.length()-2) == '"')
	    	    	{
	    	    		novo = novo.substring(0, novo.length()-1);
	    	    	}
	    	    	else
	    	    		novo = novo+"\"";
	    	    	
	    	    }
	    	    System.out.println(novo);   
	    	    
	    	    System.out.println(id +";\""+novo+"\"\n");
	    	    fw.write(id +";\""+novo+"\"\n");
	    		}
	    		else{
	    	    fw.write(line + "\n");
	    		}
	    }

	    fw.close();
	    br.close();
	    fr.close();
	    //Finally replace the original file.
	    if (file.exists()) file.delete(); 	
	    tempFile.renameTo(file);
	}

	public void replaceWithCSVReader(File file) throws IOException{
		
		String line[];
		int i = 1;
		String header[] = {"UserName","#","ArticleId","Article Tite","Broken ArticleURL","New Article","Sort"};
				//criando leitor do csv
				CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()),';');
			    
				//criando escritor de csv em arquivo temporario
				File tempFile = File.createTempFile("buffer", ".tmp"); 
			    CSVWriter writer = new CSVWriter(new FileWriter(tempFile), ';', CSVWriter.NO_QUOTE_CHARACTER);
				
			    line = reader.readNext(); //le e escreve cabecalho
			    
			    writer.writeNext(header);
				while ((line = reader.readNext()) != null) {
					String address = line[0];
					
					if(line[2].equals("redir"))
						continue;
					
					String[] r = splitLine(line[2]); 
					String id = r[0];
					String title = r[1];
					
					
					String[] newLine = { "", i+"", id, title, address,"","" };
					writer.writeNext(newLine);
					i++;
				}
				
				reader.close();
			    writer.close();
			    //Finally replace the original file.
			    if (file.exists()) file.delete(); 	
			      tempFile.renameTo(file);
			      
			      
	}
	
	public boolean hasOddQuotes(String s){
		  int sum = 0;
		  for(int i = 0; i<s.length();i++){
			  if (s.charAt(i)=='"')
				  sum++;
		  }
		    
		  return sum % 2 == 1;
	  }

	public String[] splitLine(String line){
		String[] result = new String[2];
		int space = line.indexOf(" ");
		
		if(space != -1){
			result[0] = line.substring(0, space);
	    String novo = line.substring(space+1, line.length());
	   
	     
	    
	    /*
	     * novo = novo.replace("\"\"", "\"");
	     * 
	    if(novo.charAt(novo.length()-1) != '"'){
	    	novo = novo+"\"";
	    }
	    
	    if(hasOddQuotes(novo)){
	    	if(novo.charAt(novo.length()-1) == '"' && novo.charAt(novo.length()-2) == '"')
	    	{
	    		//removes an extra double quote at the end
	    		novo = novo.substring(0, novo.length()-1);
	    	}
	    	else
	    		novo = novo+"\"";
	    	
	    }
	    
	    */
	    
	    if(novo.indexOf(' ')!=-1)
	    	novo = novo.substring(novo.indexOf(' ')+1,novo.length());
	    else
	    	novo = "";
	    
	    novo = novo.replaceAll("\"", "");
	    
	    System.out.println(novo);
	    result[1]= novo;
	   
		}
		 return result;
	}
	
}
