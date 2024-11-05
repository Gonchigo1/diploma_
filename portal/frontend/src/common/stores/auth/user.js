import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {list, create, update, deleteOne, get} from '../../services/auth/user'

class UserStore {
  @observable data = {
    list: [],
    pagination: [],
  }
  @observable current = {}
  @observable loading = false

  @observable searchFormValues = {}

  constructor() {
    makeAutoObservable(this)
  }

  @action
  setList(data) {
    this.data = Object.assign(this.data, data)
  }

  @action
  setCurrent(current) {
    this.current = Object.assign(this.current, current)
  }

  @action
  list(token, payload) {
    this.loading = true
    list(token, Object.assign({deleted: false}, payload)).then(apiResult => {
      if (apiResult.result === true && apiResult.data) {
        apiResult.data.status = apiResult.result
        runInAction(() => {
          this.setList(apiResult.data)
          this.loading = false
        })
      }
    })
  }

  @action
  get(payload, token) {
    this.loading = true
    get(payload, token).then(apiResult => {
      if (apiResult.result === true && apiResult.data) {
        apiResult.data.status = apiResult.result
        runInAction(() => {
          this.setCurrent(apiResult.data)
          this.loading = false
        })
      }
    })
    return get(payload)
  }

  @action
  create(token, payload) {
    return create(token, payload)
  }

  @action
  update(token, payload) {
    return update(token, payload)
  }

  @action
  deleteOne(params, token) {
    return deleteOne(params, token)
  }
  @action
  setSearchFormValues(current) {
    this.searchFormValues = current
  }

  @action
  clearCurrent() {
    this.current = {}
  }
}

export default UserStore
