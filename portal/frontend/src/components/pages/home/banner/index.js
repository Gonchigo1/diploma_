import {observer} from 'mobx-react-lite'
import {Button, Col, List, Row} from "antd";

import styles from './banner.module.scss'

const data = [
  {
    title: "ЗААВАЛ УНШИХ НОМНУУД",
    image: "/images/common/banner-01.png",
  },
  {
    title: "ОНЦЛОХ НОМ",
    image: "/images/common/banner-02.png",
  },
  {
    title: "ОНЦЛОХ ҮЙЛ ЯВДАЛ",
    image: "/images/common/banner-03.png",
  },
  {
    title: "1000 НОМ",
    image: "/images/common/banner-04.png",
  }
];

const Banner = observer(() => {
  return (
    <div className={styles.wrapper}>
      <div className={'container'}>
        <Row gutter={25}>
          <Col xs={24} sm={24} md={12} lg={12}>
            <div className={styles.left}>
              <div className={styles.greeting}>
                👋 Тавтай морилно уу!
              </div>
              <h1>Цахим сургалтын нэгдсэн систем</h1>
              <div className={styles.extra}>
                <span>
                  <img src='/images/icons/audio.svg' alt=''/>
                  Аудио хичээл, дасгал
                </span>
                <span>
                  <img src='/images/icons/video.svg' alt=''/>
                  Видео хичээл, дасгал
                </span>
              </div>
            </div>
          </Col>
          <Col xs={24} sm={24} md={12} lg={12} style={{textAlign: 'right'}}>
            <div className={styles.right}>
              <img src='/images/common/banner.svg' alt=""/>
            </div>
          </Col>
        </Row>
      </div>
    </div>
  )
})

export default Banner