import {observer} from 'mobx-react-lite'
import React, {useCallback, useEffect} from 'react'
import {useSession} from 'next-auth/react'
import {useRouter} from 'next/router'
import {ArrowLeftOutlined, ArrowRightOutlined} from '@ant-design/icons'
import {Button, Col, Row, Table, Tag} from 'antd'
import numeral from 'numeral'

import styles from './bookTopic.module.scss'

import {useStore} from '@context/mobxStore'
import Breadcrumb from '@components/elements/breadcrumb'

const BookTopic = observer(() => {
  const {data: session} = useSession()
  const token = session?.token
  const router = useRouter()
  const {id: bookId, topicId} = router.query

  const oxfordThinkersStore = useStore('oxfordThinkersStore')
  const bookStore = useStore('bookStore')
  const lessonStore = useStore('lessonStore')

  const {current} = bookStore
  const {data, loading, searchFormValues} = oxfordThinkersStore
  const {list, pagination} = data
  const {current: currentPage, pageSize} = pagination

  useEffect(() => {
    if (bookId && token) {
      bookStore.fetchId(bookId)
      refreshTable({ ...searchFormValues, currentPage, pageSize })
    }
  }, [bookId, token])

  const refreshTable = useCallback(
    (params) => {
      if (bookId) {
        oxfordThinkersStore.fetchList(token, { ...params, bookId })
        if (topicId) {
          lessonStore.fetchList(token, { ...params, bookId, topicId })
        }
      }
    },
    [bookId, token, topicId]
  );

  const handleExercise = (record) => {
    router.push(`/teacher/book/exercise?id=${record?.id}`)
  }

  const handleTableChange = (pagination) => {
    const params ={ 
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
    }
    oxfordThinkersStore.setSearchFormValues(params)
    refreshTable(params) 
  }

  const paginationProps = {
    ...pagination,
    showQuickJumper: true,
    showSizeChanger: true,
  }

  const columns = [
    {
      title: '№',
      dataIndex: '',
      align: 'center',
      render: (_, __, index) => index + 1 + pagination.currentPage * pagination.pageSize,
      width: 50
    },
    {
      title: 'Хичээл',
      dataIndex: 'name',
    },
    {
      title: 'Нийт сэдвийн тоо',
      dataIndex: 'countLessons',
    },
  ]

  return (
    <>
      <Breadcrumb
        routes={[
          {
            title: 'Нүүр',
            link: '/'
          },
          {
            title: 'Сургалт',
            link: '/teacher'
          }
        ]}
        title='Сургалтын дэлгэрэнгүй'
      />
      <div className={styles.wrapper}>
        <div className='container'>
          <div className={'action'}>
            <Button
              shape='circle'
              icon={<ArrowLeftOutlined/>}
              type='primary'
              onClick={() => router.push('/teacher')}
            />
            <div className={'title'}>
              <h3>Сургалтын дэлгэрэнгүй</h3>
            </div>
          </div>
          <Row gutter={25}>
             <Col xs={24} sm={24} md={7} xl={current ? 6 : 0}>
               {current &&
                 <div className={styles.bookItem}>
                   <div className={styles.image}>
                     {current.image && current.image.length > 0 &&
                       <img src={current.image[0].url} alt=''/>
                     }
                   </div>
                   <div className={styles.name}>
                     Номын нэр: <b>{current.name}</b>
                   </div>
                   <div className={styles.type}>
                     Номын төрөл: <b>{current.type}</b>
                   </div>
                 </div>
               }
             </Col>
             <Col xs={24} sm={24} md={17} xl={current ? 18 : 24}>
               <Table
                 rowKey={(record) => record.id}
                 size='middle'
                 bordered
                 columns={columns}
                 dataSource={list}
                 loading={loading}
                 pagination={paginationProps || {}}
                 onChange={handleTableChange}
                 locale={{emptyText: 'Сэдвийн мэдээлэл байхгүй'}}
                 title={() => (
                   <Tag color={'blue'}>
                     Нийт хичээл: <b>{pagination?.total && numeral(pagination.total).format('0,0') || 0}</b>
                   </Tag>
                 )}
                 className='custom-table'
                 expandable={{
                   expandedRowRender: (record) => {
                     return (
                       <div>
                         {record.lessons?.length > 0 ? (
                           record.lessons.map((item, index) => (
                             <div key={item.id} className={styles.lessonItem}>
                               <div className={styles.name}>
                                 {index + 1 + pagination.currentPage * pagination.pageSize}.
                                 Сэдвийн нэр: <span>{item.lesson}</span>
                               </div>
                               {/*<div className={styles.count}>*/}
                               {/*  Нийт дасгалын тоо: {exercisesStore.data.list.filter(exe => exe.lessonId === record.id).length}*/}
                               {/*</div>*/}
                               <div className={styles.action}>
                                 Үйлдэл: <span onClick={() => {
                                 handleExercise(item);
                               }}> Дасгал харах <ArrowRightOutlined/></span>
                               </div>
                             </div>
                           ))
                         ) : (
                           <div>Хичээлийн мэдээлэл байхгүй</div>
                         )}
                       </div>
                     );
                   }
                 }}
                 style={{cursor: 'pointer'}}
               />
             </Col>
          </Row>
        </div>
      </div>
    </>
  )
})

export default BookTopic