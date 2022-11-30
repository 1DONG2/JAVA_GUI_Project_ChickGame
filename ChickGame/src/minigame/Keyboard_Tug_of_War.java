package minigame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import mainscreen.Main_game;
import minigame.Quickness_Test.ScoreDialog;

public class Keyboard_Tug_of_War extends JFrame{
	// Swing 디자인
	Font basicFont= new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	Timer timer=null;
	final int X=1024;
	final int Y=768;
	int score=0;
	int remainingTime=10;
	Random random=new Random();
	char randomAlphabet=(char)(random.nextInt(26)+97-32);	// 아스키코드와 랜덤 함수로 랜덤 알파벳을 저장한다.
	
	Container c=null;
	JFrame my=this;
	
	JPanel KeyP = new JPanel();
	JPanel KeyPT = new JPanel();
	JLabel targetAlphabet=new JLabel();
	
	JLabel scoreLabel=null;
	
	JLabel countdownLabel= new JLabel(""+remainingTime);
	
	ScoreDialog dialog;
	
	public Keyboard_Tug_of_War() {
		setTitle("키보드 줄다리기");
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.PINK);
		c.addKeyListener(new MyKeyListener());
		
		scoreLabel=new JLabel(""+score);	//점수 라벨
		scoreLabel.setBounds(X/2-50, Y/16-25,100,50);
		scoreLabel.setFont(basicFont);
		c.add(scoreLabel);
		
		countdownLabel.setBounds(X/2-50, Y/16+50,100,50);	//시간초 라벨
		countdownLabel.setFont(basicFont);
		c.add(countdownLabel);
		
		targetAlphabet.setBackground(Color.white); //랜덤 알파벳 라벨
		targetAlphabet.setText(""+randomAlphabet);
		targetAlphabet.setFont(new Font("둥근모꼴", Font.PLAIN, 100));
		targetAlphabet.setBounds(X/2, Y/2, 300, 300);
		KeyP.add(targetAlphabet);
		
		KeyP.setBounds(X/2-200, Y/2-150, 300, 250);
		KeyP.setBackground(Color.WHITE);
		c.add(KeyP);
		KeyPT.setBounds(X/2-200, Y/2, 300, 200);
		KeyPT.setBackground(Color.gray);
		c.add(KeyPT);
		
		c.requestFocus();
		
		timer = new Timer(1000, countdown); //1초 타이머
		timer.start();
	}
	
	ActionListener  countdown = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	remainingTime--;
        	
        	countdownLabel.setText(""+remainingTime);
        	revalidate();
        	if(remainingTime==0) {	//여기서 리턴하라고 한 것 같은데 차라리 이 부분에서 파일 시스템에 접근해서 수정하는 것도 좋을 것 같네요. 저라면 그렇게 할 것 같은데 메인스크린이 구현이 안됨
            	try {
					dialog=new ScoreDialog(my, getTitle(), score);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	dialog.setLocation(X/2-100, Y/4-75);
            	dialog.setVisible(true);
            	targetAlphabet.setVisible(false);
            	timer.stop();
        	}
        }
    };
	
	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()==randomAlphabet||e.getKeyChar()==randomAlphabet+32) { //랜덤 알파벳 라벨의 알파벳과 같은 알파벳 키를 눌렀을 경우 (대소문자 구별 없음)
				randomAlphabet=(char)(random.nextInt(26)+97-32); // 다른 랜덤 알파벳으로 바꿈
	        	targetAlphabet.setText(""+randomAlphabet);
				score++;	// 점수증가
				scoreLabel.setText("점수 : "+score);
				
			}
		}
	}
	ActionListener changingTarget=new ActionListener(){
		public void actionPerformed(ActionEvent evt) {
			score++;
        }
	};
	
	class ScoreDialog extends JDialog {
		int X=500;
		int Y=300;
		Font basicFont= new Font("둥근모꼴",Font.PLAIN,15); // 폰트 적용
		private JLabel scoreLabel=new JLabel();
		private JLabel goldLabel=new JLabel();
		JLabel increasingStatLabel=new JLabel();
		private JButton okbutton=new JButton("확인");
		
		String[] stringStat=new String[2];
		int[] intStat=new int[10];
		
		
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
			stringStat[0]=reading.split(":")[1];	// 이름
			reading=reader.readLine();
			stringStat[1]=reading.split(":")[1];	// 성별
			while((reading=reader.readLine())!=null) { // 2부터 시작함
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
			
			totalScore=(int)Score/6; // 각 게임마다 다름
			
			increasingStatLabel.setText("점수: "+Score);
			increasingStatLabel.setFont(basicFont);
			increasingStatLabel.setBounds(X/2-50, Y/4-25,100, 50);
			add(increasingStatLabel);
			
			scoreLabel.setText("올라간 건강: "+totalScore);	//각 게임마다 다름
			scoreLabel.setFont(basicFont);
			scoreLabel.setBounds(X/2-50, Y/4+100,150, 50);
			add(scoreLabel);
			
			okbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					intStat[7]--;
					intStat[4]+=totalScore;	//각 게임마다 다름
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
		new Keyboard_Tug_of_War();
	}

}
