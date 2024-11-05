import {action, makeAutoObservable, observable,runInAction} from 'mobx'
import {fetchOne, create, fetchAll,getList,deleteItem, update} from '../../services/oxfordThinkers'
class oxfordThinkersStore {
	rootStore;
	@observable loading = false;
	@observable profile;

	@observable data = {
	  list: [],
	  pagination: {},
	};
	@observable current = {};
	@observable currentTopic = {};
	@observable selectData = [];
	constructor(rootStore) {
	  this.rootStore = rootStore
	  makeAutoObservable(this)
	}

	@action
	fetchOne(id) {
	  this.loading = true
	  fetchOne(id).then((response) => {
	    if (response.result) {
	      this.current = response.data
	      console.log('Current Data:', this.current) 
	      this.loading = false
	    }
	  })
	}
	@action
	fetchList(token, payload) {
	  this.loading = true
	  getList(token, payload)
	    .then((response) => {
	      runInAction(() => {
	        if (response.result) {
	          this.data = response.data
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
	  this.loading = true
	  fetchAll(token).then((response) => {
	    if (response.result) {
	      this.currentTopic = response.data
	      this.loading = false
	    }
	  })
	}
	@action
	create(token, payload) {
	  return create(token, payload)
	}
	@action
	async delete(token, id) {
	  this.loading = true
	  try {
	    const response = await deleteItem(token, id)
	    runInAction(() => {
		  if (response.result) {
	        this.data.list = this.data.list.filter(item => item.id !== id)
		  }
	    })
	  } catch (error) {
	    console.error('Error deleting item:', error)
	  } finally {
	    this.loading = false
	  }
	}
	@action
	update(payload, token) {
	  const promise = update(payload, token)
	  promise.then(response => {
	    if (response.result === true && response.data) {
		  this.current = response.data
	    }
	    this.loading = false
	  })
	  return promise
	}
	@action
	setSearchFormValues(current) {
	  this.searchFormValues = current
	}
  
}

export default oxfordThinkersStore
