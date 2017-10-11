import {getCookie} from '../util/assist'

export const adminOrigin = location.origin.indexOf('localhost') !== -1
  ? '47.94.2.0:8080'
  : location.origin

export const appContext = {
  apiUrl: '/esview',
}

export const appApi = {
  menu: adminOrigin + appContext.apiUrl + '/oauth/source/left_menu_list_p/'+ getCookie('access_token'),
  info: adminOrigin + appContext.apiUrl + '/oauth/user_info_p/',
  logout: adminOrigin + appContext.apiUrl + '/oauth/logout?token=' + getCookie('access_token'),
  applyForToken: adminOrigin + appContext.apiUrl + '/oauth/applyfortoken?returnUri=',
}

