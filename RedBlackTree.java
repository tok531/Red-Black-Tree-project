package trees;

import java.util.Iterator;

import trees.LinkedBinaryTree.Node;

public class RedBlackTree<E> extends AbstractBinaryTree<E> {
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		private char color;


		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild,char c ) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
			color=c;
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
		public char getColor(){
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
		public void setColor(char c){
			color=c;
		}
	}
	/** Factory function to create a new node storing element e. */
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right,char c) {
		return new Node<E>(e, parent, left, right,c);
	}
	
	protected Node<E> root = null;
	private int size = 0;
	
	public RedBlackTree(){
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
		root = createNode(e, null, null, null,'b');
		size = 1;
		return root;
	}
	protected Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getLeft() != null)
			throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e, parent, null, null,'r');
		parent.setLeft(child);
		size++;
		return child;
	}
	protected Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getRight() != null)
			throw new IllegalArgumentException("p already has a right child");
		Node<E> child = createNode(e, parent, null, null,'r');
		parent.setRight(child);
		size++;
		return child;
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
		if(node==null) return;
		System.out.print(node.getElement()+" ");
		preorder(node.getLeft());
		preorder(node.getRight());
	}
	
	public void postorder(Position<E> p){
		Node<E> node = (Node<E>)p;
		if(node==null) return;
		postorder(node.getLeft());
		postorder(node.getRight());
		System.out.print(node.getElement()+" ");
	}

	public void inorder(Position<E> p){
		Node<E> node = (Node<E>)p;
		if(node==null) return;
		postorder(node.getLeft());
		System.out.print(node.getElement()+" ");
		postorder(node.getRight());
	}
	public Position<E> LeftRotation(Position<E>p){
		Node<E> node = validate(p);
		Node<E> y=node.getRight();
		node.setRight(y.getLeft());
		if(y.getLeft()!=null){
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
		return node;
	}
	public Position<E> RightRotation(Position<E>p){
		Node<E> node = validate(p);
		Node<E> y=node.getLeft();
		node.setLeft(y.getRight());
		if(y.getRight()!=null){
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
		return node;
	}
	
}
