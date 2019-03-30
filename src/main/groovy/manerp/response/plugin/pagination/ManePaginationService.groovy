package manerp.response.plugin.pagination

import grails.gorm.DetachedCriteria

class ManePaginationService
{

    static ManePaginatedResult paginate(Class clss, ManePaginationProperties properties, Closure closure = null, HashSet<String> excludedFieldSet = null)
    {
        def criteria = new DetachedCriteria(clss).build {

            if ( closure ) {
                closure.resolveStrategy = Closure.DELEGATE_ONLY
                closure.delegate = delegate
                closure()
            }


            properties.sortPairList.forEach { item ->
                order(item.field, item.direction)
            }

        }

        def list = criteria.list(offset: properties.offset, max: properties.limit)
        def fieldList = properties.fieldList

        fieldList = filterFieldList(fieldList, clss, excludedFieldSet)
        list = filterPaginatedList(fieldList, list, clss)

        return new ManePaginatedResult(list, list.size(), properties)
    }

    private static List filterPaginatedList(List fieldList, List paginatedList, Class clss)
    {
        def filteredList = paginatedList

        if ( fieldList ) {

            filteredList = []

            paginatedList.each {

                Map filteredMap = [:]

                for ( def field in fieldList ) {

                    def fieldValue = it.getAt(field)

                    // to lazy load domain classes
                    fieldValue = fieldValue.hasProperty('id') ? [id: fieldValue.id] : fieldValue

                    filteredMap.put(field, fieldValue)
                }

                filteredList.add(filteredMap)
            }
        }

        filteredList
    }

    private static List filterFieldList(List fieldList, Class clss, HashSet<String> excludedFieldSet)
    {
        HashSet clssPropertiesSet = clss.constrainedProperties.keySet()
        clssPropertiesSet.add('id')
        if ( excludedFieldSet ) clssPropertiesSet.removeAll(excludedFieldSet)

        fieldList.retainAll(clssPropertiesSet)

        fieldList
    }

}
