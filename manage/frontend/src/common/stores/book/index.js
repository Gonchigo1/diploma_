import {action, makeAutoObservable, observable} from 'mobx'
import {fetchOne, create, getList, deleteOne} from '../../services/book'

export default class BookStore {
	@observable data = {
	  list: [],
	  pagination: {},
	};
	@observable loading = false;

	@observable searchFormValues = {}

	@observable profile;

	@observable current = {};
	@observable selectAllData = [];

	constructor() {
	  makeAutoObservable(this)
	}

	@action
	fetchOne(id) {
	  this.loading = true
	  fetchOne(id).then((response) => {
	    if (response.result) {
	      this.current = response.data
	    }
	    this.loading = false
	  })
	}

	@action
	fetchList(token, params) {
	  this.loading = true
	  getList(token, params).then((response) => {
	    if (response.result && response.data) {
		  this.data = response.data
	    }
	    this.loading = false
	  })
	}

    @action
	create(token, payload) {
	  this.loading = true
	  const promise = create(token, payload)
	  promise.then(response => {
	    if (response.result && response.data) {
	      this.current = response.data
	    }
	    this.loading = false
	  })
	  return promise
	}
	@action
    deleteOne(payload, token) {
	  return deleteOne(payload, token)
    }

	@action
	setSearchFormValues(current) {
	  this.searchFormValues = current
	}
}
