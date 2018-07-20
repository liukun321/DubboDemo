package com.mixiusi.protocol.marshal;

import java.util.ArrayList;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class ArrayMable implements Marshallable
{
    public ArrayList<Marshallable> list;
    
    public Class<? extends Marshallable> clazz;
    
    public ArrayMable() {
    	
    }
    
    public ArrayMable(Class<? extends Marshallable> clazz)
    {
        this.clazz = clazz;
    }
    
    public ArrayMable(Marshallable[] l)
    {
        list = new ArrayList<Marshallable>(l.length );
        
        for( Marshallable s : l )
            list.add( s );
    }
    
    public ArrayMable(int size)
    {
        list = new ArrayList<Marshallable>(size);
    }
    
    public  ArrayMable(ArrayList<Marshallable> l)
    {
        this.list = l;
    }
    public void add(Marshallable o)
    {
    	
        this.list.add(o);
    }
    
    public int size()
    {
        return list.size();
    }
    
    public void setClazz(Class<? extends Marshallable> clazz) {
    	this.clazz = clazz;
    }
    
    @Override
    public void marshal(Pack pack)
    {
        pack.putInt( list.size() );
        
        for( int i = 0; i < list.size(); ++i )
            pack.putMarshallable( list.get( i ) );
    }
    
    @Override
    public void unmarshal(Unpack unpack)
    {
        int len = unpack.popInt();
        list = new ArrayList<Marshallable>(len);
        
        for (int i = 0; i < len; ++i)
        {
            try
            {
                list.add(unpack.popMarshallable((Marshallable) clazz.newInstance()));
            }
            catch (InstantiationException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}