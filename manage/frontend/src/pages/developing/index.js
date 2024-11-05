import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'

import styles from './developing.module.scss'

const Developing = observer(() => {

  return (
    <>
      <NextSeo title='Хөгжүүлэлт хийгдэж байна'/>
      <div className={styles.wrapper}>
        <img alt='logo' src='/images/common/icon-coding.png' />
        <h3>Хөгжүүлэлт хийгдэж байна</h3>
      </div>
    </>
  )
})

Developing.appRoles = ['ROLE_DASHBOARD_VIEW']

export default Developing
