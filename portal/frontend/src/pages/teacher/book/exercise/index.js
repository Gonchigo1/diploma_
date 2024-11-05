import React, {useEffect, useState, useRef} from 'react'
import {useRouter} from 'next/router'
import {useSession} from 'next-auth/react'
import {observer} from 'mobx-react-lite'
import {Button, Table, Tag} from 'antd'
import {ArrowLeftOutlined, RightOutlined} from '@ant-design/icons'
import numeral from 'numeral'

import styles from './exercise.module.scss'

import {useStore} from '@context/mobxStore'
import Breadcrumb from '@components/elements/breadcrumb'

const ExercisePage = observer(() => {
  const router = useRouter()
  const {query} = router
  const {data: session = {}} = useSession()
  const exercisesStore = useStore('exercisesStore')
  const bookStore = useStore('bookStore')
  const {current} = bookStore
  const {id: lessonId} = query
  const {data, loading} = exercisesStore
  const {list, pagination} = data

  const [active, setActive] = useState(lessonId || list?.[0]?.lessonId)

  useEffect(() => {
    if (session?.token && router.query?.id) {
      exercisesStore.fetchList(session?.token, {lessonId})
    }
  }, [session, lessonId])

  useEffect(() => {
    if (lessonId && list?.length > 0) {
      setActive(list[0].key)
    }
  }, [lessonId, list]);

  const handleChange = (key) => {
    setActive(key)
    router.push({
      pathname: router.pathname,
      query: {...query, lessonId: key},
    })
  }

    const columns = [
      {
        title: 'Аудио',
        dataIndex: 'audio',
        render: (audios) => {
          if (audios && audios.length > 0 && audios[0].url) {
            return (
              <audio controls controlsList='nodownload'>
                <source src={audios[0].url} type='audio/mp3' />
                Таны веб хөтөч тухайн аудиог тоглуулахад дэмжихгүй байна.
              </audio>
            )
          }
          return <span>Аудио байхгүй</span>
        },
        width: 260
      },
      {
        title: 'Видео',
        dataIndex: 'video',
        render: (videos) => {
          if (videos && videos.length > 0 && videos[0].url) {
            return (
              <video controls style={{ height: 140, width:'90%' }} controlsList='nodownload'>
                <source src={videos[0].url} type='video/mp4' />
              </video>
            )
          }
          return <span>Видео байхгүй</span>
        },
      },
    ]

  const items = list.map((item) => {
    return {
      key: item.key,
      label: (
        <div
          key={item.key}
          className={`${styles.title} ${active === item.key ? styles.active : ''}`}
          onClick={() => {handleChange(item.key)}}
        >
          <div className={styles.name}>{item.exercise}</div>
          <RightOutlined />
        </div>
      ),
      children: (
        <div
          className='ant-table-content'
          onContextMenu={(e) => e.preventDefault()}
        >
          <Table
            bordered
            columns={columns}
            dataSource={[item]}
            loading={loading}
            rowKey='id'
            size={'middle'}
            className='custom-table'
            title={() => (
              <Tag color={'blue'}>
                Нийт дасгал: <b>{pagination?.total && numeral(pagination.total).format('0,0') || 0}</b>
              </Tag>
            )}
          />
        </div>
      )
    }
  })

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
          },
          {
            title: 'Сургалтын дэлгэрэнгүй',
            link: `/teacher/book/detail?id=${current.id}`
          }
        ]}
        title='Хичээл'
      />
      <div className={styles.wrapper}>
        <div className='container'>
          <div className={styles.action}>
            <Button
                shape='circle'
                icon={<ArrowLeftOutlined/>}
                type='primary'
                onClick={() => router.push(`/teacher/book/detail?id=${current.id}`)}
            />
            <div className={styles.title}>
              <h3>Хичээл</h3>
            </div>
          </div>
        </div>
        <div className={'container'}>
          <div className={styles.list}>
            <div className={styles.detail}>
              {items.map((item) => (item.label))}
            </div>
            <div className={styles.content}>
              {items.find(item => item.key === active)?.children}
            </div>
          </div>
        </div>
      </div>
    </>
  )
})

export default ExercisePage
