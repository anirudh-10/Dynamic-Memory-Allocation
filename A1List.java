// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {

        A1List node=new A1List(address,size,key);
        A1List head=this;
        A1List temp=head.next;
        temp.prev=node;
        head.next=node;
        node.next=temp;
        node.prev=head;
        return node;
        
    }

    public boolean Delete(Dictionary d) 
    {
        //corner case 
        if(d==null)
        {
            return false;
        }
        //sentinel nodes should not be deleted
        if(d.size==-1&&d.key==-1&&d.address==-1)
        {
            return false;
        }
        //if current node has to be deleted then transfer data of previous node into this and delete previous
        // so that user doesn't lose reference to the DLL.
        if(this.key==d.key&&this.size==d.size&&this.address==d.address)
        {
            this.address=this.prev.address;
            this.key=this.prev.key;
            this.size=this.prev.size;
            A1List to_be_deleted=this.prev;
            if(to_be_deleted.prev!=null)
            {
                to_be_deleted.prev.next=to_be_deleted.next;
            }
            to_be_deleted.next.prev=to_be_deleted.prev;
            to_be_deleted.next=null;
            to_be_deleted.prev=null;
            to_be_deleted=null;
            return true;
        }
        //initialize temporary variable for forward and backward search 
        A1List temp1=this,temp2=this;
        //forward search
        while(temp1.next!=null)
        {
            if(temp1.key==d.key&&temp1.address==d.address&&temp1.size==d.size)
            {
                //cant delete "this" node as the user has pointer referring to "this" node only
                //will handle this case later on separately if no other node has same triplet
                temp1.prev.next=temp1.next;
                temp1.next.prev=temp1.prev;
                temp1.next=null;
                temp1.prev=null;
                temp1=null;
                return true;
                
            }
            temp1=temp1.next;
        }
        //backward search
        while(temp2.prev!=null)
        {
            if(temp2.key==d.key&&temp2.address==d.address&&temp2.size==d.size)
            {
                temp2.prev.next=temp2.next;
                temp2.next.prev=temp2.prev;
                temp2.next=null;
                temp2.prev=null;
                temp2=null;
                return true;    
            }
            temp2=temp2.prev;
        }
        return false;
    
    }
    public A1List Find(int k, boolean exact)
    { 
        //reaching head sentinel
        A1List head=this;
        while(head.prev!=null)
        {
            head=head.prev;
        }
        A1List temp=head.next;
        //case 1
        if(exact==true)
        {
            while(temp.next!=null)
            {
                if(temp.key==k)
                {
                    return temp;
                }
                temp=temp.next;
            }
        }
        //case 2
        else
        {
            temp=head.next;
            while(temp.next!=null)
            {
                if(temp.key>=k)
                {
                    return temp;
                }
                temp=temp.next;
            }

        }
        return null;
    }

    public A1List getFirst()
    {
        A1List temp=this;
        while(temp.prev!=null)
        {
            temp=temp.prev;
        }
        if(temp.next.next==null)
        {
            return null;
        }
        return temp.next;
    }
    public A1List getNext() 
    {
        if(this.next==null||this.next.next==null)
        {
            return null;
        }
        return this.next;
    }
    
    public boolean sanity()
    {
        //first we will check if any cycle exists as this cound hinder us from checling other cases correctly
        //Using Floyd Cycle Detection Algorithm
        {
            A1List slow_Pointer=this;
            A1List fast_Pointer=this;
            while(true)
            {
                if(fast_Pointer.next==null||fast_Pointer.next.next==null)
                {
                    break;
                }
                slow_Pointer=slow_Pointer.next;
                fast_Pointer=fast_Pointer.next.next;
                if(slow_Pointer==fast_Pointer)
                {
                    return false;
                }
            }
            while(true)
            {
                if(fast_Pointer.prev==null||fast_Pointer.prev.prev==null)
                {
                    break;
                }
                slow_Pointer=slow_Pointer.prev;
                fast_Pointer=fast_Pointer.prev.prev;
                if(slow_Pointer==fast_Pointer)
                {
                    return false;
                }
            }

        }
        //checking if head and tail sentinel nodes have their prev and next pointer pointing to null respectively
        // also ensures existence of sentinel nodes
        {
            A1List temp=this;
            while(temp.next!=null)
            {
                temp=temp.next;
            }
            if(temp.key!=-1||temp.address!=-1||temp.size!=-1)
            {
                return false;
            }
            temp=this;
            while(temp.prev!=null)
            {
                temp=temp.prev;
            }
            if(temp.key!=-1||temp.address!=-1||temp.size!=-1)
            {
                return false;
            }
        }
        //defining head to headsentinel
        A1List head=this;
        while(head.prev!=null)
        {
            head=head.prev;
        }
        //checking if node.next.prev=node while going forward and node.pre.next=node while going backward for all nodes
        {
            //going forward
            A1List temp=this;
            while(temp.next!=null)
            {
                if(temp.next.prev!=temp)
                {
                    return false;
                }
                temp=temp.next;
            }
            temp=this;
            //going backward
            while(temp.prev!=null)
            {
                if(temp.prev.next!=temp)
                {
                    return false;
                }
                temp=temp.prev;
            }
        }
        return true;
    }

}


