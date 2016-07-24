import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.opencsv.CSVReader;


public class CalculoValoresEDatas {

	
	public static void main(String args[]) throws IOException, ParseException{
		 
		CalculoValoresEDatas anc = new CalculoValoresEDatas();
		anc.limpeza(new File("/Users/Omar/Desktop/Dados PAC - Separados por estado"));

		
		
	}
	
	public void limpeza(File file) throws IOException, ParseException{
		
		CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()),';');
		
		ArrayList<String[]> grupo = new ArrayList<String[]>();
		
		
		 String line[] = reader.readNext(); //le e escreve cabecalho
         line = reader.readNext(); //primeira linha
		 String lastid = "xxxx";
		 String id;
		 
     	do {
     		id = line[0];

     		if (id.equals(lastid)){
     			grupo.add(line);
     		}else{
     			analise(grupo);
     			
     			grupo = new ArrayList<String[]>();

         			grupo.add(line);
     			
     		}
     					
     	} while ((line = reader.readNext()) != null);
		
		
	}
	
	public void analise(ArrayList<String[]> param) throws ParseException{
		
		final int VALPRE = 3;
		final int VALPOS = 5;
		final int TOTAL = 6;
		final int ESTAGIO = 10;
		final int CICLO = 11;
		final int CONCLUSAO = 12;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		
		boolean teveRevisao = false;
		Date inicioRevisao=null;
		long periodoRevisao, diasRevisao;
		
		int idEstagio = 0 ,  idAnterior;
		
		
		boolean teveLicitacao = false;
		Date inicioLicitacao=null;
		long periodoLicitacao, diasLicitacao;
		
		boolean teveLicitacaoProj = false;
		Date inicioLicitacaoProj=null;
		long periodoLicitacaoProj, diasLicitacaoProj;
		
		boolean teveObra = false;
		Date inicioObra; 
		long periodoObra,diasObra;
		
		boolean teveExecucao = false;
		Date inicioExecucao; 
		long periodoExecucao,diasExecucao;
		
		boolean concluido = false;
		
		boolean teveOperacao = false;
		Date inicioOperacao; 
		long periodoOpercao,diasOperacao;
		
		for(String[] linha : param){
			
			//testa revisao	
			if (linha[VALPRE].equalsIgnoreCase("em revisão")){
				if (!teveRevisao){
					teveRevisao = true;
					inicioRevisao = formatter.parse(linha[CICLO]);
				} 
					
				
				
				if (idEstagio == 70){
					
				}
				
			}//quando nao for mais revisao, calcula tempo
			else{
				periodoRevisao = formatter.parse(linha[CICLO]).getTime() - inicioRevisao.getTime();
			    long diffHours = periodoRevisao / (60 * 60 * 1000);
			    long dias = periodoRevisao / (diffHours*24);
			}
			
			
			
			
			
			
		}
		
		
	}
	
}
