package mainscreen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import mainscreen.Title;
import java.io.*;
import java.sql.*;
import java.net.*;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Option extends JFrame{
	
	// Swing 디자인
	Font basicFont= new Font("둥근모꼴",Font.PLAIN,20); // 폰트 적용
	LineBorder bb = new LineBorder(Color.black, 2, true);
	Color mybackColor=new Color(240,255,255);

	final int X=1024;	//해상도
	final int Y=768;
	boolean ismusicactivated=true; //음악이 꺼졌는지 켜졌는지 확인
	Container c=null;
	JFrame my=this;
	
	JLabel optionLabel=new JLabel("옵션");
	
	JLabel soundLabel= new JLabel("배경음악");
	JCheckBox sound=new JCheckBox("", true); // 배경음악 체크박스
	JButton resetButton= new JButton("백업"); // 데이터를 SQL 데이터에 저장합니다.
	JButton backButton= new JButton("뒤로가기");
	
	FileReader fr;
	BufferedReader reader;
	
	
	Option(Clip clip){

		
		setTitle("7일간 병아리 키우기");
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.PINK);
		
		optionLabel.setBounds(X/2-200,Y/4,500,60);	// 옵션 글자
		optionLabel.setFont(basicFont);
		c.add(optionLabel);
		
		soundLabel.setBounds(X*3/4+10,Y/2-70,150,50);	// 배경음악 글자
		soundLabel.setFont(basicFont);
		c.add(soundLabel);
		
		sound.setBounds(X*3/4+120,Y/2-55,20,20);	// 배경음악 체크박스
		sound.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					clip.start();
					ismusicactivated=true;
				}
				else{
					clip.stop();
					ismusicactivated=false;
	            }
			}
		});
		c.add(sound);
		
		// 백업 버튼으로 만들 예정
		resetButton.setBounds(X*3/4,Y/2,150,50);	
		resetButton.setFont(basicFont);
		resetButton.setBackground(mybackColor);
		c.add(resetButton);
		resetButton.setBorder(bb);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					backup();
				}
			});
		
		backButton.setBounds(X*3/4,Y/2+70,150,50);	//뒤로가기 버튼
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
	
	// 백업기능
	void backup() {
		BufferedReader in = null;
		BufferedWriter out = null;
		Socket socket = null;
		// 읽기관련 변수
		FileReader fr;
		BufferedReader reader;

		String tempString = "";
		String reading = "";

		// 배열
		String[] stringStat = new String[10];

		// 다음 줄이 없다는 걸 확인
		String tmp = "";
		try {
			socket = new Socket("localhost", 8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// 스탯 읽기
			fr = new FileReader("stat.txt");
			reader = new BufferedReader(fr);
			int n = 0;
			while ((tmp = reader.readLine()) != null) {
				reading = tmp;
				stringStat[n] = reading.split(":")[1];
				// 0:이름, 1:성별, 2:일차, 3:행복, 4:건강, 5:센스, 6:성실, 7:기력, 8:친밀도 9:알종류 순
				tempString += (stringStat[n] + ",");
				n++;
			}
			System.out.println(tempString);
			fr.close(); // 파일 닫기
			out.write(tempString);
			out.flush();
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(socket != null) socket.close();
			}catch(IOException e) {
				System.out.println("오류 발생!");
			}
		}
	}
	
	public static void main(String[] args) {
		new Option(null);
	}
}
