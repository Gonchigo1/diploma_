import {useEffect} from 'react'
import {useRouter} from 'next/router'

import styles from './auth.module.scss'

const Auth = ({children}) => {
  const router = useRouter()

  useEffect(() => {
    const pathNames = router.pathname.split('/')
    if (pathNames.length > 2) {
    // setActiveKey(router.pathname.split('/')[2])
    } else {
        router.push('/auth/login')
    }
  }, [router])

    return (
      <div className='container'>
        <div className={styles.main}>
          <div className={styles.wrapper}>
            <div className={styles.content}>
              <div className={styles.inner}>
                {children}
              </div>
            </div>
          </div>
        </div>
      </div>
    )
}

export default Auth