import {observer} from 'mobx-react-lite'
import {useEffect, useState} from 'react'
import {Card, List} from 'antd'

import styles from './bookItem.module.scss'
import {ArrowRightOutlined} from "@ant-design/icons";

const BookItem = observer(({ activeCategory }) => {
  const [books, setBooks] = useState([])

  useEffect(() => {
    if (activeCategory) {
      const categoryBooks = [
        {
          image: <img src='/images/common/cover-book.png' />,
          title: 'Алиса тооны орноор',
          price: '25,000₮',
          id: '1'
        },
        {
          image: <img src='/images/common/cover-book.png' />,
          title: 'Роалд дал',
          price: '25,000₮',
          id: '2'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Доминик морьхны хайрын түүх',
            price: '25,000₮',
            id: '3'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Шидэт аялгуу',
            price: '30,000₮',
            id: '4'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Хоббит',
            price: '35,000₮',
            id: '5'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Дэлхийн түүх',
            price: '28,000₮',
            id: '6'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Хүүхдийн зохиол',
            price: '22,000₮',
            id: '7'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Өөрийгөө хөгжүүлэх',
            price: '20,000₮',
            id: '8'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Уран зохиол',
            price: '32,000₮',
            id: '9'
        },
        {
            image: <img src='/images/common/cover-book.png' />,
            title: 'Математикийн гайхамшиг',
            price: '27,000₮',
            id: '10'
        }
      ]
      setBooks(categoryBooks)
    }
  }, [activeCategory])

    return (
      <div className={styles.wrapper}>
        <div className={styles.header}>
          <h1>{activeCategory ? activeCategory.name : 'Select a category'}</h1>
        </div>
          <div className={styles.content}>
            <div className={styles.bookItemWrapper}>
              <List
                grid={{
                  gutter: 20,
                  xs: 2,
                  sm: 2,
                  md: 3,
                  lg: 3,
                  xl: 4,
                  xxl: 4,
                }}
                  dataSource={books}
                  renderItem={(item) => (
                    <List.Item key={item.id}>
                      <div className={styles.bookItem}>
                        <div className={styles.image}>
                          {item.image}
                        </div>
                        <div className={styles.title}>
                          {item.title}
                        </div>
                        <div className={styles.price}>
                          {item.price}
                        </div>
                        <div className={styles.more}>
                          Дэлгэрэнгүй <ArrowRightOutlined/>
                        </div>
                        </div>
                    </List.Item>
                  )}
                />
            </div>
          </div>
        </div>
    )
})

export default BookItem