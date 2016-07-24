package limpeza;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class LimpaEleicaoPrefeito {

	static String cabecalho = "DATA_GERACAO;HORA_GERACAO;ANO_ELEICAO;NUM_TURNO;DESCRICAO_ELEICAO;SIGLA_UF;SIGLA_UE;DESCRICAO_UE;CODIGO_CARGO;DESCRICAO_CARGO;NOME_CANDIDATO;SEQUENCIAL_CANDIDATO;NUMERO_CANDIDATO;NOME_URNA_CANDIDATO;COD_SITUACAO_CANDIDATURA;DES_SITUACAO_CANDIDATURA;NUMERO_PARTIDO;SIGLA_PARTIDO;NOME_PARTIDO;CODIGO_LEGENDA;SIGLA_LEGENDA;COMPOSICAO_LEGENDA;NOME_LEGENDA;CODIGO_OCUPACAO;DESCRICAO_OCUPACAO;DATA_NASCIMENTO;NUM_TIT_ELEITORAL_CANDIDATO;IDADE_DATA_ELEICAO;CODIGO_SEXO;DESCRICAO_SEXO;COD_GRAU_INSTRUCAO;DESCRICAO_GRAU_INSTRUCAO;CODIGO_ESTADO_CIVIL;DESCRICAO_ESTADO_CIVIL;CODIGO_NACIONALIDADE;DESCRICAO_NACIONALIDADE;SIGLA_UF_NASCIMENTO;CODIGO_MUNICIPIO_NASCIMENTO;NOME_MUNICIPIO_NASCIMENTO;DESPESA_MAX_CAMPANHA;COD_SITUACAO_TURNO;DESCRICA_SITUACAO_TURNO";
	
	
	public static void main(String args[]) throws IOException{
		
		File folder = new File("/Users/Omar/Desktop/Dados Prefeitos - Modificados/consulta_cand_2008");
		 
		LimpaEleicaoPrefeito anc = new LimpaEleicaoPrefeito();
		anc.listFilesForFolder(folder);
		
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

	    Reader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    while(br.ready()) {   	
	    		String line = br.readLine();
	    	    String novo = line.replaceAll("\"\"", "\""); 
	    	    novo = novo.replaceAll("'", ""); 
	    	    String[] csv = novo.split(";");
	    	    if(novo.contains("VICE-PREFEITO") || novo.contains("VEREADOR") || !csv[41].equals("\"ELEITO\""))
	    	    	continue;
	    	    fw.write(novo + "\n");    			    		
	    }

	    fw.close();
	    br.close();
	    fr.close();
	    //Finally replace the original file.
	    if (file.exists()) file.delete();
	    	
	    tempFile.renameTo(file);
	}

}
