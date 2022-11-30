package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Bestiary extends JFrame{
	// Swing 디자인
	Font basicFont = new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	ImageIcon leftIcon= new ImageIcon("ChickJavaPrj_Sprite\\\\\\\\left_btn1.png");
	ImageIcon rightIcon= new ImageIcon("ChickJavaPrj_Sprite\\\\\\\\right_btn1.png");
	ImageIcon leftIconHover= new ImageIcon("ChickJavaPrj_Sprite\\\\\\\\left_btn2.png");
	ImageIcon rightIconHover= new ImageIcon("ChickJavaPrj_Sprite\\\\\\\\right_btn2.png");
	
	final int X=1024;
	final int Y=768;
	
	final int chickens=12;
	int page=0;
	Container c=null;
	JFrame my=this;
	
	JPanel[] chickenPanel=new JPanel[chickens];	// 닭마다 패널을 만듬 
	ImageIcon[] chickenImg=new ImageIcon[chickens]; // 이미지라벨 안에 들어갈 이미지,
	JLabel[] chickenImgLabel=new JLabel[chickens]; // 패널 안에 들어갈 이미지라벨 
	JLabel[] chickenNameLabel=new JLabel[chickens];	// 닭 이름
	JLabel[] chickenDescriptionLabel=new JLabel[chickens]; //닭 설명
	

	JButton prevButton = new JButton(leftIcon);
	JButton nextButton = new JButton(rightIcon);
	
	JButton backButton= new JButton("뒤로가기");	
	
	boolean[] unlocked =new boolean[20];
	int temp=0;
	String reading="";
	int i=0;
	
	FileReader fr;
	BufferedReader reader;
	
	Bestiary(){
		
		Arrays.fill(unlocked, false);	//언락되었는지 확인하는 코드
		try {
			fr = new FileReader("bestiary.txt");
			reader =new BufferedReader(fr);
			
			while((reading=reader.readLine())!=null) {
				temp=Integer.parseInt(reading.split("!")[0]);
				if(temp==1) {
					unlocked[i]=true;
				}
				System.out.println(reading+" "+unlocked[i]);
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
		c.setBackground(Color.PINK);
		
		// 이름 표시해주는 라벨
		chickenNameLabel[0]=new JLabel("닭");
		chickenNameLabel[1]=new JLabel("선글라스 닭");
		chickenNameLabel[2]=new JLabel("럭키 닭");
		chickenNameLabel[3]=new JLabel("안경 닭");
		chickenNameLabel[4]=new JLabel("토종닭");
		chickenNameLabel[5]=new JLabel("헐크 닭");
		chickenNameLabel[6]=new JLabel("허약한 닭");
		chickenNameLabel[7]=new JLabel("행복한 닭");
		chickenNameLabel[8]=new JLabel("금닭");
		chickenNameLabel[9]=new JLabel("건강한 닭");
		chickenNameLabel[10]=new JLabel("불닭");
		chickenNameLabel[11]=new JLabel("-");
		
		// 닭에 대한 설명 라벨
		chickenDescriptionLabel[0]=new JLabel("도감 1번, 평범하게 자란 닭이다");
		chickenDescriptionLabel[1]=new JLabel("도감 2번, 패션에 관심이 많은 닭");
		chickenDescriptionLabel[2]=new JLabel("도감 3번, 겉으론 평범하지만 왠지 보기 드문 닭");
		chickenDescriptionLabel[3]=new JLabel("도감 4번, 공부를 너무 많이 해 눈이 멀었다.");
		chickenDescriptionLabel[4]=new JLabel("도감 5번, 토종 한국의 닭!");
		chickenDescriptionLabel[5]=new JLabel("도감 6번, 겁나 짱셈");
		chickenDescriptionLabel[6]=new JLabel("도감 7번, 건강이 좋지 않아. 투약 중이다.");
		chickenDescriptionLabel[7]=new JLabel("도감 8번, 저 얼굴은 누가봐도 행복해보여");
		chickenDescriptionLabel[8]=new JLabel("도감 9번, 상당히 비싸보인다.");
		chickenDescriptionLabel[9]=new JLabel("도감 10번, 역시 운동은 닭가슴이지");
		chickenDescriptionLabel[10]=new JLabel("도감 11번, 화르륵 화르륵 매운맛");
		chickenDescriptionLabel[11]=new JLabel("-");
		
		chickenImg[0]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken1.png");
		chickenImg[1]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken2.png");
		chickenImg[2]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken3.png");
		chickenImg[3]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken4.png");
		chickenImg[4]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken5.png");
		chickenImg[5]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken6.png");
		chickenImg[6]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken7.png");
		chickenImg[7]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken8.png");
		chickenImg[8]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken9.png");
		chickenImg[9]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken10.png");
		chickenImg[10]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken11.png");
		chickenImg[11]=new ImageIcon("ChickJavaPrj_Sprite\\\\chicken12.png");
		
		for (int i = 0; i < chickens; i++) {	//초기화 
			chickenPanel[i]=new JPanel();
			
			if(unlocked[i]==true) {	
				chickenPanel[i].setBackground(Color.white); //해금되었을 경우 배경색을 하얀색으로 
			}
			else if(unlocked[i]==false){
				chickenPanel[i].setBackground(new Color(100,100,100)); //해금되지 않았을 시 배경색을 짙은 회색으로
				chickenNameLabel[i].setText("???");;
				chickenDescriptionLabel[i].setText("???");;
			}
			chickenPanel[i].setLayout(null);
			chickenPanel[i].setBounds(100,Y/8+(250*(i%2)),800,200);
			
			chickenImgLabel[i]=new JLabel(chickenImg[i]);
			chickenImgLabel[i].setBounds(0,0,200,200);
			chickenPanel[i].add(chickenImgLabel[i]);
			
			chickenNameLabel[i].setBounds(200,0,600,100);
			chickenNameLabel[i].setBackground(Color.magenta);
			chickenNameLabel[i].setFont(basicFont);
			chickenPanel[i].add(chickenNameLabel[i]);
			
			chickenDescriptionLabel[i].setBounds(200,100,600,100);
			chickenDescriptionLabel[i].setBackground(Color.magenta);
			chickenDescriptionLabel[i].setFont(basicFont);
			chickenPanel[i].add(chickenDescriptionLabel[i]);
		}
		
		c=getContentPane();
		
		for (int i = 0; i < chickens; i++) {	//한 후 일단 모두 비활성화
			c.add(chickenPanel[i]);
			chickenPanel[i].setVisible(false);
		}
		
		chickenPanel[0].setVisible(true);	//그리고 첫번째, 두 번째 패널만 활성화
		chickenPanel[1].setVisible(true);
		
		backButton.setBounds(X/2-200, Y-200, 365, 128);	// 뒤로가기 버튼
		backButton.setFont(basicFont);
		backButton.setBorder(bb);
		c.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				my.dispose();
				new Ready();
			}
		});
		
		
		// 다음 도감 버튼
		nextButton.setBounds(X-150, 280, 128, 128);
		nextButton.setFont(basicFont);
		nextButton.setPressedIcon(rightIconHover); // 버튼을 누르면 해당 이미지로
		nextButton.setRolloverIcon(rightIconHover); // 마우스를 올리면 해당 이미지로
		c.add(nextButton);
		// 2개씩 보여주는...
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page<chickens/2-1) {
					chickenPanel[2*page].setVisible(false);
					chickenPanel[2*page+1].setVisible(false);
					page++;
					chickenPanel[2*page].setVisible(true);
					chickenPanel[2*page+1].setVisible(true);
					revalidate();
				}
			}
		});
		
		
		// 이전 도감 버튼
		prevButton.setBounds(10, 280, 128, 128);
		prevButton.setFont(basicFont);
		prevButton.setPressedIcon(leftIconHover); // 버튼을 누르면 해당 이미지로
		prevButton.setRolloverIcon(leftIconHover); // 마우스를 올리면 해당 이미지로
		c.add(prevButton);
		// 2개 이전으로
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page>0) {
					chickenPanel[2*page].setVisible(false);
					chickenPanel[2*page+1].setVisible(false);
					page--;
					chickenPanel[2*page].setVisible(true);
					chickenPanel[2*page+1].setVisible(true);
					revalidate();
				}
			}
		});
		revalidate();
	}
	
	public static void main(String[] args) {
		new Bestiary();
	}

}
