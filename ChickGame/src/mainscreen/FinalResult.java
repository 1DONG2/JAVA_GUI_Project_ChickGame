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

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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

public class FinalResult extends JFrame {
	
	// Swing 디자인
	Font basicFont= new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);
	
	final int X=1024;
	final int Y=768;
	JFrame my=this;
	
	// 버튼
	JButton okButton = new JButton("메인 화면으로..");

	
	Container c = null;
	
	// 읽기관련 변수
	FileReader fr;
	BufferedReader reader;
	BufferedWriter writer;
	
	String tempString="";
	String reading="";
	
	Timer timer;
	
	
	JLabel beforeResultLabel=new JLabel("7일이 지나고 우리의 닭은...");
	JLabel ResultLabel=new JLabel(""); //이 되었습니다!
	ImageIcon chickenImage; //닭 이미지
	JLabel chickenImageLabel;//닭 이미지가 들어갈 라벨
	String chickenName="";
	JLabel ResultLabel2=new JLabel("도감에 기록합니다...");
	int chickenNumber=1;
	
	
	FinalResult(int bestiaryNumber) throws NumberFormatException, IOException{
		
		fr = new FileReader("bestiary.txt");
		reader =new BufferedReader(fr);
		while((reading=reader.readLine())!=null) {
			if(reading.split("!")[2].equals(""+bestiaryNumber)) {
				chickenName=reading.split("!")[1];
				chickenNumber=Integer.parseInt(reading.split("!")[2]);
			}
		}
		reading="";
		setSize(X,Y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setBackground(Color.black);
		
		beforeResultLabel.setFont(new Font("둥근모꼴",Font.BOLD,30));
		beforeResultLabel.setHorizontalAlignment(JLabel.CENTER);
		beforeResultLabel.setForeground(Color.white);
		c.add(beforeResultLabel);
		
		
		
        ResultLabel.setText(chickenName+"이 되었습니다!");
        chickenImage=new ImageIcon("ChickJavaPrj_Sprite\\chicken"+bestiaryNumber+".png");
        ResultLabel.setBounds(X/2-250,Y/8,500,100);
        ResultLabel.setFont(new Font("둥근모꼴",Font.PLAIN,40));
        
        ResultLabel2.setBounds(X/2-150,Y/8+100,500,100);
        ResultLabel2.setFont(new Font("둥근모꼴",Font.PLAIN,20));
        
        Image img=chickenImage.getImage();
        Image changedImage=img.getScaledInstance(512, 512, DO_NOTHING_ON_CLOSE);
        chickenImage=new ImageIcon(changedImage);
        chickenImageLabel=new JLabel(chickenImage);
        
        okButton.setBounds(X-280,Y-200,200,50);
        okButton.setFont(new Font("둥근모꼴",Font.PLAIN,17));
        okButton.setBorder(bb);
        okButton.setBackground(mybackColor);
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					reader =new BufferedReader(new FileReader("bestiary.txt"));
					while((reading=reader.readLine())!=null) {
						 if(reading.split("!")[2].equals(""+bestiaryNumber)) {
		                     tempString+=("1!"+chickenName+"!"+chickenNumber+"\n");}
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
				try {
					new Title(true);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		});
        
		timer = new Timer(2000 ,countdown);
		timer.start();
	}
	ActionListener  countdown = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            timer.stop();
            beforeResultLabel.setVisible(false);
            c.setLayout(null);
            c.setBackground(Color.yellow);

            c.add(ResultLabel);
            c.add(ResultLabel2);
            
            chickenImageLabel.setBounds(200,250,512,512);
            c.add(chickenImageLabel);

            c.add(okButton);

        }
    };

	public static void main(String[] args) throws NumberFormatException, IOException {
		new FinalResult(4);
	}
}
