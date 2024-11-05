import {jsonRequestWithToken} from "@util/request";
import {getApiUrl} from "../../base";
import {toQueryString} from "@util/queryString";

const baseUrl = '/lesson'

export async function getList(token, params) {
    return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}