package com.semenog.game.tetris;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameView extends SurfaceView implements ISurface{
	static int BRICK_SIZE = 38;
	//static final int BRICK_GAP_SIZE = 1; 

	protected static int _iWidth;
	protected static int _iHeight;
	
	protected static int bWidth;
	protected static int bHeight;	
	
	protected float fx, fy, sx, sy;
	protected float acc = 0;
	
	static boolean gameOver = false;
	
	protected static HashMap<Integer, Bitmap> num_image, lnum_image;
	
	
	protected static int stage;
	protected static int lineCount = 10;
	
	public static Bitmap bg, image1,image2,image3,image4,image5,image6,image7 ;
	public static Bitmap simage1,simage2,simage3,simage4,simage5,simage6,simage7 ;
	public static Bitmap brickholder, brickholderclicked;
	public static Bitmap left, right, l_rotate, r_rotate, down, downedge;
	public static Bitmap numbers, num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,colon;
	public static Bitmap snum1,snum2,snum3,snum4,snum5,snum6,snum7,snum8,snum9,snum0,scolon;
	public static Bitmap lnum1,lnum2,lnum3,lnum4,lnum5,lnum6,lnum7,lnum8,lnum9,lnum0;
	public static Bitmap time, line;
	public static ArrayList<Bitmap> images = null;
	public static ArrayList<Bitmap> simages = null;
	
	
	static int score = 0;

	Handler handler;
	SurfaceHolder holder;
	
	public static GameLoopThread gameLoopThread;
	public static DownLoopThread downLoopThread;
	//public static ColorControlThread colorcontrolThread;
	public static TimerThread timerThread;
	public static WaveStageThread wavestageThread;

	static int bgColor;
	static int fgColor;
	Paint p;
	
	Context context;
	
	
	
	
	static int field[][]; 
	
	static Figure figure;
	
	public GameView(Context context) {
		super(context);
		
		this.context = context;		
		
		p = new Paint();
		gameOver = false;
		handler = new Handler();
		figure = new Figure();
		holder = getHolder();
		_iWidth =context.getResources().getDisplayMetrics().widthPixels;
		_iHeight =context.getResources().getDisplayMetrics().heightPixels;
		
		initField();
		
		gameLoopThread = new GameLoopThread(holder, this);
		downLoopThread = new DownLoopThread(holder, this);
		//colorcontrolThread = new ColorControlThread(holder, this);
		timerThread = new TimerThread(holder,this);
		wavestageThread = new WaveStageThread(holder, this);
		
		bg = BitmapFactory.decodeResource(context.getResources(),  R.drawable.bg1);			
		bg = Bitmap.createScaledBitmap(bg, (int)(BRICK_SIZE*10*1.1), (int)(BRICK_SIZE*20*1.05), true);
		brickholder = BitmapFactory.decodeResource(context.getResources(),  R.drawable.brickholder);
		brickholderclicked = BitmapFactory.decodeResource(context.getResources(),  R.drawable.brickholderclicked);
		
		left = BitmapFactory.decodeResource(context.getResources(), R.drawable.left);
		left = Bitmap.createScaledBitmap(left, BRICK_SIZE, BRICK_SIZE, true);
		right = BitmapFactory.decodeResource(context.getResources(), R.drawable.right);
		right = Bitmap.createScaledBitmap(right, BRICK_SIZE, BRICK_SIZE, true);
		l_rotate = BitmapFactory.decodeResource(context.getResources(), R.drawable.l_rotate);
		l_rotate = Bitmap.createScaledBitmap(l_rotate, BRICK_SIZE, BRICK_SIZE, true);
		r_rotate = BitmapFactory.decodeResource(context.getResources(), R.drawable.r_rotate);
		r_rotate = Bitmap.createScaledBitmap(r_rotate, BRICK_SIZE, BRICK_SIZE, true);
		down = BitmapFactory.decodeResource(context.getResources(), R.drawable.down);
		down = Bitmap.createScaledBitmap(down, BRICK_SIZE, BRICK_SIZE, true);
		downedge = BitmapFactory.decodeResource(context.getResources(), R.drawable.downedge);
		downedge = Bitmap.createScaledBitmap(downedge, BRICK_SIZE, BRICK_SIZE, true);
		
		time = BitmapFactory.decodeResource(context.getResources(), R.drawable.time);
		time = Bitmap.createScaledBitmap(time, BRICK_SIZE*2, BRICK_SIZE, true);
		line = BitmapFactory.decodeResource(context.getResources(), R.drawable.line);
		line = Bitmap.createScaledBitmap(line, BRICK_SIZE, BRICK_SIZE, true);
		line = Bitmap.createScaledBitmap(line, BRICK_SIZE*2, BRICK_SIZE, true);
		
		num0 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num0);
		snum0 = Bitmap.createScaledBitmap(num0, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum0 = Bitmap.createScaledBitmap(num0, BRICK_SIZE/2, BRICK_SIZE/2, true);		
		num1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num1);
		snum1 = Bitmap.createScaledBitmap(num1, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum1 = Bitmap.createScaledBitmap(num1, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num2);
		snum2 = Bitmap.createScaledBitmap(num2, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum2 = Bitmap.createScaledBitmap(num2, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num3);
		snum3 = Bitmap.createScaledBitmap(num3, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum3 = Bitmap.createScaledBitmap(num3, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num4 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num4);
		snum4 = Bitmap.createScaledBitmap(num4, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum4 = Bitmap.createScaledBitmap(num4, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num5 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num5);
		snum5 = Bitmap.createScaledBitmap(num5, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum5 = Bitmap.createScaledBitmap(num5, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num6 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num6);
		snum6 = Bitmap.createScaledBitmap(num6, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum6 = Bitmap.createScaledBitmap(num6, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num7 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num7);
		snum7 = Bitmap.createScaledBitmap(num7, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum7 = Bitmap.createScaledBitmap(num7, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num8 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num8);
		snum8 = Bitmap.createScaledBitmap(num8, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum8 = Bitmap.createScaledBitmap(num8, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num9 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num9);
		snum9 = Bitmap.createScaledBitmap(num9, BRICK_SIZE/3, BRICK_SIZE/3, true);
		lnum9 = Bitmap.createScaledBitmap(num9, BRICK_SIZE/2, BRICK_SIZE/2, true);
		num0 = BitmapFactory.decodeResource(context.getResources(),R.drawable.num0);	
		colon = BitmapFactory.decodeResource(context.getResources(),R.drawable.colon);	
		scolon = Bitmap.createScaledBitmap(colon, BRICK_SIZE/3, BRICK_SIZE/3, true);
		
		num_image = new HashMap<Integer, Bitmap>();		
		num_image.put(1, GameView.snum1);
		num_image.put(2, GameView.snum2);
		num_image.put(3, GameView.snum3);
		num_image.put(4, GameView.snum4);
		num_image.put(5, GameView.snum5);
		num_image.put(6, GameView.snum6);
		num_image.put(7, GameView.snum7);
		num_image.put(8, GameView.snum8);
		num_image.put(9, GameView.snum9);
		num_image.put(0, GameView.snum0);	
		num_image.put(10, GameView.scolon);	
		
		lnum_image = new HashMap<Integer, Bitmap>();	
		lnum_image.put(1, GameView.lnum1);
		lnum_image.put(2, GameView.lnum2);
		lnum_image.put(3, GameView.lnum3);
		lnum_image.put(4, GameView.lnum4);
		lnum_image.put(5, GameView.lnum5);
		lnum_image.put(6, GameView.lnum6);
		lnum_image.put(7, GameView.lnum7);
		lnum_image.put(8, GameView.lnum8);
		lnum_image.put(9, GameView.lnum9);
		lnum_image.put(0, GameView.lnum0);	
		
		
		image1 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image01);
		image1 = Bitmap.createScaledBitmap(image1, BRICK_SIZE, BRICK_SIZE, true);
		image2 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image02);
		image2 = Bitmap.createScaledBitmap(image2, BRICK_SIZE, BRICK_SIZE, true);
		image3 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image03);
		image3 = Bitmap.createScaledBitmap(image3, BRICK_SIZE, BRICK_SIZE, true);
		image4 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image04);
		image4 = Bitmap.createScaledBitmap(image4, BRICK_SIZE, BRICK_SIZE, true);
		image5 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image05);
		image5 = Bitmap.createScaledBitmap(image5, BRICK_SIZE, BRICK_SIZE, true);
		image6 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image06);
		image6 = Bitmap.createScaledBitmap(image6, BRICK_SIZE, BRICK_SIZE, true);
		image7 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.image07);
		image7 = Bitmap.createScaledBitmap(image7, BRICK_SIZE, BRICK_SIZE, true);
			
		simage1 =  Bitmap.createScaledBitmap(image1, BRICK_SIZE/2, BRICK_SIZE/2, true);
		simage2 = Bitmap.createScaledBitmap(image2, BRICK_SIZE/2, BRICK_SIZE/2, true);
		simage3 = Bitmap.createScaledBitmap(image3, BRICK_SIZE/2, BRICK_SIZE/2, true);
		simage4 = Bitmap.createScaledBitmap(image4, BRICK_SIZE/2, BRICK_SIZE/2, true);
		simage5 = Bitmap.createScaledBitmap(image5, BRICK_SIZE/2, BRICK_SIZE/2, true);
		simage6 = Bitmap.createScaledBitmap(image6, BRICK_SIZE/2, BRICK_SIZE/2, true);
		simage7 = Bitmap.createScaledBitmap(image7, BRICK_SIZE/2, BRICK_SIZE/2, true);
		
		simages = new ArrayList<Bitmap>();
		simages.add(simage1);
		simages.add(simage2);
		simages.add(simage3);
		simages.add(simage4);
		simages.add(simage5);
		simages.add(simage6);
		simages.add(simage7);	
				
		images = new ArrayList<Bitmap>();
		images.add(image1);
		images.add(image2);
		images.add(image3);
		images.add(image4);
		images.add(image5);
		images.add(image6);
		images.add(image7);		
		images.add(bg);
		
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				_iWidth = width;
				_iHeight = height;
				
				//initField();
			}

			@Override
			public  void surfaceCreated(SurfaceHolder holder) {
			
				
				 
				GameView.gameLoopThread.setRunning(true);
				try {
					GameView.gameLoopThread.start();
					
					
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				}
				GameView.downLoopThread.setRunning(true);
				try {
					GameView.downLoopThread.start();
				
					
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				} 
				GameView.timerThread.setRunning(true);
				try {
					GameView.timerThread.start();				
					
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				} 
				wavestageThread.setRunning(true);
				try {
					wavestageThread.start();
					
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				}
				
				
				GameView.timerThread.setRunning(false);
				GameView.downLoopThread.setRunning(false);
				bgColor = getResources().getColor(R.color.bg_color);
				fgColor = getResources().getColor(R.color.fg_color);
				
				
				
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				downLoopThread.setRunning(false);
				//colorcontrolThread.setRunning(false);
				timerThread.setRunning(false);
				wavestageThread.setRunning(false);
				Figure.bricksList.clear();
			    Figure.flag = false;
				//ColorControlThread.changeColor = 1;
				
				/*while (retry){
					try {
						gameLoopThread.join();
						downLoopThread.join();
						colorcontrolThread.join();
						timerThread.join();
						wavestageThread.join();
						retry = false;
					} catch (InterruptedException e){
						e.printStackTrace();
					}
				}*/
			}
			
		});
	}	
	
	
	@Override
	public void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		canvas.drawColor(bgColor);				
		
		/*canvas.drawBitmap(bg, (bWidth*2)-19  , bWidth -19, null);	*/	
							
	}
	
	public void initField(){
		bWidth = _iWidth/16 ;
		bHeight = _iHeight/bWidth ;		
		BRICK_SIZE = bWidth;
	
		field = new int[bHeight][16];
		CleanupField();		
		
	}
	
	
	@Override
	public void onInitialize() {
		
		/*
		Stage stage = new Stage();
		stage.stageSetting(1);*/
				
		WaveStageThread.channel = 2;
		WaveStageThread.stagefield = 1;
		
		/*figure.pos = new Point(14/2,0);*/
		figure.posX = 7;
		figure.posY = 0;
		figure.pos2 = new Point(16/2,0);
		
		
		for (int i = 0; i < 8; i++) {
			Figure.makeBricks();	
		}		
		figure.type = Figure.bricksList.get(0);
		
		 Figure.flag = false;
		for (int i = 0; i < 7; i++) {
/*			Figure.paint[i] = null;*/
			Figure.paint[i] = new Paint();
			Figure.paint[i].setAntiAlias(true);
		}
		
	}
	private void CleanupField() {
		for (int j = 1; j < field[0].length; ++j){//first and last lines
			//field[0][j] = 1;
			field[21][j] = 9;
		}
		for (int i = 1; i < field.length ; ++i){
			field[i][((16-10)/2)-1] = 9;
			field[i][((16-10)/2)+10] = 9;
		}
	}

	void moveDownFigure(){
		if (canMoveFigure(Figure.FigureMotion.Down))
			/*++figure.pos.y;*/
			++figure.posY;
		else{
						
			putFigure();					
			
			synchronized (this) {
				Figure.makeBricks();
				Figure.bricksList.remove(0);		
			}			
			
			gethercompletedLines();
			
			if (lineCount ==0) {
				Stage nextstage = new Stage();
				nextstage.stageClear(stage);
				
			}
			
			//figure.refresh();
			/*figure.pos.set(14/2, 0);*/
			figure.posX = 7;
			figure.posY = 0;
			figure.pos2.set(16/2, 0);
			Figure.type = Figure.bricksList.get(0);
			gameOver = isGameOver();
		}
	}
	//
	// field
	// [ 0 9 0 0 0 0 0 0 9 0 ] 
	// [ 0 9 0 0 0 0 0 0 9 0 ]
	// [ 0 9 0 0 0 0 0 0 9 0 ]
	// [ 0 9 0 0 0 0 0 0 9 0 ]
	// [ 0 9 0 0 0 0 0 0 9 0 ]
	// [ 0 9 0 0 0 0 0 0 9 0 ]
	// [ 0 9 0 0 0 0 0 0 9 0 ]
	// [ 0 9 9 9 9 9 9 9 9 0 ]
	//
		


	void gethercompletedLines(){
		boolean completedLine;
		boolean emptyLine;
		int completedLinesCount = 0; 
		
		out:
		for (int row = (int) (figure.posY+ figure.getMatrix().length - 1); row > 0; --row){
			completedLine = true;
			emptyLine = true;  
			for (int column = (16-10)/2; column < field[0].length  - (16-10)/2; ++column){ //start from bottom most line figure reached
				if (field[row+completedLinesCount][column] !=0  ) {
					emptyLine = false; //in order to skip cheking if top of stack is reached
					field[row+completedLinesCount][column] = field[row][column]; 
				}
				if (field[row][column] ==0)
					completedLine = false; // completedLine
			}
			if (emptyLine)
				break out; //skip cheking if top of stack is reached
			if (completedLine){
				++completedLinesCount;
				-- lineCount;
			}
		}
		
		score += completedLinesCount*completedLinesCount;
	}
	
	boolean canMoveFigure(Figure.FigureMotion motion){
		Point p;
		int f[][];
		switch (motion){
		case Down:
			p = new Point (0,1);
			f = figure.getMatrix();
			break;
		case Left:
			p = new Point (-1,0);
			f = figure.getMatrix();
			break;
		case Right:
			p = new Point (1,0);
			f = figure.getMatrix();
			break;
		case RotateR:
			p = new Point (0,0);
			f = figure.getMatrix(false);
			break;
		case RotateL:
			p = new Point (0,0);
			f = figure.getMatrix(true);
			break;
		default:
			throw new IllegalArgumentException("Unhandled MotionType!");	
		}
		
		
		for (int row = 0 ; row < f.length; ++row){
			for (int column = 0; column < f[row].length; ++column){
				if ((f[row][column] != 0) && (field[ (int) (figure.posY + p.y + row)][(int) (figure.posX + p.x + column)] !=0)  ){
					return false;
				}
			}
		}
		
		return true;
	}
	
	void putFigure(){
		int f [][] = figure.getMatrix();
		for (int row = 0; row < f.length; ++row)
			for (int column = 0; column < f[row].length; ++column)
				
				if(f[row][column] != 0)
				field[(int) (figure.posY+row)][(int) (figure.posX+column)] = f[row][column] ;		
	}
	
	boolean isGameOver(){
		
		int f [][] = figure.getMatrix();
		for (int row = 0 ; row < f.length; ++row){
			for (int column = 0; column < f[row].length; ++column){
				if (f[row][column] !=0 && field[(int) (figure.posY+row+1)][(int) (figure.posX+column)] !=0){
					handler.post(new Runnable(){
					    public void run(){
					        Toast.makeText(context, "Game Over!", Toast.LENGTH_LONG).show();
					        WaveStageThread.channel = 0;
					    }
					});
					
					GameView.timerThread.setRunning(false);							
				    gameOver = true;
				    //ColorControlThread.changeColor = 1;
				   // GameView.colorcontrolThread.setRunning(false);
					
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void onUpdate(long gameTime) {
		if (!gameOver)
			moveDownFigure();
				
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
	
		
		fx = event.getX();		 fy = event.getY();
		
		
		if (!gameOver) {
		
		
		if(event.getAction() == event.ACTION_DOWN){  
		 			
			if (fx < GameView.BRICK_SIZE*((16-10)/2) && fy > GameView.BRICK_SIZE*21) {
				if(canMoveFigure(Figure.FigureMotion.Left)  ){
					--figure.posX;
					}				
			} 
			if( ( fx > GameView.BRICK_SIZE*((16-10)/2) && fx < GameView.BRICK_SIZE*(((16-10)/2)+3) ) && fy > GameView.BRICK_SIZE*21) {
				if(canMoveFigure(Figure.FigureMotion.Right)  ){
					++figure.posX;		
					}
			} 
			if((fx > GameView.BRICK_SIZE*(((16-10)/2)+3) && fx < GameView.BRICK_SIZE*(((16-10)/2)+7) ) && fy > GameView.BRICK_SIZE*21) {
				if(canMoveFigure(Figure.FigureMotion.RotateL)  ){
					figure.rotateL();		
					}
			}
			if((fx > GameView.BRICK_SIZE*(((16-10)/2)+7) && fx < GameView.BRICK_SIZE*(((16-10)/2)+10)) && fy > GameView.BRICK_SIZE*21){				
				downToBottom();
			}
			if((fx > GameView.BRICK_SIZE*(((16-10)/2)+10) ) && fy > GameView.BRICK_SIZE*21) {
				if(canMoveFigure(Figure.FigureMotion.RotateR)  ){
				figure.rotateR();		
				}
			}
			/*else if ((fx < GameView.BRICK_SIZE*((16-10)/2))  && fy > GameView.BRICK_SIZE*21) {
				brickholder = brickholderclicked;						
			}*/	 
		}
		
		/*
		if(event.getAction() == event.ACTION_MOVE){
		 sx = event.getX();		 sy = event.getY();							
		
		 if (sx > 60 || sy >130) {
			 brickholder = BitmapFactory.decodeResource(context.getResources(),  R.drawable.brickholder);
			}		 
		if((sx - fx) >50){			
				if(canMoveFigure(Figure.FigureMotion.Right)  ){
				++figure.posX;				
				fx = sx;	 //fy = sy;
				}					
		}
		if((fx - sx) >50){			
				if(canMoveFigure(Figure.FigureMotion.Left)  ){
				--figure.posX;				
				fx = sx;	 //fy = sy;
				}							
		}			
		if ((fy - sy) >80){
			if(canMoveFigure(Figure.FigureMotion.RotateL)  ){
				figure.rotateL();				
				fx = sx; 	fy = sy;
				}				
		}
		if ((sy - fy) >80){
			if(canMoveFigure(Figure.FigureMotion.RotateR)  ){
				figure.rotateR();				
				fx = sx; 	fy = sy;
				}				
		}		
		}
		if(event.getAction() == event.ACTION_UP){  
		    downLoopThread.flag = false;
			brickholder = BitmapFactory.decodeResource(context.getResources(),  R.drawable.brickholder);				
			if(canMoveFigure(Figure.FigureMotion.Down) ){				
		    	while(true){
					if(canMoveFigure(Figure.FigureMotion.Down))
					{			
						
						try {
							Thread.sleep(5);							
							figure.posY += acc;
							
							acc += 0.01;							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else{		
						
						
						synchronized (this) {	
							acc =0;
							putFigure();	
							Figure.bricksList.remove(0);
							Figure.makeBricks();					
							
						}			
						
						gethercompletedLines();		
						//figure.refresh();
						figure.pos.set(14/2, 0);
						figure.posX = 7;
						figure.posY = 0;
						figure.pos2.set(16/2, 0);
						
						Figure.type = Figure.bricksList.get(0);
						gameOver = isGameOver();
						break;	}
				}			    	
		    	fx = sx;	 fy = sy;				
		    }
			downLoopThread.flag = true;
			}	*/
		
		
		}
		 return false;    
		
			}


	private void downToBottom() {
		downLoopThread.flag = false;
		brickholder = BitmapFactory.decodeResource(context.getResources(),  R.drawable.brickholder);				
		if(canMoveFigure(Figure.FigureMotion.Down) ){				
	    	while(true){
				if(canMoveFigure(Figure.FigureMotion.Down))
				{			
					
					try {
						Thread.sleep(5);							
						figure.posY += acc;
						
						acc += 0.01;							
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{							
					synchronized (this) {	
						acc =0;
						putFigure();	
						Figure.bricksList.remove(0);
						Figure.makeBricks();							
					}			
					
					gethercompletedLines();		
					//figure.refresh();
					/*figure.pos.set(14/2, 0);*/
					figure.posX = 7;
					figure.posY = 0;
					figure.pos2.set(16/2, 0);
					
					Figure.type = Figure.bricksList.get(0);
					gameOver = isGameOver();
					break;	}
			}			    	
	    	fx = sx;	 fy = sy;				
	    }
		downLoopThread.flag = true;
		}


	@Override
	public void onTime(long gameTime) {

		
		
	}

}
