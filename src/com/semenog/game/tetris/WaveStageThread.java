package com.semenog.game.tetris;

import android.graphics.Point;
import android.view.SurfaceHolder;

public class WaveStageThread extends Thread {   
	static  long FPS = 1000;	
	static int channel = 2 ;
	private boolean running = false;	
	static int stagefield =1;
	static int wavechange = 1;
	Stage stage = new Stage();
	int temp = 0;
	int wave_column = ((16-10)/2);
	int wave_row = 0;
	int snake_column = ((16-10)/2)+4;
	int snake_row = 15;
	int snake_cnt = 1;
	boolean snake_flag = true;
	
	int [][] sfield ;
	
	private int wavecount = 0;
	
	
	public WaveStageThread() {
	}	
	public WaveStageThread(SurfaceHolder surfHolder, ISurface panel){ // �깮�꽦�옄�뿉 SurfaceHolder�� ISurface �씤�꽣�럹�씠�뒪瑜� 媛��졇���꽌 �젣�뼱�븷 �닔 �엳�떎.
		}
	
	public void setRunning(boolean runn){
		running = runn;
	}
	
	@Override
	public void run(){ 
		//GameView.downLoopThread.setRunning(true);					
				
		/*GameView.figure.posX = 7;
		GameView.figure.posY = 0;
		GameView.timerThread.setRunning(true);
		GameView.gameOver = false;*/			
		
		/*GameView.figure.posX = 7;
		GameView.figure.posY = 0;
		GameView.timerThread.setRunning(true);
		GameView.gameOver = false;		
		channel = 2;*/
		GameView.figure.posX = 7;
		GameView.figure.posY = 0;
		//GameView.gameOver = false;		
		channel = 2;
		
		while (true){
			
			if (running) {		
			
			if (GameView.lineCount <= 0) {				
				channel = 2;
				stagefield ++;
				GameView.figure.flag = false;
				GameView.timerThread.setRunning(false);
				GameView.downLoopThread.setRunning(false);
			}		
			
			synchronized (this) {
				
				switch(channel){
				case 0 :							
					ColorControlThread.changeColor = 1;
					/*try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					for (int row = 20; row >= 1; row--) {
						for (int column =((16-10)/2)+9; column >= ((16-10)/2); column--) {
							
							if (GameView.field[row][column] == 0) {
								GameView.field[row][column] = 1;
							}									
						}
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}		
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int row = 1; row <= 20; row++) {
						for (int column =((16-10)/2)+9; column >= ((16-10)/2); column--) {							
						
								GameView.field[row][column] = 0;
															
						}
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					GameView.downLoopThread.setRunning(true);					
					Stage stage = new Stage();
					stage.stageSetting(stagefield);
					channel = 1;
					GameView.figure.posX = 7;
					GameView.figure.posY = 0;
					GameView.timerThread.setRunning(true);
					GameView.gameOver = false;
					
					break;
				
				case 1 :				
					switch(WaveStageThread.wavechange){
					
					case 1:						
						
						
						for (int row = 1; row <= 20; row++) {
							
							for (int column =((16-10)/2); column <= ((16-10)/2)+9; column++) {
								
								// move one block to other position
								if(wave_column == column){									
									GameView.field[row-1][column] = GameView.field[row][column];
									
								}																			
							}							
						}
						
						for (int row = 20; row >= 1; row--) {
							
							for (int column =((16-10)/2); column <= ((16-10)/2)+9; column++) {
								
								// move one block to other position																
								if(wave_column == column){
									GameView.field[row+1][column-1] = GameView.field[row][column-1];
								}else if(wave_column == ((16-10)/2)){
									GameView.field[row+1][((16-10)/2)+9] = GameView.field[row][((16-10)/2)+9];
								}
								
								GameView.field[21][column] = 9;																		
							}							
						}
	
						
						if(wave_column == ((16-10)/2)+9) wave_column = ((16-10)/2);
						else wave_column ++;
						
						
						
					//WaveStageThread.wavechange = 2;
					break;
					
					case 2:
						
						for (int row = 1; row <= 20; row++) {
							for (int column =((16-10)/2)+9; column >= ((16-10)/2); column--) {
								
									if (column ==((16-10)/2)+9 ) {
										temp = GameView.field[row][column];
									}else{
									GameView.field[row][column+1] = GameView.field[row][column];
									}
									if (column ==((16-10)/2)) {
										GameView.field[row][((16-10)/2)] = temp;
									}
																		
							}
						}
						break;
						
					case 3:
						
						for (int row = 1; row <= 20; row++) {
							for (int column =((16-10)/2); column <= ((16-10)/2)+9; column++) {																
									
//								if(wave_column != ((16-10)/2)+9){							
								
								
								if(row == wave_row && wave_row != 20){
									temp = GameView.field[wave_row][column];
									GameView.field[wave_row][column] = GameView.field[wave_row+1][column];
									GameView.field[wave_row+1][column] = temp;
									
								}else if(row == wave_row && wave_row == 20){
									temp = GameView.field[wave_row][column];
									GameView.field[wave_row][column] = GameView.field[0][column];
									GameView.field[0][column] = temp;								
									
								}
													
														
							}
						}						
						
						
						if(wave_row == 20) wave_row = 0;
						else wave_row ++;
						
						
						
						break;
					case 4:
						for (int row = 1; row <= 20; row++) {
							for (int column =((16-10)/2); column <= ((16-10)/2)+9; column++) {
								if( row % 2 == 0){
									if (column == ((16-10)/2) ) {
										temp = GameView.field[row][column];
									}else{
									GameView.field[row][column-1] = GameView.field[row][column];
									}
									if (column ==((16-10)/2)+9) {
										GameView.field[row][((16-10)/2)+9] = temp;
									}
								}							
							}
						}
						for (int row = 1; row <= 20; row++) {
							for (int column =((16-10)/2)+9; column >=((16-10)/2) ; column--) {
								
								if( row % 2 == 1){
									if (column == ((16-10)/2)+9 ) {
										temp = GameView.field[row][column];
									}else{
									GameView.field[row][column+1] = GameView.field[row][column];
									}
									if (column ==((16-10)/2)) {
										GameView.field[row][column] = temp;
									}
								}
																		
							}
						}
						break;
					case 5:
						for (int row = 1; row <= 20; row++) {
							for (int column =((16-10)/2); column <= ((16-10)/2)+9; column++) {																
									
//								if(wave_column != ((16-10)/2)+9){
																
								if(column == wave_column && wave_column != ((16-10)/2)+9){
									temp = GameView.field[row][wave_column];
									GameView.field[row][wave_column] = GameView.field[row][wave_column+1];
									GameView.field[row][wave_column+1] = temp;
								}else if(column == wave_column && wave_column == ((16-10)/2)+9){
									temp = GameView.field[row][wave_column];
									GameView.field[row][wave_column] = GameView.field[row][((16-10)/2)];
									GameView.field[row][((16-10)/2)] = temp;
									
								}
													
														
							}
						}
						
						if(wave_column == ((16-10)/2)+9) wave_column = ((16-10)/2);
						else wave_column ++;
						break;
						
						
					}
					break;
				case 2 :
					
					/*GameView.timerThread.setRunning(false);
					GameView.downLoopThread.setRunning(false); */
					
					switch(WaveStageThread.stagefield){
					case 1:
						 sfield = new int[][]
						{
							 
							 	{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 2, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 2, 2, 5, 5, 5, 5 },
//									{5, 5, 5, 2, 2, 2, 2, 5, 5, 5 },
//									{5, 5, 5, 2, 2, 2, 2, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
//									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);		
							stage = new Stage();
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;		
					
					case 2:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);							
							stage = new Stage();
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;	
							
					case 3:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;	
					case 4:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;	
					case 5:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;	
					case 7:
						 sfield = new int[][]
						{
							 	{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;	
					case 8:
						 sfield = new int[][]
						{
							 	{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 9:
						 sfield = new int[][]
						{
							 	{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
								{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 10:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 11:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 12:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 13:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 14:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 15:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 16:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 17:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 18:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 19:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 20:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 21:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 22:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
					case 23:
						 sfield = new int[][]
						{
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 5, 5, 5, 5, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 5, 5, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 2, 2, 2, 2, 2, 2, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
									{5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },	
									
						};
						 
						    startStage(sfield);						 						
							stage = new Stage();							
							stage.stageSetting(stagefield);
							
							GameView.timerThread.setRunning(true);
							GameView.downLoopThread.setRunning(true);						
							GameView.figure.flag = true;
							//GameView.colorcontrolThread.setRunning(false);
							//GameView.colorcontrolThread.changeColor = 1;
							break;
							
					}
					
				case 3 :
						
						break;					
				
				}
				
				}				
			} 
			WaveStageThread.channel = 1;
			try {
				sleep(FPS);
			} catch (Exception e){
				e.printStackTrace();
			};
		}
	}
	
	
	private void startStage(int[][] sfield) {
				
		for (int row = 1; row <= 20; row++) {
			for (int column =((16-10)/2)+9; column >= ((16-10)/2); column--) {							
			 
				GameView.field[row][column] =	sfield[row-1][column-((16-10)/2)];
												
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int row = 1; row <= 20; row++) {
			for (int column =((16-10)/2)+9; column >= ((16-10)/2); column--) {							
			
					GameView.field[row][column] = 0;
												
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}
	
