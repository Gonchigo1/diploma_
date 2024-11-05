import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {
  fetchOne,
  create,
  fetchAll,
} from '../../services/topic'
class topicStore {
	rootStore;
	@observable loading = false;
	@observable profile;

	@observable data = {
	  list: [],
	  pagination: {},
	};
	@observable current = {};

	@observable selectData = [];
	constructor(rootStore) {
	  this.rootStore = rootStore
	  makeAutoObservable(this)
	}

	@action
	fetchOne(token, id) {
	  this.loading = true
	  fetchOne(token, id).then((response) => {
	    if (response.result) {
	      this.current = response.data
	      this.loading = false
	    }
	  })
	}
	@action
	fetchAll(token) {
	  this.loading = true
	  fetchAll(token).then((response) => {
	    if (response.result) {
	      this.selectData = response.data
	      this.loading = false
	    }
	  })
	}
	@action
	create(token, payload) {
	  this.loading = true
	  create(token, payload).then((response) => {
	    runInAction(() => {
	      this.current = response.data
	      this.loading = false
	    })
	  })
	}

}

export default topicStore
