const dayjs = require('dayjs')

const getNowDate = () => {
  let nowDate = dayjs()
  return nowDate.format('YYYY-MM-DD HH:mm:ss') + ' : '
}

module.exports = {getNowDate}
