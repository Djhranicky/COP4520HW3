package presents;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * Implementation of a lock free concurrent linked list. Only takes ints as
 * values, and is always sorted least to greatest.
 */
public class ConcurrentLinkedList{
    private Node head;

    /**
     * Create an "empty" ConcurrentLinkedList. Integer.MIN_VALUE is the sentinel node
     */
    public ConcurrentLinkedList(){
        this.head = new Node(Integer.MIN_VALUE);
        this.head.next = new AtomicMarkableReference<Node>(null, false);
    }

    /**
     * Create a ConcurrentLinkedList with the given value as the first node.
     * Integer.MINVALUE is the sentinel node.
     * 
     * @param value Value to be initialized
     */
    public ConcurrentLinkedList(int value){
        this.head = new Node(Integer.MIN_VALUE);
        Node next = new Node(value);
        this.head.next = new AtomicMarkableReference<Node>(next, false);
    }

    /**
     * Finds the node before and the node of a given value
     * @param head The head of the ConcurrentLinkedList to search through
     * @param key The value to find
     * @return A new window object
     */
    public Window find(Node head, int key){
        Node pred = null, curr = null, succ = null;
        boolean[] marked = {false};
        boolean snip;
        retry: while(true){
            pred = head;
            if(pred.next.getReference() == null){
                return new Window(pred, curr);
            }
            curr = pred.next.getReference();
            while(true){
                succ = curr.next.get(marked);
                while(marked[0]){
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if(!snip) continue retry;
                    if(succ == null){
                        return new Window(pred, succ);
                    }
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if(curr.key >= key){
                    return new Window(pred, curr);
                }
                if(succ == null){
                    return new Window(curr, succ);
                }
                pred = curr;
                curr = succ;
            }
        }
    }

    public boolean contains(int key){
        boolean marked;
        Node curr = this.head;
        while(curr != null && curr.key < key){
            curr = curr.next.getReference();
        }
        if(curr == null) return false;
        marked = curr.next.isMarked();
        return (curr.key == key && !marked);
    }

    public boolean add(int key){
        while(true){
            Window window = find(this.head, key);
            Node pred = window.pred, curr = window.curr;
            if (curr == null){
                curr = new Node(key);
                curr.next = new AtomicMarkableReference<Node>(null, false);
                if(pred.next.compareAndSet(null, curr, false, false)) return true;
            }
            if (curr.key == key){
                return false;
            } else {
                Node node = new Node(key);
                node.next = new AtomicMarkableReference<Node>(curr, false);
                if(pred.next.compareAndSet(curr, node, false, false)){
                    return true;
                }
            }
        }
    }

    public boolean remove(int key){
        boolean snip;
        while(true){
            Window window = find(this.head, key);
            Node pred = window.pred, curr = window.curr;
            if(curr == null || curr.key != key){
                return false;
            } else {
                Node succ = curr.next.getReference();
                snip = curr.next.compareAndSet(succ, succ, false, true);
                if(!snip) continue;
                pred.next.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("[");
        Node curr = head.next.getReference();
        while(curr != null){
            ret.append(curr.key+", ");
            curr = curr.next.getReference();
        }
        ret.append("]");
        return ret.toString();
    }

    public boolean isEmpty(){
        return this.head.next.getReference() == null;
    }

    private class Node{
        int key;
        AtomicMarkableReference<Node> next;

        public Node(int key){
            this(key, null);
        }

        public Node(int key, AtomicMarkableReference<Node> next){
            this.key = key;
            this.next = next;
        }
    }

    private class Window{
        Node pred;
        Node curr;
        public Window(Node pred, Node curr){
            this.pred = pred;
            this.curr = curr;
        }
    }
}
