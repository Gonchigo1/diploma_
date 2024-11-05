import {jsonRequest} from '../../util/request'
import {getApiUrl} from '../../base'
import {toQueryString} from '../../util/queryString'

const baseUrl = '/v1/myHistory'

export async function list(params) {
  return jsonRequest(getApiUrl() + `${baseUrl}?${toQueryString(params)}`, 'GET')
}
export async function get(id) {
  return jsonRequest(`${getApiUrl()}${baseUrl}/${id}`, 'GET')
}
