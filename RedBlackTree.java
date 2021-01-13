package trees;

import java.util.Iterator;

import trees.LinkedBinaryTree.Node;

public class RedBlackTree<E> extends AbstractBinaryTree<E> {
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		private int color;


		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild,int c ) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
			color=c;
		}
		public Node(){
			
		}

		public E getElement() {
			return element;
		}
        
		public Node<E> getParent() {
			return parent;
		}

		public Node<E> getLeft() {
			return left;
		}

		public Node<E> getRight() {
			return right;
		}
		public int getColor(){
			return color;
		}

		public void setElement(E e) {
			element = e;
		}
		
		public void setParent(Node<E> parentNode) {
			parent = parentNode;
		}

		public void setLeft(Node<E> leftChild) {
			left = leftChild;
		}

		public void setRight(Node<E> rightChild) {
			right = rightChild;
		}
		public void setColor(int c){
			color=c;
		}
	}
	/** Factory function to create a new node storing element e. */
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right,int c) {
		return new Node<E>(e, parent, left, right,c);
	}
	
	protected Node<E> root;
	private int size = 0;
	private Node<E> TNULL;
	
	public RedBlackTree() {
		TNULL = new Node<E>();
		TNULL.setColor(0);
		TNULL.setLeft(null);
		TNULL.setRight(null);
		root=TNULL;
		}
	

	/** Validates the position and returns it as a node. */
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node))
			throw new IllegalArgumentException("Not valid position type");
		Node<E> node = (Node<E>) p;
		if (node.getParent() == node)
			// our convention for defunct node
			throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}
	
	// accessor methods (not already implemented in AbstractBinaryTree)
	/** Returns the number of nodes in the tree. */
	public int size() {
		return size;
	}

	/** Returns the root Position of the tree (or null if tree is empty). */
	public Position<E> root() {
		return root;
	}

	/** Returns the Position of p's parent (or null if p is root). */
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}

	/** Returns the Position of p's left child (or null if no child exists). */
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}

	/** Returns the Position of p's right child (or null if no child exists). */
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}
	public Position<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty())
			throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null,0);
		size = 1;
		return root;
	}
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E temp = node.getElement();
		node.setElement(e);
		return temp;
	}
	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		return null;
	}
	public void preorder(Position<E> p){
		Node<E> node = (Node<E>)p;
		if(node==TNULL) return;
		System.out.print(node.getElement()+","+node.getColor()+" // ");
		preorder(node.getLeft());
		preorder(node.getRight());
	}
	
	public void postorder(Position<E> p){
		Node<E> node = (Node<E>)p;
		if(node==TNULL) return;
		postorder(node.getLeft());
		postorder(node.getRight());
		System.out.print(node.getElement()+","+node.getColor()+" // ");
	}

	public void inorder(Position<E> p){
		Node<E> node = (Node<E>)p;
		if(node==TNULL) return;
		postorder(node.getLeft());
		System.out.print(node.getElement()+","+node.getColor()+"// ");
		postorder(node.getRight());
	}
	public void LeftRotation(Node<E> node){
		Node<E> y=node.getRight();
		node.setRight(y.getLeft());
		if(y.getLeft()!=TNULL){
			y.getLeft().setParent(node);
		}
		y.setParent(node.getParent());
		if(node.getParent()==null){
			root=y;
		}
		else if(node==node.getParent().getLeft()){
			node.getParent().setLeft(y);
			}
		else{
			node.getParent().setRight(y);
		}
		y.setLeft(node);
		node.setParent(y);
	}
	public void RightRotation(Node<E> node){
		Node<E> y=node.getLeft();
		node.setLeft(y.getRight());
		if(y.getRight()!=TNULL){
			y.getRight().setParent(node);
		}
		y.setParent(node.getParent());
		if(node.getParent()==null){
			root=y;
		}
		else if(node==node.getParent().getRight()){
			node.getParent().setRight(y);
			}
		else{
			node.getParent().setLeft(y);
		}
		y.setRight(node);
		node.setParent(y);
		
	}
	public int compare(E a, E b) throws ClassCastException {
        return ((Comparable<E>) a).compareTo(b);
    }
	public void insert(E e){
		Node<E> node=new Node<E>(e,null,TNULL,TNULL,1);
		Node<E>y=null;
		Node<E> x=this.root;
		size++;
		while(x!=TNULL){
			y=x;
			if(compare(node.getElement(),x.getElement())<0){
				x=x.getLeft();
			}
			else{
				x=x.getRight();
			}
		}
		node.setParent(y);
		if(y==null){
			root=node;
		}
		else if(compare(node.getElement(),y.getElement())<0){
			y.setLeft(node);
		}
		else{
			y.setRight(node);
		}
		if(node.getParent()==null){
			node.setColor(0);
			return;
		}
		if(node.getParent().getParent()==null){
			return;
		}
		fixInsert(node);
		
	}
	public void fixInsert(Node<E> k){
		Node<E> u;
		while(k.getParent().getColor()==1){
			if(k.getParent()==k.getParent().getParent().getRight()){
				u=k.getParent().getParent().getLeft();
				if(u.getColor()==1){
					u.setColor(0);
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					k=k.getParent().getParent();
				}else{
					if(k==k.getParent().getLeft()){
						k=k.getParent();
						RightRotation(k);
					}
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					LeftRotation(k.getParent().getParent());
				}
			}else{
				u=k.getParent().getParent().getRight();
				if(u.getColor()==1){
					u.setColor(0);
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					k=k.getParent().getParent();
				}else{
					if(k==k.getParent().getRight()){
						k=k.getParent();
						LeftRotation(k);
					}
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					RightRotation(k.getParent().getParent());
				}
			}
			if(k==root){
				break;
			}
		}
		root.setColor(0);
	}
	public void deleteNode(Position<E> p, E value) {
		Node<E> node = validate(p);
		Node<E> z = TNULL;
		Node<E> x, y;
		while (node != TNULL){
			if (node.getElement() == value) {
				z = node;
				break;
			}
			if (compare(value,node.getElement())==1) {
				node = node.getRight();
			} else {
				node = node.getLeft();
			}
		}
		if (z == TNULL) {
			System.out.println("Couldn't find this value in the tree");
			return;
		} 
		y = z;
		int y_Color = y.getColor();
		if (z.getLeft() == TNULL) {
			x = z.getRight();
			retransplant(z, z.getRight());

		} else if (z.getRight() == TNULL) {

			x = z.getLeft();

			retransplant(z, z.getLeft());

		} else {

			y = minimum(z.getRight());

			y_Color = y.getColor();

			x = y.getRight();

			if (y.getParent() == z) {

				x.setParent(y) ;

			} else {

				retransplant(y, y.getRight());

				y.setRight(z.getRight());

				y.getRight().setParent(y);

			}
			retransplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			y.setColor(z.getColor());
		}

		if (y_Color == 0){
			rebalancing_of_Delete(x);
		}

	}
	private void rebalancing_of_Delete(Position<E> X) {
		Node<E> x = validate(X);
		Node<E> s;
		while (x != root && x.getColor() == 0) {
			if (x == x.getParent().getLeft()) {
				s = x.getParent().getRight();
				if (s.getColor() == 1) {
					// case 3.1
					s.setColor(0);
					x.getParent().setColor(1);
					LeftRotation(x.getParent());
					s = x.getParent().getRight();
				}
				if (s.getLeft().getColor() == 0 && s.getRight().getColor() == 0) {
					// case 3.2
					s.setColor(1);
					x = x.getParent();
				} else {
					if (s.getRight().getColor() == 0) {
						// case 3.3
						s.getLeft().setColor(0);
						s.setColor(1);
						RightRotation(s);
						s = x.getParent().getRight();
					} 
					// case 3.4
					s.setColor(x.getParent().getColor());
					x.getParent().setColor(0);
					s.getRight().setColor(0);
					LeftRotation(x.getParent());
					x = root;
				}
			} else {
				s = x.getParent().getLeft();
				if (s.getColor() == 1) {
					// case 3.1
					s.setColor(0);
					x.getParent().setColor(1);
					RightRotation(x.getParent());
					s = x.getParent().getLeft();
				}
				if (s.getRight().getColor() == 0 && s.getRight().getColor() == 0) {
					// case 3.2
					s.setColor(1);
					x = x.getParent();
				} else {
					if (s.getLeft().getColor() == 0) {
						// case 3.3
						s.getRight().setColor(0);
						s.setColor(1);
						LeftRotation(s);
						s = x.getParent().getLeft();
					} 
					// case 3.4
					s.setColor(x.getParent().getColor());
					x.getParent().setColor(0);
					s.getLeft().setColor(0);
					RightRotation(x.getParent());
					x = root;
				}
			} 
		}
		x.setColor(0);		
	}
	private Node<E> minimum(Position<E> R) {
		Node<E> rightsubtree = validate(R);
		while (rightsubtree.getLeft() != TNULL) {

			rightsubtree = rightsubtree.getLeft();

		}

		return rightsubtree;

	}

	private void retransplant(Position<E> Z, Position<E> Y) {
		Node<E> z = validate(Z);
		Node<E> y = validate(Y);
		if (z.getParent() == null) {
			root = y;
		} else if (z == z.getParent().getLeft()){
			z.getParent().setLeft(y);
		} else {
			z.getParent().setRight(y);
		}
		y.setParent(z.getParent());		
	}
	public static void main(String[] args){
	    	RedBlackTree bst = new RedBlackTree();
	        bst.insert(10);
	    	bst.insert(18);
	    	bst.insert(7);
	    	bst.insert(15);
	    	bst.insert(16);
	    	bst.insert(30);
	    	bst.insert(25);
	    	bst.insert(40);
	    	bst.insert(60);
	    	bst.insert(2);
	    	bst.insert(1);
	    	bst.insert(70);
	    	System.out.println("Preorder Traversal");
			bst.preorder(bst.root());	
			System.out.println();

			System.out.println(((Node<Integer>)bst.root()).getRight().getRight().getElement());
			System.out.println(((Node<Integer>)bst.root()).getRight().getRight().getColor());
			System.out.println(bst.size());
	    	
	

		
	
	}
	
}
