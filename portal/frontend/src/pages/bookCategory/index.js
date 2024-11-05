import {useState} from 'react'
import {observer} from 'mobx-react-lite'
import {Col, Row} from 'antd'

import BookCategoryList from '@pageComponent/home/bookCategory/bookCategoryList'
import BookItem from '@pageComponent/home/bookCategory/bookItem'

const BookCategory = observer(() => {
  const [activeCategory, setActiveCategory] = useState(null)

  return (
    <>
      <div className='container'>
        <Row gutter={25}>
          <Col xs={24} sm={24} md={24} lg={7} xl={7}>
            <BookCategoryList activeCategory={activeCategory} setActiveCategory={setActiveCategory} />
          </Col>
          <Col xs={24} sm={24} md={24} lg={17} xl={17}>
            <BookItem activeCategory={activeCategory}/>
          </Col>
        </Row>
      </div>
    </>
  )
})

export default BookCategory