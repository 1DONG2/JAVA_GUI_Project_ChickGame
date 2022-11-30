package minigame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import mainscreen.Main_game;
public class Quickness_Test extends JFrame{
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
	
	
	Container c=null;
	JFrame my=this;
	
	ImageIcon targetimg1= new ImageIcon("ChickJavaPrj_Sprite\\\\bean1.png"); // 타깃 누르기 전
	ImageIcon targetimg2= new ImageIcon("ChickJavaPrj_Sprite\\\\bean2.png"); // 누른 후
	JButton target=new JButton(targetimg1);

	JLabel scoreLabel=null;
	
	JLabel countdownLabel= new JLabel(""+remainingTime);
	
	ScoreDialog dialog;
	
	public Quickness_Test(){
		setTitle("순발력 테스트");
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(214, 255, 235));
		
		scoreLabel=new JLabel(""+score);
		scoreLabel.setBounds(X/2-50, Y/16-25,100,50);
		scoreLabel.setFont(basicFont);
		c.add(scoreLabel);
		
		countdownLabel.setBounds(X/2-50, Y/16+50,100,50);
		countdownLabel.setFont(basicFont);
		c.add(countdownLabel);
		
		// 버튼 색 없애기
		target.setContentAreaFilled(false);
		
		target.setRolloverIcon(targetimg2);
		target.setBorderPainted(false);
		target.setPreferredSize(new Dimension(128, 128));
		target.setBounds(random.nextInt(100,X-200), random.nextInt(300,Y-200), 128, 128);
		target.addActionListener(changingTarget);
		c.add(target);
		
		timer = new Timer(1000 ,countdown);
		timer.start();
		
		
	}
	
	
	ActionListener  countdown = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	remainingTime--;
        	countdownLabel.setText(""+remainingTime);
        	if(remainingTime==0) {
            	try {
					dialog=new ScoreDialog(my, getTitle(), score);
				} catch (IOException e) {
					e.printStackTrace();
				}
            	dialog.setLocation(X/2-100, Y/4-75);
            	dialog.setVisible(true);
            	target.setVisible(false);
            	timer.stop();
        	}
        		
        }
    };
    
	ActionListener changingTarget=new ActionListener(){
		public void actionPerformed(ActionEvent evt) {
			score++;
			scoreLabel.setText(""+score);
			target.setLocation(random.nextInt(100,X-200), random.nextInt(300,Y-200));
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
		
		
		
		totalScore=(int)Score/4; //각 게임마다 다름
		
		increasingStatLabel.setText("점수: "+Score);
		increasingStatLabel.setFont(new Font("Serif",Font.BOLD,15));
		increasingStatLabel.setBounds(X/2-50, Y/4-25,100, 50);
		add(increasingStatLabel);
		
		scoreLabel.setText("올라간 행복: "+totalScore);	//각 게임마다 다름
		scoreLabel.setFont(new Font("Serif",Font.BOLD,15));
		scoreLabel.setBounds(X/2-50, Y/4+100,100, 50);
		add(scoreLabel);
		
		okbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				intStat[7]--;
				intStat[3]+=totalScore;	//각 게임마다 다름
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
		new Quickness_Test();
	}
}
