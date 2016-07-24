import java.io.File;
import java.io.IOException;


public class Amazon {
	public static void main(String args[]) throws IOException{
		 
		String balls[] = {"a","11","1","2","3","4","5","6","+","Z","X","+","+"};
		int scoreHisto[] = new int[balls.length];
		int value = 0;
		int score = 0;
		int pos = -1;
		char special = 'c';
		boolean flag = true;// 1 for number , 0 for special
		boolean wasPlus = false;
		for(int i = 0; i< balls.length;i++){
               
            try{
                value = Integer.parseInt(balls[i]);
                flag = true;
            }
            catch(Exception e){
            	special = balls[i].charAt(0);
            	flag = false;
            }
                      
            if(flag){
            	score += value;   
            	scoreHisto[++pos]= value;
            }
            else{
            	// ASSUMPTION 1 : Hitting x, + and z in the first round does nothing
            	//  I am assuming in this case that you'll skip the special
            	switch(special){
            	case 'X':  
            		//solve for X with no past entry
            		if(pos==-1) break;
            		scoreHisto[++pos] = 2*scoreHisto[pos-1];
            		score += scoreHisto[pos];
            		break;
            	case 'Z':
            		//solve for Z with no past entry
            		if(pos==-1) break;
            		
            		score -= scoreHisto[pos--];
            		break;
            		// ASSUMPTION 2 : Hitting a + when there is less than 2 entries makes no sense
            		// I am assuming in this case that you'll skip the special
            	case '+':    
            		//solve assuption 2
            		if(pos<1) break;
            		scoreHisto[++pos] = scoreHisto[pos-1] + scoreHisto[pos-2] ;
            		score += scoreHisto[pos];
            		break;   
            		
            	default:break;
            	}
            	         	
            }
            System.out.println(score);
        }
		
	}
}
