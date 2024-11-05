import {action, makeAutoObservable, observable} from 'mobx'
import {getSelect} from '../../services/book-type/index'

class BookTypeStore {
    @observable selectData = []
    @observable selectLoading = []

    constructor() {
        makeAutoObservable(this)
    }

    @action
    fetchSelect(payload, token) {
        this.selectLoading = true
        const promise = getSelect(payload, token)
        promise.then(response => {
            if (response.result === true && response.data) {
                this.selectData = response.data
            }
            this.selectLoading = false
        })
        return promise
    }
}

export default BookTypeStore