package window;

public class TreeNode {
	
	//Variables
	//Children of the current node
	public TreeNode left_child;
	public TreeNode right_child;
	//True if the node is a leaf
	public Boolean is_leaf;
	//Value for face (if leaf) or testing (if not leaf)
	public double value;
	//Unique identifier of node in a tree
	public int id;
	
	//Constructors
	public TreeNode(TreeNode lchild, double val, TreeNode rchild, int id) {
		this.left_child = lchild;
		this.right_child = rchild;
		this.is_leaf = false;
		this.value = val;
		this.id = id;
	}
	
	public TreeNode(TreeNode lchild, double val, int id) {
		this.left_child = lchild;
		this.right_child = null;
		this.is_leaf = false;
		this.value = val;
		this.id = id;
	}
	
	public TreeNode(double val, TreeNode rchild, int id) {
		this.left_child = null;
		this.right_child = rchild;
		this.is_leaf = false;
		this.value = val;
		this.id = id;
	}
	
	public TreeNode(double val, int id) {
		this.left_child = null;
		this.right_child = null;
		this.is_leaf = true;
		this.value = val;
		this.id = id;
	}
	
	//Methods
	public setValue(double val) {
		this.value = val;
	}
}
