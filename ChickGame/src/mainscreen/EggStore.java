package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class EggStore extends JFrame {
	
	// Swing 디자인
	Font basicFont= new Font("DungGeunMo",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	ImageIcon egg1Icon= new ImageIcon("ChickJavaPrj_Sprite\\\\bronze_egg.png");
	ImageIcon egg2Icon= new ImageIcon("ChickJavaPrj_Sprite\\\\silver_egg.png");
	ImageIcon egg3Icon= new ImageIcon("ChickJavaPrj_Sprite\\\\gold_egg.png");
	ImageIcon incubatorIcon= new ImageIcon("ChickJavaPrj_Sprite\\\\gold_eggopener.png");
	
	
	FileReader fr;
	BufferedReader reader;
	
	int[] item=new int[10];
	String tempString="";
	String reading="";
	
	final int X=1024;
	final int Y=768;
	int i=0;
	Container c=null;
	JFrame my=this;

	JLabel lowQualityEgg= new JLabel(egg1Icon);
	JButton lowQualityEggButton= new JButton("초급 알 구매 (100)");
	
	JLabel middleQualityEgg= new JLabel(egg2Icon);
	JButton middleQualityEggButton= new JButton("중급 알 구매 (500)");
	
	JLabel highQualityEgg= new JLabel(egg3Icon);
	JButton highQualityEggButton= new JButton("고급 알 구매 (1000)");
	
	JLabel incubator= new JLabel(incubatorIcon);	
	JButton incubatorButton= new JButton("고급부화기 구매 (2000)");
	
	JButton backButton=new JButton("뒤로가기");
	
	JLabel amountofItem=new JLabel();
	JLabel noticeItem=new JLabel();
	
	
	EggStore(){	
		try {
			fr = new FileReader("item.txt");	//파일 시스템은 여기에 있는 거 적당히 배끼면 메인화면 파일 시스템 연동은 쉽겠네요. 이렇게 안 할거면 안 해도 되고요
			reader =new BufferedReader(fr);
			
			while((reading=reader.readLine())!=null) {
				item[i]=Integer.parseInt(reading.split(":")[1]);//0은 돈, 1은 저급 알, 2는 중급 알, 3은 고급 알, 4는 고급 부화기 //이건 개수만 나옴
				tempString+=(reading+"\n"); //이거는 텍스트 출력할 때 참고용으로 원본 파일의 복사본을 만들었음
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
		
		lowQualityEgg.setBounds(X*3/4-300,(int)(Y/2)-300,128,128);	//초급 알 관련
		c.add(lowQualityEgg);
		
		lowQualityEggButton.setBounds(X*3/4-120,(int)(Y/2)-265,300,70);
		lowQualityEggButton.setFont(basicFont);
		lowQualityEggButton.setBorder(bb);
		lowQualityEggButton.setBackground(mybackColor);
		lowQualityEggButton.setFont(basicFont);
		c.add(lowQualityEggButton);
		lowQualityEggButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item[0]>=100) {
					item[0]-=100;
					item[1]++;
					amountofItem.setText("돈:"+item[0]+"  초급 알: "+item[1]+"  중급 알: "+item[2]+"  고급 알:"+item[3]+"  고급 부화기: "+item[4]);
					noticeItem.setText("초급 알을 구매하였습니다. 100 골드가 차감됩니다.");
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("item.txt"));
						bw.write("돈:"+item[0]+"\n초급알:"+item[1]+"\n중급알:"+item[2]+"\n고급알:"+item[3]+"\n고급부화기:"+item[4]);
						bw.flush();
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else {
					noticeItem.setText("초급 알을 사기엔 돈이 모자랍니다!");
				}
			}
		});
		
		middleQualityEgg.setBounds(X*3/4-300,(int)(Y/2)-300+125,128,128);	//중급 알 관련
		c.add(middleQualityEgg);
		
		middleQualityEggButton.setBounds(X*3/4-120,(int)(Y/2)-265+125,300,70);
		middleQualityEggButton.setFont(basicFont);
		middleQualityEggButton.setBorder(bb);
		middleQualityEggButton.setBackground(mybackColor);
		c.add(middleQualityEggButton);
		middleQualityEggButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item[0]>=500) {
					item[0]-=500;
					item[2]++;
					amountofItem.setText("돈:"+item[0]+"  초급 알: "+item[1]+"  중급 알: "+item[2]+"  고급 알:"+item[3]+"  고급 부화기: "+item[4]);
					noticeItem.setText("중급 알을 구매하였습니다. 500 골드가 차감됩니다.");
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("item.txt"));
						bw.write("돈:"+item[0]+"\n초급알:"+item[1]+"\n중급알:"+item[2]+"\n고급알:"+item[3]+"\n고급부화기:"+item[4]);
						bw.flush();
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else {
					noticeItem.setText("중급 알을 사기엔 돈이 모자랍니다!");
				}
			}
		});
		
		highQualityEgg.setBounds(X*3/4-300,(int)(Y/2)-300+125+125,128,128);	//고급 알 관련
		c.add(highQualityEgg);
		
		highQualityEggButton.setBounds(X*3/4-120,(int)(Y/2)-265+125+125,300,70);
		highQualityEggButton.setFont(basicFont);
		highQualityEggButton.setBorder(bb);
		highQualityEggButton.setBackground(mybackColor);
		c.add(highQualityEggButton);
		
		highQualityEggButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item[0]>=1000) {
					item[0]-=1000;
					item[3]++;
					amountofItem.setText("돈:"+item[0]+"  초급 알: "+item[1]+"  중급 알: "+item[2]+"  고급 알:"+item[3]+"  고급 부화기: "+item[4]);
					noticeItem.setText("고급 알을 구매하였습니다. 1000 골드가 차감됩니다.");
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("item.txt"));
						bw.write("돈:"+item[0]+"\n초급알:"+item[1]+"\n중급알:"+item[2]+"\n고급알:"+item[3]+"\n고급부화기:"+item[4]);
						bw.flush();
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else {
					noticeItem.setText("고급 알을 사기엔 돈이 모자랍니다!");
				}				
			}
		});
		
		incubator.setBounds(X*3/4-300,(int)(Y/2)-300+125+125+125,128,128);	//고급 부화기 관련
		c.add(incubator);
		if(item[4]>0){	//이미 부화기가 있을 시 비활성화
			incubatorButton.setEnabled(false);
		}
		
		incubatorButton.setBounds(X*3/4-120,(int)(Y/2)-265+125+125+125,300,70);
		incubatorButton.setFont(basicFont);
		incubatorButton.setBorder(bb);
		incubatorButton.setBackground(mybackColor);
		c.add(incubatorButton);
		incubatorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item[0]>=2000) {
					item[0]-=2000;
					item[4]++;
					amountofItem.setText("돈:"+item[0]+"  초급 알: "+item[1]+"  중급 알: "+item[2]+"  고급 알:"+item[3]+"  고급 부화기: "+item[4]);
					noticeItem.setText("고급 부화기를 구매하였습니다. 2000 골드가 차감됩니다.");
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("item.txt"));
						bw.write("돈:"+item[0]+"\n초급알:"+item[1]+"\n중급알:"+item[2]+"\n고급알:"+item[3]+"\n고급부화기:"+item[4]);
						bw.flush();
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else {
					noticeItem.setText("고급 부화기를 사기엔 돈이 모자랍니다!");
				}
			}
		});
		
		backButton.setBounds(X*3/4,(int)(Y*0.5)+70+70+70+10,180,50);	//뒤로가기 버튼
		backButton.setFont(basicFont);
		backButton.setBorder(bb);
		backButton.setBackground(mybackColor);
		c.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Ready();
			}
		});
		
		amountofItem.setText("돈:"+item[0]+"  초급 알: "+item[1]+"  중급 알: "+item[2]+"  고급 알:"+item[3]+"  고급 부화기: "+item[4]);	//아이템 현황
		amountofItem.setBounds(150,Y-100,600,50);
		amountofItem.setFont(basicFont);
		c.add(amountofItem);
		
		noticeItem.setText("어서오세요. 물품을 선택해주세요.");	//아이템 현황
		noticeItem.setBounds(150,Y-150,600,50);
		noticeItem.setFont(basicFont);
		c.add(noticeItem);
		
		}

	public static void main(String[] args) {
		new EggStore();
	}

}
