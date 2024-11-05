import Link from 'next/link'
import {Breadcrumb, Button} from 'antd'

import styles from './breadcrumb.module.scss'
import {ArrowLeftOutlined} from "@ant-design/icons";

const BreadcrumbComponent = ({routes, title, hasBack}) => {
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
      </div>
    </div>
  )
}

export default BreadcrumbComponent
