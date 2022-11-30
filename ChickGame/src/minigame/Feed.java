package minigame;
import java.awt.*;
import java.util.Random;
public class Feed {
	public static int window_width=1024; // 윈도우 크기
	public static int window_height=768; // 윈도우 크기
	Image img;
	int x, y;
	int w, h;
	int fy;//떨어지는 속도
	//height, width화면 크기
	
	boolean isDead=false; // 먹이가 죽었는지
	
	// 떨어지는 먹이 x, y 랜덤 설정
	public Feed(Image imgImage){
		img = imgImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH); //이미지 크기 설정
		w = h = 32;
		Random rd = new Random();
		x = rd.nextInt(window_width-w*2)+w;
		y = -h;
		fy = rd.nextInt(15)+1;
	}
	
	public void move()
	{
		y += fy;
		if(y>window_height)isDead=true;
	}
}
