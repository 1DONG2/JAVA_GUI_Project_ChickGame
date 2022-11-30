package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Sleeping extends JFrame{
	Timer timer;
	
	final int X=1024;
	final int Y=768;
	
	Container c=null;
	JFrame my=this;
	JLabel zzz=new JLabel("Z...Z...Z...");
	public Sleeping() {

		setSize(X,Y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c=getContentPane();
		c.setBackground(Color.black);
		zzz.setFont(new Font("둥근모꼴",Font.BOLD,70));
		zzz.setHorizontalAlignment(JLabel.CENTER);
		zzz.setForeground(Color.white);
		c.add(zzz);
		
		timer = new Timer(3000 ,countdown);
		timer.start();
		
		

	}
	ActionListener  countdown = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            timer.stop();
            
        	String[] stringStat=new String[2];
        	int[] intStat=new int[10];
        	
        	String tempString="";
        	String reading="";
        	int i=2;
        	
        	FileReader fr;
        	BufferedReader reader;
        	
            try {
        		fr = new FileReader("stat.txt");
        		reader =new BufferedReader(fr);
        		reading=reader.readLine();
        		stringStat[0]=reading.split(":")[1];	//이름
        		reading=reader.readLine();
        		stringStat[1]=reading.split(":")[1];	//성별
        		while((reading=reader.readLine())!=null) {//2부터 시작함
        			intStat[i]=Integer.parseInt(reading.split(":")[1]);	//2:일차,3:행복,4:건강,5:센스 6:성실 7:기력 8:친밀도 순
        			tempString+=(reading+"\n");
        			i++;
        		}
        		i=2;
        		fr.close();
        		intStat[2]++;	//일차 넘어감
            	intStat[7]=5;	//기력 회복
				BufferedWriter bw = new BufferedWriter(new FileWriter("stat.txt"));
				bw.write("이름:"+stringStat[0]+"\n성별:"+stringStat[1]+"\n일차:"+intStat[2]+"\n행복:"+intStat[3]+"\n건강:"+intStat[4]+"\n센스:"+intStat[5]+"\n성실:"+intStat[6]+"\n기력:"+intStat[7]+"\n친밀도:"+intStat[8]+"\n알종류:"+intStat[9]);
				bw.flush();
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            
            new Main_game();
            my.dispose();
        }
    };
	
    public static void main(String[] args) {
    	new Sleeping();
    }
}
