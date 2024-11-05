import React, {useEffect} from 'react'
import {useSession} from 'next-auth/react'
import {useRouter} from 'next/router'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import {List, Tag, Input, Button, Form, Card, Tooltip} from 'antd'
import {ArrowRightOutlined,ReadOutlined,FormOutlined,BookOutlined } from '@ant-design/icons'
import numeral from 'numeral'

import {useStore} from '@context/mobxStore'
import Breadcrumb from '@components/elements/breadcrumb'
import {MaterialForm} from '@components/elements/materialForm'

import styles from './teacherBook.module.scss'

const {Item: FormItem} = Form

const TeacherBooks = observer(() => {
  const router = useRouter()
  const {data: session = {}} = useSession()
  const [form] = Form.useForm()
  const bookStore = useStore('bookStore')
  const {data, loading, searchFormValues} = bookStore
  const {list, pagination} = data

  useEffect(() => {
    if (session?.token) {
      refreshTable()
    } else {
      router.push('/home')
    }
  }, [session])

  const refreshTable = (params) => {
    bookStore?.fetchList(session?.token, params)
  }

  const handleDetail = (record) => {
    router.push(`/teacher/book/detail?id=${record.id}`)
  }

  const handleChange = (page, pageSize) => {
    const params = {
      ...searchFormValues,
      currentPage: page,
      pageSize: pageSize,
    }

    bookStore.setSearchFormValues(params)
    refreshTable(params)
  }

  const handleFormReset = () => {
    form.resetFields()
    bookStore.setSearchFormValues({})
    refreshTable()
  }

  const handleSearch = () => {
    bookStore.setSearchFormValues(form.getFieldsValue())
    refreshTable(form.getFieldsValue())
  }

  const renderFilterForm = () => (
    <Form
      form={form}
      onFinish={handleSearch}
      layout='inline'
    >
      <MaterialForm label='Сургалтын нэр'>
        <FormItem name='name'>
          <Input placeholder='Сургалтын нэр' allowClear style={{width: 240}}/>
        </FormItem>
      </MaterialForm>
      <MaterialForm label='Сургалтын төрөл'>
        <FormItem name='type'>
          <Input placeholder='Сургалтын төрөл' allowClear style={{width: 240}}/>
        </FormItem>
      </MaterialForm>
      <FormItem>
        <Button type='primary' htmlType='submit' style={{marginLeft: 10}}>
          Хайх
        </Button>
        <Button style={{marginLeft: 8}} onClick={handleFormReset}>
          Цэвэрлэх
        </Button>
      </FormItem>
    </Form>
  )

  return (
    <>
      <NextSeo title='Сургалт - E-Course' />
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
          <div className='container'>
            {renderFilterForm()}
          </div>
        </div>
        <div className={'container'}>
          <Tag color='blue'>
            Нийт сургалт: <b>{pagination?.total ? numeral(pagination.total).format('0,0') : 0}</b>
          </Tag>
          <br/>
          <br/>
          <List
            loading={loading}
            grid={{
              gutter: 25,
              xs: 2,
              sm: 2,
              md: 3,
              lg: 3,
              xl: 4,
              xxl: 4,
            }}
            pagination={pagination ? {
              ...pagination,
              showQuickJumper: true,
              showSizeChanger: true,
              size: 'small',
              onChange: handleChange,
            } : false}
            dataSource={list || []}
            renderItem={(item) => (
              <List.Item
                key={item.id}
              >
                <div
                  className={styles.bookItem}
                  onClick={() => handleDetail(item)}
                >
                  <div className={styles.image}>
                    {item.image && item.image.length > 0 &&
                      <img src={item.image[0].url} alt=''/>
                    }
                  </div>
                  <div className={styles.content}>
                    <div className={styles.name}>
                      Номын нэр: <span>{item.name}</span>
                    </div>
                    <div className={styles.countContainer}>
                      <Tooltip placement={'top'} title={'Сэдэв'} className={styles.tooltip}>
                        <BookOutlined/> <b>{item.countTopics}</b>
                      </Tooltip>
                      <Tooltip placement={'top'} title={'Хичээл'} className={styles.tooltip}>
                        <ReadOutlined/> <b>{item.countLessons}</b>
                      </Tooltip>
                      <Tooltip placement={'top'} title={'Дасгал'} className={styles.tooltip}>
                        <FormOutlined/> <b>{item.countExercises}</b>
                      </Tooltip>
                    </div>
                  </div>
                  <div className={styles.more}>
                    Дэлгэрэнгүй харах <ArrowRightOutlined/>
                  </div>
                </div>
              </List.Item>
            )}
          />
        </div>
      </div>
    </>
  )
})

export default TeacherBooks
