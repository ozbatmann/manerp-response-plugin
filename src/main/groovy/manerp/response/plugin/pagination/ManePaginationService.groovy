package manerp.response.plugin.pagination

import grails.gorm.DetachedCriteria

class ManePaginationService
{
    ManePaginatedResult paginate(Class clss, ManePaginationProperties properties, Closure closure = null)
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

        new ManePaginatedResult(list, list.size(), properties)
    }

    public List filterPaginatedList(List fieldList, List paginatedList)
    {
        def filteredList = paginatedList

        if ( fieldList ) {

            filteredList = []

            paginatedList.each { item ->

                Map filteredMap = filterItem(item, fieldList)
                filteredList.add(filteredMap)
            }
        }

        filteredList
    }

    public Map filterItem(def domainInstance, List fieldList)
    {
        Map filteredMap = [:]

        for ( def field in fieldList ) {

            if ( field instanceof HashMap ) {

                String key = field.keySet().getAt(0)
                def innerDomain = domainInstance.getAt(key) as Map
                innerDomain.keySet().retainAll(field.values().getAt(0))

                filteredMap.put(key, innerDomain)
            } else {

                def fieldValue = domainInstance.getAt(field)

                // to lazy load domain classes
                fieldValue = fieldValue.hasProperty('id') ? [id: fieldValue.id] : fieldValue
                filteredMap.put(field, fieldValue)
            }
        }

        filteredMap
    }
}
