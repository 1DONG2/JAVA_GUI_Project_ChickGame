package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;



public class Title extends JFrame{
	// 음악관련
	File music;
	AudioInputStream stream; 
	Clip clip;
	
	// Swing 디자인
	Font basicFont= new Font("DungGeunMo",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	// 이미지 불러오기
	ImageIcon optionIcon= new ImageIcon("ChickJavaPrj_Sprite\\setting_btn1.png");
	ImageIcon optionIconPressed= new ImageIcon("ChickJavaPrj_Sprite\\setting_btn2.png");
	ImageIcon title= new ImageIcon("ChickJavaPrj_Sprite\\title.png");
	
	// 해당 해상도를 상수로
	final int X=1024;
	final int Y=768;
	
	// 콘테이너
	Container c=null;
	JFrame my=this;
	
	// 버튼 목록들
	JButton startButton = new JButton("새로하기");
	JButton continueButton = new JButton("이어하기");
	JButton creditButton = new JButton("개발정보");
	JButton exitButton = new JButton("종료");
	JButton optionButton = new JButton(optionIcon); // 이미지로 만든 버튼
	public JButton titleBtn = new JButton(title); // 타이틀을 버튼으로 만든 이유는 이벤트 하려고
	
	Title(boolean ismusicactivated) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		// 음악 관련
		if(!ismusicactivated) {
			// 음악 변경 예정!
			music = new File("Girasol - Quincas Moreira.wav");
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(music));
			clip.loop(Clip.LOOP_CONTINUOUSLY);	// 무한루프
			clip.start();
		}
		
		setTitle("Chick Simulation"); // 이름
		this.setSize(X, Y); // 고정 사이즈
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 콘테이너 관련
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(214, 255, 235));
		c.setFont(basicFont);
		
		
		// 타이틀 달기
		titleBtn.setBounds(X/2-400, Y/4, 512, 256);	// 게임 이름 글자
		titleBtn.setContentAreaFilled(false); // 색없애기
		titleBtn.setBorderPainted(false); // 테두리 없애기
		c.add(titleBtn);
		// 눌렀을 때
		titleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 불닭이 해금
				new fireDialog();
			}
		});
		
		
		// 시작 버튼
		startButton.setBounds(X*3/4,(int)(Y*0.5),150,50);
		startButton.setFont(basicFont); 
		startButton.setBorder(bb);
		startButton.setBackground(mybackColor);
		c.add(startButton);
		// 눌렀을 때
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				my.dispose();
				new Ready();
			}
		});
		
		
		// 이어하기 버튼
		continueButton.setBounds(X*3/4,(int)(Y*0.5)+70,150,50);
		continueButton.setFont(basicFont);
		continueButton.setBorder(bb);
		continueButton.setBackground(mybackColor);
		c.add(continueButton);
		// 눌렀을 때
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				my.dispose();
				new Main_game();
			}
		});
		
		
		//옵션 버튼
		optionButton.setBounds(X-150,50,128,128);	
		optionButton.setFont(basicFont);
		optionButton.setContentAreaFilled(false); // 버튼 그라데이션 없애기
		optionButton.setBorderPainted(false); // 버튼 색 없애기
		optionButton.setPressedIcon(optionIconPressed); // 버튼을 누르면 해당 이미지로
		optionButton.setRolloverIcon(optionIconPressed); // 마우스를 올리면 해당 이미지로
		c.add(optionButton);
		// 눌렀을 때
		optionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Option(clip);
			}
		});
		
		
		//제작자 버튼
		creditButton.setBounds(X*3/4,(int)(Y*0.5)+70+70,150,50);	
		creditButton.setFont(basicFont);
		creditButton.setBorder(bb);
		creditButton.setBackground(mybackColor);
		// 눌렀을 때
		c.add(creditButton);
		creditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Credit();
			}
		});
		
		
		//나가기 버튼
		exitButton.setBounds(X*3/4,(int)(Y*0.5)+70+70+70,150,50);	
		exitButton.setFont(basicFont);
		exitButton.setBorder(bb);
		exitButton.setBackground(mybackColor);
		c.add(exitButton);
		// 눌렀을 때
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // 해당 프로그램 종료
			}
		});
	}
	
	// 불닭 레이아웃
		class fireDialog extends JDialog {
			int X=600;
			int Y=400;
			
			// 버튼
			JButton okButton = new JButton("확인");

			Container c = null;
			
			// 읽기관련 변수
			FileReader fr;
			BufferedReader reader;
			BufferedWriter writer;
			
			String tempString="";
			String reading="";
			
			Timer timer;
			
			JLabel beforeResultLabel=new JLabel("도감번호 11, 불닭이 해금됐습니다!");
			ImageIcon chickenImage; //닭 이미지
			JLabel chickenImageLabel;//닭 이미지가 들어갈 라벨
			JLabel ResultLabel2=new JLabel("도감에 기록합니다...");
			int chickenNumber=1;
			
			public fireDialog() {

				setSize(X,Y);
				setVisible(true);
				
				c=getContentPane();
				
				beforeResultLabel.setBounds(X/2-180,50,350,50);
				beforeResultLabel.setFont(basicFont);
				beforeResultLabel.setHorizontalAlignment(JLabel.CENTER);
				beforeResultLabel.setForeground(Color.red);
				c.add(beforeResultLabel);

		        ResultLabel2.setBounds(X/2-100,80,200,50);
		        ResultLabel2.setFont(basicFont);
		        c.add(ResultLabel2);
		        
		        chickenImage=new ImageIcon("ChickJavaPrj_Sprite\\chicken11.png");
		        chickenImageLabel=new JLabel(chickenImage);
		        chickenImageLabel.setBounds(X/2-80,120,128,128);
		        c.add(chickenImageLabel);
		        
		        okButton.setBounds(X/2-50, Y-100,100, 50);
		        okButton.setFont(new Font("둥근모꼴",Font.PLAIN,17));
		        okButton.setBorder(bb);
		        okButton.setBackground(mybackColor);
		        okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							reader =new BufferedReader(new FileReader("bestiary.txt"));
							while((reading=reader.readLine())!=null) {
								 if(reading.split("!")[2].equals(""+11)) {
				                     tempString+=("1!불닭!11\n");}
				                 else{
				                	 tempString+=reading+"\n";}
				                 }
							writer = new BufferedWriter(new FileWriter("bestiary.txt"));
							writer.write(tempString);
							writer.flush();
							writer.close();
							reader.close();
							} catch (IOException e2) {
								//e2.printStackTrace();
							}
						dispose();
					}
				});
		        c.add(okButton);
			}
		}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Title(false);
	}

}
