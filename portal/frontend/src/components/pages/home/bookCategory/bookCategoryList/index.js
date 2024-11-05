import {useEffect, useState} from 'react'
import {observer} from 'mobx-react-lite'
import {Checkbox, List} from 'antd'
import classNames from 'classnames'

import styles from './bookCategoryList.module.scss'

const CheckboxGroup = Checkbox.Group
const options = [
  { label: 'Нас 3', value: 'age1' },
  { label: 'Нас 4', value: 'age2' },
  { label: 'Нас ...17', value: 'age3' },
]

const BookCategoryList = observer(({activeCategory, setActiveCategory}) => {
  const [selectedValues, setSelectedValues] = useState([])

  useEffect(() => {
    if (!activeCategory) {
      setActiveCategory(data[0])
    }
  }, [activeCategory, setActiveCategory])

    const data = [
      {
        name: 'Бестселлэр',
        count: '10',
        id: '1'
      },
      {
        name: 'Монгол зохиолч, зураачдын ном',
        count: '78',
        id: '2'
      },
      {
        name: 'Түүхэн ном',
        count: '45',
        id: '3'
      },
      {
        name: 'Гадаад уран зохиол',
        count: '32',
        id: '4'
      },
      {
        name: 'Эрдэм шинжилгээний бүтээл',
        count: '25',
        id: '5'
      },
      {
        name: 'Хүүхдийн ном',
        count: '60',
        id: '6'
      },
      {
        name: 'Шинэ ном',
        count: '18',
        id: '7'
      },
      {
        name: 'График роман',
        count: '15',
        id: '8'
      },
      {
        name: 'Сурах бичиг',
        count: '40',
        id: '9'
      },
      {
        name: '1000 ном',
        count: '1000',
        id: '10'
      }
    ]

    const onChange = (checkedValues) => {
      console.log('Checked value: ', checkedValues)
      setSelectedValues(checkedValues)
    }

    return (
        <div className={styles.wrapper}>
          <div className={styles.header}>
            <h1>Ангилал</h1>
          </div>
          <div className={styles.content}>
            <List
                dataSource={data}
                renderItem={(item) => (
                    <List.Item
                        key={item.id}
                        className={
                          classNames(styles.itemWrapper, activeCategory && activeCategory.id === item.id ? styles.active : {})
                        }
                        onClick={() => setActiveCategory(item)}
                    >
                      <div onClick={() => {
                        console.log('active', activeCategory)
                      }}>
                        <div className={styles.name}>
                          {item.name}
                        </div>
                        <div className={styles.count}>
                          {item.count}
                        </div>
                      </div>
                    </List.Item>
                )}
            />
          </div>
        </div>
    )
})

export default BookCategoryList