import {observable, makeAutoObservable, action, runInAction} from 'mobx'
import {getList, getId} from '@services/book'

class BookStore {
  @observable data = {
    list: [],
    pagination: {},
  }
  @observable loading = false

  @observable current = {}

  @observable searchFormValues = {}

  constructor() {
    makeAutoObservable(this)
  }

  @action
  fetchList(token, params) {
    this.loading = true
    getList(token, params).then(response => {
      if (response.result === true && response.data) {
        this.data = response.data
      }
      this.loading = false
    })
  }

  @action
  fetchId(id) {
    this.loading = true
    getId(id).then(response => {
      if (response.result) {
        this.current = response.data
      }
      this.loading = false
    })
  }

  @action
  setSearchFormValues(current) {
    this.searchFormValues = current
  }
}

export default BookStore