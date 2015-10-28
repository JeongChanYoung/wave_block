package com.semenog.game.tetris;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class ColorControlThread extends Thread {   // 그림을 직접 그리는 게 아니라. static paint객체에 접근하여 컬러값을 제어하기 위한 클래스
	static final long FPS = 10;	
	private boolean running = false;	
	static boolean changeColorFlag = true;
	private ColorMatrix cm [] = new ColorMatrix[7];
	ColorMatrixColorFilter [] f = new ColorMatrixColorFilter[7];
	
	public ColorControlThread() {
	}
	
	public ColorControlThread(SurfaceHolder surfHolder, ISurface panel){ // 생성자에 SurfaceHolder와 ISurface 인터페이스를 가져와서 제어할 수 있다.
		}
	static float changeColor, changeColor1, changeColor2, changeColor3, changeColor4, changeColor5, changeColor6, changeColor7 = 1;
	public void setRunning(boolean runn){ // 이건 그냥 함수
		running = runn;
	}
	
	@Override
	public void run(){   // 스레드 실행 함수
	
		
		for (int i = 0; i < 7; i++) {
			Figure.paint[i] = new Paint();
			cm[i] = new ColorMatrix();
			
			f[i] = new ColorMatrixColorFilter(cm[i]);
			Figure.paint[i].setColorFilter(f[i]);
	      
		}
		
		changeColor = (float) 0.7;
		
		while (true){
			if (running) {
				
			
			cm[0].setSaturation(changeColor);			
	        cm[1].setSaturation(changeColor);	
	        cm[2].setSaturation(changeColor);	
	        cm[3].setSaturation(changeColor);	
	        cm[4].setSaturation(changeColor);	
	        cm[5].setSaturation(changeColor);	
	        cm[6].setSaturation(changeColor);    
	      
	             
	        f[0] = new ColorMatrixColorFilter(cm[0]);
	        f[1] = new ColorMatrixColorFilter(cm[1]);
	        f[2] = new ColorMatrixColorFilter(cm[2]);
	        f[3] = new ColorMatrixColorFilter(cm[3]);
	        f[4] = new ColorMatrixColorFilter(cm[4]);
	        f[5] = new ColorMatrixColorFilter(cm[5]);
	        f[6] = new ColorMatrixColorFilter(cm[6]);
	        
		        Figure.paint[0].setColorFilter(f[0]);
		        Figure.paint[1].setColorFilter(f[1]);
		        Figure.paint[2].setColorFilter(f[2]);
		        Figure.paint[3].setColorFilter(f[3]);
		        Figure.paint[4].setColorFilter(f[4]);
		        Figure.paint[5].setColorFilter(f[5]);
		        Figure.paint[6].setColorFilter(f[6]);
		        
		        
		      
		        	if (changeColorFlag &&  changeColor < 0.7 ) {
						changeColor += 0.1;
					}else if(!changeColorFlag && changeColor >0.1){
						changeColor -= 0.1;
					}else if(changeColor >=0.7){
						changeColorFlag = false;
					}else if(changeColor <0.1){
						changeColorFlag = true;
					}
				
				
				
			try {
				sleep(100);
			} catch (Exception e){
				e.printStackTrace();
			};
		}
	}}
}
	
