import AuthStore from './auth/auth'
import AccountStore from './auth/account'
import ArticleStore from './article/article'
import BusinessRoleStore from './auth/businessRole'
import MyHistoryStore from './about/myHistory'
import OxfordThinkersStore from './oxford-thinkers'
import PlatformStore from './about/platform'
import UserStore from './auth/user'
import BannerStore from './banner/banner'
import BookStore from './book'
import BookTypeStore from './book-type'
import LessonStore from './lesson'
import ExercisesStore from "./exercises";

class RootStore {
  accountStore
  authStore
  articleStore
  businessRoleStore
  bannerStore
  myHistoryStore
  oxfordThinkersStore
  platformStore
  userStore
  bookStore
  bookTypeStore
  lessonStore
  exercisesStore

  constructor() {
    this.accountStore = new AccountStore()
    this.authStore = new AuthStore()
    this.articleStore = new ArticleStore()
    this.businessRoleStore = new BusinessRoleStore()
    this.bannerStore = new BannerStore()
    this.myHistoryStore = new MyHistoryStore()
    this.oxfordThinkersStore = new OxfordThinkersStore()
    this.platformStore = new PlatformStore()
    this.userStore = new UserStore()
    this.bookStore = new BookStore()
    this.bookTypeStore = new BookTypeStore()
    this.lessonStore = new LessonStore()
    this.exercisesStore = new ExercisesStore()
  }

  getStores() {
    return {
      accountStore: this.accountStore,
      authStore: this.authStore,
      articleStore: this.articleStore,
      businessRoleStore: this.businessRoleStore,
      bannerStore: this.bannerStore,
      myHistoryStore: this.myHistoryStore,
      oxfordThinkersStore: this.oxfordThinkersStore,
      platformStore: this.platformStore,
      userStore: this.userStore,
      bookStore: this.bookStore,
      bookTypeStore: this.bookTypeStore,
      lessonStore: this.lessonStore,
      exercisesStore: this.exercisesStore,
    }
  }
}

export default RootStore
