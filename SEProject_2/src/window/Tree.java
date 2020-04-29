package window;

public class Tree {
	
	//Variable
	public TreeNode root;
	public TreeNode active;

	public TreeNode getRoot() {
		this.active = this.root;
		return this.root;
	}


	//Constructor
	//Takes in the value of the root node
	public Tree(double val, int feat) {
		this.root = new TreeNode(val, feat, null, -1);
		this.active = this.root;
	}
	
	//Methods
	/** 
	 * Adds a node to a tree using a recursive method
	 * @param val The value of the new node
	 * @param feat The feature associated with the node
	 * @param face The number of the face for a leaf node
	 */
	public void addNode(double val, int feat, int face) {
		//If current root and parent match, add the node
		if(active.left_child == null) {
			this.active.left_child = new TreeNode(val, feat, this.active, face);
			this.active.is_leaf = false;
			this.active = this.active.left_child;
		} else {
			this.active.right_child = new TreeNode(val, feat, this.active, face);
			this.active.is_leaf = false;
			this.active = this.active.right_child;
		}
	}

	/**
	 * Move the active node up the tree
	 */
	public void moveUp() {
		if (this.active.getParent() != null) {
			this.active = this.active.getParent();
			if (this.active.right_child != null) {
				this.moveUp();
			}
		}
	}
}
