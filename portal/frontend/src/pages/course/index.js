import {useStore} from '@context/mobxStore'
import {useSession} from 'next-auth/react'
import {observer} from 'mobx-react-lite'
import {Button, Modal, List, Tag} from 'antd'
import {useRouter} from 'next/router'
import React, {useEffect, useState} from 'react'

import styles from './course.module.scss'

import Breadcrumb from '@components/elements/breadcrumb'

const OxfordThinkers = observer(() => {
  const router = useRouter()
  const {data: session = {}} = useSession()
  const oxfordThinkersStore = useStore('oxfordThinkersStore')
  const [isModalVisible, setIsModalVisible] = useState(false)

  useEffect(() => {
    oxfordThinkersStore.fetchList()
    oxfordThinkersStore.fetchUpdate()
    console.log('fetchList', oxfordThinkersStore.fetchList())
  }, [])

  const showModal = () => {
    if (session) {
      router.push('/teacher')
    } else {
      setIsModalVisible(true)
    }
  }

  const handleCancel = () => {
    setIsModalVisible(false)
  }

  const handleLogin = () => {
    router.push('/auth/login')
  }

  const dataBook = [
    {
      icon: <img src='/images/common/textbook.svg' alt=''/>,
      name: 'Сурах бичиг',
      description: 'Хөтөлбөрийн агуулга нь эрчимжүүлсэн, бүрэн цогц бөгөөд хичээлүүдийн агуулгыг түвшин, түвшинд системтэй төлөвлөсөн',
      type: 'Судлагдаж байгаа',
      color: '#ff7307'
    },
    {
      icon: <img src='/images/common/workbook.svg' alt=''/>,
      name: 'Дасгалын ном',
      description: 'Дадлага болон бататгалыг хичээл бүрд багтаасан. Олон төрлийн бичлэгийн хэлбэр, унших материалтай',
      type: 'Судлагдаж байгаа',
      color: '#ff7307'
    },
    {
      icon: <img src='/images/common/teacher-book.svg' alt=''/>,
      name: 'Видео файл',
      description: 'Хамгийн сүүлийн үеийн суралцах болон заах арга зүйн агуулга бүхий материалууд',
      type: 'Хийгдсэн',
      color: '#34d300'
    },
    {
      icon: <img src='/images/common/audio.svg' alt=''/>,
      name: 'Аудио файл',
      description: 'Багш сурагчдад өгөх онлайн эрхээр аудио файлуудыг үзэх боложтой',
      type: 'Хийгдсэн',
      color: '#34d300'
    },
    {
      icon: <img src='/images/common/teacher-training.svg' alt=''/>,
      name: 'Багшийн сургалт',
      description: 'Чиглүүлэх сургалт, сэргээх сургалт, заах арга зүйн шинэ арга барилаар хангах сургалт орох боломжтой',
      type: 'Судлагдаж байгаа',
      color: '#ff7307'
    },
    {
      icon: <img src='/images/common/course-plan.svg' alt=''/>,
      name: 'Ээлжит хичээлийн төлөвлөгөө',
      description: 'Хичээлийн агуулгыг сурагчдад хүргэхэд туслах багшид зориулсан алхам, алхмаар төлөвлөсөн жишиг ээлжит хичээлийн төлөвлөгөө (3 нэгж хичээл)',
      type: 'Судлагдаж байгаа',
      color: '#ff7307'
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
        ]}
        title='Сургалт'
      />
      <div className={styles.wrapper}>
        <div className={styles.header}>
          <div className={'container'}>
            <div className={styles.title}>
              <h3>Сургалт</h3>
            </div>
            <div className={styles.content}>
              <h4>Танилцуулга</h4>
              <p>
                Техник технологи хөгжихийн хэрээр оюутан, сурагчдын сурах арга барил болон сургалтын
                орчин өөрчлөгдөж байна. Оюутан, сурагч нар олон төрлийн форматыг ашиглан суралцдаг.
                Зарим нь уншихыг, зарим нь сонсохыг, зарим нь үзэхийг илүүд үздэг. Гэсэн хэдий ч одоо
                байгаа системүүд нь оюутнуудаас эдгээр эх сурвалжид хандахын тулд өөр өөр программ
                эсвэл вэбсайт хооронд шилжихийг илүүд үздэг бөгөөд энэ нь сургалтын үйл явцыг удаашруулж
                болзошгүй юм. Аудио хичээл, видео хичээл гэх мэт төрөл бүрийн мультимедиа контентын хэрэглээ улам
                өргөжсөөр байгаа.
              </p>
              <p>
                Энэхүү <span style={{color: '#D1242A'}}>”Цахим сургалтын нэгдсэн систем”</span> нь багш нарт оюутан,
                сурагч нартаа нэг дор унших, сонсох, хичээл үзүүлэх боломжтой нэгдсэн шийдлээр өсөн нэмэгдэж буй
                хэрэгцээг хангах зорилготой юм.
                Энэхүү систем нь хэрэглэхэд хялбар интерфейсийг санал болгож, заах болон суралцах чадварыг сайжруулж,
                багш болон сурагч нарт хялбар аргаар болон нэг дороос суралцах боломж бололцоог хангасан
                систем юм.
              </p>
            </div>
            <br/>
            <br/>
            <div className={styles.bottom}>
              <div className={styles.title}>
                <h3>Цахим сургалтын хөтөлбөр</h3>
              </div>
              <List
                grid={{
                  gutter: 15,
                  xs: 2,
                  sm: 2,
                  md: 3,
                  lg: 3,
                  xl: 6,
                  xxl: 6
                }}
                dataSource={dataBook || []}
                renderItem={(item) => (
                  <List.Item>
                    <div className={styles.course}>
                      <div className={styles.icon}>{item.icon}</div>
                      <div className={styles.name}>{item.name}</div>
                      <div className={styles.description}>{item.description}</div>
                      <Tag color={item.color}>{item.type}</Tag>
                    </div>
                  </List.Item>
                )}
              />
            </div>
            <div className={styles.action} onClick={showModal}>
              <span>Аудио, видео хичээл үзэх</span>
            </div>
            <Modal
                title="Аудио, видео хичээл үзэх"
                visible={isModalVisible}
                onCancel={handleCancel}
                cancelText={'Хаах'}
                warning
                footer={[
                  <Button key='cancel' onClick={handleCancel}>
                    Болих
                  </Button>,
                  <Button key='login' type='primary' onClick={handleLogin}>
                    Нэвтрэх
                  </Button>,
                ]}
            >
              <p>Та хичээл үзэхийн тулд нэвтэрч орно уу!</p>
            </Modal>
          </div>
        </div>
      </div>
    </>
  )
})

export default OxfordThinkers