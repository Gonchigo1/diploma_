import {action, makeAutoObservable, observable} from "mobx";
import {getList} from "@services/lesson"

class LessonStore {
    @observable data = {
        list: [],
        pagination: {},
    }
    @observable loading = false

    constructor(rootStore) {
        this.rootStore = rootStore
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
}

export default LessonStore