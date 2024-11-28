    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
    //               (
    //                 <img
    //                   src='/images/logo/side-logo.svg'
    //                   alt='logo-main'
    //                   style={{height: 56}}
    //                 />
    //               )}
    //           </Link>
    //         </div>
    //         <Menu
    //           openKeys={openKeys}
    //           onOpenChange={onOpenChange}
    //           mode='inline'
    //           theme='light'
    //           items={menuItems}
    //         />
    //       </div>
    //     </Layout.Sider>
    //   )
    // })

    // export default SideMenu
    // import {useEffect, useState} from 'react'
    // import {useRouter} from 'next/router'
    // import {useSession} from 'next-auth/react'
    // import Link from 'next/link'
    // import {observer} from 'mobx-react-lite'
    // import {Layout, Menu} from 'antd'
    // import {DashboardOutlined, OrderedListOutlined, SettingOutlined, UsergroupAddOutlined, UserOutlined, BookOutlined} from '@ant-design/icons'
    // import classNames from 'classnames'

    // import {checkAuthRole} from '../../../common/util/auth'

    // import styles from './sideMenu.module.scss'

    // const rootMenuKeys = [
    //   'dashboard',
    //   'dashboard1',
    //   'report',
    //   'settings',
    // ]

    // const menuItemTemplate = [
    //   {
    //     key: 'dashboard',
    //     label: <Link href={'/dashboard'}>Хянах самбар</Link>,
    //     icon: <DashboardOutlined/>,
    //     role: 'ROLE_DASHBOARD_VIEW',
    //   },
    //   {
    //     key: 'Book',
    //     label: 'Сургалт',
    //     icon: <BookOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'BookList',
    //         label: <Link href={'/book'}>Жагсаалт</Link>,
    //         title: 'Жагсаалт',
    //         icon: <BookOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'classification',
    //     label: 'Ангилалын сан',
    //     icon: <OrderedListOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //     children: [
    //       {
    //         key: 'classification-book-type',
    //         label: <Link href={'/classification/book-type'}>Сургалтын төрөл</Link>,
    //         title: 'Сургалтын төрөл',
    //         icon: <OrderedListOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW','ROLE_LOCALE_VIEW', 'ROLE_OXFORD_THINKERS_VIEW','ROLE_OXFORD_THINKERS_MANAGE'],
    //       }
    //     ],
    //   },
    //   {
    //     key: 'settings',
    //     label: 'Тохиргоо',
    //     icon: <SettingOutlined/>,
    //     role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //     children: [
    //       {
    //         key: 'settings-business-role',
    //         label: <Link href={'/settings/business-role'}>Хэрэглэгчийн эрх</Link>,
    //         title: 'Хэрэглэгчийн эрх',
    //         icon: <UsergroupAddOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },
    //       /*{
    //         key: 'settings-user',
    //         label: <Link href={'/settings/usertesc'}>Системийн хэрэглэгч</Link>,
    //         title: 'Системийн хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       },*/
    //       {
    //         key: 'teacher',
    //         label: <Link href={'/settings/teacher'}>Багш</Link>,
    //         title: 'Системийн онцгой эрхтэй хэрэглэгч',
    //         icon: <UserOutlined/>,
    //         role: ['ROLE_BUSINESS_ROLE_VIEW', 'ROLE_USER_VIEW'],
    //       }
    //     ],
    //   },
    // ]

    // const SideMenu = observer(({collapsed, setCollapsed}) => {
    //   const router = useRouter()
    //   const {data: session = {}} = useSession()

    //   const [menuItems, setMenuItems] = useState([])
    //   const [openKeys, setOpenKeys] = useState([])

    //   useEffect(() => {
    //     setOpenKeys(router.pathname.split('/').filter(item => item !== ''))
    //   }, [router.pathname])

    //   useEffect(() => {
    //     if (session?.applicationRoles)
    //       setMenuItems(getCheckedMenuItems(menuItemTemplate, session.applicationRoles))
    //   }, [session])

    //   const onOpenChange = keys => {
    //     const latestOpenKey = keys.find(key => openKeys.indexOf(key) === -1)
    //     if (rootMenuKeys.indexOf(latestOpenKey) === -1) {
    //       setOpenKeys(keys)
    //     } else {
    //       setOpenKeys(latestOpenKey ? [latestOpenKey] : [])
    //     }
    //   }

    //   const getCheckedMenuItems = (menuItems, applicationRoles) => {
    //     const checkedMenuItems = []

    //     menuItems.map(menuItem => {
    //       let checkedChildren = null

    //       if (menuItem.children) {
    //         checkedChildren = []
    //         menuItem.children.map(subMenuItem => {
    //           const hasSubAccess = checkAuthRole(subMenuItem.role, applicationRoles)
    //           checkedChildren.push(Object.assign(subMenuItem, {
    //             disabled: !hasSubAccess,
    //             label: hasSubAccess ? subMenuItem.label : subMenuItem.title,
    //           }))
    //         })
    //       }

    //       const hasAccess = checkAuthRole(menuItem.role, applicationRoles)

    //       checkedMenuItems.push(Object.assign(menuItem, {
    //         disabled: !hasAccess,
    //         children: checkedChildren
    //       }))
    //     })

    //     return checkedMenuItems
    //   }

    //   return (
    //     <Layout.Sider
    //       width={collapsed ? 80 : 280} 
    //       collapsible 
    //       collapsed={collapsed} 
    //       onCollapse={(value) => setCollapsed(value)}
    //       className={styles.sideMenu}
    //     >
    //       <div className={styles.wrapper}>
    //         <div className={classNames(styles.logoWrapper, collapsed && styles.small)}>
    //           <Link href='/dashboard'>
    //             {!collapsed ? (
    //               <img
    //                 src='/images/logo/main-logo.svg'
    //                 alt='logo-main'
    //                 style={{height: 50}}
    //               />
    //             )
    //               :
