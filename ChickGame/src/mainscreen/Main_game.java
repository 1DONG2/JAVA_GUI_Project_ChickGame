package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import minigame.Feed_food;
import minigame.Keyboard_Tug_of_War;
import minigame.Put_in_order;
import minigame.Quickness_Test;

public class Main_game extends JFrame {
	int Chicken_idx;// 0부터 병아리 1
	int randomEvent = 0;// 랜럼으로 이벤트 방생
	Image Img_chicken;// 닭 이미지
	Image eggopener;// 브화기 사진
	String Event_append = "";// 이벤트 발생이 textArea에 추가될 문장
	int Rating_egg=0;// 달걀 등급
	int Count_eggopener=0;//부화기 개수
	String tmp = "";// 다음 줄이 없다는 걸 확인
	
	// Swing 디자인
	Font basicFont= new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	final int X=1024;
	final int Y=768;
	GameSelectDialog dialog;
	JFrame my=this;
	
	// 게임 내 출력을 위한 라벨
	JLabel nameLabel=new JLabel(); // 이름
	JLabel dayLabel= new JLabel(); // 일차
	JLabel sexLabel=new JLabel(); // 성별
	
	JPanel statPanel =new JPanel(); // 스탯 관련
	JLabel happinessLabel = new JLabel(); // 행복
	JLabel healthLabel = new JLabel(); // 건강
	JLabel senseLabel = new JLabel(); // 센스
	JLabel sincerityLabel = new JLabel(); // 성실
	JLabel energyLabel = new JLabel(); // 체력
	JLabel energyLabel2 = new JLabel(); // 체력
	JLabel intimacy = new JLabel(); // 호감
	JLabel goldLabel=new JLabel(); // 돈
	int gold=0; // 돈 변수
	int number_Agg;
	Random random=new Random(); // 랜덤 변수
	
	JLabel chickenImgLabel = new JLabel(); // 이미지
	
	// 버튼
	JButton playButton = new JButton("놀기");
	JButton feedingButton = new JButton("간식");
	JButton sleepButton = new JButton("자기");
	
	ImageIcon leftIcon= new ImageIcon("ChickJavaPrj_Sprite\\\\\\\\left_btn1.png");
	JButton backButton = new JButton(leftIcon);
	
	// 배열
	String[] stringStat = new String[2];
	int[] intStat = new int[10];
	
	Container c = null;
	
	
	// 채팅 관련
	int chatting_index=0;//채팅 횟수
	private JTextField inputText = new JTextField(20); // 입력하는 필드
	private JTextArea showText = new JTextArea(7, 20); // 추가되는 필드
	
	// 스탯 변경 시 저장
	BufferedWriter bw;

	// 대사관련 변수
	BufferedReader input; // 텍스트파일을 읽어들일 변수
	String mySpeak[] = new String[10]; // 읽어들인 변수에서 내 대사를 가져옴
	String chickenSpeak[] = new String[10]; // 병아리의 대사를 가져와서 저장
	int Speak_idx = 0; // 대사의 인덱스->textField에 입력되는 값과 같은 값을 찾기 위해 
	String chat; //내 채팅에 대응되는 병아리가 말하는 문자  
	
	//말풍선 관련 변수
	public Image chatting;// 말풍선 이미지
	boolean find_Word = false;// 파일에서 대사를 찾으면 true로 변경, 말풍선 출력
	boolean pressEnter = false;
	Timer timer;
	int remainingTime;
	
	// 읽기관련 변수
	FileReader fr;
	BufferedReader reader;
	
	String tempString="";
	String reading="";
	
	public Main_game() {
		int i=2;
		// RandomEvent.txt는 0부터 5까지다
				Random rd = new Random();
				int n = (rd.nextInt(30)) % 9; // 0~8 6이상이면 이벤트 발생x
				System.out.println("n:" + n);
				randomEvent = n;// 이벤트 발생
				
				try {
					// 스탯 읽기
					fr = new FileReader("stat.txt");
					reader = new BufferedReader(fr);
					reading = reader.readLine();
					stringStat[0] = reading.split(":")[1]; // 이름
					reading = reader.readLine();
					stringStat[1] = reading.split(":")[1]; // 성별
					
					while ((tmp = reader.readLine()) != null) {
						reading=tmp;
						// 2부터 시작함
						intStat[i] = Integer.parseInt(reading.split(":")[1]);
						// 2:일차, 3:행복, 4:건강, 5:센스, 6:성실, 7:기력, 8:친밀도 9:알종류 순
						tempString += (reading + "\n");
						i++;
					}
					i = 2;
					fr.close();
				}catch(NumberFormatException e)
				{
					intStat[i]=0;
				}
				catch (FileNotFoundException e1) {
					System.out.println("파일을 열 수 없음");
					e1.printStackTrace();
				} catch (IOException e) {
					System.out.println("입출력 오류");
				}
				number_Agg=intStat[9];

		
		//골드
		try {
			BufferedReader a=new BufferedReader(new FileReader("item.txt"));
			while((reading=a.readLine())!=null) {
				if(reading.split(":")[0].equals("돈")) {
					gold=Integer.parseInt(reading.split(":")[1]);
				}
			}
			a.close();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(intStat[7]<=0) {	// 체력이 0일 경우 강제로 자게 됨
			this.setVisible(false);
			new Sleeping();
		}
		else
			setVisible(true);
		if (2 < intStat[2] && intStat[2] < 7) {// 병아리일 떄 이벤트 발생
			Chicken_idx = 1;
			RandomEvent();//랜덤 이벤트 발생
		}
		if(intStat[2]>7) {	// 8일 경과시;	행복, 건강, 센스, 성실 순임
			if(intStat[3]<=100&&intStat[4]<=100&&intStat[5]<=100&&intStat[6]<=100){ //황금 닭
				dispose();
				try {
					new FinalResult(9);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=50&&intStat[4]<=100&&intStat[5]<=50&&intStat[6]<=50){ //헐크 닭
				dispose();
				try {
					new FinalResult(6);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=50&&intStat[4]<=50&&intStat[5]<=50&&intStat[6]<=50){ // 토종 닭
				dispose();
				try {
					new FinalResult(5);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=20&&intStat[4]<=20&&intStat[5]<=50&&intStat[6]<=50){ //안경 닭
				dispose();
				try {
					new FinalResult(4);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=20&&intStat[4]<=50&&intStat[5]<=20&&intStat[6]<=20){ //건강한 닭
				dispose();
				try {
					new FinalResult(10);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=50&&intStat[4]<=20&&intStat[5]<=20&&intStat[6]<=20){ //행복한 닭
				dispose();
				try {
					new FinalResult(8);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=30&&intStat[4]<=30&&intStat[5]<=30&&intStat[6]<=30){ //선글라스 닭
				dispose();
				try {
					new FinalResult(2);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(intStat[3]<=20&&intStat[4]<=20&&intStat[5]<=20&&intStat[6]<=20){ //일반 닭
				if(random.nextInt(10)<8) {
					dispose();
					try {
						new FinalResult(1);
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else { //행운의 닭
					dispose();
					try {
						new FinalResult(3);
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			else{ //허약 닭
				dispose();
				try {
					new FinalResult(3);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		setLayout(null);
		c=getContentPane();
		
		// 스탯 라벨 부분
		happinessLabel.setText("행복:   " + intStat[3] + "   ");
		healthLabel.setText("건강:   " + intStat[4] + "   ");
		senseLabel.setText("센스:   " + intStat[5] + "   ");
		sincerityLabel.setText("성실:   " + intStat[6] + "   ");
		happinessLabel.setFont(basicFont);
		healthLabel.setFont(basicFont);
		senseLabel.setFont(basicFont);
		sincerityLabel.setFont(basicFont);
		statPanel.add(happinessLabel);
		statPanel.add(healthLabel);
		statPanel.add(senseLabel);
		statPanel.add(sincerityLabel);
		
		// 스탯 판
		statPanel.setBounds(X-400, Y-250, 300, 70);
		c.add(statPanel);
		setSize(X, Y);
		
		// 일차
		dayLabel.setBounds(30, Y/16-25, 150, 70);
		dayLabel.setFont(basicFont);
		dayLabel.setText(intStat[2] + "일차");
		c.add(dayLabel);
		
		// 이름
		nameLabel.setBounds(X-400, Y/16-25, 150, 70);
		nameLabel.setFont(basicFont);
		nameLabel.setText("이름: " + stringStat[0]);
		c.add(nameLabel); 
		
		// 성별
		sexLabel.setBounds(X-125, Y/16-25, 150, 70);
		sexLabel.setFont(basicFont);
		sexLabel.setText("(" + stringStat[1] + ")");
		c.add(sexLabel); 
		
		// 체력
		energyLabel.setBounds(X - 400, 60, 200, 70);
		energyLabel.setFont(basicFont);
		energyLabel.setText("체력: ");
		c.add(energyLabel);
		
		energyLabel2.setBounds(X - 340, 60, 200, 70);
		energyLabel2.setFont(basicFont);
		String heart = "";
		for(int j=0;j<intStat[7];j++)
		{
			heart+="■";
		}
		energyLabel2.setForeground(Color.red);
		energyLabel2.setText(heart);
		c.add(energyLabel2);

		// 호감도
		intimacy.setBounds(X - 400, 90, 150, 70);
		intimacy.setFont(basicFont);
		intimacy.setFont(basicFont);
		intimacy.setText("호감도: " + intStat[8]);
		c.add(intimacy);
		
		// 골드
		goldLabel.setBounds(X - 400, 120, 150, 70);
		goldLabel.setFont(basicFont);
		goldLabel.setText("골드: " + gold);
		c.add(goldLabel);
		
		// 놀기 버튼
		playButton.setBounds(X-400, Y/4, 300, 70);
		playButton.setFont(basicFont);
		playButton.setBorder(bb);
		playButton.setBackground(mybackColor);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog =new GameSelectDialog();
				dialog.setVisible(true);
			}
		});
		add(playButton);
		
		// 식사 버튼
		feedingButton.setBounds(X-400, Y/4+100, 300, 70);
		feedingButton.setFont(basicFont);
		feedingButton.setBorder(bb);
		feedingButton.setBackground(mybackColor);
		feedingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				////////////////////////////////////
				try {
					BufferedReader a=new BufferedReader(new FileReader("item.txt"));
					while((reading=a.readLine())!=null) {
						if(reading.split(":")[0].equals("돈")) {
							gold=Integer.parseInt(reading.split(":")[1]);
					tempString="";
							if(gold<=10) {
								tempString+=("돈:"+gold+"\n");
							}
							
							else {
								showText.append("맛있어\n");	//이쪽으로 옮김
								intStat[8]+=1;
								tempString+=("돈:"+(gold-10)+"\n");
							}
						}
						else {
							tempString+=(reading+"\n");
						}
						
					}
					BufferedWriter b=new BufferedWriter(new FileWriter("item.txt"));
					b.write(tempString);
					b.flush();
					b.close();
					
					a.close();
					goldLabel.setText("돈:"+gold);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				////////////////////////////////////
				BufferedWriter bw;
				try {
					bw = new BufferedWriter(new FileWriter("stat.txt"));
					bw.write("이름:" + stringStat[0] + "\n성별:" + stringStat[1] + "\n일차:" + intStat[2] + "\n행복:"
							+ intStat[3] + "\n건강:" + intStat[4] + "\n센스:" + intStat[5] + "\n성실:" + intStat[6] + "\n기력:"
							+ intStat[7] + "\n친밀도:" + intStat[8]+"\n알 종류:"+number_Agg);
					bw.flush();
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				intimacy.setText("호감도:"+intStat[8]);
				// 채팅창에 맛있어! 같은 글이 나오고 친밀도 1 증가
			}
		});
		add(feedingButton);
		
		// 자기 버튼
		sleepButton.setBounds(X-400, Y/4+100+100, 300, 70);
		sleepButton.setFont(basicFont);
		sleepButton.setBorder(bb);
		sleepButton.setBackground(mybackColor);
		sleepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				my.dispose();
				new Sleeping();
			}
		});
		add(sleepButton);
		
		// 뒤로가기 버튼
		backButton.setBounds(X-200, 600, 100, 100);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				my.dispose();
				new Ready();
			}
		});
		add(backButton);
		
		JScrollPane js = new JScrollPane(showText);//JScrollPane에 textArea를 넣음

		js.setBounds(10, 400, 390, 200);
		inputText.setBounds(10, 600, 390, 31);
		add(showText);
		//setLayout(null);
		add(inputText);
		add(js);
		js.setViewportView(showText);
		
		inputText.addActionListener(enter);
		// mySpeak, chickenSpeak변수에 대사들을 저장
				try {
					input = new BufferedReader(new FileReader("speak.txt"));
					String read_msg;
					while ((read_msg = input.readLine()) != null) {
						String Speak[] = read_msg.split(":");
						mySpeak[Speak_idx] = Speak[0];
						chickenSpeak[Speak_idx] = Speak[1];
						Speak_idx++;
					}
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		repaint();
	}
	
	// 채팅 관련 함수
	ActionListener enter = new ActionListener() {// textField에 입력시 이벤트 발생
		public void actionPerformed(ActionEvent e) {
			JTextField t = (JTextField) e.getSource();
			String result[] = t.getText().split(" ");// add인지 확인
			String check_ins = result[0];
			if (check_ins.equalsIgnoreCase("add")) {// 첫 글자가 add면 speak.txt에 저장
				try {
					bw = new BufferedWriter(new FileWriter("speak.txt", true));
					bw.write("\n" + result[1] + ":" + result[2]);
					bw.flush();
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mySpeak[Speak_idx] = result[1];
				chickenSpeak[Speak_idx] = result[2];
				System.out.println("명령이 완료됐습니다.");
				Speak_idx++;
			} else {// add명령이 아니라면
				showText.append(t.getText() + "\n");
				// if(pressEnter == true) {pressEnter = false;pressEnter = true;} 작동이 안되는 것 같음
				pressEnter = true;
				for (int i = 0; i < Speak_idx; i++) {
					if (mySpeak[i].equals(t.getText())) // mySpeak에 입력한 문자가 있나 탐색
					{
						chat = chickenSpeak[i]; // 병아리의 말을 chickenSpeak에서 가져옴
						find_Word = true;
						break;
					} else {
						find_Word = false;
					}
				}
				chatting_index++;// 채팅 횟수 +1
			}
			t.setText("");// 엔터를 누를 시 text필드 초기화
			repaint();
		}
	};
	void check_agg()//어떤 계란인지 체크
		{
			if(number_Agg==1)
				Img_chicken = new ImageIcon("ChickJavaPrj_Sprite\\\\bronze_egg.png").getImage();
			else if(number_Agg==2) 
					Img_chicken = new ImageIcon("ChickJavaPrj_Sprite\\\\silver_egg.png").getImage();
			else if(number_Agg==3) 
				Img_chicken = new ImageIcon("ChickJavaPrj_Sprite\\\\gold_egg.png").getImage();
		}
		void check_eggopener()//어떤 부화기인지 확인
		{
			//item.txt
			if(Count_eggopener>=1) {
				eggopener=new ImageIcon("ChickJavaPrj_Sprite\\\\gold_eggopener.png").getImage();
				int statUp=10;
				for(int i=3;i<7;i++)
				{
					intStat[i]+=statUp;
				}
			}
			else
				eggopener=new ImageIcon("ChickJavaPrj_Sprite\\\\bronze_eggopener.png").getImage();
				
		}
	// 말풍선을 그리는 함수
	public void paint(Graphics g) {
		super.paint(g);
		
		if (intStat[2] <= 2)// 1~2일차 알
		{
			check_agg();
			//부화기 이미지
			check_eggopener();
		}
		else Img_chicken = new ImageIcon("ChickJavaPrj_Sprite\\\\\\chick.gif").getImage();
		g.drawImage(Img_chicken, 100, 150, 150, 150, this);
		if(intStat[2] <= 2)g.drawImage(eggopener, 100, 150, 150, 150, this);
		remainingTime = 3;
		chatting = new ImageIcon("ChickJavaPrj_Sprite\\\\\\chatting_project.png").getImage();// 말풍선 이미지
		if (pressEnter == true) {
			if (find_Word == false)
				chat = "무슨 말인지 모르겠습니다.";
			g.drawImage(chatting, 300, 50, 150, 150, this);// 말풍선의 이미지 좌표,크기
			g.drawString(chat, 310, 120);// 병아리 대사 좌표
			timer = new Timer(1000, countdown); // 타이머 시작
			timer.start();
		}
	}
	
	ActionListener countdown = new ActionListener() // 타이머,  remainingTime이 0되면 타이머 종료하고 말풍선 없앰
		{public void actionPerformed(ActionEvent e) {
				remainingTime--;
				if (remainingTime == 0) {
					find_Word = false;
					chatting = null;// 말풍선 없애고
					chat = "";// 병아리 대사도 없앰
					pressEnter = false;
					timer.stop();
					repaint();}}};
	
					public void RandomEvent() {// 랜덤 확률으로 이벤트 발생
						if (randomEvent >= 6)
							return;
						try {
							fr = new FileReader("randomEvent.txt");// 0~4번개의 투플
							// 인덱스 0은 testField에 출력할 글
							// 1~4는 증가할 능력치
							// 5는 화면에 출력 할 글자
							// :으로 나누자
							// 10개씩 만듬
							// Eventappend;
							int q;
							reader = new BufferedReader(fr);
							// reading="";
							int k = 0;
							// randomEvent(0~5)번째 이벤트를 찾아서 처리
						
							for (int i = 0; i <= randomEvent && (tmp = reader.readLine()) != null; i++) {
								reading = tmp;
							}
							Event_append = reading.split(":")[0]; // 출력 대사
							for (q = 1; q < 5; q++)// 올라갈 스텟
							{
								intStat[q + 2] += Integer.parseInt(reading.split(":")[q]);
							}
							chat = reading.split(":")[5];// 병아리가 대사
							showText.append("***     " + Event_append + "      ***\n");// 여기에 숫자가 들감
							fr.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {// 변경된 값 저장(스텟)
							bw = new BufferedWriter(new FileWriter("stat.txt"));
							bw.write("이름:" + stringStat[0] + "\n성별:" + stringStat[1] + "\n일차:" + intStat[2] + "\n행복:" + intStat[3]
									+ "\n건강:" + intStat[4] + "\n센스:" + intStat[5] + "\n성실:" + intStat[6] + "\n기력:" + intStat[7]
									+ "\n친밀도:" + intStat[8]);
							bw.flush();
							bw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						pressEnter = true;
						find_Word = true;
					}				
	
	// 게임 선택 레이아웃
	class GameSelectDialog extends JDialog {
		int X=800;
		int Y=600;

		String[] GameNameArr = {"간식 빠르게 먹기 (행복 UP)",
				"키보드 줄다리기 (건강 UP)",
				"순서 맞추기 (센스 UP)",
				"먹이 빨리먹기 (성실 UP)"};
		
		ImageIcon NPC=new ImageIcon("ChickJavaPrj_Sprite\\exkoala.png");
		JLabel NPCLabel=new JLabel(NPC);
		
		JButton[] startButton=new JButton[4];
		JButton backButton=new JButton("뒤로가기");
		
		JDialog thisis=this;
		
		
		GameSelectDialog() {
			// 창 사이즈 20% 줄이기
			setSize((int)(X*0.8),(int)(Y*0.8));
			setLayout(null);
			c=getContentPane();
			
			NPCLabel.setBounds(0, 30, 300, 400);
			c.add(NPCLabel);
					
			for(int i=0; i<startButton.length; i++) {
				startButton[i]=new JButton(GameNameArr[i]);
				startButton[i].setBounds(300, 50+i*100, 300, 50);
				startButton[i].setFont(basicFont); 
				startButton[i].setBorder(bb);
				startButton[i].setBackground(mybackColor);
				c.add(startButton[i]);
			}
			startButton[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					my.dispose();
					thisis.dispose();
					new Quickness_Test();
				}
			});
			startButton[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					my.dispose();
					thisis.dispose();
					new Keyboard_Tug_of_War();
				}
			});
			startButton[2].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					my.dispose();
					thisis.dispose();
					new Put_in_order();
				}
			});
			startButton[3].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					my.dispose();
					thisis.dispose();
					new Feed_food();
				}
			});
			backButton.setBounds(600,500,100,50);
			c.add(backButton);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thisis.dispose();
				}
			});
		}
	}

	public static void main(String[] args) {
		new Main_game();
	}
}
