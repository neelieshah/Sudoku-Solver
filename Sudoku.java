import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sudoku {

	private static int boardSize = 0;
	private static int partitionSize = 0;
	
	public static void main(String[] args)
	{
		String filename = args[0];
		File inputFile = new File(filename);
		Scanner input = null;
		int[][] vals = null;
		
		int temp = 0;
    	int count = 0;
    	
	    try {
			input = new Scanner(inputFile);
			temp = input.nextInt();
			boardSize = temp;
			partitionSize = (int) Math.sqrt(boardSize);
			System.out.println("Boardsize: " + temp + "x" + temp);
			vals = new int[boardSize][boardSize];			
			
			System.out.println("Input:");
	    	int i = 0;
	    	int j = 0;
	    	while (input.hasNext()){
	    		temp = input.nextInt();
	    		count++;
	    		System.out.printf("%3d", temp);
	    		vals[i][j] = temp;
				if (temp == 0) {
					// TODO
				} 
				j++;
				if (j == boardSize) {
					j = 0;
					i++;
					System.out.println();
				}
				if (j == boardSize) {
					break;
				}
	    	}
	    	input.close();
	    } catch (FileNotFoundException exception) {
	    	System.out.println("Input file not found: " + filename);
	    }
	    if (count != boardSize*boardSize) throw new RuntimeException("Incorrect number of inputs.");
	    
	    
		boolean solved = solve(vals);
		
		// Output
		if (!solved) {
			System.out.println("No solution found.");
			return;
		}
		System.out.println("\nOutput\n");
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.printf("%3d", vals[i][j]);
			}
			System.out.println();
		}		
		
	}
	
	public static boolean solve(int [][] vals)
	{
		if (isFull(vals))
		{
			return true;
		}
		for (int i = 0; i < boardSize; i ++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				if(vals[i][j] == 0)
				{
					for (int num = 1 ; num <= boardSize; num++)
					{
						boolean placed = canBePlaced(vals, i, j, num);
						if (placed)
						{
							vals[i][j] = num;
							if (solve(vals))
							{
								return true;
							}
							
							else 
							{
								vals[i][j]= 0;
							}
						}

					}
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isFull(int[][]vals) 
	{
		for (int i = 0; i < boardSize; i ++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				if (vals[i][j] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean canBePlaced(int[][]vals, int row, int col, int num)
	{
		return vertical(vals,row,num)&&horizonal(vals,col,num)&&box(vals,row,col,num);
	}
	
	public static boolean vertical(int[][]vals, int row, int num)
	{
		for (int i = 0 ; i < boardSize; i ++)
		{
			if (vals[row][i] == num)
			{
				return false;
			}
		}
		
		return true;
	}
		
	public static boolean horizonal(int[][]vals, int col, int num)
	{
		for (int i = 0 ; i < boardSize; i ++)
		{
			if (vals[i][col] == num)
			{
				return false;
			}
		}
		
		return true;	
	}
	
	public static boolean box(int[][]vals, int row, int col, int num)
	{
		int boxRow = row/partitionSize;
		int boxCol = col/partitionSize;
		
		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				if (i/partitionSize == boxRow && j/partitionSize == boxCol && vals[i][j] == num)
				{
					return false;
				}
			}
		}
		
		return true; 
	}


}
