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
	public TreeNode parent;
	//Number of the face of a leaf node
	public int face_value;
	
	
	//Constructor
	public TreeNode(double val, int feat, TreeNode parent, int face_val) {
		if (face_val == 0) {
			System.out.println("yay c:");
		}
		this.left_child = null;
		this.right_child = null;
		this.is_leaf = true;
		this.value = val;
		this.feature = feat;
		this.parent = parent;
		this.face_value = face_val;
	}
	
	//Methods
	public void setValue(double val) {
		this.value = val;
	}
	
	public void setFeature(int feat) {
		this.feature = feat;
	}
	
	public TreeNode getParent() {
		return this.parent;
	}
	
	public int getFeature() {
		return this.feature;
	}
}
