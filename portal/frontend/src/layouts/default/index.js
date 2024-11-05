import {FloatButton, Layout} from 'antd'
import {ArrowUpOutlined} from '@ant-design/icons'
import {observer} from 'mobx-react-lite'
import {DefaultSeo} from 'next-seo'
import {ContainerQuery} from 'react-container-query'
import {useState} from 'react'
import {isMobile} from 'react-device-detect'

import {Header, Footer} from '@element'
import SideMenu from '../../components/elements/sideMenu'

import styles from './defaultLayout.module.scss'

const DEFAULT_SEO = {
  defaultTitle: 'UniCourse',
  description: 'UniCourse',
  openGraph: {
    title: 'UniCourse',
    description: 'UniCourse',
    images: [
      {url: ''},
    ],
    site_name: 'UniCourse',
  }
}

const DefaultLayout = observer(({children}) => {
  const [collapsed, setCollapsed] = useState(true)

  const [screenQuery] = useState({
    'screenXs': {
      maxWidth: 575
    },
    'screenSm': {
      minWidth: 576,
      maxWidth: 767
    },
    'screenMd': {
      minWidth: 768,
      maxWidth: 991
    },
    'screenLg': {
      minWidth: 992,
      maxWidth: 1199
    },
    'screenXl': {
      minWidth: 1200,
      maxWidth: 1599
    },
    'screenXxl': {
      minWidth: 1600
    }
  })

  return (
    <>
      <ContainerQuery query={screenQuery}>
        {params => (
          <>
            <DefaultSeo {...DEFAULT_SEO} />
            <Layout hasSider className={styles.wrapper}>
              {isMobile && <SideMenu collapsed={collapsed} setCollapsed={setCollapsed} />}
              <Layout>
                <Header collapsed={collapsed} setCollapsed={setCollapsed} isMobile={params.screenXs}/>
                <Layout.Content style={{backgroundColor: '#FFF'}}>
                  <div className={styles.content} style={{margin: 0}}>
                    {children}
                  </div>
                  <br/>
                  <br/>
                  <FloatButton.BackTop>
                    <div className={styles.anchor}>
                      <ArrowUpOutlined/>
                    </div>
                  </FloatButton.BackTop>
                </Layout.Content>
                <Footer/>
              </Layout>
            </Layout>
          </>
        )}
      </ContainerQuery>
    </>
  )
})

export default DefaultLayout