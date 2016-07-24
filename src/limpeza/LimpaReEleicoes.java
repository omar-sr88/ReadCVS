package limpeza;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class LimpaReEleicoes {

	final int CIDADE = 7;
	
	public static void main (String[] args) throws IOException{
		
		
		File folder = new File("/Users/Omar/Desktop/Dados Prefeitos - Modificados/consulta_cand_2012");
		
	    LimpaReEleicoes l = new LimpaReEleicoes();
	    l.listFilesForFolder(folder);

	   
		
	}
	
	public void listFilesForFolder(final File folder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	    
	            limpar(fileEntry);
	        }
	    }
	}
	
	
	public void limpar(File file) throws IOException{
		
		CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()),';');
		List<String> listaDeCidades = new ArrayList<String>();
	    List todasLinhas = reader.readAll();
		
	    File tempFile = File.createTempFile("buffer", ".tmp");
	    
	    CSVWriter writer = new CSVWriter(new FileWriter(tempFile), ';',CSVWriter.NO_QUOTE_CHARACTER);
	    
	    System.out.println(tempFile.getAbsolutePath());
	    
	    for (int i = todasLinhas.size()-1;i>=0;i--){
	    	String[] current = (String[]) todasLinhas.get(i);
	    	
	    	if(listaDeCidades.contains(current[CIDADE]))
	    		continue;
	    	
	    	//Diversos modelos para nova eleicao. Escolher baseado na "falta" do nome padrao
	    	if(!current[4].contains("ELEIÇÃO MUNICIPAL")){
	    		listaDeCidades.add(current[CIDADE]); // add nome da cidade duplicada
	    	}
	    		
	    	writer.writeNext(current);
	    }
	    
	    reader.close();
	    writer.close();
	    if (file.exists()) 
	    	 file.delete();
    	
	    tempFile.renameTo(file);
	}
	
	
}
