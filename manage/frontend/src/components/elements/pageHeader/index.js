import Link from 'next/link'
import {Breadcrumb, Button} from 'antd'
import {ArrowLeftOutlined} from '@ant-design/icons'
import {useRouter} from 'next/router'

import styles from './pageHeader.module.scss'

const PageHeader = ({routes, title, subTitle, action, hasBack}) => {
  const router = useRouter()

  return (
    <div className={styles.wrapper}>
      <div className='container'>
        {routes && (
          <Breadcrumb>
            {routes.map((route, index) => (
              <Breadcrumb.Item key={`breadcrumb-${index}`}>
                <Link href={route.link}>{route.title}</Link>
              </Breadcrumb.Item>
            ))}
            <Breadcrumb.Item>{title}</Breadcrumb.Item>
          </Breadcrumb>
        )}
        <div className={styles.title}>
          {hasBack && (
            <div className={styles.back}>
              <Button
                shape='circle'
                type='primary'
                icon={<ArrowLeftOutlined/>}
                onClick={() => router.back()}
              />
            </div>
          )}
          <div className={styles.left}>
            <h3>{title || ''}</h3>
            <p>{subTitle || ''}</p>
          </div>
          <div className={styles.right}>
            {action}
          </div>
        </div>
      </div>
    </div>
  )
}

export default PageHeader
