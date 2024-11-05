import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'

const Developing = dynamic(() => import('../developing'))

const Dashboard = observer(() => {

  return (
    <>
      <Developing />
    </>
  )
})

Dashboard.appRoles = ['ROLE_DASHBOARD_VIEW']

export default Dashboard
