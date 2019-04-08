package manerp.response.plugin.pagination

import grails.gorm.DetachedCriteria

class ManePaginationService
{

    ManePaginatedResult paginate(Class clss, ManePaginationProperties properties, Closure closure = null, HashSet<String> excludedFieldSet = null)
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
        list = filterPaginatedList(fieldList, list)

        new ManePaginatedResult(list, list.size(), properties)
    }

    private List filterPaginatedList(List fieldList, List paginatedList)
    {
        def filteredList = paginatedList

        if ( fieldList ) {

            filteredList = []

            paginatedList.each { item ->

                Map filteredMap = prepareFilteredMap(item, fieldList)
                filteredList.add(filteredMap)
            }
        }

        filteredList
    }

    private Map prepareFilteredMap(def domainInstance, List fieldList)
    {
        Map filteredMap = [:]

        for ( def field in fieldList ) {

            def fieldValue

            if ( field instanceof HashMap ) {

                String key = field.keySet().getAt(0)
                List innerDomainFields = field.values().getAt(0)
                def innerDomain = domainInstance.getAt(key)

                Map innerDomainMap = [:]

                innerDomainFields.each { f ->
                    if ( innerDomain.hasProperty(f) ) {
                        innerDomainMap.put(f, innerDomain[f])
                    }
                }

                fieldValue = innerDomainMap
                filteredMap.put(field.keySet().getAt(0), fieldValue)
            } else {

                fieldValue = domainInstance.getAt(field)

                // to lazy load domain classes
                fieldValue = fieldValue.hasProperty('id') ? [id: fieldValue.id] : fieldValue
                filteredMap.put(field, fieldValue)
            }
        }

        filteredMap
    }

    private List filterFieldList(List fieldList, Class clss, HashSet<String> excludedFieldSet)
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

    public Map filterDomainInstance(def domain, List fieldList, HashSet<String> excludedFieldSet)
    {
        List filteredFields = filterFieldList(fieldList, domain.getClass(), excludedFieldSet)
        prepareFilteredMap(domain, fieldList)
    }

}
