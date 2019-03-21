package manerp.response.plugin.pagination

class ManePaginationProperties {

    int offset
    short max
    List<SortPair> sortPairList

    ManePaginationProperties(short max, int offset) {

        if ( max > 1000 ) throw new Exception('Maximum result cannot be greater than 1000')
        if ( offset < 0 ) throw new Exception('Offset cannot be a negative value')

        this.max = max
        this.offset = offset
        sortPairList = new ArrayList<SortPair>()
    }

    ManePaginationProperties(short max, int offset, String sortParam) {

        this(max, offset)
        parseSortParamToList(sortParam)
    }

    private void parseSortParamToList(String sortParam) {

        String[] sortList = sortParam.split(",")

        for ( String sort : sortList ) {

            String[] kv = sort.split(" ")

            if ( kv.length == 1 ) {

                sortPairList.add(new SortPair(kv[0], "asc"))
            } else if ( kv.length == 2 ) {

                String key = kv[0]
                String value = kv[1].toLowerCase()

                if ( value == "asc" || value == "desc" ) {

                    sortPairList.add(new SortPair(key, value))
                }
            }

        }
    }
}
