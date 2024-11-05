import {action, makeAutoObservable, observable,runInAction} from 'mobx'
import {fetchOne, create, fetchAll,getList,deleteItem,update} from '../../services/lesson'
class lessonStore {
	rootStore;
	@observable loading = false;
	@observable profile;

	@observable data = {
	  list: [],
	  pagination: {},
	};

	@observable searchFormValues = {}

	@observable current = {};
	@observable currentTopic = {};
	@observable selectData = [];
	constructor(rootStore) {
	  this.rootStore = rootStore
	  makeAutoObservable(this)
	}
	@action
	async fetchListByTopicId(token, exerciseId) {
	  this.loading = true
	  try {
	    const response = await getList(token, {exerciseId})
	    runInAction(() => {
	      this.data.list = response.data
	      this.loading = false
	    })
	    return response
	  } catch (error) {
	    runInAction(() => {
	      this.loading = false
	    })
	    console.error('Error fetching exercises:', error)
	  }
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
	  const promise = getList(token, payload)
	  promise.then((response) => {
	    runInAction(() => {
	      if (response.result) {
	        this.data = response.data
	      }
	      this.loading = false
	    })
	  })
	  return promise
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
	      this.data = response.data
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

export default lessonStore
