import React from 'react'
import {observer} from "mobx-react-lite"
import {List, Badge} from "antd"
import {ArrowRightOutlined, CheckCircleOutlined, ClockCircleOutlined} from "@ant-design/icons"
import ScrollAnimation from 'react-animate-on-scroll'

import styles from './widget.module.scss'

const HomeWidget = observer(() => {

  const data = [
    {
        icon: '/images/common/textbook.svg',
        name: 'Сурах бичиг',
        description: 'Хөтөлбөрийн агуулга нь эрчимжүүлсэн, бүрэн цогц бөгөөд хичээлүүдийн агуулгыг түвшин, түвшинд системтэй төлөвлөсөн',
        gradientColor: ['#2492ff', '#2c63ff'],
        type: null,
    },
    {
        icon: '/images/common/workbook.svg',
        name: 'Дасгалын ном',
        description: 'Дадлага болон бататгалыг хичээл бүрд багтаасан. Олон төрлийн бичлэгийн хэлбэр, унших материалтай',
        gradientColor: ['#a871ff', '#8f51ff'],
        type: null,
    },
    {
        icon: '/images/common/teacher-book.svg',
        name: 'Видео файл',
        description: 'Багш сурагчдад өгөх онлайн эрхээр видео файлуудыг үзэх боложтой',
        gradientColor: ['#ff9797', '#ff7597'],
        type: 'done',
    },
    {
        icon: '/images/common/audio.svg',
        name: 'Аудио файл',
        description: 'Багш сурагчдад өгөх онлайн эрхээр аудио файлуудыг үзэх боложтой',
        gradientColor: ['#ffc78f', '#ffa700'],
        type: 'done',
    },
    {
        icon: '/images/common/teacher-training.svg',
        name: 'Багшийн сургалт',
        description: 'Чиглүүлэх сургалт, сэргээх сургалт, заах арга зүйн шинэ арга барилаар хангах сургалт орох боломжтой',
        gradientColor: ['#55ff9a', '#32dc9a'],
        type: null,
    },
    {
        icon: '/images/common/course-plan.svg',
        name: 'Ээлжит хичээлийн төлөвлөгөө',
        description: 'Хичээлийн агуулгыг сурагчдад хүргэхэд туслах багшид зориулсан алхам, алхмаар төлөвлөсөн жишиг ээлжит хичээлийн төлөвлөгөө',
        gradientColor: ['#4affe9', '#24bcff'],
        type: null,
    },
  ]

  const renderItem = (item, index) => {
    const content = (
      <div
        className={styles.itemWrapper}
        style={{
            background: `linear-gradient(180deg, ${item.gradientColor[1]} 0%, ${item.gradientColor[0]} 100%)`
        }}
        >
          <div className={styles.header}>
            <div className={styles.name}>
              {item.name}
            </div>
            <div className={styles.subtitle}>
              {item.description}
            </div>
          </div>
          <div className={styles.icon}>
            <img src={item.icon} alt={item.name} />
          </div>
          {/*<div className={styles.more}>
            Дэлгэрэнгүй <ArrowRightOutlined />
          </div>*/}
        </div>
      )

    return (
      <ScrollAnimation animateOnce={true} offset={-500} animateIn='animate__fadeInUp' delay={50 * index} key={index}>
        {!item.type ? (
          <Badge.Ribbon text={<small><ClockCircleOutlined /> <b>Судлагдаж байгаа</b></small>} color={'gold'}>
            {content}
          </Badge.Ribbon>
        ) :
          <Badge.Ribbon text={<small><CheckCircleOutlined /> <b>Хийгдсэн</b></small>} color={'green'}>
            {content}
          </Badge.Ribbon>
        }
      </ScrollAnimation>
    )
  }

  return (
    <div className={'container'}>
      <div className={styles.wrapper}>
        <h3>Цахим сургалтын хөтөлбөр</h3>
        <List
          grid={{
              gutter: 25,
              xs: 1,
              sm: 2,
              md: 3,
              lg: 4,
              xl: 4,
              xxl: 4,
          }}
          dataSource={data || []}
          renderItem={(item, index) => (
              <List.Item style={{padding: 0}}>
                  {renderItem(item, index)}
              </List.Item>
          )}
        />
      </div>
    </div>
  )
})

export default HomeWidget