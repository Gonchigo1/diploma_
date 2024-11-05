import {action, makeAutoObservable, observable, runInAction} from 'mobx'
import {list} from '../../services/banner/banner'

class BannerStore {
  @observable data = []
  @observable loading = false
  @observable footerBanner = []
  @observable footerBannerLoading = false

  constructor() {
    makeAutoObservable(this)
  }

  @action
  fetchHeaderBanner(payload) {
    this.loading = true
    const newPayload = Object.assign({bannerType: 'HEADER_BANNER'}, payload)
    list(newPayload)
      .then(response => {
        if (response.result === true && response.data) {
          runInAction(() => {
            this.data = response.data
          })
        }
        runInAction(() => {
          this.loading = false
        })
      })
  }

  @action
  fetchFooterBanner(payload) {
    this.footerBannerLoading = true
    const newPayload = Object.assign({bannerType: 'FOOTER_BANNER'}, payload)
    list(newPayload)
      .then(response => {
        if (response.result === true && response.data) {
          runInAction(() => {
            this.footerBanner = response.data
          })
        }
        runInAction(() => {
          this.footerBannerLoading = false
        })
      })
  }

}

export default BannerStore