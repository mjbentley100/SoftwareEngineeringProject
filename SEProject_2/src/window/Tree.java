package window;

public class Tree {
	
	//Variable
	public TreeNode root;
	
	//Constructor
	//Takes in the value of the root node
	public Tree(double val) {
		this.root = new TreeNode(val, 0, -1);
	}
	
	//Methods
	/** 
	 * Adds a node to a tree using a recursive method
	 * @param val The value of the new node
	 * @param face The number of the face for a leaf node
	 * @param base The current root
	 * @param parent_id The parent of the new node
	 * @param is_left True if the new node is a left child
	 */
	public void addNode(double val, int face, TreeNode base, int parent_id, Boolean is_left) {
		//If current root and parent match, add the node
		if(base.getID() == parent_id) {
			if(is_left) {
				base.left_child = new TreeNode(val, 2 * parent_id + 1, face);
			} else {
				base.right_child = new TreeNode(val, 2 * parent_id + 2, face);
			}
			base.is_leaf = false;
		//Otherwise, go through the tree to try and find a match
		} else {
			if(base.left_child != null) {
				addNode(val, face, base.left_child, parent_id, is_left);
			}
			if(base.right_child != null ) {
				addNode(val, face, base.right_child, parent_id, is_left);
			}
		}
	}
	
	/**
	 * Checks if a node exists in the tree using a recursive method
	 * @param id The id of the node to be checked
	 * @param base The current root
	 * @return True if the node exists in the current tree
	 */
	public Boolean exists(int id, TreeNode base) {
		//If the root matches
		if(base.id == id) {
			return true;
		//Otherwise check in the subtrees
		} else {
			Boolean in_left = false;
			Boolean in_right = false;
			if(base.left_child != null) {
				in_left = exists(id, base.left_child);
			}
			if(base.right_child != null) {
				in_right = exists(id, base.right_child);
			}
			return in_left || in_right;
		}
	}
	
	/**
	 * Finds the node of a tree based on id - Assumes that the node exists somewhere
	 * @param id The id of the node to be found
	 * @param base The current root
	 * @return The node to be found
	 */
	public TreeNode getNode(int id, TreeNode base) {
		//If the root matches
		if(base.id == id) {
			return base;
		} else {
			//If in the left subtree
			if(exists(id, base.left_child)) {
				return getNode(id, base.left_child);
			//If in the right subtree
			} else {
				return getNode(id, base.right_child);
			}
		}
	}
}
