import {action, makeAutoObservable, observable} from "mobx"
import {getList, getAll, getId} from '@services/exercises'

class ExercisesStore {
  @observable data = {
    list: [],
    pagination: {},
  }
  @observable loading = false
  @observable current = {}

  constructor() {
    makeAutoObservable(this)
  }

  @action
  fetchAll(token, payload) {
    this.loading = true
    getAll(token, payload).then(response => {
      if (response.result === true && response.data) {
        this.data = response.data
      }
      this.loading = false
    })
  }

  @action
  fetchList(token, payload) {
    this.loading = true
    getList(token, payload).then(response => {
      if (response.result === true && response.data) {
        this.data = response.data
      }
      this.loading = false
    })
  }

  @action
  fetchId(id) {
    this.loading = true
    getId(id).then((response) => {
      if (response.result === response.data) {
        this.current = response.data
      }
      this.loading = false
    })
  }
}

export default ExercisesStore