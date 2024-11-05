import {useEffect, useState} from 'react'
import dynamic from 'next/dynamic'
import {useSession} from 'next-auth/react'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import {
  Button,
  Card,
  Divider,
  Form,
  Input,
  Modal,
  message,
  Select,
  Table,
  Tag, Tooltip,
} from 'antd'
import {DeleteTwoTone, EditTwoTone,
  PlusOutlined,
} from '@ant-design/icons'

import {useStore} from '../../../context/mobxStore'
import {deleteOne} from '../../../common/services/auth/businessRole'
import CreateUpdateModal from './createUpdate'
import numeral from 'numeral'

const PageHeader = dynamic(() => import('../../../components/elements/pageHeader'))

const BusinessRoleList = observer(() => {
  const {data: session = {}} = useSession()
  const [form] = Form.useForm()
  const businessRoleStore = useStore('businessRoleStore')
  const {data, loading} = businessRoleStore
  const {list, pagination} = data
  const [searchFormValues, setSearchFormValues] = useState({})
  const [updateModalVisible, setUpdateModalVisible] = useState(false)
  const [updateData, setUpdateData] = useState({})

  useEffect(() => {
    if (session?.token) {
      businessRoleStore.fetchApplicationRoles(session.token)
      refreshTable()
    }
  }, [session])

  const refreshTable = (params) => {
    businessRoleStore.fetchSelect(session?.token, params)
    businessRoleStore.fetchList(session?.token, params)
  }

  const handleSearch = () => {
    setSearchFormValues(form.getFieldsValue())
    refreshTable(form.getFieldsValue())
  }

  const handleSearchFormReset = () => {
    form.resetFields()
    setSearchFormValues({})
    refreshTable()
  }

  const handleTableChange = (pagination, _filtersArg, _sorter) => {
    const params = {
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...searchFormValues,
    }
    // if (sorter.field) {
    //   params.sorter = `${sorter.field}_${sorter.order}`
    // }
    refreshTable(params)
  }

  const renderFilterForm = () => {
    return (
      <Form
        form={form}
        layout='inline'
        onFinish={handleSearch}
      >
        <Form.Item
          label='Цахим ажлын байр'
          name='role'
        >
          <Input
            placeholder='Цахим ажлын байр'
            onInput={(e) => {e.target.value = e.target.value.toUpperCase()}}
          />
        </Form.Item>
        <Form.Item
          label='Хандах эрхүүд'
          name='applicationRole'
        >
          <Select style={{width: 300}} placeholder='Хандах эрхүүд' allowClear showSearch>
            <Select.Option key='' value=''>Бүгд</Select.Option>
            {
              businessRoleStore.applicationRoles && businessRoleStore.applicationRoles
                .map(appRole =>
                  <Select.Option key={appRole} value={appRole}>{appRole}</Select.Option>
                )
            }
          </Select>
        </Form.Item>
        <Form.Item>
          <Button
            type='primary'
            htmlType='submit'>
            Хайх
          </Button>
        </Form.Item>
        <Form.Item>
          <Button
            onClick={handleSearchFormReset}>
            Цэвэрлэх
          </Button>
        </Form.Item>
      </Form>
    )
  }

  const columns = [
    {
      title: '№',
      dataIndex: '',
      width: '50px',
      align: 'center',
      render: (_, __, index) => index + 1 + pagination.currentPage * pagination.pageSize,
    },
    {
      title: 'Нэр',
      dataIndex: 'name',
      width: 200
    },
    {
      title: 'Хэрэглэгчийн эрх',
      dataIndex: 'role',
      width: 280
    },
    {
      title: 'Хандах эрхүүд',
      dataIndex: 'applicationRoles',
      render: (text, record) => (
        <span>
          {
            record.applicationRoles != null
              ? record.applicationRoles.map(appRole =>
                <Tag color='blue' key={`${record.role}_${appRole}`}>{appRole}</Tag>,
              ) : ''
          }
        </span>
      ),
    },
    {
      title: 'Үйлдэл',
      render: (text, record) => (
        <>
          <Tooltip title={'Засах'}>
            <Button 
              shape={'circle'} 
              icon={<EditTwoTone />} 
              onClick={() => showModal(record)}
            />
          </Tooltip>
          <Tooltip title={'Устгах'}>
            <Divider type='vertical'/>
            <Button 
              shape={'circle'} 
              icon={<DeleteTwoTone twoToneColor={'red'} />} 
              onClick={() => showDeleteConfirm(record.key)}
            />
          </Tooltip>
        </>
      ),
      width: 150,
      align: 'center'
    },
  ]

  const showModal = (values) => {
    setUpdateData(values)
    setUpdateModalVisible(true)
  }

  const closeModal = (refresh) => {
    setUpdateModalVisible(false)
    setUpdateData({})
    if (refresh)
      handleSearch()
  }

  const showDeleteConfirm = clickedId => {
    const parentMethods = {handleDelete: handleDelete}

    Modal.confirm({
      title: 'Анхааруулга',
      content: 'Хэрэглэгчийн эрхийг устгах уу? Тухайн төрөлд хамаарах хэрэглэгчид системд нэвтрэх боломжгүй болно',
      okText: 'Тийм',
      okType: 'danger',
      cancelText: 'Үгүй',
      onOk() {
        parentMethods.handleDelete(clickedId)
      },
    })
  }

  const handleDelete = id => {
    deleteOne(session.token, {id})
      .then(response => {
        if (!response.result)
          message.error(`Хэрэглэгчийн эрх устгахад алдаа гарлаа: ${response.message}`)
        refreshTable(searchFormValues)
      })
      .catch(e => {
        message.error(`Хэрэглэгчийн эрх устгахад алдаа гарлаа: ${e.message}`)
      })
  }

  const headerActions = (
    <Button icon={<PlusOutlined/>} type='primary' onClick={() => showModal({})}>
      Бүртгэх
    </Button>
  )

  return (
    <>
      <NextSeo title='Хэрэглэгчийн эрх - UniCourse'/>
      <PageHeader
        routes={[
          {
            title: 'Нүүр',
            link: '/',
          },
          {
            title: 'Тохиргоо',
            link: '/settings/business-role',
          }
        ]}
        title='Хэрэглэгчийн эрх'
        action={headerActions}
      />
      <>
        <Card>
          {renderFilterForm()}
        </Card>
        <br/>
        <Table
          rowKey='key'
          size='middle'
          loading={loading}
          columns={columns}
          dataSource={list || []}
          pagination={{
            ...pagination,
            showQuickJumper: true,
            showSizeChanger: true,
            pageSizeOptions: ['20', '50', '100'],
            defaultPageSize: 100,
            locale: {
              items_per_page: '/ хуудас',
              jump_to: 'Шилжих',
              jump_to_confirm: 'Confirm',
              page: 'Хуудас',
              prev_page: 'Өмнөх',
              next_page: 'Дараах',
              prev_5: 'Өмнөх 5',
              next_5: 'Дараах 5',
              prev_3: 'Өмнөх 3',
              next_3: 'Дараах 3',
            },
          }}
          onChange={handleTableChange}
          className='custom-table'
          title={() => (
            <div>
              Нийт эрх: <b>{pagination?.total && numeral(pagination.total).format('0,0') || 0}</b>
            </div>
          )}
        />
      </>
      <CreateUpdateModal
        visible={updateModalVisible}
        updateData={updateData}
        closeModal={closeModal}
      />
    </>
  )
})

export default BusinessRoleList
