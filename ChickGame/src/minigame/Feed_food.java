package minigame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import mainscreen.Main_game;

// Arraylist에 먹이를 저장
// GamePanel의 makeFood에서 먹이가 나올 확률을 랜덤으로 설정
// GamePanel는 이미지를 JFrame에 그림, 먹이 먹었는지 확인,닭을 움직임,확률적으로 먹이를 생성
// Feed_food클래스에서 윈도우 창 크기,위치 GameThread, GamePanel생성

public class Feed_food extends JFrame {
	// Swing 디자인
		Font basicFont= new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
		LineBorder bb = new LineBorder(Color.black, 2, true);
		Color mybackColor=new Color(240,255,255);
	
	Container c;
	GamePanel panel;
	GameThread gThread; // 스레드 작업
	JFrame jf = this; // ScoreDialog 호출하기 위해 씀
	ScoreDialog dialog; // remainTime이 0이 되면 나오는 창
	Timer timer; // 타이머
	
	int remainingTime = 15; // 게임 진행 시간
	
	
	public Feed_food() {
		super("Feed_food");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Feed.window_width, Feed.window_height); // 창의 크기 조절
		Dimension frameSize = getSize(); // 창을 윈도우 중앙에 배치
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize(); // 배치2
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);// 배치3
		
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		setVisible(true);

		gThread = new GameThread();
		gThread.start();

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {// 키를 때면 병아리를 멈춤
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_LEFT:
					panel.c_x = 0;
					break;
				case KeyEvent.VK_RIGHT:
					panel.c_x = 0;
					break;
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {// 키를 누르면 이벤트 발생
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_LEFT:
					panel.c_x = -8;
					break;
				case KeyEvent.VK_RIGHT:
					panel.c_x = 8;
					break;
				}
			}
		});
	}

	class GamePanel extends JPanel {
		Image imgBack, imgChicken, imgFeed;
		int x, y, w, h;// 플레이어 중심좌표 //이미지의 크기의 절반
		int width, height;
		int c_x = 0;// 플레이어 이미지의 이동속도
		ArrayList<Feed> feed = new ArrayList<Feed>();//먹이를 저장하는 배열
		int score=0;//점수

		public GamePanel() {
			// 도구상자 객체
			imgBack = new ImageIcon("ChickJavaPrj_Sprite\\\\background.jpg").getImage();
			imgChicken = new ImageIcon("ChickJavaPrj_Sprite\\\\chick.gif").getImage();
			imgFeed = new ImageIcon("ChickJavaPrj_Sprite\\\\bean1.png").getImage();
			
			timer = new Timer(1000, countdown);//타이머 시작
			timer.start();
		}
		
		//타이머 설정
		ActionListener countdown = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remainingTime--;
				if (remainingTime == 0) {//종료시 ScoreDialog창 출력
					timer.stop();
					ScoreDialog dialog = null;
					try {
						dialog = new ScoreDialog(jf, score);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dialog.setLocation(Feed.window_width / 2 - 100, Feed.window_height / 4 - 75);
					dialog.setVisible(true);
				}
			}
		};

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (width == 0 && height == 0) {
				width = getWidth();
				height = getHeight();
				imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);//백그라운드 이비지의 크기를 조정
				x = width / 2;
				y = height - 100;
				w = h = 64;
			}
			g.drawImage(imgBack, 0, 0, this);
			for (Feed t : feed) {
				g.drawImage(t.img, t.x - t.w, t.y - t.h, this);//먹이들을 그림
			}
			g.drawImage(imgChicken, x - w, y - h, this);//병아리를 그림 
			g.drawString("Score:" + score, 10, 30);//점수를 그림 
			g.drawString(""+remainingTime,Feed.window_width / 2 - 50,Feed.window_height / 16 + 50);//남은 시간을 그림 

		}

		void move_Chicken() {
			for (int i = feed.size() - 1; i >= 0; i--) {
				Feed t = feed.get(i);
				t.move();// 먹이가 움직임
				if (t.isDead)// 먹이가 병아리와 충돌하면 죽임
					feed.remove(i);
			}
			x += c_x;
			if (x < w) {//벽에 부딛치면 멈춤 (1)
				x = w;
			}
			if (x > width - w) {//(2)
				x = width - w;
			}
		}

		void makeFeed() {//확률적으로 먹이를 생성
			Random rd = new Random();
			int n = rd.nextInt(30);
			if (n == 0)
				feed.add(new Feed(imgFeed));
		}

		void checkCollision()// 충돌 여부
		{
			for (Feed t : feed) {//먹이의 상하좌우를 저장
				int left = t.x - t.w;
				int right = t.x + t.w;
				int top = t.y - t.h;
				int bottom = t.y + t.h;

				if (x > left && x < right && y > top && y < bottom) {
					t.isDead = true;//Feed.java에 있는 변수 true면 위에서 만든 없앰    
					score += 5;//점수 증가
				}
			}
		}
	}

	class ScoreDialog extends JDialog { //끝나면 나오는 창
		int X=400;
		int Y=300;
		
		private JLabel scoreLabel=new JLabel();
		JLabel increasingStatLabel=new JLabel();
		private JLabel goldLabel=new JLabel();
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
		
		public ScoreDialog(JFrame frame, int Score) throws IOException {
			setSize(X, Y);
			setLayout(null);
			scoreLabel.setText("점수:" + Score);
			scoreLabel.setFont(new Font("둥근모꼴", Font.PLAIN, 15));
			scoreLabel.setBounds(X / 2 - 50, Y / 4 - 25, 100, 50);
			add(scoreLabel);
			c=getContentPane();
			
			Font basicFont= new Font("둥근모꼴",Font.PLAIN,15); // 폰트 적용
			
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
					gold=Integer.parseInt(reading.split(":")[1])+Score/5*3;
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
			
			
			
			totalScore=(int)Score/6; //각 게임마다 다름
			
			increasingStatLabel.setText("점수: "+Score);
			increasingStatLabel.setFont(new Font("둥근모꼴", Font.PLAIN, 15));
			increasingStatLabel.setBounds(X/2-50, Y/4-25,100, 50);
			add(increasingStatLabel);
			
			scoreLabel.setText("올라간 성실: "+totalScore);	//각 게임마다 다름
			scoreLabel.setFont(new Font("둥근모꼴", Font.PLAIN, 15));
			scoreLabel.setBounds(X/2-50, Y/4+100,100, 50);
			add(scoreLabel);
			
			okbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					intStat[7]--;
					intStat[6]+=totalScore;	//각 게임마다 다름
					BufferedWriter bw;
					try {
						bw = new BufferedWriter(new FileWriter("stat.txt"));
						bw.write("이름:"+stringStat[0]+"\n성별:"+stringStat[1]+"\n일차:"+intStat[2]+"\n행복:"+intStat[3]+"\n건강:"+intStat[4]+"\n센스:"+intStat[5]+"\n성실:"+intStat[6]+"\n기력:"+intStat[7]+"\n친밀도:"+intStat[8]+"\n알종류:"+intStat[9]);
						bw.flush();
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					jf.dispose();
					new Main_game();
				}
			});
			
			okbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					frame.dispose();
				}
			});

			okbutton.setBounds(X / 2 - 50, Y / 2 - 25, 100, 50);
			okbutton.setFont(basicFont);
			okbutton.setBorder(bb);
			okbutton.setBackground(mybackColor);
			add(okbutton);
		}

	}

	class GameThread extends Thread {
		@Override
		public void run() {
			while (true)
			{
				panel.makeFeed();
				panel.move_Chicken();
				panel.checkCollision();
				panel.repaint();
				try {
					sleep(20);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	public static void main()
	{
		new Feed_food();
	}
}