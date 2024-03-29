package com.nus.logicuniversity.retrofit;

import com.nus.logicuniversity.model.AdjustmentVoucher;
import com.nus.logicuniversity.model.CollectionPointListResponse;
import com.nus.logicuniversity.model.Delegate;
import com.nus.logicuniversity.model.DelegatedInfoResponse;
import com.nus.logicuniversity.model.DisbursementDTO;
import com.nus.logicuniversity.model.DisbursementDetailItem;
import com.nus.logicuniversity.model.DisbursementDetailsResponse;
import com.nus.logicuniversity.model.DisbursementsResponse;
import com.nus.logicuniversity.model.InventoryListResponse;
import com.nus.logicuniversity.model.LoginResponse;
import com.nus.logicuniversity.model.PriceListResponse;
import com.nus.logicuniversity.model.RepresentativesResponse;
import com.nus.logicuniversity.model.RequisitionResponse;
import com.nus.logicuniversity.model.RequisitionDetailResponse;
import com.nus.logicuniversity.model.RetrievalResponse;
import com.nus.logicuniversity.utility.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("logout")
    Call<String> logout(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("dept_head/past_orders")
    Call<RequisitionResponse> getAllPastOrders(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("dept_head/pending_orders")
    Call<RequisitionResponse> getAllPendingOrders(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("dept_head/pending_order/{order_id}")
    Call<RequisitionDetailResponse> getPendingOrderByOrderId(@Header(Util.AUTH_HEADER) String authHeader, @Path("order_id") long orderId);

    @GET("dept_head/representatives")
    Call<RepresentativesResponse> getAllRepresentatives(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("dept_head/representative/change/{emp_id}")
    Call<String> changeRepresentative(@Header(Util.AUTH_HEADER) String authHeader, @Path("emp_id") long id);

    @GET("dept_head/employees")
    Call<RepresentativesResponse> getAllEmployees(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("dept_head/pending_order/approve/{req_id}")
    Call<String> approveOrder(@Header(Util.AUTH_HEADER) String authHeader, @Path("req_id") long id);

    @GET("dept_head/pending_order/reject/{req_id}")
    Call<String> rejectOrder(@Header(Util.AUTH_HEADER) String authHeader, @Path("req_id") long id);

    @POST("dept_head/auth/delegate")
    Call<String> authDelegate(@Header(Util.AUTH_HEADER) String authHeader, @Body Delegate delegate);

    @GET("dept_head/auth/delegate/info")
    Call<DelegatedInfoResponse> authDelegateInfo(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("stock_clerk/retrievals")
    Call<RetrievalResponse> getRetrievalForms(@Header(Util.AUTH_HEADER) String authHeader);

    @POST("stock_clerk/disbursement/generate")
    Call<String> generateDisbursement(@Header(Util.AUTH_HEADER) String authHeader, @Body ArrayList<DisbursementDetailItem> items);

    @POST("representative/received")
    Call<String> updateCollection(@Header(Util.AUTH_HEADER) String authHeader, @Body ArrayList<DisbursementDetailItem> items);

    @GET("representative/disbursement/acknowledge/{id}")
    Call<String> acknowledgeDisbursement(@Header(Util.AUTH_HEADER) String authHeader, @Path("id") long id);

    @GET("representative/disbursements/pending")
    Call<DisbursementsResponse> getPendingDisbursements(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("representative/disbursements/past")
    Call<DisbursementsResponse> getPastDisbursements(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("representative/disbursement/{listId}")
    Call<DisbursementDetailsResponse> getDisbursementDetails(@Header(Util.AUTH_HEADER) String authHeader, @Path("listId") long id);

    @GET("stock_clerk/inventory/all")
    Call<InventoryListResponse> getAllInventories(@Header(Util.AUTH_HEADER) String authHeader);

    @POST("stock_clerk/inventory/price_list")
    Call<PriceListResponse> getInventoryPriceLists(@Header(Util.AUTH_HEADER) String authHeader, @Body ArrayList<Long> itemIds);

    @POST("stock_clerk/inventory/adjustment")
    Call<String> generateInventoryAdjustment(@Header(Util.AUTH_HEADER) String authHeader, @Body ArrayList<AdjustmentVoucher> adjVouchers);

    @GET("stock_clerk/collection_points")
    Call<CollectionPointListResponse> getAllCollectionPoints(@Header(Util.AUTH_HEADER) String authHeader);

    @GET("stock_clerk/disbursements/pending")
    Call<DisbursementsResponse> getAllPendingDisbursements(@Header(Util.AUTH_HEADER) String authHeader, @Query("collectionPoint") long collectionPoint);

    @POST("stock_clerk/disbursement/update")
    Call<String> updateDisbursement(@Header(Util.AUTH_HEADER) String authHeader, @Body DisbursementDTO disbursementDTO);

    @GET("stock_clerk/disbursement/{listId}")
    Call<DisbursementDetailsResponse> getDisbursementDetailsToUpdate(@Header(Util.AUTH_HEADER) String authHeader, @Path("listId") long id);
}
