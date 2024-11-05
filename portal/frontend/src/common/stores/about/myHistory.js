import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {get, list} from '../../services/about/myHistory'

class MyHistoryStore {
  @observable data = {
    list: [],
    pagination: [],
  }
  @observable loading = false
  @observable currentData = null
  @observable currentLoading = false

  constructor() {
    makeAutoObservable(this)
  }

  @action
  fetchList(payload) {
    this.loading = true
    list(payload).then(response => {
      runInAction(() => {
        if (response.result === true && response.data) {
          this.data = response.data
        }
        this.loading = false
      })
    })
  }

  @action
  fetchDetail(payload) {
    this.current = null
    this.currentLoading = true
    const promise = get(payload)
    promise.then(response => {
      if (response.result === true && response.data) {
        this.currentData = response.data
      }
      this.currentLoading = false
    })
    return promise
  }
}

export default MyHistoryStore
