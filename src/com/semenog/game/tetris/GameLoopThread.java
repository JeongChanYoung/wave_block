package com.semenog.game.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.semenog.game.tetris.Figure.FigureMotion;

public class GameLoopThread extends Thread {
	static final long FPS = 10;
	private boolean running = false;
	private SurfaceHolder surfaceHolder;
	
	static boolean gameover = false;
	int f_second = 0 ;
	int s_second = 0;
	int f_minute = 0;
	int s_minute = 0;
	
	int f_line = 0;
	int s_line = 0;
	static int gameTime;
	
	public GameLoopThread(SurfaceHolder surfHolder, ISurface panel){ 
//		this.view = view;
		
		this.surfaceHolder = surfHolder;
		
		panel.onInitialize();
	}
	
	public void setRunning(boolean runn){ 
		running = runn;
	}
	
	@Override
	public void run(){  
		/*long ticksPS = 2000 / FPS;
		long startTime;
		long sleepTime;*/
		
		while (running){
			Canvas canvas = null;
			
			//panel.onUpdate(timer);
			try {
				canvas = surfaceHolder.lockCanvas();
				if(Figure.getMatrix() != null && GameView.field != null && canvas != null){
			
					synchronized (surfaceHolder){
					//panel.onDraw(c);			
				  
				canvas.drawColor(Color.BLACK);					
				
				canvas.drawBitmap(GameView.images.get(7), GameView.BRICK_SIZE*(16-10)/2  -(int)GameView.BRICK_SIZE/2 
						, GameView.BRICK_SIZE -(int)GameView.BRICK_SIZE/2 , null);								
				
				canvas.drawBitmap(GameView.left, GameView.BRICK_SIZE*(((16-10)/2)-2), GameView.BRICK_SIZE*23, null);
				canvas.drawBitmap(GameView.right , GameView.BRICK_SIZE*(((16-10)/2)+1), GameView.BRICK_SIZE*23, null);
				canvas.drawBitmap(GameView.downedge, GameView.BRICK_SIZE*(((16-10)/2)+8), GameView.BRICK_SIZE*23, null);
				
				canvas.drawBitmap(GameView.l_rotate, GameView.BRICK_SIZE*(((16-10)/2)+5), GameView.BRICK_SIZE*23, null);
				canvas.drawBitmap(GameView.brickholder, GameView.BRICK_SIZE*(16-10)/8, 50, null);
				canvas.drawBitmap(GameView.r_rotate, GameView.BRICK_SIZE*(((16-10)/2)+11), GameView.BRICK_SIZE*23, null);
								
				/*canvas.drawBitmap(GameView.num1, GameView.BRICK_SIZE, GameView.BRICK_SIZE*7, null);
				canvas.drawBitmap(GameView.num2, GameView.BRICK_SIZE, GameView.BRICK_SIZE*9, null);
				canvas.drawBitmap(GameView.num3, GameView.BRICK_SIZE, GameView.BRICK_SIZE*11, null);
				canvas.drawBitmap(GameView.num4, GameView.BRICK_SIZE, GameView.BRICK_SIZE*13, null);
				canvas.drawBitmap(GameView.num5, GameView.BRICK_SIZE, GameView.BRICK_SIZE*15, null);
				canvas.drawBitmap(GameView.num6, GameView.BRICK_SIZE, GameView.BRICK_SIZE*17, null);
				canvas.drawBitmap(GameView.num7, GameView.BRICK_SIZE, GameView.BRICK_SIZE*19, null);
				canvas.drawBitmap(GameView.num8, GameView.BRICK_SIZE, GameView.BRICK_SIZE*21, null);
				canvas.drawBitmap(GameView.num9, GameView.BRICK_SIZE, GameView.BRICK_SIZE*23, null);
				canvas.drawBitmap(GameView.num0, GameView.BRICK_SIZE, GameView.BRICK_SIZE*25, null);*/
				
				
				canvas.drawBitmap(GameView.line, ((GameView.BRICK_SIZE*((16-10)/2)-(GameView.BRICK_SIZE/2) - GameView.time.getWidth()))/2 , GameView.BRICK_SIZE*8, null);
												
				f_line = GameView.lineCount%10;
				s_line = GameView.lineCount/10;
				
				if (GameView.lineCount >=0) {
					canvas.drawBitmap(GameView.lnum_image.get(s_line), (float) ((GameView.BRICK_SIZE*1.25) - GameView.BRICK_SIZE/2)
							,GameView.BRICK_SIZE*9, null);
					canvas.drawBitmap(GameView.lnum_image.get(f_line), (float) ((GameView.BRICK_SIZE*1.25) - GameView.BRICK_SIZE/2+GameView.BRICK_SIZE/2)
						,GameView.BRICK_SIZE*9, null);					
					
				}
						
				if (GameLoopThread.gameTime >= 0) {
					
					canvas.drawBitmap(GameView.time, ((GameView.BRICK_SIZE*((16-10)/2)-(GameView.BRICK_SIZE/2) - GameView.time.getWidth()))/2 , GameView.BRICK_SIZE*5, null);
					
					f_second = (gameTime%60)%10;
					s_second = (gameTime%60)/10;
				    f_minute = (gameTime/60)%10;
					s_minute = (gameTime/60)/10;							
					
					canvas.drawBitmap(GameView.num_image.get(s_minute), GameView.BRICK_SIZE*(16-10)/14 
							 ,GameView.BRICK_SIZE*6, null);			 
					 canvas.drawBitmap(GameView.num_image.get(f_minute), GameView.BRICK_SIZE*(16-10)/14 + GameView.num_image.get(s_minute).getWidth()
							 ,GameView.BRICK_SIZE*6, null);		
					 canvas.drawBitmap(GameView.num_image.get(10), GameView.BRICK_SIZE*(16-10)/14 + GameView.num_image.get(s_minute).getWidth() + GameView.num_image.get(f_minute).getWidth()
							 ,GameView.BRICK_SIZE*6, null);	
					 canvas.drawBitmap(GameView.num_image.get(s_second), GameView.BRICK_SIZE*(16-10)/14 + GameView.num_image.get(s_minute).getWidth() + GameView.num_image.get(f_minute).getWidth() + GameView.num_image.get(10).getWidth()
							 ,GameView.BRICK_SIZE*6, null);		
					 canvas.drawBitmap(GameView.num_image.get(f_second), GameView.BRICK_SIZE*(16-10)/14 + GameView.num_image.get(s_minute).getWidth() + GameView.num_image.get(f_minute).getWidth() + GameView.num_image.get(10).getWidth()+ GameView.num_image.get(s_second).getWidth()
							 ,GameView.BRICK_SIZE*6, null);						
				}
				 
				
				Paint paint = new Paint();
				paint.setAlpha(95);		
			for (int k = 1; k < Figure.bricksList.size(); k++) {
				int [][] nextmatrix = Figure.getDefaultMatrix( Figure.bricksList.get(k));
				for (int row = 0; row < nextmatrix.length; ++row){
					for (int column = 0; column < nextmatrix[row].length; ++column){
						for (int i = 0; i < 7; i++) {
							if (nextmatrix[row][column] == i+1) {
								paint.setAlpha(275-(k*35));
								canvas.drawBitmap(GameView.simages.get(i), (column)*GameView.BRICK_SIZE/2 +GameView.BRICK_SIZE*(16-(16-10)/2 + ((16-10)/4)), 
										(row )*GameView.BRICK_SIZE/2  + GameView.BRICK_SIZE*(2*k), paint);						
							}
						}																
					}
				}				
			}										
						
						/*if (Figure.bricksList.get(2) != null) {
							int [][] nextmatrix2 = Figure.getDefaultMatrix( Figure.bricksList.get(2));
							for (int row = 0; row < nextmatrix2.length; ++row){
								for (int column = 0; column < nextmatrix2[row].length; ++column){
									for (int i = 0; i < 7; i++) { 
										if (nextmatrix2[row][column] == i+1) {
											canvas.drawBitmap(GameView.simages.get(i), (column)*GameView.BRICK_SIZE/3 +GameView.BRICK_SIZE*13-GameView.BRICK_SIZE/3, 
													(row )*GameView.BRICK_SIZE/3  + GameView.BRICK_SIZE*19, null);
										}
									}																
								}
							}	
						}*/
						
						
									
						/*if (Figure.bricksHold != null) {
							int [][] holdmatrix1 = Figure.getDefaultMatrix( Figure.bricksHold.get(0));
							for (int row = 0; row < holdmatrix1.length; ++row){
								for (int column = 0; column < holdmatrix1[row].length; ++column){
									for (int i = 0; i < 7; i++) {
										if (holdmatrix1[row][column] == i+1) {
											canvas.drawBitmap(GameView.simages.get(i), (column)*15 +20, (row )*15 + 50, null);
										}
									}																
								}
							}	
						}*/						
						
			}						
					
				Paint paint = new Paint();
				paint.setAlpha(50);
				
				GameView.figure.pos2.x = (int) GameView.figure.posX;
				
				canMoveFigure(Figure.FigureMotion.Down);
					
					/*p.setColor(fgColor);*/
				
				
				
				if(GameView.field != null ){
					for (int row = 1; row < GameView.field.length; ++row){
						for (int column = 1; column < GameView.field[row].length; ++column){
							if (GameView.field[row][column] == 1)	{ 
								canvas.drawBitmap(GameView.image1, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[0]);  
							}else if (GameView.field[row][column] == 2)	{
								canvas.drawBitmap(GameView.image2, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[1]);
							}else if (GameView.field[row][column] == 3)	{
								canvas.drawBitmap(GameView.image3, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[2]);
							}else if (GameView.field[row][column] == 4)	{
								canvas.drawBitmap(GameView.image4, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[3]);
							}else if (GameView.field[row][column] == 5)	{
								canvas.drawBitmap(GameView.image5, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[4]);
							}else if (GameView.field[row][column] == 6)	{
								canvas.drawBitmap(GameView.image6, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[5]);
							}else if (GameView.field[row][column] == 7)	{
								canvas.drawBitmap(GameView.image7, column*GameView.BRICK_SIZE, row*GameView.BRICK_SIZE, Figure.paint[6]);
							}
						}
					}
				}				
					GameView.figure.DrawFigure(canvas, GameView.images);
				}
			} finally {
				if (null != canvas){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			
			try {
				sleep(10);
			} catch (Exception e){
				e.printStackTrace();
			}					
			
		}
	}
	
	private boolean canMoveFigure(FigureMotion down) {
		Point p;
		int f[][];
		p = new Point (0,1);
		f = GameView.figure.getMatrix();
		GameView.figure.pos2.y = 0;
		if (f != null && GameView.field != null) {
			while(true){
				for (int row = 0 ; row < f.length; ++row){
					for (int column = 0; column < f[row].length; ++column){
//						System.out.println("GameView.figure.pos2.y : " + String.valueOf(GameView.figure.pos2.y) +
//										   " p.y : " + String.valueOf(p.y) + " row : " + String.valueOf(row) );
						if ((f[row][column] != 0) && (GameView.field[GameView.figure.pos2.y + p.y + row][GameView.figure.pos2.x + p.x + column] !=0)   ){
							return false;
						}
					}
				}
				++GameView.figure.pos2.y;
				}
		}
		return false;	
		
	}
}
