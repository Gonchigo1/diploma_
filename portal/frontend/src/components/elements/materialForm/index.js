import React from 'react'
import {Form} from 'antd'
import classNames from 'classnames'

import styles from './materialForm.module.scss'

export const MaterialForm = ({label, children}) => {
  const {props: {name}} = children
  return <Form.Item noStyle
    shouldUpdate={(prevValues, currentValues) => prevValues[name] !== currentValues[name]}
  >
    {({getFieldValue}) =>
      <Form.Item required className={styles.formItem}>
        <span className={classNames('material-label', getFieldValue(name) && 'material-label-active')}>{label}</span>
        {children}
      </Form.Item>
    }
  </Form.Item>
}

export default React.memo(MaterialForm)