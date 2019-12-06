import java.util.ArrayList;

public class Hashtable {
    private ArrayList<HashNode> kvals;
    private final double LOAD_FACTOR = 0.5;
    private int numBucket;
    private int size;

    private class HashNode {
        String value;
        String key;
        HashNode next;
        HashNode(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public Hashtable() {
        this.kvals = new ArrayList<>();
        this.numBucket = 2028;
        this.size = 0;

        for(int i = 0; i < numBucket; i++) {
            kvals.add(null);
        }
    }

    public boolean containsKey(String key) {
        return kvals.get(hashfunction(key)) != null ? true : false;
    }

    public String get(String key) {
        HashNode head = kvals.get(hashfunction(key));

        while(head != null) {
            if(head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;


//        if(containsKey(key)) {    // this works too
//            HashNode temp = kvals.get(hashfunction(key));
//
//            while(!temp.key.equals(key)) {
//                temp = temp.next;
//            }
//            return temp.value;
//        }
//        return null;
    }

    public void put(String key, String value) {
        HashNode head = kvals.get(hashfunction(key));
        HashNode head_temp = head;
        while (head_temp != null) {
            if (head_temp.key.equals(key)) {
                head_temp.value = value;
                return;
            }
            head_temp = head_temp.next;
        }

        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        kvals.set(hashfunction(key), newNode);
        size++;

        if((1.0 * size)/numBucket >= LOAD_FACTOR) {
            ArrayList<HashNode> temp = kvals;

            numBucket = 2 * numBucket;

            kvals = new ArrayList<HashNode>(numBucket);

            size = 0; // size equals to 0 because you are calling the put function again to update the new arraylist

            for(int i = 0; i < numBucket; i++) {
                kvals.add(null);
            }

            for(HashNode node : temp) {
                while(node != null) {
                    put(node.key, node.value);
                    node = node.next;
                }
            }

        }
    }

    public String remove(String key) throws Exception{
//        HashNode head = kvals.get(hashfunction(key)); // this works too
//        if(head.key.equals(key)) {
//            kvals.set(hashfunction(key), head.next);
//            return head.value;
//        }else{
//            HashNode prev = head;
//            head = head.next;
//            while(head != null) {
//                if(head.key.equals(key)) {
//                    prev.next = head.next;
//                    return head.value;
//                }
//                prev = head;
//                head = head.next;
//            }
//        }
//        return null;
        if(containsKey(key)) {
            HashNode head = kvals.get(hashfunction(key));
            HashNode prev = null;
            while(head != null && !head.key.equals(key)) {
                prev = head;
                head = head.next;
            }
            if(prev == null) {
                kvals.set(hashfunction(key), head.next);
            }else{
                prev.next = head.next;
            }
            size--;
            return head.value;
        }else{
            throw new Exception();
        }
    }

    private int hashfunction(String key) {
        return (Math.abs(key.hashCode()) % numBucket);
    }


}
