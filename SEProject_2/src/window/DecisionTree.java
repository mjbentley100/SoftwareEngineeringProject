package window;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Math;

public class DecisionTree {
	
	//Variables
	public Tree tree;
	
	//Constructor
	public DecisionTree() {
		this.tree = new Tree(0);
	}
	
	//Methods
	/**
	 * Generate a decision tree based on a text file
	 * @return 0 if successful
	 */
	public int createTree() {
		//Read in the text file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("../../decision_tree/trtxt.txt"));
			String line = reader.readLine();
			//Add the value of the root node
			line = line.replace("<=", "");
			this.tree.root.setValue(parseDouble(line.trim()));
			line = reader.readLine();
			//Add all the other nodes
			while(line != null) {
				//True if the current line is already a node
				Boolean created = false;
				if(line.contains(">")) {
					created = true;
				}
				//Depth of the tree
				int depth = StringUtils.countMatches(line, "~");
				line = line.replace("~", "");
				//Get the right node id
				for(int i = pow(2, depth) - 1; i < pow(2, depth + 1) - 1; i++) {
					if(!created && !this.tree.exists(i, this.tree.root)) {
						line = line.replace("<=", "");
						//Add the node
						if(i % 2 == 0) {
							this.tree.addNode(parseDouble(line.trim()), this.tree.root, (i - 2) / 2, false);
						} else {
							this.tree.addNode(parseDouble(line.trim()), this.tree.root, (i - 1) / 2, true);
						}
						created = true;
					}
				}
				line = reader.readLine();
			}
			reader.close();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public double parseAudio() {
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
			return x;
		} else if (value <= base.value) {
			return parseAudio(value, base.left_child);
		} else {
			return parseAudio(value, base.right_child);
		}
	}
}
