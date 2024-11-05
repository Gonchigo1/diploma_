import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {getProfile, changePassword, get} from '../../services/auth/account'

class AccountStore {
  rootStore
  @observable loading = false
  @observable profile
  @observable current = {}

  @action
  setCurrent(current) {
    this.current = Object.assign(this.current, current)
  }

  constructor(rootStore) {
    this.rootStore = rootStore
    makeAutoObservable(this)
  }

  @action
  fetchProfile(token) {
    this.loading = true
    const _promise = getProfile(token)
      .then(response => {
        runInAction(() => {
          this.profile = response.data
          this.loading = false
        })
      })
  }

  @action
  changePassword(token, payload) {
    return changePassword(token, payload)
  }

  @action
  get(token) {
    this.loading = true
    get(token).then(apiResult => {
      if (apiResult.result === true && apiResult.data) {
        apiResult.data.status = apiResult.result
        runInAction(() => {
          this.setCurrent(apiResult.data)
          this.loading = false
        })
      }
    })
  }
}

export default AccountStore
