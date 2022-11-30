package minigame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import mainscreen.Main_game;
import minigame.Quickness_Test.ScoreDialog;

public class Put_in_order extends JFrame{
	// Swing 디자인
	Font basicFont= new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	
	Panel p=new Panel();	//바둑판이 들어갈 공간
	GridLayout orderBox=new GridLayout(3,3,10,10);	//3곱하기3 바둑판에 행, 열간 간격은 10
	int[] order=new int[9];	//랜덤 숫자를 넣을 공간
	JButton[] orderbuttons=new JButton[9]; //랜덤 숫자를 넣을 버튼
	Timer timer=null;
	final int X=1024;
	final int Y=768;
	int score=0;
	int point=0;
	int remainingTime=15;
	Random random=new Random();
	
	Container c=null;
	JFrame my=this;
	
	
	JLabel scoreLabel=null; //score는 최종 점수
	
	JLabel countdownLabel= new JLabel(""+remainingTime);
	
	JLabel pointLabel= new JLabel(""+point); //point는 부분점수? 콤보라고 봐도 좋을듯
	
	ScoreDialog dialog;
	
	void randombutton() {
		for (int i = 0; i < order.length; i++) {
			order[i]=random.nextInt(9)+1;	//1-9까지 랜덤 숫자를 order에 넣을 거임
			for(int j=0;j<i;j++) {	//중복 없이
				if(order[i] == order[j]){
					i--;
				}
			}
		}
		for (int i = 0; i < orderbuttons.length; i++) {
			orderbuttons[i].setFont(basicFont);
			orderbuttons[i].setText(""+order[i]);
				switch (orderbuttons[i].getText()) {	//버튼마다 색깔 지정
				case "1": 
					orderbuttons[i].setBackground(new Color(204,153,255));
					break;
				case "2":
					orderbuttons[i].setBackground(new Color(000,255,255));
					break;
				case "3":
					orderbuttons[i].setBackground(new Color(153,255,102));
					break;
				case "4":
					orderbuttons[i].setBackground(new Color(255,153,51));
					break;
				case "5":
					orderbuttons[i].setBackground(new Color(255,102,153));
					break;
				case "6":
					orderbuttons[i].setBackground(new Color(255,204,51));
					break;
				case "7":
					orderbuttons[i].setBackground(new Color(204,204,204));
					break;
				case "8":
					orderbuttons[i].setBackground(new Color(153,153,255));
					break;
				case "9":
					orderbuttons[i].setBackground(new Color(204,255,0));
					break;
				default:
					
				}
				orderbuttons[i].addActionListener(new MyActionListener());
				p.add(orderbuttons[i]);
				revalidate();	//새로고침 안하면 이상해지더라
			}
	}
	public Put_in_order() {
		setTitle("순서 맞추기");
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.PINK);
		
		scoreLabel=new JLabel(""+score);	//최종점수
		scoreLabel.setBounds(X/2-50, Y/16-25,100,50);
		scoreLabel.setFont(basicFont);
		c.add(scoreLabel);
		
		countdownLabel.setBounds(X/2-50, Y/16+50,100,50);	//남은 시간
		countdownLabel.setFont(basicFont);
		c.add(countdownLabel);
		
		pointLabel.setBounds(X/2-50, Y*4/5,100,50);	//콤보
		pointLabel.setFont(basicFont);
		c.add(pointLabel);
		
		p.setLayout(orderBox);	//위에서 만든 그리드 레이아웃을 패널 p에 적용
		p.setBounds(X/4, Y/4, 120*3+50, 120*3+50);
		p.setBackground(Color.gray);
		p.setVisible(true);
		c.add(p);
		for (int i = 0; i < order.length; i++) {	//패널에 버튼을 집어넣음
			orderbuttons[i]=new JButton(""+order[i]);
		}
		randombutton(); //랜덤으로 초기화
		timer = new Timer(1000 ,countdown);
		timer.start();
	}
	private class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText().equals((point+1)+"")) {	//1-9까지 차례대로 눌러야 되는데 공교롭게도 콤보는 0에서 시작해서 1씩 올라감. 콤보가 0이면 1을 누를 차례이기 때문에 point+1과 누른 버튼의 텍스트가 같을 경우
				b.setBackground(Color.white);	//버튼 색깔을 흰색으로
				point++;	//포인트 증가
				pointLabel.setText(""+point);
			}
			if(point==9) { //9개를 전부 맞출 경우
				point=0;	//다시 콤보는 초기화
				pointLabel.setText(""+score);	
				score++;//최종점수 증가
				scoreLabel.setText(""+score);
				randombutton();	//버튼에 랜덤으로 다시 숫자를 넣음
			}
		}
	}
	
	
	ActionListener  countdown = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	remainingTime--;
        	countdownLabel.setText(""+remainingTime);
        	if(remainingTime==0) {
            	p.setVisible(false);
            	try {
					dialog=new ScoreDialog(my, getTitle(), score);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	dialog.setLocation(X/2-100, Y/4-75);
            	dialog.setVisible(true);
            	timer.stop();
        	}
        		
        }
    };
    
    class ScoreDialog extends JDialog {
    	int X=400;
    	int Y=300;
    	
    	private JLabel scoreLabel=new JLabel();
    	JLabel increasingStatLabel=new JLabel();
		private JLabel goldLabel=new JLabel();
    	private JButton okbutton=new JButton("확인");
    	
    	String[] stringStat=new String[2];
    	int[] intStat=new int[10];
    	Font basicFont= new Font("둥근모꼴",Font.PLAIN,15); // 폰트 적용
    	
    	String tempString="";
    	String reading="";
    	int i=2;
    	
    	FileReader fr;
    	BufferedReader reader;
    	
    	int totalScore=0; //최종으로 올라갈 스탯
    	int gold=0; //올라갈 골드
    	
    	public ScoreDialog(JFrame frame, String title, int Score) throws IOException {
    		super(frame,title);
    		setSize(X,Y);
    		setLayout(null);
    		c=getContentPane();
    		
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
    		
			tempString="";	///////////////////////////새로 쓴 곳. 골드 올라가는 곳
			c.setBackground(new Color(255,255,220));
			fr = new FileReader("item.txt");
			reader =new BufferedReader(fr);
			
			while((reading=reader.readLine())!=null) {
				if(reading.split(":")[0].equals("돈")) {
					gold=Integer.parseInt(reading.split(":")[1])+Score*2;
					goldLabel.setText("총 골드: "+gold+"( + "+(gold-Integer.parseInt(reading.split(":")[1]))+")"); //골드 라벨
					tempString+= ("돈:"+gold+"\n");
				}
				else {
					tempString+=reading+"\n";
				}
			}
			fr.close();
			BufferedWriter writer=new BufferedWriter(new FileWriter("item.txt"));
			writer.write(tempString);
			writer.flush();
			writer.close();
			
			
			
			goldLabel.setFont(basicFont);
			goldLabel.setBounds(X/2-50, Y/4,200, 50);
			add(goldLabel);
////////////////////////////
    		
    		
    		totalScore=(int)Score*3; //각 게임마다 다름
    		
    		increasingStatLabel.setText("점수: "+Score);
    		increasingStatLabel.setFont(new Font("둥근모꼴", Font.PLAIN, 15));
    		increasingStatLabel.setBounds(X/2-50, Y/4-25,100, 50);
    		add(increasingStatLabel);
    		
    		scoreLabel.setText("올라간 센스: "+totalScore);	//각 게임마다 다름
    		scoreLabel.setFont(new Font("둥근모꼴", Font.PLAIN, 15));
    		scoreLabel.setBounds(X/2-50, Y/4+100,100, 50);
    		add(scoreLabel);
    		
    		okbutton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				intStat[7]--;
    				intStat[5]+=totalScore;	//각 게임마다 다름
    				BufferedWriter bw;
    				try {
    					bw = new BufferedWriter(new FileWriter("stat.txt"));
    					bw.write("이름:"+stringStat[0]+"\n성별:"+stringStat[1]+"\n일차:"+intStat[2]+"\n행복:"+intStat[3]+"\n건강:"+intStat[4]+"\n센스:"+intStat[5]+"\n성실:"+intStat[6]+"\n기력:"+intStat[7]+"\n친밀도:"+intStat[8]+"\n알종류:"+intStat[9]);
    					bw.flush();
    					bw.close();
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}

    				my.dispose();
    				new Main_game();
    			}
    		});
    		okbutton.setBounds(X/2-50, Y/2-25,100, 50);
    		okbutton.setFont(basicFont);
			okbutton.setBorder(bb);
			okbutton.setBackground(mybackColor);
    		add(okbutton);
    	}
    	
    }
    
	public static void main(String[] args) {
		new Put_in_order();
	}

}
