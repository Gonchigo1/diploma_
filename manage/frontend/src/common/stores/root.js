import AuthStore from './auth/auth'
import BusinessRoleStore from './auth/businessRole'
import BookStore from './book'
import TeacherStore from './user'
import OxfordthinkersStore from './oxfordThinkers/oxfordThinkers'
import AccountStore from './auth/account'
import ReferenceDataStore from './reference/data'
import ReferenceTypeStore from './reference/type'
import SysLanguageStore from './classification/syslanguage'
import BookTypeStore from './bookType/bookType'
import lessonStore from './lesson/index'
import exerciseStore from './exercise/index'
import NameSpaceStore from './syslocale/namespace'
import LocaleStore from './syslocale/locale'

class RootStore {
	accountStore
	authStore
	businessRoleStore
	bookStore
    userStore
	lessonStore
    oxfordThinkersStore
	referenceDataStore
	referenceTypeStore
	sysLanguageStore
    BookTypeStore
	nameSpaceStore
	localeStore
	exerciseStore

	constructor() {
	  this.accountStore = new AccountStore()
	  this.authStore = new AuthStore(this)
	  this.businessRoleStore = new BusinessRoleStore(this)
	  this.bookStore = new BookStore(this)
	  this.userStore = new TeacherStore(this)
	  this.lessonStore = new lessonStore(this)
	  this.oxfordThinkersStore = new OxfordthinkersStore(this)
	  this.referenceDataStore = new ReferenceDataStore(this)
	  this.referenceTypeStore = new ReferenceTypeStore(this)
	  this.sysLanguageStore = new SysLanguageStore(this)
	  this.bookTypeStore = new BookTypeStore(this)
	  this.nameSpaceStore = new NameSpaceStore(this)
	  this.localeStore = new LocaleStore(this)
	  this.exerciseStore = new exerciseStore(this)

	}

	getStores() {
	  return {
	    accountStore: this.accountStore,
	    authStore: this.authStore,
	    businessRoleStore: this.businessRoleStore,
	    bookStore: this.bookStore,
	    userStore: this.userStore,
	 	lessonStore: this.lessonStore,
	    oxfordThinkersStore: this.oxfordThinkersStore,
	    referenceDataStore: this.referenceDataStore,
	    referenceTypeStore: this.referenceTypeStore,
	    sysLanguageStore: this.sysLanguageStore,
	    bookTypeStore: this.bookTypeStore,
	    nameSpaceStore: this.nameSpaceStore,
	    localeStore: this.localeStore,
	    exerciseStore: this.exerciseStore
	  }
	}
}

export default RootStore
