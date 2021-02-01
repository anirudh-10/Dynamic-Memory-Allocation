public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the “dictionary” class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        int blk=blockSize;  
        if(blk<=0)
        {
            return -1;
        }

        Dictionary block=this.freeBlk.Find(blk,false);
        
        if(block==null)
        {
            return -1;
        }
        
        int new_address=block.address+blk;
        int fragment_size=block.size-blk;
        if(fragment_size>0)
        {
            this.freeBlk.Insert(new_address,fragment_size,fragment_size);
        }
        
        this.freeBlk.Delete(block);
        this.allocBlk.Insert(block.address,blk,block.address);
        return block.address;

    } 
    // return 0 if successful, -1 otherwise
    public int Free(int startAddr) 
    {
        if(startAddr<0)
        {
            return -1;
        }
        Dictionary block=this.allocBlk.Find(startAddr,true);
        if(block!=null)
        {
            this.allocBlk.Delete(block);
            
            this.freeBlk.Insert(block.address,block.size,block.size);
            
            return 0;
        }

        return -1;
    }
}