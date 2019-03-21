package manerp.response.plugin.pagination

class ManePaginatedResult {

    def data
    int totalCount
    ManePaginationProperties properties


    ManePaginatedResult(def data, int totalCount, ManePaginationProperties properties) {
        this.data = data
        this.totalCount = totalCount
        this.properties = properties
    }

    Map toMap() {

        Map map = [
                data    : data,
                metaData: [
                        pagination: [
                                offset    : properties.offset,
                                limit     : properties.max,
                                totalCount: totalCount
                        ]
                ]
        ]

        if ( !properties.sortPairList ) {
            map.metaData.sortedBy = properties.sortPairList.collect { item ->

                return [field: item.field, direction: item.direction]
            } as LinkedHashMap<String, Integer>
        }

        return map
    }
}
