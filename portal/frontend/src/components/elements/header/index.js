import {useEffect, useState} from 'react'
import {Button, Dropdown, Layout, Menu, Modal} from 'antd'
import {DownOutlined, MenuOutlined} from '@ant-design/icons'
import {observer} from 'mobx-react-lite'
import {useRouter} from 'next/router'
import Link from 'next/link'
import classNames from 'classnames'
import {useSession} from 'next-auth/react'

import styles from './header.module.scss'

const Header = observer(({collapsed, setCollapsed, isMobile}) => {
  const router = useRouter()
  const {data: session = {}} = useSession()
  const [loggingIn, _setLoggingIn] = useState(false)
  const [active, setActive] = useState('home')

  useEffect(() => {
    setActive(router.pathname.split('/')[1])
    console.log('session', session)
  }, [router, session])


  const logout = () => {
    return Modal.confirm({
      title: 'Анхааруулга',
      content: 'Та системээс гарахдаа итгэлтэй байна уу?',
      okText: 'Тийм',
      cancelText: 'Үгүй',
      onOk () {
        router.push('/auth/signout')
      }
    })
  }

  const menuItems = [
    { key: 'Нүүр', link: '/home', title: 'Нүүр' },
    { key: 'Сургалт', link: '/course', title: 'Сургалт' },
  ]

  const userMenu = (
    <Menu style={{width: 120}}>
      {/*<Menu.Item key='dashboard'>
        <div onClick={() => router.push('/dashboard')} style={{width: '-webkit-fill-available'}}>
          Миний мэдээлэл
        </div>
      </Menu.Item>*/}
      <Menu.Item key='teacher'>
        <div onClick={() => router.push('/teacher')} style={{width: '-webkit-fill-available'}}>
          Сургалт
        </div>
      </Menu.Item>
      <Menu.Item key='logout'>
        <Button size='small' danger block onClick={() => logout()} style={{width: '-webkit-fill-available'}}>
          Гарах
        </Button>
      </Menu.Item>
    </Menu>
  )

  const handleLogin = () => {
    return router.push('/auth/login')
  }

  return (
    <Layout.Header
      theme={router.pathname === '/home' ? 'dark' : 'light'}
      className={classNames(
        styles.wrapper, isMobile && styles.mobile, router.pathname !== '/home' && styles.full
      )}
    >
      <div className={isMobile ? '' : 'container'}>
        <div className={styles.container}>
          {isMobile && (
            <Button
              icon={<MenuOutlined/>}
              size='large'
              type='link'
              onClick={() => setCollapsed(!collapsed)}
              className={styles.toggle}
            />
          )}
          <Link href='/home' className={styles.logo}>
            <img
              alt='logo'
              src={router.pathname === '/home' ? '/images/logo/long-logo.svg' : '/images/logo/long-logo.svg'}
            />
          </Link>
          {!isMobile && (
            <div className={styles.menu}>
              <Menu
                theme={router.pathname === '/home' ? 'light' : 'light'}
                mode={isMobile ? 'inline' : 'horizontal'}
                selectedKeys={[active]}
              >
                {menuItems.map((item) => (
                  <Menu.Item key={item.key}>
                    <Link href={item.link}>
                      {item.title}
                    </Link>
                  </Menu.Item>
                ))}
              </Menu>
            </div>
          )}
          <div className={styles.right}>
            {session?.token ?
              <Dropdown
                type='primary'
                block
                placement='bottomRight'
                overlay={userMenu}
                loading={loggingIn}
                trigger={['click']}
              >
                <Button
                  type='primary'
                  className={styles.loginButton}
                  onClick={(e) => {
                    e.preventDefault()
                  }}
                >
                  <div className={styles.signIn}>
                    <img src='/images/icons/user-light.png' alt=''/>
                    <span>
                  {session?.user?.name}
                      <DownOutlined style={{marginLeft: 4}}/>
                </span>
                  </div>
                </Button>
              </Dropdown>
              :
              <Button
                type='primary'
                className={styles.loginButton}
                onClick={() => handleLogin()}
              >
                <div className={styles.signIn}>
                  <img src='/images/icons/user-light.png' alt=""/>
                  Нэвтрэх
                </div>
              </Button>
            }
        </div>
          {/*<div className={styles.basket}>
            <img src='/images/icons/basket.png' alt=''/>
            Сагс
          </div>*/}
        </div>
      </div>
    </Layout.Header>
  )
})

export default Header