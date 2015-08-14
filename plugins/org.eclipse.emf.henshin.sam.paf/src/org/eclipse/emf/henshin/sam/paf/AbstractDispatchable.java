package org.eclipse.emf.henshin.sam.paf;

/**
 * No comment provided by developer, please add a comment to improve documentation.
 */
public abstract class AbstractDispatchable
    implements IDispatchable
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FilterDispatcher filterDispatcher;


   /**
    * Get the filterDispatcher attribute of the AbstractDispatchable object
    *
    * @return   The filterDispatcher value
    */
   public FilterDispatcher getFilterDispatcher()
   {
      return  (this.filterDispatcher);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public abstract void reset();


   /**
    * Sets the filterDispatcher attribute of the AbstractDispatchable object
    *
    * @param value  The new filterDispatcher value
    * @return       No description provided
    */
   public boolean setFilterDispatcher (FilterDispatcher value)
   {
      boolean changed = false;
      if (value != this.filterDispatcher)
      {
         if (this.filterDispatcher != null)
         {
            FilterDispatcher oldvalue = this.filterDispatcher;
            this.filterDispatcher = null;
            oldvalue.removeFromIDispatchable (this);
         }
         this.filterDispatcher = value;
         changed = true;
         if (value != null)
         {
            value.addToIDispatchable (this);
         }
      }
      return  (changed);
   }

}

/*
 * $Log$
 */
