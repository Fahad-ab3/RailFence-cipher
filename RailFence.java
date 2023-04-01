package Project;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class RailFence {

	
	private int key;
	private char [][] matrix;
	private int size;
	
	public RailFence(int key) {
	this.key = key;
	}
	
		
	
	public String decrypt(File cipherF)throws Exception{
					
		FileWriter myWriter = new FileWriter(cipherF);
		
		String text = "";
		
		int increment = key + (key - 2);
		
		for (int i = 0; i < size; i+=increment) {
			 
            for (int j = 0;i + j < size && j < key; j++)
                if(matrix[j][i + j] != 0)	
                	text += matrix[j][i + j];
            
            for (int j = key - 2;i + key < size && j > 0; j--) 
            	if(matrix[j][i + key] != 0)
                	text += matrix[j][i + key];
            
		}
		myWriter.append(text);
		myWriter.close();
		return cipherF.getAbsolutePath();
	}
	
	
	public String encrypt(File textF) throws Exception{
		Scanner myReader = new Scanner(textF);
		
		String text = "";
		
		while(myReader.hasNextLine())	
			text+= myReader.nextLine();
		
//		If you don't want to remove spaces from your cipher replace the following line with the one after it.
//		text = text.trim();
		text = text.toLowerCase();
		text = text.replaceAll("\\s", "");
				
		fillMatrix(text);
		
		myReader.close();
		
		return encrypt("Cipher.txt");
	}

	
	private void fillMatrix(String s){
		size = s.length();
		matrix = new char [key][size];
		int char_idx = 0;
		
		int increment = key + (key - 2);
		
		for (int i = 0; i < size; i+=increment) {

            for (int j = 0;char_idx != size && j < key; j++) 
            	if(s.charAt(char_idx) != 0)
                	matrix[j][i + j] = s.charAt(char_idx++);
            
            
            for (int j = key - 2;char_idx != size && j > 0; j--) 
            	if(s.charAt(char_idx) != 0)
                	matrix[j][i + key] = s.charAt(char_idx++);
            
            }
	}
	
	
//	This method will write the cipher in the file.
	private String encrypt(String filePath) throws Exception{
		
		File cipherF = new File(filePath);
		cipherF.createNewFile();
		
		FileWriter myWriter = new FileWriter(cipherF);
		
		if(matrix != null) {
		String text = "";
        // Loop through all rows
        for (int i = 0; i < key; i++)
            // Loop through all elements of current row
            for (int j = 0; j < size; j++)
                if(matrix[i][j] != 0)
                	text += (char) matrix[i][j];
        myWriter.append(text);
    }
		
		myWriter.close();
		return cipherF.getAbsolutePath();
		}
	
	
	
	public static void main(String[] args) {

		RailFence rf;
 	
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the key: (Positive key)");
			int key = in.nextInt();
	
			if(key > 0) {
				rf = new RailFence(key);
			
			//Before running the main function fill the text.txt file with the text that you want to encrypt.
			File textF = new File("text.txt");		
			
			
			System.out.println("The absolute path for cipher file: " + rf.encrypt(textF));
			
			File cipherF = new File("text_after_deciphering.txt");
			cipherF.createNewFile();
			
			System.out.println("The absolute path for text file after deciphering: " + rf.decrypt(cipherF));
			}
			else
				System.out.println("You entered an invalid key value!");
			in.close();
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
	



	}

}
