package manerp.response.plugin.pagination

import grails.gorm.DetachedCriteria

class ManePaginationService {

    static ManePaginatedResult paginate(Class clss, ManePaginationProperties properties, Closure closure = null) {

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

        return new ManePaginatedResult(list, list.totalCount as int, properties)
    }
}
