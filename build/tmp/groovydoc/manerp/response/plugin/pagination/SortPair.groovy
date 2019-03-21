package manerp.response.plugin.pagination

class SortPair<F, D> {

    F field
    D direction

    SortPair(F field, D direction) {
        this.field = field
        this.direction = direction
    }

}
