import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


public class RemoveDuplicada {
	public static void main(String args[]) throws IOException{
		 
		RemoveDuplicada anc = new RemoveDuplicada();
		anc.removeDuplicada("/Users/Omar/Desktop/combined.csv");

		
		
	}
	
	public void listFilesForFolder(final File folder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().equals("LEIAME"))
	        		continue;
	        	
	        	//corrigePontoEVirgulaNoTexto(new File(fileEntry.getAbsolutePath()));
	          
	            
	        }
	    }
	}
	
	public void removeDuplicada(String filename) throws IOException{
		
		  	File tempFile = File.createTempFile("buffer", ".tmp");
		  	FileWriter fw = new FileWriter(new File("/Users/Omar/Desktop/TodosUploadDocsClean.txt"));
		  	
		  	/*final Path file1 = Paths.get(filename);
		  	final Set<String> file1Lines 
		    = new HashSet<>(Files.readAllLines(file1, StandardCharsets.UTF_8));
		  	
		  	*/
		  	File f = new File(filename);
		  	System.out.println(f);
		 	BufferedReader reader = new BufferedReader(new FileReader(filename));
		    Set<String> lines = new HashSet<String>(300000); // maybe should be bigger
		    String line;
		    while ((line = reader.readLine()) != null) {
		        lines.add(line);
		    }
		    reader.close();
		    BufferedWriter writer = new BufferedWriter(fw);
		    for (String unique : lines) {
		        writer.write(unique);
		        writer.newLine();
		    }
		    writer.close();
		    
		    if (f.exists()) 
		    	f.delete();
	    	
		    tempFile.renameTo(f);
		 
	}
	
	
}
