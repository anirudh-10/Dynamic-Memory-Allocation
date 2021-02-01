// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    private void connect(BSTree node,BSTree par,int x)
    {
        if(x==1)
        {
            par.left=node;  
        }
        else
        {
            par.right=node;
        }
        node.parent=par;
        node.left=null;
        node.right=null;
        return;
    }
    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree temp=this;
        BSTree node=new BSTree(address,size,key);
        while(temp.parent!=null)
        {
            temp=temp.parent;
        }
        if(temp.left==null&&temp.right==null)
        {
            temp.right=node;
            node.parent=temp;
            node.left=null;
            node.right=null;
            return node;
        }

        temp=temp.right;
        //System.out.println(address + " haha " + size + " haha " + key);
        //printtree(temp);
        while(true)
        {
            if(temp.key>node.key)
            {
                if(temp.left==null)
                {
                    connect(node,temp,1);
                    break;
                }
                temp=temp.left;
            }
            else if(temp.key<node.key)
            {
                if(temp.right==null)
                {
                    connect(node,temp,0);
                    break;
                }
                temp=temp.right;
            }
            else
            {
                if(temp.address>node.address)
                {
                    if(temp.left==null)
                    {
                        connect(node,temp,1);
                        break;
                    }
                    temp=temp.left;
                }
                else
                {
                    if(temp.right==null)
                    {   
                        connect(node,temp,0);
                        break;
                    }
                    temp=temp.right;
                }
            }
        }
        return node;
    }
    private BSTree successor(BSTree node)
    {
        while(node.left!=null)
        {
            node=node.left;
        }
        return node;
    }
    private boolean del(BSTree root,Dictionary e)
    {
        //System.out.println(e.address + " main "+e.size + " main "+e.key);
        while(true)
        {
            if(root==null)
            {
                return false;
            }
            if(root.key>e.key||(root.key==e.key&&root.address>e.address))
            {
                root=root.left;
                continue;
            }
            else if(root.key<e.key||(root.key==e.key&&root.address<e.address))
            {
                root=root.right;
                continue;
            }
            else if(root.key==e.key&&root.address==e.address&&root.size==e.size)
            {
                //no child
                if(root.left==null&&root.right==null)
                {   
                    if(root.parent.left==root)
                    {
                        root.parent.left=null;
                    }
                    else if(root.parent.right==root)
                    {
                        root.parent.right=null;
                    }
                    root.parent=null;
                    root.left=null;
                    root.right=null;
                }
                //1 left child
                else if(root.right==null)
                {
                    
                    if(root.parent.left==root)
                    {
                    //  System.out.println("herehereherehere");
                        root.parent.left=root.left;
                        root.left.parent=root.parent;
                    }

                    else if(root.parent.right==root)
                    {
                        root.parent.right=root.left;
                        root.left.parent=root.parent;
                    }
                }
                else if(root.left==null)
                {
                    if(root.parent.left==root)
                    {
                        root.parent.left=root.right;
                        root.right.parent=root.parent;
                    }
                    else
                    {
                        root.parent.right=root.right;
                        root.right.parent=root.parent;
                    }   
                    root.parent=null;
                    root.left=null;
                    root.right=null;
                }
                else
                {
                    BSTree next_min=root.right;
                    while(next_min.left!=null)
                    {
                        next_min=next_min.left;
                    }
                    BSTree copy_of_next_min = new BSTree(next_min.address,next_min.size,next_min.key);
                    if(root.parent.left==root)
                    {
                        root.parent.left=copy_of_next_min;
                    }
                    copy_of_next_min.parent=root.parent;
                    copy_of_next_min.left=root.left;
                    if(root.parent.right==root)
                    {
                        root.parent.right=copy_of_next_min;
                    }
                    copy_of_next_min.right=root.right;
                    root.left.parent=copy_of_next_min;
                    root.right.parent=copy_of_next_min;
                    root.parent=null;
                    root.left=null;
                    root.right=null;
                    boolean temporary=del(copy_of_next_min.right,next_min);
                    /*
                    if(root.right==next_min)
                    {
                        if(root.parent.left==root)
                        {
                            root.parent.left=next_min;
                        }
                        else
                        {
                            root.parent.right=next_min;
                        }
                        root.left.parent=next_min;
                        next_min.left=root.left;
                        next_min.parent=root.parent;
                        root.parent=null;
                        root.left=null;
                        root.right=null;

                    }
                    else
                    {
                        if(next_min.right!=null)
                        {
                            if(next_min.parent==null)
                            {
                                System.out.println("no way");
                            }
                            next_min.parent.left=next_min.right;
                            next_min.right.parent=next_min.parent;
                        }
                        else
                        {
                            next_min.parent.left=null;
                        }
                        
                        if(root.parent.right==root)
                        {
                            root.parent.right=next_min;
                        }   
                        else
                        {
                            root.parent.left=next_min;
                        }
                        next_min.parent=root.parent;
                        next_min.right=root.right;
                        next_min.left=root.left; 
                        root.parent=null;
                        root.left=null;
                        root.right=null;    
                    }
                    */      
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        
    }
    public boolean Delete(Dictionary e)
    { 
        BSTree temp=this;

        while(temp.parent!=null)
        {
            temp=temp.parent;
        }
        BSTree head=temp;
        if(temp.right==null)
        {
            return false;
        }
        temp=temp.right;
        //printtree(temp);
        //System.out.println("treecomp");
        boolean possible=del(temp,e);
        //e.size=size_temp;
        //e.address=address_temp;
        //e.key=key_temp;
        
        if(possible==true)
        {
        //      printtree(head.right);
        //System.out.println("treecomp");
            return true;
        }
        return false;
    }
        
    public BSTree Find(int key, boolean exact)
    { 
        BSTree temp=this;
        while(temp.parent!=null)
        {
            temp=temp.parent;
        }
        if(temp.right==null)
        {
            return null;
        }
        temp=temp.right;
        /*
        if(exact==true)
        {
            printtree(temp);
        }
        */
        
        
        if(exact==true)
        {
            while(true)
            {
                if(temp==null)
                {
                    return null;
                }
                else if(temp.key>key)
                {
                    temp=temp.left;
                }
                else if(temp.key<key)
                {
                    temp=temp.right;
                }
                else
                {
                    //System.out.println(key+ " findb "+  temp.key );
                    while(temp.left!=null&&temp.left.key==temp.key)
                    {
                        temp=temp.left;
                    }
                    //System.out.println(key+ " finda "+  temp.key );
                    return temp;
                }
            }
        }   
        else
        {
            BSTree node=null;   
            while(true)
            {
                if(temp==null)
                {
                    return node;
                }
                else if(temp.key<key)
                {
                    temp=temp.right;
                }
                else
                {
                    while(temp.left!=null&&temp.left.key>=key)
                    {
                        temp=temp.left;
                    }
                    node=temp;
                    temp=temp.left;
                }
            }
            
        }
        
    }

    public BSTree getFirst()
    { 
        BSTree temp=this;
        while(temp.parent!=null)
        {
            temp=temp.parent;
        }
        if(temp.right==null)
        {
            return null;
        }
        temp=temp.right;
        while(temp.left!=null)
        {
            temp=temp.left;
        }
        return temp;
    }

    public BSTree getNext()
    { 
        BSTree temp=this;
        if(temp.parent==null)
        {
            return null;
        }

        if(temp.right==null)
        {
            while(true)
            {
                if(temp.parent==null)
                {
                    return null;
                }
                if(temp.parent.left==temp)
                {
                    return temp.parent;
                }
                temp=temp.parent;
            }
        }
        else
        {
            temp=temp.right;
            while(temp.left!=null)
            {
                temp=temp.left;
            }
            return temp;
        }
    }
    //BST search property validity
    private boolean is_BST_Valid(BSTree root,int mn_key,int mx_key,int mn_address,int mx_address)
    {
        if(root==null)
        {
            return true;
        }
        if(root.key>mx_key)
        {
            return false;
        }
        if(root.key<mn_key)
        {
            return false;
        }
        if(root.key==mn_key)
        {
            if(root.address<mn_address)
            {
                return false;
            }

        }
        if(root.key==mx_key)
        {
            if(root.address>mx_address)
            {
                return false;
            }
        }
        return ((is_BST_Valid(root.left,mn_key,root.key,mn_address,root.address))&&(is_BST_Valid(root.right,root.key,mx_key,root.address,mx_address)));
    }
    //checking root.child.parent==root
    private boolean check_double_relation(BSTree root,int x)
    {
        if(x==1)
        {
            if(root.right.parent!=root)
            {
                return false;
            }
            if((root.left!=null)&&(root.right==root.left))
            {
                return false;
            }
        }
        else
        {
            if(root.left.parent!=root)
            {
                return false;
            }
            if((root.right!=null)&&(root.left==root.right))
            {
                return false;
            }
        }
        return true;
    }
    //euler tour for confirming that there are no cycles and nodes are doubly connected
    private boolean euler_tour(BSTree root)
    {
        if(root.left==null&&root.right==null)
        {
            return true;
        }
        if(root.left!=null)
        {
            boolean relation=check_double_relation(root,0);
            if(relation==false)
            {
                return false;
            }
            return euler_tour(root.left);
        }
        if(root.right!=null)
        {
            boolean relation=check_double_relation(root,1);
            if(relation==false)
            {
                return false;
            }
            return euler_tour(root.right);
        }
        return true;
    }
    public boolean sanity()
    { 
        //reaching head sentinel from this and checking if cycle exists in that path(floyd cycle detection)
        // After that assuring nodes are doubly linked and there is no cycle in the whole tree(graph)
        BSTree head=this;
        {
            BSTree slow_pointer=this;
            BSTree fast_pointer=this;
            while(true)
            {
                if(fast_pointer.parent==null||fast_pointer.parent.parent==null)
                {
                    break;
                }
                slow_pointer=slow_pointer.parent;
                fast_pointer=fast_pointer.parent.parent;
                if(slow_pointer==fast_pointer)
                {
                    return false;
                }
            }
            head =fast_pointer;
            if(fast_pointer.parent!=null)
            {
                head=head.parent;
            }
            boolean validity=euler_tour(head);
            if(validity==false)
            {
                return false;
            }
        }
        //checking validity of BST
        {
            BSTree temp=head.right;
            boolean validity=is_BST_Valid(temp,Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
            if(validity==false)
            {
                //System.out.println("HERE");
                return false;
            }   
        }
        return true;
        
    }

}


 


