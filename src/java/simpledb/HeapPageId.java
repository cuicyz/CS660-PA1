package simpledb;

import java.util.NoSuchElementException;

/** Unique identifier for HeapPage objects. */
public class HeapPageId implements PageId {

    /**
     * Constructor. Create a page id structure for a specific page of a
     * specific table.
     *
     * @param tableId The table that is being referenced
     * @param pgNo The page number in that table.
     */
	private int page_no;
	private int table_id;
	
    public HeapPageId(int tableId, int pgNo) {
        // some code goes here
    	page_no=pgNo;
    	table_id=tableId;
    }

    /** @return the table associated with this PageId */
    public int getTableId() {
        // some code goes here
        return table_id;
    }

    /**
     * @return the page number in the table getTableId() associated with
     *   this PageId
     */
    public int pageNumber() {
        // some code goes here
        return page_no;
    }

    /**
     * @return a hash code for this page, represented by the concatenation of
     *   the table number and the page number (needed if a PageId is used as a
     *   key in a hash table in the BufferPool, for example.)
     * @see BufferPool
     */
    public int hashCode() {
        // some code goes here
        //throw new UnsupportedOperationException("implement this");
    	String str="table_id"+"page_no";
    	return str.hashCode();
    }

    /**
     * Compares one PageId to another.
     *
     * @param o The object to compare against (must be a PageId)
     * @return true if the objects are equal (e.g., page numbers and table
     *   ids are the same)
     */
    public boolean equals(Object o) {
        // some code goes here
    	if(o!=null&&o instanceof PageId) {
    		PageId new_pgid=(PageId)o;
    		return this.getTableId()==new_pgid.getTableId()&&this.pageNumber()==new_pgid.pageNumber();
    	}
    	return false;
    }

    /**
     *  Return a representation of this object as an array of
     *  integers, for writing to disk.  Size of returned array must contain
     *  number of integers that corresponds to number of args to one of the
     *  constructors.
     */
    public int[] serialize() {
        int data[] = new int[2];

        data[0] = getTableId();
        data[1] = pageNumber();

        return data;
    }

}
