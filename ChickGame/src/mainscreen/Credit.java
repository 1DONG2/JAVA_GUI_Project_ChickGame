package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Credit extends JFrame{
	Font basicFont= new Font("둥근모꼴",Font.PLAIN,20);
	
	final int X=1024;
	final int Y=768;
	
	Container c=null;
	JFrame my=this;
	
	JLabel[] words= new JLabel[6];
	JButton backButton= new JButton("뒤로가기");
	
	Credit(){
		setTitle("7일간 병아리 키우기");
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.PINK);
		my.setResizable(false);
		
		ImageIcon Kb = new ImageIcon("ChickJavaPrj_Sprite\\kickboard.png"); // 킥보드
		JLabel img = new JLabel(Kb);
		img.setBounds(530, 400, 256, 256);
		c.add(img);
		
		for (int i = 0; i < words.length; i++) {
			words[i]=new JLabel();
			words[i].setBounds(X/2-300,(Y/4)+i*70,700,50);
			words[i].setFont(new Font("둥근모꼴",Font.BOLD,30));
			c.add(words[i]);
		}
		words[0].setText("제작자들 (군산대학교 소프트웨어학과)");
		words[1].setText("PM, 디자인: 한동엽");
		words[2].setText("프로그래밍, 기획: 최민서");
		words[3].setText("프로그래밍: 서보선");
		words[4].setText("-");
		words[5].setText("2022년 12월 개발");
		
		backButton.setBounds(X*3/4,(int)(Y*0.5)+70+70,150,50);
		backButton.setFont(new Font("둥근모꼴",Font.BOLD,20));
		backButton.setBackground(new Color(240,255,255));
		c.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				my.dispose();
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
	
	public static void main(String[] args) {
		new Credit();
	}

}
