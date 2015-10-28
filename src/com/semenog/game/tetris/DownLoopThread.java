package com.semenog.game.tetris;

import android.view.SurfaceHolder;

public class DownLoopThread extends Thread {
	static long sleepTime;
	static boolean flag = true;
	private boolean running = false;
	
	private ISurface panel;
	private long timer;
	
	public DownLoopThread(SurfaceHolder surfHolder, ISurface panel){
		
		this.panel = panel;
		
	}
	
	public void setRunning(boolean runn){
		running = runn;
	}
	
	@Override
	public void run(){		
		
		while (true){
			if (running) {		
			
			if(flag){
			panel.onUpdate(timer);
			}
			try {
				sleep((sleepTime > 0)? sleepTime : 10);
			} catch (Exception e){
				e.printStackTrace();
			}
			
			}
		}
	}
}
