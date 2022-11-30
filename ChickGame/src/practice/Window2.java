package practice;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Window2 extends JFrame{
	final int X=1024;
	final int Y=768;
	Container c;
	JButton btn1=new JButton("창 1 열기");
	
	Window2(){
		this.setSize(X,Y);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setTitle("창 2");
		c=getContentPane();
		c.add(btn1);
		
		 btn1.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	             new Window1();
	             setVisible(false); // 창 안보이게 하기 
	         }
	     });
	}
	public static void main(String[] args) {
		new Window2();

	}

}