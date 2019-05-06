package com.untech.mcrcb.web.enhance;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
 * 
 *
 * @author            lxt
 * @version           1.0
 * @since             2014-5-9
 */
public class LowercaseAliasToEntityMapResultTransformer extends AliasedTupleSubsetResultTransformer {

	public static final LowercaseAliasToEntityMapResultTransformer INSTANCE = new LowercaseAliasToEntityMapResultTransformer();

	private LowercaseAliasToEntityMapResultTransformer() {
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
		for ( int i=0; i<tuple.length; i++ ) {
			String alias = aliases[i];
			if ( alias!=null ) {
				result.put(extractFieldName(alias), tuple[i] );
			}
		}
		return result;
	}

	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	private Object readResolve() {
		return INSTANCE;
	}
	
	private String extractFieldName(String columnName)
    {
        StringBuffer temp = new StringBuffer();
        String[] str = columnName.split("_");

        boolean first = true;
        for (int i = 0; i < str.length; i++)
        {
            if (str[i].equals(""))
            {
                continue;
            }
            if (first)
            {
                if (str[i].length() == 1) {
                    temp.append(str[i].toUpperCase());
                } else {
                    temp.append(str[i].toLowerCase());
                }
                first = false;
            }
            else
            {
                temp.append(str[i].substring(0, 1).toUpperCase()).append(
                        str[i].substring(1).toLowerCase());
            }
        }

        return temp.toString();
    }
}
