package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {
	
	private ArrayList<TDItem> new_item;

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;
        
        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        return new_item.iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        new_item=new ArrayList<TDItem>(typeAr.length);
        for(int i=0;i<typeAr.length;i++) {
        	TDItem other=new TDItem(typeAr[i],fieldAr[i]);
        	new_item.add(other);
        }
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        this(typeAr, new String[typeAr.length]);
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        return new_item.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        if (i<0||i>numFields())
        	throw new NoSuchElementException("Wrong i");
        return new_item.get(i).fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        if (i<0||i>numFields()) 
        	throw new NoSuchElementException("Wrong i");
        return new_item.get(i).fieldType;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    @SuppressWarnings("unused")
	public int fieldNameToIndex(String name) throws NoSuchElementException {
        if (name==null)
        	throw new NoSuchElementException("There is no Name");
        for (int i=0;i<new_item.size();i++) {
        	String curr_name=new_item.get(i).fieldName;
        	if (curr_name==null)
        		continue;
        	if (curr_name.equals(name))
        		return i;
        }
        throw new NoSuchElementException("No matching");			
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        int total_size=0;
        for (TDItem item:new_item) {
        	total_size+=item.fieldType.getLen();
        }
        return total_size;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        int new_size=td1.numFields()+td2.numFields();
    	String[] new_name=new String[new_size];
    	Type[] new_type=new Type[new_size];
    	/**different type and name for different part of tuple**/
    	for(int i=0;i<td1.numFields();i++) {
    		new_type[i]=td1.getFieldType(i);
    		new_name[i]=td1.getFieldName(i);
    	}
    	for(int j=td1.numFields();j<td1.numFields()+td2.numFields();j++) {
    		new_type[j]=td2.getFieldType(j-td1.numFields());
    		new_name[j]=td2.getFieldName(j-td1.numFields());
    	}
    			
        return new TupleDesc(new_type,new_name);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        if(o==null)
        	return false;
        if(!(o instanceof TupleDesc))
        	return false;
        TupleDesc a=(TupleDesc) o;
        if(this.numFields()==a.numFields()&&this.getSize()==a.getSize()) {
        	for(int i=0;i<a.numFields();i++)
        		if(!(this.getFieldType(i).equals(a.getFieldType(i))))
        			return false;
        return true;
        }
        return false;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        StringBuffer str=new StringBuffer();
        for(int i=0;i<this.numFields();i++) {
        	str.append(this.getFieldType(i));
        	str.append('(');
        	str.append(this.getFieldName(i));
        	str.append(')');
        	if (i!=this.numFields()-1)
        		str.append(',');
        }
        return str.toString();
    }
}
