package com.semenog.game.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Point;

public class Figure {
		
	
	public enum FigureMotion{
		Left, Right, Down, RotateR, RotateL;
	}
	
	public enum FigureType {
		I, O, L, J, S, Z, T;
		
		private static final List<FigureType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		//TODO: randomize
		public static FigureType randomFigure() {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}	
	}
	
	public static ArrayList<FigureType> bricksList = new ArrayList<FigureType>();	
	public static ArrayList<FigureType> bricksHold = new ArrayList<FigureType>();	
	
	static final int BRICK_SIZE = 38;
	static boolean flag = false;
	float posX;
	float posY;
	// float posY_after; 
	Point pos2;
	static FigureType type;
	private static int rotation;
	
	static Paint [] paint = new Paint[7];
	
	public Figure () {
		
		/*this.pos = new Point(); */
		
		this.posX = 0;
		this.posY = 0;
		
		this.pos2 = new Point();
		this.type = FigureType.randomFigure();
		this.rotation = 0;
		
		
	}
	
	public void refresh(){
		this.type = FigureType.randomFigure();
		this.rotation = 0;		
	}
	
	private int getLeftRotation(){
		return (3 == rotation) ? 0 : rotation + 1;
	}
	private int getRightRotation(){
		return (0 == rotation) ? 3 : rotation - 1;
	}
	
	public void rotateR(){
		rotation = getRightRotation();
	}
	public void rotateL(){
		rotation = getLeftRotation();
	}

	int[][] getMatrix(boolean rotated){
		if(rotated == true){
		return getMatrix(getLeftRotation());
		}else{
		return getMatrix(getRightRotation());
		}
		
	}
	
	static int[][] getMatrix(){
		return getMatrix(rotation);
	}
	
	private static int[][] getMatrix(int rotation){
		int[][] bricks = null;
		switch(type){
		case O:
			switch (rotation){
			case 0:
			case 1:
			case 2:
			case 3:
				bricks = new int[][]{
						{ 1, 1 },	// X X
						{ 1, 1 } 	// X X
				};
				break;
			}
			break;
		case I:
			switch (rotation){
			case 0:
			case 2:
				bricks = new int[][]{
						{ 2 },	// X
						{ 2 },	// X
						{ 2 },	// X
						{ 2 } 	// X
				};
				break;
			case 1:
			case 3:
				bricks = new int[][]{
						{ 2, 2, 2, 2}		// X X X X
				};
				break;				
			}
			break;
		case S:
			switch (rotation){
			case 0:
			case 2:
				bricks = new int[][]{
						{ 0, 3, 3 },	//   X X
						{ 3, 3, 0 }	   // X X
				};
				break;
			case 1:
			case 3:
				bricks = new int[][]{
						{ 3, 0 },    // X
						{ 3, 3 },	// X X
						{ 0, 3 }   	//    X
				};
				break;				
			}
			break;
		case Z:
			switch (rotation){
			case 0:
			case 2:
				bricks = new int[][]{
						{ 4, 4, 0},	// X X
						{ 0, 4, 4}	//   X X
				};
				break;
			case 1:
			case 3:
				bricks = new int[][]{
						{ 0, 4 },    //   X
						{ 4, 4 },	// X X
						{ 4, 0 }  	// X
				};
				break;				
			}
			break;
		case L:
			switch (rotation){
			case 0:
				bricks = new int[][]{
						{ 5, 0},		// X 
						{ 5, 0},		// X
						{ 5, 5 }		// X X
				};
				break;
			case 1:
				bricks = new int[][]{
						{ 0, 0, 5 },	//       X
						{ 5, 5, 5 }		// X X X
				};
				break;
			case 2:
				bricks = new int[][]{
						{ 5, 5 }, // X X
						{ 0, 5 },  //   X
						{ 0, 5 }   //   X
				};
				break;
			case 3:
				bricks = new int[][]{
						{ 5, 5, 5 },	// X X X
						{ 5, 0, 0}		// X 
				};
				break;				
			}
			break;
		case J:
			switch (rotation){
			case 0:
				bricks = new int[][]{
						{ 0, 6},	 //   X
						{ 0, 6},	 //   X
						{ 6, 6}		 // X X
				};
				break;
			case 1:
				bricks = new int[][]{
						{ 6, 6, 6},	// X X X 
						{ 0, 0, 6}	//     X 
				};
				break;
			case 2:
				bricks = new int[][]{
						{ 6, 6  },	// X X
						{ 6, 0 },	// X
						{ 6, 0 }		// X
				};
				break;
			case 3:
				bricks = new int[][]{
						{ 6, 0,  0 },	// X 
						{ 6, 6,  6  }	// X X X
				};
				break;				
			}
			break;
		case T:
			switch (rotation){
			case 0:
				bricks = new int[][]{
						{ 0, 7, 0 },	//    X 
						{ 7,  7, 7 }	// X X X
				};
				break;
			case 1:
				bricks = new int[][]{
						{ 0,  7 },	//    X 
						{ 7,  7 },	// X X 
						{ 0, 7 }		//    X   
				};
				break;
			case 2:
				bricks = new int[][]{
						{ 7, 7, 7 },	// X X X
						{ 0, 7, 0 }	    //    X
				};
				break;
			case 3:
				bricks = new int[][]{
						{ 7, 0 },    // X 
						{ 7, 7 },	// X X 
						{ 7, 0 }	    // X   
				};
				break;				
			}
			break;
			default:
				throw new IllegalArgumentException("Unhandled FiguteType!");
		}
		return bricks;
	}	
	
	void DrawFigure(Canvas c, ArrayList<Bitmap> image){		
		
		DrawFigure(c, getMatrix(), image);
	}
	void DrawFigure(Canvas c, int[][] matrix, ArrayList<Bitmap> image){
		DrawFigure(c, matrix, false, image);
	}
	void DrawFigure(Canvas c, int[][] matrix, boolean clean, ArrayList<Bitmap> image){
		Paint p = new Paint();
		p.setColor(0xFFFFFFFF);
		DrawFigure(c, matrix, p, image);
	}
	void DrawFigure(Canvas c, int[][] matrix, Paint p, ArrayList<Bitmap> img){
		c.save();		
		
		Paint paint = new Paint();
		paint.setAlpha(90);
        
        
		
		if (!GameView.gameOver && flag) {
			
	
		
		for (int row = 0; row < matrix.length; ++row){
			for (int column = 0; column < matrix[row].length; ++column){
				if (matrix[row][column] == 1)	{
					c.drawBitmap(img.get(0), ( posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[0]);
				}else if (matrix[row][column] == 2)	{
					c.drawBitmap(img.get(1), (posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[1]);
				}else if (matrix[row][column] == 3)	{
					c.drawBitmap(img.get(2), (posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[2]);
				}else if (matrix[row][column] == 4)	{
					c.drawBitmap(img.get(3), (posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[3]);
				}else if (matrix[row][column] == 5)	{
					c.drawBitmap(img.get(4), (posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[4]);
				}else if (matrix[row][column] == 6)	{
					c.drawBitmap(img.get(5), (posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[5]);
				}else if (matrix[row][column] == 7)	{
					c.drawBitmap(img.get(6), (posX + column)*GameView.BRICK_SIZE, (posY + row )*GameView.BRICK_SIZE, Figure.paint[6]);
				}					
					
			}
		}
		
				for (int row = 0; row < matrix.length; ++row){
					for (int column = 0; column < matrix[row].length; ++column){
						if (matrix[row][column] == 1)	{
							c.drawBitmap(img.get(0), (pos2.x + column )*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}else if (matrix[row][column] == 2)	{
							c.drawBitmap(img.get(1), (pos2.x + column)*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}else if (matrix[row][column] == 3)	{
							c.drawBitmap(img.get(2), (pos2.x + column)*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}else if (matrix[row][column] == 4)	{
							c.drawBitmap(img.get(3), (pos2.x + column)*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}else if (matrix[row][column] == 5)	{
							c.drawBitmap(img.get(4), (pos2.x + column)*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}else if (matrix[row][column] == 6)	{
							c.drawBitmap(img.get(5), (pos2.x + column)*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}else if (matrix[row][column] == 7)	{
							c.drawBitmap(img.get(6), (pos2.x + column)*GameView.BRICK_SIZE, (pos2.y + row )*GameView.BRICK_SIZE, paint);
						}					
							
					}
				}
		}
				
		c.restore();
	}
	
	static void makeBricks(){
			
		bricksList.add(FigureType.randomFigure());		
	}
	
	
	public static int[][] getDefaultMatrix(FigureType type){
		int[][] bricks = null;		
		switch(type){
		case O:			
				bricks = new int[][]{
						{ 1, 1 },	// X X
						{ 1, 1 } 	// X X
				};
				break;			
		case I:					
				bricks = new int[][]{
						{ 2, 2, 2, 2}		// X X X X
				};
				break;				
		case S:
				bricks = new int[][]{
						{ 0, 3, 3 },	//   X X
						{ 3, 3, 0 }	   // X X
				};
				break;			
		case Z:			
				bricks = new int[][]{
						{ 4, 4, 0},	// X X
						{ 0, 4, 4}	//   X X
				};
				break;			
		case L:			
				bricks = new int[][]{
						{ 5, 0},		// X 
						{ 5, 0},		// X
						{ 5, 5 }		// X X
				};
				break;			
		case J:			
				bricks = new int[][]{
						{ 0, 6},	 //   X
						{ 0, 6},	 //   X
						{ 6, 6}	// X X
				};
				break;			
		case T:		
				bricks = new int[][]{
						{ 0, 7, 0 },	//    X 
						{ 7,  7, 7 }	// X X X
				};
				break;
		default:
				throw new IllegalArgumentException("Unhandled FiguteType!");
		}
		return bricks;
	}
}
