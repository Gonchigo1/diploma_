import {useEffect} from 'react'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {Button, Card,} from 'antd'
import {useRouter} from 'next/router'
import {useStore} from '../../context/mobxStore'
import {useSession} from 'next-auth/react'

const PageHeader = dynamic(() => import('../../components/elements/pageHeader'))
const Book = observer(() => {
  const referenceDataStore = useStore('referenceDataStore')
  const router = useRouter()
  const {data: session} = useSession()

  useEffect(()=> {
    referenceDataStore.fetchSelect({typeCode: 'BOOK_TYPE'}, session?.token)
  }, [])

  const handleNavigateToPage2 = () => {
    router.push('bookCategory/create')
  }
  return (
    <>
      <NextSeo title='Сургалт - UniCourse' />
      <PageHeader
        routes={[
          {
            title: 'Нүүр',
            link: '/',
          },
        ]}
        title='Ангилал'
        subTitle=' Ангилал'
        action={<Button type='primary' onClick={handleNavigateToPage2}>+ Ангилал Бүртгэх</Button>}
      />

      <Card title='Ангилал Бүртгэх'>
        <p>
        Ангилал Бүртгэх
        </p>
      </Card>
    </>
  )
})

export default Book
