import material.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class represents a tree data structure using a linked implementation.
 * It implements the NAryTree interface.
 *
 * @param <E> the type of element stored in the tree
 */
public class LinkedTree<E> implements NAryTree<E> {
    private TreeNode<E> root;
    private int size;

    /**
     * This class represents a node in a tree data structure.
     * It implements the Position interface.
     *
     * @param <T> the type of element stored in the node
     */

    private class TreeNode<T> implements Position<T> {
        private ArrayList<Position<T>> children;
        private T element;

        public ArrayList<Position<T>> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<Position<T>> children) {
            this.children = children;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public TreeNode<T> getParent() {
            return parent;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        TreeNode<T> parent;
        public TreeNode(TreeNode<T> p,T element){
            this.children = new ArrayList<Position<T>>();
            this.parent = p;
            this.element = element;
        }
        @Override
        public T getElement() {
            return this.element;

        }

    }

    @Override
    public Position<E> addRoot(E e) {
        if (this.root==null){
            this.root = new TreeNode<>(null,e);
            this.size++;
            return this.root;
        }
        throw new RuntimeException();

    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> padre = checkPosition(p);
        TreeNode<E> hijo = new TreeNode<>(padre,element);
        padre.getChildren().add(hijo);
        this.size++;
        return hijo;
    }

    public TreeNode<E> checkPosition(Position<E> p){
        if (p == null || !(p instanceof TreeNode<E>)) {
            throw new UnsupportedOperationException("The position is invalid");
        }
        return (TreeNode<E>) p;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        TreeNode<E> padre = checkPosition(p);
        checkPositionOfChildrenList(n,padre);
        TreeNode<E> hijo = new TreeNode<>(padre,element);
        padre.getChildren().add(hijo);
        this.size++;
        return hijo;
    }
    private void checkPositionOfChildrenList(int n, TreeNode<E> parent) {
        if (n < 0 || n > parent.getChildren().size()) {
            throw new RuntimeException("The position is invalid");
        }
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        TreeNode<E> node1 = checkPosition(p1);
        TreeNode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node = checkPosition(p);
        E aux = node.getElement();
        node.setElement(e);
        return aux;
    }

    @Override
    public void remove(Position<E> p) {
        if(isRoot(p)){
            root = null;
            this.size = 0;
        }

        TreeNode<E> node = checkPosition(p);

    }
    public int numNodos(TreeNode <E> node){
        int cont = 1;
        for (Position<E> p: node.getChildren()){
            cont += numNodos(checkPosition(p));
        }
        return cont;
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        LinkedTree<E> t = new LinkedTree<>();
        t.root = node;
        t.size = numNodos(node);
        return t;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        return this.root==null;
    }

    @Override
    public Position<E> root() {
        return this.root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        if (isRoot(v)){
            throw new RuntimeException();
        }
        TreeNode<E> node = checkPosition(v);
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getChildren();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        if (isRoot(v)){
            return false;
        }
        TreeNode<E> node = checkPosition(v);
        if (node.getChildren().size()==0){
            return false;
        }
        return true;
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return this.root.equals(node);
    }

    @Override
    public Iterator<Position<E>> iterator() {
        //comprobar si está vacio
        List<Position<E>>positions = new ArrayList<>();
        breadthOrder(root, positions);
        return positions.iterator();
    }

    private void breadthOrder(TreeNode<E> node, List<Position<E>> positions) {
        if(node != null){
            List<TreeNode<E>> queue = new ArrayList<>();
            queue.add(node);
            while(!queue.isEmpty()){
                TreeNode<E> toExplore = queue.remove(0);
                positions.add(toExplore);
                queue.addAll(node.getChildren());
            }
        }
    }

    public int size() {
        return this.size;
    }
}
