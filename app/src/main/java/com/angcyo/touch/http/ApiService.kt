package com.angcyo.flow.http

import com.angcyo.uiview.less.utils.Root
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2018/10/15
 */
interface ApiService {

    //?u=11686868&p=123456
    @GET("http://phone.17liuliang.com/login.aspx")
    fun login(@Query("u") u: String, @Query("p") p: String, @Query("ip") ip: String = Root.initImei()): Observable<ResponseBody>

    //?u=686868&p=111111&t=2655350
    @GET("http://phone.17liuliang.com/registe.aspx")
    fun register(@Query("u") u: String, @Query("p") p: String, @Query("t") t: String): Observable<ResponseBody>

    @GET("https://raw.githubusercontent.com/angcyo/RHttp/master/app_flow/config.json")
    fun config(): Observable<ResponseBody>

    //?token=4F4F6BB6450D4FBC958CDE8CC6F89CE0&tbAccNo=only官方&phone_lx=2&ip=1.1.1.1
    @GET("http://phone.17liuliang.com/phone_llrw_get.aspx")
    fun getTask(
        @Query("token") token: String,
        @Query("tbAccNo") tbAccNo: String, //当前用户名(拼多多用户名)
        @Query("phone_lx") phone_lx: String, //任务类型(如果任务类型是100就是随机获取任务类型)
        @Query("ip") ip: String = Root.initImei()
    ): Observable<ResponseBody>

    //?token=4F4F6BB6450D4FBC958CDE8CC6F89CE0&taskId=222421625&status=10&tbAccNo=only%E5%AE%98%E6%96%B9&ip=1.1.1.1
    @GET("http://phone.17liuliang.com/phone_llrw_update.aspx")
    fun updateTask(
        @Query("token") token: String,
        @Query("taskId") taskId: String, //当前用户名(拼多多用户名)
        @Query("tbAccNo") tbAccNo: String, //当前用户名(拼多多用户名)
        @Query("ip") ip: String = Root.initImei()
    ): Observable<ResponseBody>

    //?token=FA0DE1BD159F4CD68CEA3A4ED1486147
    @GET("http://phone.17liuliang.com/get_info.aspx")
    fun getInfo(
        @Query("token") token: String
    ): Observable<ResponseBody>

    //?token=FA0DE1BD159F4CD68CEA3A4ED1486147
    @GET("http://phone.17liuliang.com/get_dian_rz.aspx")
    fun getTaskList(
        @Query("token") token: String
    ): Observable<ResponseBody>
}