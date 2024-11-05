import {Menu, Drawer} from 'antd'
import {useRouter} from 'next/router'
import {observer} from 'mobx-react-lite'
import styles from './sideMenu.module.scss'

function getItem(label, key, icon, children, type) {
  return {
    key,
    icon,
    children,
    label,
    type,
  }
}

const SideMenu = observer(({collapsed, setCollapsed}) => {
  const router = useRouter()

  const items = [
    getItem('Бидний тухай', '/about', null),
    getItem('1000 ном', '/books', null),
    getItem('Load Library', '/library', null),
    getItem('Oxford Thinkers', '/thinkers', null),
    getItem('FoxBox', '/foxbox', null),
  ]

  const onMenuClick = (e) => {
    console.log(e)
    router.push(e.key)
    setCollapsed(!collapsed)
  }

  return (
    <Drawer
      open={!collapsed}
      placement='left'
      width={256}
      onClose={() => setCollapsed(!collapsed)}
      className={styles.wrapper}
    >
      <Menu
        mode='inline'
        items={items}
        onClick={onMenuClick}
      />
    </Drawer>
  )
})

export default SideMenu
