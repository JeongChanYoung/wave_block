package com.semenog.game.tetris;

import android.view.SurfaceHolder;

public class TimerThread extends Thread {
	static final long FPS = 10;
	static boolean flag = true;
	private boolean running = false;
	private SurfaceHolder surfaceHolder;
	private ISurface panel;
	private ColorControlThread colorcontrolThread;
	
	public TimerThread(SurfaceHolder surfHolder, ISurface panel){
		
		this.panel = panel;
		this.surfaceHolder = surfHolder;
		
	}
	
	public void setRunning(boolean runn){
		running = runn;
	}
	
	@Override
	public void run(){			    	
						
		while(true){	 				 				   			  			 
			
			if(running){
				 if(GameLoopThread.gameTime ==10 ){
					 /*colorcontrolThread = new ColorControlThread(surfaceHolder, panel);*/
					/* GameView.colorcontrolThread.setRunning(true);
						try {
							GameView.colorcontrolThread.start();
							
						} catch (IllegalThreadStateException e) {
							e.printStackTrace();
						}*/
					 
				 }
				 if (GameLoopThread.gameTime == 0 ) {				
					boolean retry = true;				
					//GameView.colorcontrolThread.setRunning(false);								
				
					GameView.gameOver = true;
					GameView.downLoopThread.setRunning(false);
					GameView.timerThread.setRunning(false);		
					GameView.wavestageThread.channel = 0;
					
					
				}
				 
				 GameLoopThread.gameTime --;		
				try {
					sleep(1000);
				} catch (Exception e){
					e.printStackTrace();
				}		
		}
		}
		   
	}
	}

