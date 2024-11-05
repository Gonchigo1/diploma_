import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {getAll, getList, getListBook, getUpdate} from '../../services/oxford-thinkers/index'

class OxfordThinkersStore {
  @observable data = {
    list: [],
    pagination: {},
  };
  @observable loading = false

  @observable selectData = []
  @observable selectLoading = false

  @observable updateData = []
  @observable updateLoading = false

  @observable selectAllData = {}
  @observable selectAllLoading = {}

  constructor() {
    makeAutoObservable(this)
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
  fetchUpdate(payload) {
    this.updateLoading = true
    const promise = getUpdate(payload)
    promise.then(response => {
      if (response.result === true && response.updateData) {
        this.updateData = response.data
      }
      this.updateLoading = false
    })
  }

  //Номын сэдэв
  @action
  fetchListBook(token) {
    console.log('called')
    this.loading = true
    getListBook(token)
        .then((response) => {
          runInAction(() => {
            if (response.result) {
              this.data.list = response.data
            }
            this.loading = false
          })
        })
        .catch((error) => {
          runInAction(() => {
            console.error('Error fetching data:', error)
            this.loading = false
          })
        })
  }

  @action
  fetchAll(token) {
    this.selectAllLoading = true
    getAll(token).then((response) => {
      if (response.result) {
        this.currentTopic = response.data
        this.selectAllLoading = false
      }
    })
  }

	@action
	setSearchFormValues(current) {
	  this.searchFormValues = current
	}
}

export default OxfordThinkersStore