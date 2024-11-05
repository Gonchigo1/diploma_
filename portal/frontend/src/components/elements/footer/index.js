import {observer} from 'mobx-react-lite'
import {Layout} from 'antd'
import dayjs from 'dayjs'

import styles from './footer.module.scss'

const Footer = observer(({}) => {
  return (
    <Layout.Footer className={styles.wrapper}>
      <div className='container'>
        <div className={styles.footer}>
          <p>© {dayjs().format('YYYY')} Зохиогчийн эрхээр хамгаалагдсан</p>
          <div className={styles.right}>
            <img src='/images/logo/red-logo.svg' alt=""/>
          </div>
        </div>
      </div>
    </Layout.Footer>
  )
})

export default Footer