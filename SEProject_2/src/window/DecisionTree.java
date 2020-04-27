package window;

import java.io.*;
import java.lang.Math;
import java.util.Scanner;

public class DecisionTree {
	
	//Variable
	public Tree tree;
	
	//Constructor
	public DecisionTree() {
		this.tree = new Tree(0);
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
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Logan\\eclipse-workspace\\SoftwareEngineeringProject-master\\SoftwareEngineeringProject-master\\decision_tree\\trtxt.txt"));
			String line = reader.readLine();
			line = line.replace("|", "");
			line = line.replace("-", "");
			line = line.replace("a","");
			line = line.replace("<=", "");
			line = line.trim();
			//Add the value of the root node
			this.tree.root.setValue(Double.parseDouble(line));
			line = reader.readLine();
			//Add all the other nodes
			while(line != null) {
				//True if the node for this line has been created
				Boolean created = false;
				if(line.contains(">")) {
					created = true;
				}
				//Depth of the tree
				int depth = this.countDepth(line);
				line = line.replace("-", "");
				line = line.replace("a","");
				line = line.replace("|", "");
				//Get the right node id
				//Find the newest node in depth-1 and add this node as that node's child
				for(int i = (int)Math.pow(2, depth - 1) - 1; i < (int)Math.pow(2, depth) - 1; i++) {
					//Add testing nodes
					if(line.contains("<=")) {
						//If the depth isn't full yet
						if(!created && !this.tree.exists(i, this.tree.root)) {
							//Add the node
							line = line.replace("<=", "");
							line = line.trim();
							//Right child
							if(this.tree.getNode(i - 1, this.tree.root).left_child != null) {
								this.tree.addNode(Double.parseDouble(line), -1, this.tree.root, (i - 1), false);
							//Left child
							} else {
								this.tree.addNode(Double.parseDouble(line), -1, this.tree.root, (i - 1), true);
							}
							created = true;
						}
						//If the depth is full
						if(!created && i == (int)Math.pow(2, depth) - 2) {
							line = line.replace("<=", "");
							line = line.trim();
							//Right child
							if(this.tree.getNode(i, this.tree.root).left_child != null) {
								this.tree.addNode(Double.parseDouble(line), -1, this.tree.root, i, false);
							//Left child
							} else {
								this.tree.addNode(Double.parseDouble(line), -1, this.tree.root, i, true);
							}
							created = true;
						}
					//Add leaf nodes
					} else if (!created) {
						//If the depth isn't full yet
						if(!created && !this.tree.exists(i, this.tree.root)) {
							//Add the node
							//Right child
							line = line.replace("clss: ", "");
							line = line.trim();
							if(this.tree.getNode(i - 1, this.tree.root).left_child != null) {
								this.tree.addNode(-1, (int)Double.parseDouble(line), this.tree.root, (i - 1), false);
								System.out.println((int)Double.parseDouble(line));
							//Left child
							} else {
								this.tree.addNode(-1, (int)Double.parseDouble(line), this.tree.root, (i - 1), true);
								System.out.println((int)Double.parseDouble(line));
							}
							created = true;
						}
						//If the depth is full
						if(!created && i == (int)Math.pow(2, depth) - 1) {
							//Right child
							System.out.println(line);
							if(this.tree.getNode(i, this.tree.root).left_child != null) {
								this.tree.addNode(-1, (int)Double.parseDouble(line), this.tree.root, i, false);
								System.out.println((int)Double.parseDouble(line));
							//Left child
							} else {
								this.tree.addNode(-1, (int)Double.parseDouble(line), this.tree.root, i, true);
								System.out.println((int)Double.parseDouble(line));
							}
							created = true;
						}
					}
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
	
	//TODO
	//Take in audio - probably via .wav file
	//Convert to decimal the same way Python did it
	// NOTE: AudioAnalyzer must be running at the same time
	// TODO: Possibly find a way to run python code as another thread? 
	public double parseAudio() {
		try{
        	// reads file modified by AudioAnalyzer.py 
            File f = new File("check.txt");
            Scanner read = new Scanner(f);
            // This is an array of features printed into the file check.txt
            System.out.println(read.nextLine());
        }catch(Exception e){}
		double audio_val = 0;
		return audio_val;
	}
	
	/**
	 * Find the appropriate face for some audio value
	 * @param value The value from some processed audio
	 * @param base The current root
	 * @return The number of the face to make
	 */
	public int getFace(double value, TreeNode base) {
		if(base.is_leaf) {
			return base.face_value;
		} else if (value <= base.value) {
			return getFace(value, base.left_child);
		} else {
			return getFace(value, base.right_child);
		}
	}
}
