package manerp.response.plugin.pagination

import manerp.response.plugin.util.FieldParser

class ManePaginationProperties
{

    private static final FIELD_QUERY_THRESHOLD = 50
    int offset = 0
    short limit = 10
    ArrayList<SortPair> sortPairList = new ArrayList<SortPair>()
    ArrayList<Object> fieldList = new ArrayList<Object>()

    ManePaginationProperties(short limit, int offset)
    {

        if ( limit > 1000 ) throw new Exception('Max result cannot be greater than 1000')
        if ( offset < 0 ) throw new Exception('Offset cannot be a negative value')

        this.limit = limit
        this.offset = offset
        sortPairList = new ArrayList<SortPair>()
    }

    ManePaginationProperties(short limit, int offset, String sortParam)
    {
        this(limit, offset)
        if ( sortParam ) parseSortParamToList(sortParam)
    }

    ManePaginationProperties(short limit, int offset, String sortParam, String fields)
    {
        this(limit, offset, sortParam)
        if ( fields ) {
            FieldParser parser = new FieldParser();
            this.fieldList = parser.parseFieldsToList(fields)
        }
    }

    private void parseSortParamToList(String sortParam)
    {
        String[] sortList = sortParam.split(',')

        for ( String sort : sortList ) {

            String[] kv = sort.split(' ')

            if ( kv.length == 1 ) {

                sortPairList.add(new SortPair(kv[0], 'asc'))
            } else if ( kv.length == 2 ) {

                String key = kv[0]
                String value = kv[1].toLowerCase()

                if ( value == 'asc' || value == 'desc' ) {

                    sortPairList.add(new SortPair(key, value))
                }
            }

        }
    }

}
