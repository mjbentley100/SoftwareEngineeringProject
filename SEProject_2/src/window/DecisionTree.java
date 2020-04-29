package window;

import java.io.*;
import java.lang.Math;
import java.util.Scanner;

public class DecisionTree {
	
	//Variable
	public Tree tree;
	
	//Constructor
	public DecisionTree() {
		this.tree = new Tree(0, -1);
	}


	public Tree getTree() {
		return this.tree;
	}

	
	//Methods
	/**
	 * Read in a line and count the number of ~
	 * @param s The string to be checked
	 * @return The number of ~ in s
	 */
	public int countDepth(String s) {
		int count = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) =='|') {
				count++;
			}
		}
		return count - 1;
	}
	
	/**
	 * Generate a decision tree based on a text file
	 * @return 0 if successful
	 */
	public int createTree() {
		//Read in the text file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Wesley Pick-Roth\\Documents\\GitHub\\SoftwareEngineeringProject\\decision_tree\\trtxt.txt"));
			String line = reader.readLine();
			line = line.replace("|", "");
			line = line.replace("-", "");
			line = line.replace("<=", "");
			line = line.trim();
			String[] lines = line.split(" ");
			//Add the value of the root node
			this.tree.root.setValue(Double.parseDouble(lines[2]));
			this.tree.root.setFeature(Integer.parseInt(lines[0]));
			line = reader.readLine();
			//Add all the other nodes
			TreeNode parent = null;
			while(line != null) {
				line = line.replace("-", "");
				line = line.replace("|", "");
				//Create new node
				if(line.contains("<=")) {
					line = line.replace("<=", "");
					line = line.trim();
					lines = line.split(" ");
					this.tree.addNode(Double.parseDouble(lines[2]), Integer.parseInt(lines[0]), -1);
				//Move up the tree
				} else if (line.contains(">")) {
					this.tree.moveUp();
				//Create leaf
				} else {
					line = line.replace("class: ", "");
					line = line.trim();
					this.tree.addNode(-1, -1, (int)Double.parseDouble(line));
				}
				line = reader.readLine();
			}
			reader.close();
			return 0;
		} catch (IOException e) {
			//e.printStackTrace();
			return -1;
		}
	}
	
	// NOTE: AudioAnalyzer must be running at the same time
	// TODO: Possibly find a way to run python code as another thread? 
	public double[] parseAudio() {
		try{
        	// reads file modified by AudioAnalyzer.py 
            BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\Wesley Pick-Roth\\Documents\\GitHub\\SoftwareEngineeringProject\\SEProject_2\\check.txt"));
            // This is an array of features printed into the file check.txt
            String line = read.readLine().trim();
            line = line.replace("[", "");
            line = line.replace(",", "");
            line = line.replace("]", "");
            String[] lines = line.split(" ");
            double[] audio_val = new double[17];
            for(int i = 0; i < 17; i++) {
            	audio_val[i] = Double.parseDouble(lines[i]);
            }
            read.close();
            return audio_val;
        }catch(Exception e){}
		double[] audio_val = {-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0};
		return audio_val;
	}
	
	/**
	 * Find the appropriate face for some audio value
	 * @param values The value from some processed audio
	 * @param base The current root
	 * @return The number of the face to make
	 */
	public int getFace(double[] values, TreeNode base) {
		if(base.right_child == null) {
			return base.face_value;
		} else {
			int feature = base.getFeature();
			if(values[feature] <= base.value) {
				return getFace(values, base.left_child);
			} else {
				return getFace(values, base.right_child);
			}
		}
	}
}
