import {DefaultSeo} from 'next-seo'

import styles from './loginLayout.module.scss'

const LoginLayout = ({children}) => {
  return (
    <>
      <DefaultSeo title='UniCourse' />
      <div className={styles.container} style={{backgroundImage: 'url(/images/common/auth-background.svg)'}}>
        <div className={styles.content}>
          {children}
        </div>
      </div>
    </>
  )
}

export default LoginLayout
