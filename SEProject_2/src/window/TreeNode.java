package window;

public class TreeNode {
	
	//Variables
	public TreeNode left_child;
	public TreeNode right_child;
	public Boolean is_leaf;
	//Testing value if node isn't a leaf
	public double value;
	//Number of the feature that's being tested
	public int feature;
	public int id;
	//Number of the face of a leaf node
	public int face_value;
	
	
	//Constructor
	public TreeNode(double val, int feat, int id, int face_val) {
		this.left_child = null;
		this.right_child = null;
		this.is_leaf = true;
		this.value = val;
		this.feature = feat;
		this.id = id;
		this.face_value = face_val;
	}
	
	//Methods
	public void setValue(double val) {
		this.value = val;
	}
	
	public void setFeature(int feat) {
		this.feature = feat;
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getFeature() {
		return this.feature;
	}
}
