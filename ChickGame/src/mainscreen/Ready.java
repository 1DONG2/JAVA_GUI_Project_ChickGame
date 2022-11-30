package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.util.Random;

public class Ready extends JFrame{
	// Swing 디자인
		Font basicFont = new Font("DungGeunMo",Font.PLAIN,20); // 폰트 적용
		LineBorder bb = new LineBorder(Color.black, 2, true);
		Color mybackColor=new Color(240,255,255);
	
	ImageIcon Incubator= new ImageIcon("target.png");	//고급 부화기를 사면 준비화면의 부화기가 금색으로 되었으면 좋겠어요
	ImageIcon GoodIncubator= new ImageIcon("targetbeated.png");
	
	FileReader fr;
	BufferedReader reader;
	
	JTextField t;
	
	int[] item=new int[10];	//굳이 10개를 고른 이유는 딱히 없음
	String tempString="";
	String reading="";
	int i=0;
	final int X=1024;
	final int Y=768;
	
	Container c=null;
	JFrame my=this;
	
	JLabel eggIncubator;
	
	JButton BestiaryButton= new JButton("병아리 도감");
	JButton EggStoreButton= new JButton("알 사오기");
	JButton StartButton= new JButton("육성 시작!");
	JButton backButton=new JButton("뒤로가기");
	Ready(){
		try {
			fr = new FileReader("item.txt");
			reader =new BufferedReader(fr);
			
			while((reading=reader.readLine())!=null) {
				item[i]=Integer.parseInt(reading.split(":")[1]);//0은 돈, 1은 저급 알, 2는 중급 알, 3은 고급 알, 4는 고급 부화기
				tempString+=(reading+"\n");
				i++;
			}
			i=0;
			fr.close();
		} catch (FileNotFoundException e1) {
			System.out.println("파일을 열 수 없음");
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("입출력 오류");
		}
		
		setTitle("7일간 병아리 키우기");
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(214, 255, 235));
		
		if(item[4]>=1) {	//고급 부화기가 있을 경우
			eggIncubator=new JLabel(GoodIncubator);
		}
		else {	//없을 경우
			eggIncubator=new JLabel(Incubator);
		}
		eggIncubator.setBounds(100,150,300,450);
		eggIncubator.setOpaque(true);
		eggIncubator.setBackground(Color.red);
		c.add(eggIncubator);
		
		BestiaryButton.setBounds(X*3/4,(int)(Y*0.3),150,50);	//닭 도감
		BestiaryButton.setFont(basicFont);
		BestiaryButton.setBorder(bb);
		BestiaryButton.setBackground(mybackColor);
		c.add(BestiaryButton);
		BestiaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Bestiary();
			}
		});
		
		EggStoreButton.setBounds(X*3/4,(int)(Y*0.3)+70,150,50);	//상점
		EggStoreButton.setFont(basicFont);
		EggStoreButton.setBorder(bb);
		EggStoreButton.setBackground(mybackColor);
		c.add(EggStoreButton);
		EggStoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new EggStore();
			}
		});
		
		StartButton.setBounds(X*3/4,(int)(Y*0.3)+70+70,150,100);	//시작 버튼
		StartButton.setFont(basicFont);
		StartButton.setBorder(bb);
		StartButton.setBackground(new Color(249, 202, 205));
		c.add(StartButton);
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartDialog dialog= new StartDialog(my, item[1], item[2],item[3]);
				setVisible(false);
            	dialog.setLocation(X/2-100, Y/4-75);
            	dialog.setVisible(true);
			}
		});
		
		backButton.setBounds(X*3/4,(int)(Y*0.3)+70+70+120,150,50);	//뒤로가기 버튼
		backButton.setFont(basicFont);
		backButton.setBorder(bb);
		backButton.setBackground(mybackColor);
		c.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					new Title(true);
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	// 알 선택창
	class StartDialog extends JDialog {
		int X=600;
		int Y=400;
		JTextField NameField = new JTextField(20);
		
		ButtonGroup eggButtonGroup = new ButtonGroup();
		JRadioButton egg1 = new JRadioButton("초급 알: "+item[1]+"개",true);
		JRadioButton egg2 = new JRadioButton("중급 알: "+item[2]+"개");
		JRadioButton egg3 = new JRadioButton("고급 알: "+item[3]+"개");
		JPanel eggPanel=new JPanel();
		
		ImageIcon egg1Icon= new ImageIcon("ChickJavaPrj_Sprite\\\\bronze_egg.png");
		ImageIcon egg2Icon= new ImageIcon("ChickJavaPrj_Sprite\\\\silver_egg.png");
		ImageIcon egg3Icon= new ImageIcon("ChickJavaPrj_Sprite\\\\gold_egg.png");
		
		JLabel nameLabel=new JLabel("이름:");
		JButton okbutton=new JButton("확인");
		String name="0";
		StartDialog(JFrame frame, int badegg, int middleegg, int goodegg) {
			super(frame);
			
			
			try {
				fr = new FileReader("item.txt");
				reader =new BufferedReader(fr);
				while((reading=reader.readLine())!=null) {
					item[i]=Integer.parseInt(reading.split(":")[1]);
					tempString+=(reading+"\n");
					i++;
				}
				i=0;
				fr.close();
			} catch (FileNotFoundException e1) {
				System.out.println("파일을 열 수 없음");
				e1.printStackTrace();
			} catch (IOException e) {
				System.out.println("입출력 오류");
			}
			
			setSize(X,Y);
			setLayout(null);
			c=getContentPane();
			
			nameLabel.setBounds(X/2-100,50,100,50);
			nameLabel.setFont(basicFont);
			c.add(nameLabel);
			
			NameField.setBounds(X/2-50,50,120,50);
			c.add(NameField);
			NameField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NameField.setText("");
				}
			});
			
			egg1.setFont(basicFont);
			egg2.setFont(basicFont);
			egg3.setFont(basicFont);
			
			eggButtonGroup.add(egg1);
			eggButtonGroup.add(egg2);
			eggButtonGroup.add(egg3);
			
			JLabel lowQualityEgg = new JLabel(egg1Icon);
			JLabel middleQualityEgg = new JLabel(egg2Icon);
			JLabel highQualityEgg = new JLabel(egg3Icon);
			
			eggPanel.add(lowQualityEgg);
			eggPanel.add(middleQualityEgg);
			eggPanel.add(highQualityEgg);
			
			eggPanel.add(egg1);
			eggPanel.add(egg2);
			eggPanel.add(egg3);
			eggPanel.setBounds(50,100,500,180);
			c.add(eggPanel);
			
			// 이어하기 및 저장 파일 만들기
			okbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Random random=new Random();
					String sex="";
					name=NameField.getText();
					if (random.nextInt(2)==0) {
						sex="남";
					}
					else {
						sex="여";
					}
					if(egg1.isSelected()) {
						if(item[1]>=1) {
							item[1]--;
							try {
								BufferedWriter bw = new BufferedWriter(new FileWriter("stat.txt"));
								bw.write("이름:"+name+"\n성별:"+sex+"\n일차:"+1+"\n행복:"+10+"\n건강:"+10+"\n센스:"+10+"\n성실:"+10+"\n기력:"+5+"\n친밀도:"+0+"\n알종류:1");
								bw.flush();
								bw.close();
								bw = new BufferedWriter(new FileWriter("item.txt"));
								bw.write("돈:"+item[0]+"\n초급 알:"+item[1]+"\n중급 알:"+item[2]+"\n고급 알:"+item[3]+"\n고급 부화기:"+item[4]);
								bw.flush();
								bw.close();
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							my.dispose();
							new Main_game();
						}

					}
					else if (egg2.isSelected()) {
						if(item[2]>=1) {
							item[2]--;
							try {
								BufferedWriter bw = new BufferedWriter(new FileWriter("stat.txt"));
								bw.write("이름:"+name+"\n성별:"+sex+"\n일차:"+1+"\n행복:"+30+"\n건강:"+30+"\n센스:"+30+"\n성실:"+30+"\n기력:"+5+"\n친밀도:"+0+"\n알종류:2");
								bw.flush();
								bw.close();
								bw = new BufferedWriter(new FileWriter("item.txt"));
								bw.write("돈:"+item[0]+"\n초급 알:"+item[1]+"\n중급 알:"+item[2]+"\n고급 알:"+item[3]+"\n고급 부화기:"+item[4]);
								bw.flush();
								bw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							my.dispose();
							new Main_game();
						}
					}
					else {
						if(item[3]>=1) {
							item[3]--;
							try {
								BufferedWriter bw = new BufferedWriter(new FileWriter("stat.txt"));
								bw.write("이름:"+name+"\n성별:"+sex+"\n일차:"+1+"\n행복:"+50+"\n건강:"+50+"\n센스:"+50+"\n성실:"+50+"\n기력:"+5+"\n친밀도:"+0+"\n알종류:3");
								bw.flush();
								bw.close();
								bw = new BufferedWriter(new FileWriter("item.txt"));
								bw.write("돈:"+item[0]+"\n초급 알:"+item[1]+"\n중급 알:"+item[2]+"\n고급 알:"+item[3]+"\n고급 부화기:"+item[4]);
								bw.flush();
								bw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							my.dispose();
							new Main_game();
						}
					}

				}
			});
			okbutton.setBounds(X/2-50, Y-100,100, 50);
			okbutton.setFont(basicFont);
			okbutton.setBorder(bb);
			okbutton.setBackground(mybackColor);
			add(okbutton);
		}
	}
	public static void main(String[] args) {
		new Ready();
	}

}
