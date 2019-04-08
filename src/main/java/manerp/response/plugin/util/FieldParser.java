package manerp.response.plugin.util;

import java.util.*;

public class FieldParser
{
    private int FIELD_QUERY_THRESHOLD;


    public FieldParser()
    {
        FIELD_QUERY_THRESHOLD = 50;
    }

    public FieldParser(int fieldQueryThreshold)
    {
        FIELD_QUERY_THRESHOLD = fieldQueryThreshold;
    }

    public void setFIELD_QUERY_THRESHOLD(int FIELD_QUERY_THRESHOLD)
    {
        this.FIELD_QUERY_THRESHOLD = FIELD_QUERY_THRESHOLD;
    }

    public List parseFieldsToList(String fields) throws Exception
    {
        Object[] fieldArr = (fields.replaceAll( "\\s", "" )).split( "," );
        ArrayList<Object> fieldList = new ArrayList<>( Arrays.asList( fieldArr ) );

        if (fieldList.size() > FIELD_QUERY_THRESHOLD)
            throw new Exception( "Number of requested fields cannot be greater than + " + FIELD_QUERY_THRESHOLD );

        for (int i = 0; i < fieldList.size(); i++) {

            String field = (String) fieldList.get( i );

            if (field.contains( "=" )) {

                Map<String, List<String>> fieldMap = new HashMap<>();
                String[] keyValueList = field.split( "=" );

                if (keyValueList.length > 2) throw new Exception( "Invalid syntax for query" );

                String key = keyValueList[0];
                String[] values = keyValueList[1].split( ";" );

                if (values.length > FIELD_QUERY_THRESHOLD)
                    throw new Exception( "Number of requested fields cannot be greater than + " + FIELD_QUERY_THRESHOLD );

                fieldMap.put( key, new ArrayList<>( Arrays.asList( values ) ) );
                fieldList.set( i, fieldMap );
            }
        }

        return fieldList;
    }
}
