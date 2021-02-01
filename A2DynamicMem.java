// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() 
    {
        //this.freeBlk.printthetree();
        /*
        boolean possible = this.freeBlk.sanity();
        if(possible==false)
        {
            System.out.println("false");
        }
        else
        {
            System.out.println("true");
        }
        */
        Dictionary new_free;
        if(type==2)
        {
            new_free = new BSTree();
        }
        else
        {
            new_free = new AVLTree();
        }
        
        for(Dictionary d=this.freeBlk.getFirst();d!=null;d=d.getNext())
        {
            new_free.Insert(d.address,d.size,d.address);
        }
        Dictionary prev=new_free.getFirst();
        if(prev==null)
        {
            return;
        }
        Dictionary current= prev.getNext();
        while(current!=null)
        {
            //System.out.println(prev.address+prev.size+" "+current.address+" size+address");
            if(prev.address+prev.size!=current.address)
            {
                prev=current;
                current=current.getNext();
            }
            else
            {
                
                prev.key=prev.size;
                current.key=current.size;
                this.freeBlk.Delete(prev);
                prev.key=prev.address;
                this.freeBlk.Delete(current);
                current.key=current.address;
                prev.size+=current.size;
                new_free.Delete(current);
                this.freeBlk.Insert(prev.address,prev.size,prev.size);
                current=prev.getNext();  
                //if(current==null)
                //{
                //    System.out.println(prev.address+ " "+prev.size+ "fjknsdljfnlsdn");
                //}    
            }
            
        }
        /*
        System.out.println("defragment 11 ");
        for(Dictionary d=this.freeBlk.getFirst();d!=null;d=d.getNext())
        {
            System.out.println(d.address + " "+ d.size+ " "+ d.key);
        }
        */
        new_free=null;
        //this.freeBlk.printthetree();
        return;
    }
}