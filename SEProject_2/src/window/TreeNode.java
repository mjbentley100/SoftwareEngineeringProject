package window;

public class TreeNode {
	
	//Variables
	public TreeNode left_child;
	public TreeNode right_child;
	public Boolean is_leaf;
	//Testing value if node isn't a leaf
	public double value;
	public int id;
	//Number of the face of a leaf node
	public int face_value;
	
	//Constructor
	public TreeNode(double val, int id, int face_val) {
		this.left_child = null;
		this.right_child = null;
		this.is_leaf = true;
		this.value = val;
		this.id = id;
		this.face_value = face_val;
	}
	
	//Methods
	public void setValue(double val) {
		this.value = val;
	}
}
