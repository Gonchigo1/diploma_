import {action, makeAutoObservable, observable, runInAction} from 'mobx';
import {logIn} from '@services/auth/auth';

class AuthStore {
  rootStore
  // @observable loading = false
  // @observable authBeforePath = null
  // @observable portToken = null

  constructor(rootStore) {
    this.rootStore = rootStore
    makeAutoObservable(this)
  }

  @action
  logIn (payload) {
    return logIn(payload)
  }
  //
  // @action
  // setAuthBeforePath (payload) {
  //   this.authBeforePath = payload
  // }
}

export default AuthStore;
