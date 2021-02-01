// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    //left rotation
    private AVLTree left_rotate(AVLTree z)
    {
    	AVLTree y=z.right;
    	AVLTree tree1 = y.left;
    	AVLTree par=z.parent;
    	if(par.left==z)
    	{
    		par.left=y;
    	}
    	else
    	{
    		par.right=y;
    	}
    	y.parent=par;
    	y.left=z;
    	z.parent=y;
    	z.right=tree1;
    	if(tree1!=null)
    	{
    		tree1.parent=z;
    	}
    	z.height=Math.max(1+getheight(z.left),1+getheight(z.right));
    	y.height=Math.max(1+getheight(y.left),1+getheight(y.right));
    	return y;

    }
    //right rotation
    private AVLTree right_rotate(AVLTree z)
    {
    	AVLTree y=z.left;
    	AVLTree tree1 = y.right;
    	AVLTree par=z.parent;
    	if(par.left==z)
    	{
    		par.left=y;
    	}
    	else
    	{
    		par.right=y;
    	}
    	y.parent=par;
    	y.right=z;
    	z.parent=y;
    	z.left=tree1;
    	if(tree1!=null)
    	{
    		tree1.parent=z;
    	}
    	z.height=Math.max(1+getheight(z.left),1+getheight(z.right));
    	y.height=Math.max(1+getheight(y.left),1+getheight(y.right));
    	return y;

    }
    private int getheight(AVLTree node)
    {
    	if(node!=null)
    	{
    		return node.height;
    	}
    	return 0;
    }
    private boolean is_greater(AVLTree root,AVLTree node)
    {
    	if((root.key>node.key)||(root.key==node.key&&root.address>node.address))
    	{
    		return true;
    	}
    	return false;
    }
    private boolean is_lesser(AVLTree root,AVLTree node)
    {
    	if((root.key<node.key)||(root.key==node.key&&root.address<node.address))
    	{
    		return true;
    	}
    	return false;
    }
   
    private AVLTree insertion(AVLTree root,AVLTree node)
    {
    	if(root==null)
    	{
    		return node;
    	}
    	if((root.key>node.key)||(root.key==node.key&&root.address>node.address))
    	{
    		root.left=insertion(root.left,node);
    		if(root.left!=null)
    		{
    			root.left.parent=root;
    		}
    	}
    	else
    	{
    		root.right=insertion(root.right,node);
    		if(root.right!=null)
    		{
    			root.right.parent=root;
    		}
    	}
    	
    	root.height=Math.max(1+getheight(root.left),1+getheight(root.right));
    	int check_balance=getheight(root.left)-getheight(root.right);
    	if(check_balance<-1)
    	{
    		if(is_greater(root.right,node)==true)
    		{
    			root.right=right_rotate(root.right);
    			if(root.right!=null)
    			{
    				root.right.parent=root;
    			}
    			return left_rotate(root);
    		}
    		else if(is_lesser(root.right,node)==true)
    		{
    			return left_rotate(root);
    		}
    	}
    	else if(check_balance>1)
    	{
    		if(is_greater(root.left,node)==true)
    		{
    			return right_rotate(root);
    		}
    		else if(is_lesser(root.left,node)==true)
    		{
    			root.left=left_rotate(root.left);
    			if(root.left!=null)
    			{
    				root.left.parent=root;
    			}
    			return right_rotate(root);
    		}
    	}
    	
    	return root;

    }
    public AVLTree Insert(int address, int size, int key) 
    { 
    	AVLTree temp=this;
    	AVLTree node = new AVLTree(address,size,key);
    	node.height=1;
    	while(temp.parent!=null)
    	{
    		temp=temp.parent;
    	}
    	if(temp.right==null)
    	{
    		temp.right=node;
    		node.parent=temp;
    		node.left=null;
    		node.right=null;
    		return node;
    	}
    	temp.right=insertion(temp.right,node);
    	temp.right.parent=temp;
        return node;
    }
    private AVLTree del(AVLTree root,Dictionary e,int[] deleted)
    {
    	AVLTree temp=root;
    	if(root==null)
    	{
    		return null;
    	}
    	if((root.key>e.key)||(root.key==e.key&&root.address>e.address))
    	{
    		root.left=del(root.left,e,deleted);
    		if(root.left!=null)
    		{
    			root.left.parent=root;
    		}
    	}
    	else if((root.key<e.key)||(root.key==e.key&&root.address<e.address))
    	{
    		root.right=del(root.right,e,deleted);
    		if(root.right!=null)
    		{
    			root.right.parent=root;
    		}

    	}
    	else
    	{
    		deleted[0]=1;
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
                    temp=null;
                    root.parent=null;
                    root.left=null;
                    root.right=null;
                }
                //1 left child
                else if(root.right==null)
                {
                    //System.out.println("here");
                    if(root.parent.left==root)
                    {
                    //  System.out.println("herehereherehere");
                        root.parent.left=root.left;
                        root.left.parent=root.parent;
                    }

                    else if(root.parent.right==root)
                    {
                    	//System.out.println("here1");
                    
                        root.parent.right=root.left;
                        root.left.parent=root.parent;
                    }
                    temp=root.left;
                    root.left=null;
                    root.right=null;
                    root.parent=null;
                }
                //1 right child
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
                    temp=root.right; 
                    root.parent=null;
                    root.left=null;
                    root.right=null;
                }
                else
                {
                	AVLTree next_min=root.right;
                    while(next_min.left!=null)
                    {
                        next_min=next_min.left;
                    }
                    AVLTree copy_of_next_min = new AVLTree(next_min.address,next_min.size,next_min.key);
                    temp=copy_of_next_min;
                    copy_of_next_min.height=root.height;
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
                    root.left.parent=copy_of_next_min;
                    root.right.parent=copy_of_next_min;
                    copy_of_next_min.right=root.right;
                    root.parent=null;
                    root.left=null;
                    root.right=null;

                    copy_of_next_min.right=del(copy_of_next_min.right,next_min,deleted);
                }
    	}
    	if(temp==null)
    	{
    		return temp;
    	}
    	temp.height=Math.max(1+getheight(temp.left),1+getheight(temp.right));
		int check_balance=getheight(temp.left)-getheight(temp.right);
		if(check_balance<-1)
		{
			//System.out.println("here2");
                    
		  	int balance_right=((temp.right==null)?0:(getheight(temp.right.left)-getheight(temp.right.right)));
		    if(balance_right>0)
		    {
		    	temp.right=right_rotate(temp.right);
		    	if(temp.right!=null)
    			{
    				temp.right.parent=temp;
    			}
		    	return left_rotate(temp);
		    }
		    else
			{
				return left_rotate(temp);
		    }
		}
		else if(check_balance>1)
		{
			//System.out.println("here3");
                    
		    int balance_left=((temp.left==null)?0:(getheight(temp.left.left)-getheight(temp.left.right)));
		    if(balance_left>=0)
		    {
		    	return right_rotate(temp);
		    }
		    else
		    {
		    	temp.left=left_rotate(temp.left);
		    	if(temp.left!=null)
    			{
    				temp.left.parent=temp;
    			}
		    	return right_rotate(temp);
		    }
		}
		return temp;

    }
    public boolean Delete(Dictionary e)
    {

        AVLTree temp=this;

        while(temp.parent!=null)
        {
            temp=temp.parent;
        }
        AVLTree head=temp;
        if(temp.right==null)
        {
            return false;
        }
        int[] is_deleted=new int[1];
        is_deleted[0]=0;
        temp.right=del(temp.right,e,is_deleted);
        if(temp.right!=null)
        {
        	temp.right.parent=temp;
        }
        if(is_deleted[0]==1)
        {
        	return true;
        }
        return false;
    }
        
    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree temp=this;
        while(temp.parent!=null)
        {
            temp=temp.parent;
        }
        if(temp.right==null)
        {
            return null;
        }
        temp=temp.right;
        if(exact==true)
        {
            while(true)
            {
                if(temp==null)
                {
                    return null;
                }
                else if(temp.key>k)
                {
                    temp=temp.left;
                }
                else if(temp.key<k)
                {
                    temp=temp.right;
                }
                else
                {
                    while(temp.left!=null&&temp.left.key==temp.key)
                    {
                        temp=temp.left;
                    }
                    return temp;
                }
            }
        }   
        else
        {
            AVLTree node=null;   
            while(true)
            {
                if(temp==null)
                {
                    return node;
                }
                else if(temp.key<k)
                {
                    temp=temp.right;
                }
                else
                {
                    while(temp.left!=null&&temp.left.key>=k)
                    {
                        temp=temp.left;
                    }
                    node=temp;
                    temp=temp.left;
                }
            }
            
        }
        
    }

    public AVLTree getFirst()
    { 
        AVLTree temp=this;
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

    public AVLTree getNext()
    {
        AVLTree temp=this;
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
    private boolean is_BST_Valid(AVLTree root,int mn_key,int mx_key,int mn_address,int mx_address)
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
    private boolean check_double_relation(AVLTree root,int x)
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
    private boolean euler_tour(AVLTree root)
    {
    	if(root.height!=Math.max(1+getheight(root.left),1+getheight(root.right)))
    	{
    		return false;
    	}
    	if(Math.abs(getheight(root.left)-getheight(root.right))>1)
    	{
    		return false;
    	}
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
        //In euler_tour function we also check height of each node
        AVLTree head=this;
        {
            AVLTree slow_pointer=this;
            AVLTree fast_pointer=this;
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
            AVLTree temp=head.right;
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


