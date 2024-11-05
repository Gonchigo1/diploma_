import {jsonRequest} from '../../util/request'
import {toQueryString} from '../../util/queryString'
import {getApiUrl} from '../../base'

const baseUrl = '/v1/article'

export async function list(params) {
  return jsonRequest(getApiUrl() + `${baseUrl}?${toQueryString(params)}`, 'GET')
}
export async function get(idKey) {
  return jsonRequest(`${getApiUrl()}${baseUrl}/${idKey}`, 'GET')
}
