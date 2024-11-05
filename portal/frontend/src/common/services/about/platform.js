import {toQueryString} from '../../util/queryString'
import {getApiUrl} from '../../base'
import {jsonRequest} from '../../util/request'

const baseUrl = '/v1/platform'

export async function get(params) {
  return jsonRequest(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET')
}
