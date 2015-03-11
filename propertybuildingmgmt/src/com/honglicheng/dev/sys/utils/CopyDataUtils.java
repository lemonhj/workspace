package com.honglicheng.dev.sys.utils;

import java.util.List;

public class CopyDataUtils {
	public static void copyList(List dest ,List source ) 
    {
        dest.clear();
        for( int i = 0 ; i < source.size() ; i++ )
        {
            dest.add( source.get( i ));
        }
    }
}
