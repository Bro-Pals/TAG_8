/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.util;

import bropals.level.Area;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public abstract class ObjectPool<O> {
    
    private ArrayList<O> pool;
    
    public ObjectPool() {
        pool = new ArrayList<O>();
    }
    
    public O request() {
        if (pool.size()>1) {
            O obj = pool.get(0);
            pool.remove(obj);
            pool.trimToSize();
            return obj;
        } else {
            return makeDefaultObject();
        }
    }
    
    protected abstract O makeDefaultObject();
    
    protected abstract void setToDefault(O obj);
    
    public void recycle(O recycling) {
        setToDefault(recycling);
        pool.add(recycling);
    }
}
