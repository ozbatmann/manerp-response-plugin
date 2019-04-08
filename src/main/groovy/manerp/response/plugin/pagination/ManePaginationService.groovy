package manerp.response.plugin.pagination

import grails.converters.JSON
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

            paginatedList.each { item ->

                Map filteredMap = [:]

                for ( def field in fieldList ) {

                    def fieldValue

                    if ( field instanceof HashMap ) {

                        String key = field.keySet().getAt(0)
                        List domainFields = field.values().getAt(0)
                        def domain = item.getAt(key)

                        Map domainMap = [:]

                        domainFields.each { f ->
                            if ( domain.hasProperty(f) ) {
                                domainMap.put(f, domain[f])
                            }
                        }

                        fieldValue = domainMap
                        filteredMap.put(field.keySet().getAt(0), fieldValue)
                    } else {

                        fieldValue = item.getAt(field)

                        // to lazy load domain classes
                        fieldValue = fieldValue.hasProperty('id') ? [id: fieldValue.id] : fieldValue
                        filteredMap.put(field, fieldValue)
                    }

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

        fieldList.retainAll { field ->

            if ( field instanceof String ) {

                field in clssPropertiesSet
            } else if ( field instanceof HashMap ) {

                field.keySet().getAt(0) in clssPropertiesSet
            }
        }

        fieldList
    }

}
