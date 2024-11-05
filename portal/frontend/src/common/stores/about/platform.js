import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {get} from '../../services/about/platform'

class PlatformStore {
  @observable data = {
    list: [],
    pagination: [],
  }
  @observable current = {}
  @observable loading = false

  constructor() {
    makeAutoObservable(this)
  }

  @action
  fetch(payload) {
    this.loading = true
    get(payload).then(apiResult => {
      if (apiResult.result === true && apiResult.data) {
        this.current = apiResult.data
      }
      runInAction(() => {
        this.loading = false
      })
    })
  }
}

export default PlatformStore
