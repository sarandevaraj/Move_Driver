package com.taximobility.bookingmodule

import android.animation.Animator
import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.*
import android.text.format.Time
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.mayan.sospluginmodlue.service.SOSService
import com.taximobility.*
import com.taximobility.adapter.PreferenceListAdapter
import com.taximobility.bookingmodule.Alert.AlertPackagePlan
import com.taximobility.bookingmodule.Alert.PromoCodeAlert
import com.taximobility.bookingmodule.Data.ModelData
import com.taximobility.bookingmodule.Data.NearestDriverDatas
import com.taximobility.bookingmodule.Data.SaveBookingResponse
import com.taximobility.bookingmodule.Data.ServiceData
import com.taximobility.bookingmodule.DriverLiveMovement.DriverLiveMove
import com.taximobility.bookingmodule.Interface.PickDropSetListener
import com.taximobility.bookingmodule.Interface.RouteListeners
import com.taximobility.bookingmodule.MapModule.MapFragment
import com.taximobility.bookingmodule.adapter.ModelListAdapter
import com.taximobility.bookingmodule.adapter.ServiceListAdapter
import com.taximobility.bookingmodule.pickDropLoc.SearchLocationFrag
import com.taximobility.bookingmodule.utils.CarModelsView
import com.taximobility.bookingmodule.utils.NumberFormat
import com.taximobility.data.DriverData
import com.taximobility.data.apiData.PreferencesDataList
import com.taximobility.databinding.BookTaxiHomePageBinding
import com.taximobility.features.CToast
import com.taximobility.fragments.PayModeSelection
import com.taximobility.fragments.SplitFareDialog
import com.taximobility.interfaces.*
import com.taximobility.locationSearch.PlacesData
import com.taximobility.service.GetPassengerUpdate
import com.taximobility.util.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_taxi_home_page.*
import kotlinx.android.synthetic.main.book_taxi_home_page.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


private const val MY_PERMISSIONS_REQUEST_CONTACTS = 222
private const val NEAREST_API = 100
private const val BOOK_NOW = 200
private const val BOOK_LATER = 300
private const val BOOK_NOW_API = 201


class BookTaxiHomePage : MapFragment(), PickDropSetListener,
        DialogInterface, splitfareDialog, RouteListeners, GetAddress, FragPopFront, PackageClick, PromoClick, OpenPackage, GetModelDetails {

    private var bottomSheetFragment: BottomSheetDialogFragment? = null

    private lateinit var botFavLay: LinearLayout
    private lateinit var skipDropLoc: TextView
    private lateinit var txtRequestTaxi: TextView
    private lateinit var textBookLater: LinearLayout
    private lateinit var cashCardTxt: TextView
    private lateinit var locImg: ImageView
    private lateinit var locationImg: ImageView
    private lateinit var naviIcon: ImageView
    private lateinit var locationLay: FrameLayout
    private lateinit var carModelView: CarModelsView
    private lateinit var ivLine: ImageView
    private lateinit var instructionHeader: TextView
    private lateinit var promoCodeLay: LinearLayout
    private lateinit var fareMinimumPpl: TextView
    private lateinit var animationLay: RelativeLayout
    private lateinit var bottomViewLay: LinearLayout
    private lateinit var corporateBookLater: Button
    private lateinit var requestBooking: LinearLayout
    private lateinit var preferencesDataList: PreferencesDataList

    private var placesDetailArrayList: ArrayList<PlacesData> = ArrayList()
    private var driverIdData: ArrayList<String> = ArrayList()
    private var driverMarkerService: ArrayList<Marker> = ArrayList()

    private lateinit var findETAFare: FindETAFare
    private var driverLiveMovement: DriverLiveMove = DriverLiveMove()
    private var mMap: GoogleMap? = null
    lateinit var binding: BookTaxiHomePageBinding
    lateinit var viewModel: BookTaxiHomeViewModel
    private var animatedVectorDrawable: AnimatedVectorDrawable? = null
    private var handler: Handler = Handler()
    private var splitFareDialog: SplitFareDialog? = null
    private var alertBundle: Bundle? = null
    private lateinit var showPackage: AlertPackagePlan
    private lateinit var showPromo: PromoCodeAlert

    private var isNeedToDrawRoute: Boolean = false

    private var alertMsg: String = ""
    private var availablecarcount: Int = 0
    private var pickupTime: String = ""
    private var pickupTimeAndDate: String = ""
    private var dtMDialog: Dialog? = null
    private var mcDialog: Dialog? = null
    private var loadingDialog: Dialog? = null
    lateinit var alertDialog: Dialog
    private var ampm = "AM"
    private var hour = 0
    private var min = 0
    private var date = 0
    private var month = 0
    private var year = 0
    private var intPaymentType: Int = 0

    private var approximateFare = 0.0
    private var approximateDistance: Double = 0.0
    private var approximateTime: Double = 0.0
    private var ETime = 0.0

    private var favDriverAvailable: Int = 0
    private var bookFavDriver: Int = 0
    private var friend1S: Double = 0.0
    private var friend2S: Double = 0.0
    private var friend3S: Double = 0.0
    private var friendA: Double = 100.0
    private var friend1SA: Double = 0.0
    private var friend2SA: Double = 0.0
    private var friend3SA: Double = 0.0
    private var favDriverMessage: String = ""
    private var travelModelId: String = ""
    private var bookingType: String = ""
    private var zoneFareApplicable: Boolean = false
    private var zoneZoneFare = 0.0
    private var doubleBackToExitPressedOnce: Boolean = false
    private var selected_model_id: Int = 0
    private var selected_model_size: String = ""

    private var choose_service: Dialog? = null
    private lateinit var model_list: RecyclerView
    private lateinit var modelArray: java.util.ArrayList<ModelData>
    private lateinit var img_model: ImageView
    private lateinit var tv_model_name: TextView
    private lateinit var pass_count: TextView
    private lateinit var time: TextView
    private lateinit var tv_fare: TextView
    private lateinit var preferenceRv: RecyclerView
    private lateinit var book_taxi_main_frag: FrameLayout
    private lateinit var txt_skip_drop_lay: LinearLayout
    private var mAdapter: ModelListAdapter? = null
    private lateinit var serviceArray: java.util.ArrayList<ServiceData>
    private lateinit var ll_choose_service: LinearLayout
    private lateinit var rv_service: RecyclerView
    private lateinit var txt_now_later: TextView
    private lateinit var tv_confirm: Button
    private var serviceListAdapter: ServiceListAdapter? = null
    private var serviceType: String? = ""
    private lateinit var delivery_lay: LinearLayout
    private lateinit var et_product_name: EditText
    private lateinit var et_weight: EditText
    private lateinit var et_size: EditText
    private lateinit var et_name: EditText
    private lateinit var et_phone: EditText
    private lateinit var et_date: EditText
    private lateinit var lay_rental: LinearLayout
    private lateinit var rg_rental_out: RadioGroup

    private lateinit var preference_lay: LinearLayout
    private lateinit var et_notes: EditText

    private var os_type: Int = 1
    private lateinit var ll_oneWay: LinearLayout
    private lateinit var ll_round_trip: LinearLayout
    private lateinit var tv_minus: TextView
    private lateinit var tv_pick: TextView
    private lateinit var tv_plus: TextView
    private var os_days: Int = 0
    private var zoneTozone_fare: Double = 0.0
    private var zoneTozone_applicable: Boolean = false

    enum class BOOKINGSTATE {
        STATE_ONE, STATE_TWO
    }

    companion object {
        var bookingState: BOOKINGSTATE = BOOKINGSTATE.STATE_ONE
        lateinit var searchPage: SearchLocationFrag
        var defaultCityName: String = ""
        var IS_HOME_PAGE = false
        var displayHeight: Int = 0
        var displayWidth: Int = 0
        var isDropSetFirstTime: Boolean = false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        IS_HOME_PAGE = true
        binding = DataBindingUtil.inflate(inflater, R.layout.book_taxi_home_page, container, false)
        viewModel = ViewModelProviders.of(this@BookTaxiHomePage).get(BookTaxiHomeViewModel::class.java)
        binding.apply {
            myBookViewModel = viewModel
            lifecycleOwner = this@BookTaxiHomePage
            executePendingBindings()
        }
        initialize(binding.root)
        return binding.root
    }


    private fun initialize(view: View) {
        (requireActivity() as MainHomeFragmentActivity).left_icon.setImageResource(R.drawable.ic_menu)
        (requireActivity() as MainHomeFragmentActivity).left_icon.tag = "menu"
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(listener,
                IntentFilter(TaxiUtil.ACTIVITY_ACTION))
        initializeMap(view)

        Colorchange.ChangeColor(view as ViewGroup, requireActivity())
        FontHelper.applyFont(requireActivity(), requireActivity().findViewById(R.id.lay_home))

        alertDialog = Dialog(requireActivity())
        SessionSave.saveSessionInt(TaxiUtil.SKIP_PAST_BOOKING, 1, requireActivity())
        SessionSave.saveSession(TaxiUtil.NODE_TOKEN, "", requireActivity())
        SessionSave.saveSession(TaxiUtil.SOS_NAME, SessionSave.getSession(PASS_NAME, requireActivity()), requireActivity())
        val displayMetrics: DisplayMetrics = requireActivity().resources.displayMetrics
        displayWidth = displayMetrics.widthPixels
        displayHeight = displayMetrics.heightPixels
        bookingState = BOOKINGSTATE.STATE_ONE
        botFavLay = view.findViewById(R.id.fav_bot_lay)
        skipDropLoc = view.findViewById(R.id.txt_skip_drop)
        ivLine = view.findViewById(R.id.img_iv_line)
        txtRequestTaxi = view.findViewById(R.id.textRequestTaxi)
        textBookLater = view.findViewById(R.id.textBookLater)
        carModelView = view.findViewById(R.id.car_model_view)
        corporateBookLater = view.findViewById(R.id.corporateBookLater)
        requestBooking = view.findViewById(R.id.requestBooking)
        cashCardTxt = view.findViewById(R.id.cash_card)
        locImg = view.findViewById(R.id.mov_cur_loc)
        locImg.visibility = View.GONE
        locationImg = view.findViewById(R.id.location_img)
        locationLay = view.findViewById(R.id.location_frame)
        naviIcon = view.findViewById(R.id.navi_icon_book)
        instructionHeader = view.findViewById(R.id.instruction_header)
        promoCodeLay = view.findViewById(R.id.promo_code_lay)
        fareMinimumPpl = view.findViewById(R.id.fare_minimum_ppl)
        animationLay = view.findViewById(R.id.lay_home)
        bottomViewLay = view.findViewById(R.id.bottomViewLay)
        img_model = view.findViewById(R.id.img_model);
        tv_model_name = view.findViewById(R.id.tv_model_name)
        tv_fare = view.findViewById(R.id.tv_fare)
        time = view.findViewById(R.id.time)
        pass_count = view.findViewById(R.id.pass_count);
        preferenceRv = view.findViewById(R.id.preference_rv)
        book_taxi_main_frag = view.findViewById(R.id.book_taxi_main_frag)
        txt_skip_drop_lay = view.findViewById(R.id.txt_skip_drop_lay)
        ll_choose_service = view.findViewById(R.id.ll_choose_service)
        rv_service = view.findViewById(R.id.rv_service)
        tv_confirm = view.findViewById(R.id.tv_confirm)
        delivery_lay = view.findViewById(R.id.delivery_lay)
        et_product_name = view.findViewById(R.id.et_product_name)
        et_weight = view.findViewById(R.id.et_weight)
        et_size = view.findViewById(R.id.et_size)
        et_name = view.findViewById(R.id.et_name)
        et_phone = view.findViewById(R.id.et_phone)
        et_date = view.findViewById(R.id.et_date)
        preference_lay = view.findViewById(R.id.preference_lay)
        et_notes = view.findViewById(R.id.et_notes)
        rg_rental_out = view.findViewById(R.id.rg_rental_out)
        lay_rental = view.findViewById(R.id.lay_rental)
        ll_oneWay = view.findViewById(R.id.ll_oneWay)
        ll_round_trip = view.findViewById(R.id.ll_round_trip)
        tv_minus = view.findViewById(R.id.tv_minus)
        tv_pick = view.findViewById(R.id.tv_pick)
        tv_plus = view.findViewById(R.id.tv_plus)
        setPaymentType()


        book_taxi_main_frag.visibility = View.GONE
        txt_skip_drop_lay.visibility = View.GONE

        val ss = LinearLayoutManager(requireActivity())
        ss.orientation = LinearLayoutManager.VERTICAL
        preferenceRv.layoutManager = ss



        if (SessionSave.getSession("isFromSplash", context, false)) {
            animationInScreen()
        }

        try {
            alertBundle = this.arguments
            alertBundle?.let {
                alertMsg = it.getString("alert_message", "")
                if (alertMsg.isNotEmpty()) {
                    alertView(requireActivity(), "" + NC.getString(R.string.message), "" + alertMsg, "" + NC.getString(R.string.ok), "")
                    alertMsg = ""
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivLine.background = requireActivity().getDrawable(R.drawable.progress_line_anim)
        }
        binding.root.request_lay.visibility = View.GONE

        findETAFare = FindETAFare(this)
        showPackage = AlertPackagePlan(this)
        showPromo = PromoCodeAlert(this)

        val heightStatus = DisplayDimensions.getStatusBarHeight(requireActivity())
        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        if (SessionSave.getSession(LANG, requireActivity()) == "ar") {
            lp.setMargins(TaxiUtil.getPixelsFromDp(requireActivity(), 15f), heightStatus, 0, 0)
            val left = naviIcon.paddingLeft
            naviIcon.layoutParams = lp
            naviIcon.setPadding(left, heightStatus, 0, 25)
        } else {
            val right = naviIcon.paddingRight
            lp.setMargins(TaxiUtil.getPixelsFromDp(requireActivity(), 15f), heightStatus, 0, 0)
            naviIcon.layoutParams = lp
            naviIcon.setPadding(0, heightStatus, right, 25)
        }

        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(message: Message) {
                try {
                    val ft = requireActivity().supportFragmentManager.beginTransaction()
                    val prev = requireActivity().supportFragmentManager.findFragmentByTag("dialog")
                    if (prev != null) {
                        ft.remove(prev)
                    }
                    splitFareDialog = SplitFareDialog().also {
                        it.show(ft, "dialog")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        searchPage = SearchLocationFrag()
        searchPage.setSearchFragmentListener(this)
        childFragmentManager.beginTransaction().replace(R.id.book_taxi_main_frag, searchPage, "").commit()
        // carModelView.setCarModelArray(SessionSave.getSession(MODEL_DETAILS, requireActivity()), viewModel)
        callGetPassengerInfoApi()
        if (viewModel != null) {
            viewModel.getFavouritePlaces()
            viewModel.carLayClick.value = true
        }
        viewModel.saveBookingRes.observe(viewLifecycleOwner, Observer {
            closeDialog()
            if (it != null) {
                handlingSaveBookingRes(it)
            } else {
                mcDialog = Utility.alert_view_dialog(requireActivity(),
                        "",
                        "" + NC.getString(R.string.server_con_error),
                        "" + NC.getString(R.string.ok), "",
                        true, { dialog, which -> dialog.dismiss() }, null, "")
            }
        })

        viewModel.checkPromocodeResponse.observe(viewLifecycleOwner, Observer {
            closeDialog()
            if (it != null) {
                try {
                    if (it.status == 1) {
                        mcDialog = Utility.alert_view_dialog(requireActivity(),
                                "Message",
                                "" + it.message,
                                "" + NC.getString(R.string.ok), "",
                                true, { dialog, which -> dialog.dismiss() }, { dialog, which -> dialog.dismiss() }, "")
                    } else {
                        CToast.ShowToast(requireActivity(), it.message)
                        showPromo.setPromo()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showPromo.setPromo()
                }
            } else {
                showPromo.setPromo()
            }
        })

        viewModel.skipDropClick.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                if (searchPage.pickupLocTxt == "" || searchPage.pickupLocTxt == NC.getString(R.string.pinlocation) ||
                        searchPage.pickupLocTxt == NC.getString(R.string.fetching_address) || searchPage.pickupLocTxt == NC.getString(R.string.picklocation)) {
                    getPickupAddress(searchPage.pickuplatlng)
                    CToast.ShowToast(requireActivity(), NC.getString(R.string.Need_proper_loc))
                } else {
                    if (searchPage.pickupLocTxt != NC.getString(R.string.fetching_address) && searchPage.pickupLocTxt != NC.getString(R.string.picklocation)) {
                        if (searchPage.dropLocTxt != NC.getString(R.string.fetching_address)) {
                            if (searchPage.dropLocTxt != "") {
                                if ((serviceType.equals("4", true) || serviceType.equals("5", true)) && searchPage.getDropLatLng().latitude == 0.0) {
                                    CToast.ShowToast(requireActivity(), NC.getString(R.string.select_the_drop_location))
                                    return@Observer
                                }
                                selected_model_id = 0
                                chooseServiceDialog()
                                /* setCarLay()
                             cameraChangeListeners(false)*/
                            } else {
                                CToast.ShowToast(requireActivity(), NC.getString(R.string.select_the_drop_location))
                            }
                        } else {
                            CToast.ShowToast(requireActivity(), NC.getString(R.string.Need_proper_loc))
                        }
                    }
                }
            }
        })

        viewModel.carLayClick.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                /* if (carModelView.getSelectedCarModel() != "-1") {
                     println("MODEl SIZE ${carModelView.getSelectedCarModelSize()}")
                     if (bookingState == BOOKINGSTATE.STATE_TWO) {
                         startLineProgress()
                         txtRequestTaxi.text = NC.getString(R.string.searching_text)
                         txtRequestTaxi.isEnabled = false
                     }
                     driverLiveMovement.removeDriverLiveMovementCallback()
                     driverLiveMovement.removeDriverLiveMovement()
                     if (searchPage.pickuplatlng.latitude != 0.0) {
                         getLastKnownLatlngForNearestApiCall(NEAREST_API)
                     }
                 } else {
                     txtRequestTaxi.text = NC.getString(R.string.car_not_available)
                     if (bookingState == BOOKINGSTATE.STATE_TWO) {
                         showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                 NC.getString(R.string.continue_package), NC.getString(R.string.ok),
                                 NC.getString(R.string.cancel), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                 searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "")
                     } else {
                           carModelView.setCarModelArray(SessionSave.getSession(MODEL_DETAILS, requireActivity()), viewModel)
                     }
                 }*/
            }
        })

        viewModel.cashCardClick.observe(viewLifecycleOwner, Observer {
            /*if (it != null && it) {
                PaymentAlert.cashCardPay(requireActivity(), intPaymentType, viewModel, cashCardTxt)
            }*/
            bottomSheet(intPaymentType, cashCardTxt)
        })

        viewModel.nearestResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                stopLineProgress()
                availablecarcount = 0
                handlingNearestRes(it)
            } else {
                driverLiveMovement.removeDriverLiveMovementCallback()
                driverLiveMovement.removeDriverLiveMovement()
                stopLineProgressOnError()
                CToast.ShowToast(requireActivity(), getString(R.string.server_con_error))
                Handler().postDelayed({
                    if (isResumed) {
                        getLastKnownLatlngForNearestApiCall(NEAREST_API)
                    }
                }, 5000)
            }
        })

        viewModel.loadAllFavourite().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val listSize = it.size
                if (listSize != 0) {
                    for (i in 0 until listSize) {
                        when {
                            it[i].type != "1" -> searchPage.setFavouriteUnselectIcon()
                            it[i].type == "1" -> {
                                if (searchPage.pickupLocTxt == it[i].location_name) {
                                    searchPage.setFavIcon()
                                } else searchPage.setFavouriteUnselectIcon()
                            }
                        }
                    }
                } else {
                    searchPage.setFavouriteUnselectIcon()
                    if (botFavLay != null)
                        botFavLay.removeAllViews()
                }
            } else {
                searchPage.setFavouriteUnselectIcon()
            }
        })

        viewModel.getFavouritePlaces().observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                setFavPlace(it)
                val listSize = it.size
                if (listSize == 0) {
                    if (botFavLay != null)
                        botFavLay.removeAllViews()
                }
            } else {

            }
        })

        viewModel.modelDetailsRes.observe(viewLifecycleOwner, Observer {
            closeDialog()
            if (it != null && it.isNotEmpty()) {
                val jsonObject = JSONObject(it);
                if (jsonObject.has("model_details") && jsonObject.getJSONArray("model_details").length() > 0) {
                    SessionSave.saveSession(MODEL_DETAILS, jsonObject.getString("model_details"), requireActivity())
                    ll_choose_service.visibility = View.GONE
                    locImg.visibility = View.VISIBLE
                    book_taxi_main_frag.visibility = View.VISIBLE
                    txt_skip_drop_lay.visibility = View.VISIBLE
                } else {
                    CToast.ShowToast(requireActivity(), "This service not available at the moment")
                }


            }

        })


        viewModel.modelPreferenceRes.observe(viewLifecycleOwner, Observer {
            closeDialog()
            choose_service!!.dismiss()
            preferencesDataList = PreferencesDataList()
//            if (TaxiUtil.mDriverdata != null && mMap != null)
//                driverLiveMovement.findNearestlocal(requireActivity(), this@BookTaxiHomePage, TaxiUtil.mDriverdata, driverIdData, mMap)
            if (it != null && it.isNotEmpty()) {
                val jsonObject = JSONObject(it);
                if (jsonObject.has("preference_list") && jsonObject.getJSONArray("preference_list").length() > 0) {
                    for (i in 0 until jsonObject.getJSONArray("preference_list").length()) {
                        try {
                            val pre = preferencesDataList.PreferencesData()
                            pre.preference_name = jsonObject.getJSONArray("preference_list").getJSONObject(i).getString("preference_name")
                            pre.preference_fare = jsonObject.getJSONArray("preference_list").getJSONObject(i).getString("preference_fare")
                            pre.preference_id = jsonObject.getJSONArray("preference_list").getJSONObject(i).getString("preference_id")
                            pre.isSelected = false
                            preferencesDataList.preferencesDatas.add(pre)
                        } catch (e: Exception) {

                        }

                    }
                }

            }

            if (preferencesDataList.preferencesDatas.size > 0) {
                preference_lay.visibility = View.VISIBLE
                val adapter = PreferenceListAdapter(requireActivity(), preferencesDataList)
                preferenceRv.adapter = adapter
            } else {
                preference_lay.visibility = View.GONE
            }

            if (serviceType.equals("2")) {
                delivery_lay.visibility = View.VISIBLE
                rg_rental_out.visibility = View.GONE
                lay_rental.visibility = View.GONE
            } else if (serviceType.equals("4") || serviceType.equals("5")) {
                rg_rental_out.visibility = View.VISIBLE
                lay_rental.visibility = View.VISIBLE
                delivery_lay.visibility = View.GONE

                if (serviceType.equals("4")) {
                    rg_rental_out.visibility = View.GONE
                    ll_round_trip.visibility = View.GONE
                    ll_oneWay.visibility = View.GONE
                }
            } else {
                delivery_lay.visibility = View.GONE
                rg_rental_out.visibility = View.GONE
                lay_rental.visibility = View.GONE
            }

            for (i in 0 until modelArray.size) {
                if (selected_model_id.toString().equals(modelArray.get(i).model_id)) {
                    tv_model_name.setText(modelArray.get(i).model_name)
                    pass_count.setText(modelArray.get(i).model_size)
                    Picasso.get().load(modelArray.get(i).focus_image).error(R.drawable.car2_unfocus).into(img_model)
                }
            }
            if (approximateFare > 0) {
                println("approximateTime : $approximateTime  approximateFare : $approximateFare")
                val eta_time = Math.round(approximateTime).toString()
                time.setText(NC.getString(R.string.travelling_time) + " " + eta_time + " - Mins")
                tv_fare.setText(NC.getString(R.string.estimated_fare) + " " + SessionSave.getSession("Currency", requireActivity()) + NumberFormat.convertDouble(String.format(Locale.UK, approximateFare.toString()).toDouble()).toDouble())
            } else {
                time.setText("Select to see fare estimate")
                tv_fare.setText(SessionSave.getSession("Currency", requireActivity()) + "0.00")
            }

            if (availablecarcount > 0) {
                tv_confirm.text = NC.getString(R.string.confirm)
                txtRequestTaxi.text = NC.getString(R.string.confirm)
            } else {
                tv_confirm.text = NC.getString(R.string.car_not_available)
                txtRequestTaxi.text = NC.getString(R.string.car_not_available)
            }
            setCarLay()
            cameraChangeListeners(false)

        })

        val lParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        if (SessionSave.getSession(LANG, requireActivity()) == "ar")
            lParams.setMargins(0, heightStatus, TaxiUtil.getPixelsFromDp(requireActivity(), 50f), 0)
        else
            lParams.setMargins(TaxiUtil.getPixelsFromDp(requireActivity(), 50f), heightStatus, 0, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val linearLayout = view.findViewById<LinearLayout>(R.id.instruction_header_lay)
            linearLayout.layoutParams = lParams
            linearLayout.setPadding(0, heightStatus / 2, 0, 0)
        }

        if (SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, activity) == "1") {
            requestBooking.visibility = View.GONE
            corporateBookLater.visibility = View.VISIBLE
        } else {
            requestBooking.visibility = View.VISIBLE
            corporateBookLater.visibility = View.GONE
        }

        corporateBookLater.setOnClickListener {
            textBookLater.performClick()
        }

        if (choose_service != null) {
            choose_service?.dismiss()
        }

        et_date.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                deliveryDateFun()
                return v?.onTouchEvent(event) ?: true
            }
        })

        rg_rental_out.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (group.checkedRadioButtonId == R.id.rb_one_way) {
                ll_round_trip.visibility = View.GONE
                ll_oneWay.visibility = View.VISIBLE
                os_type = 1
            } else if (group.checkedRadioButtonId == R.id.rb_round_trip) {
                os_type = 2
                ll_round_trip.visibility = View.VISIBLE
                ll_oneWay.visibility = View.GONE
            }
        })

        tv_minus.setOnClickListener({
            if (os_days > 0) {
                os_days = os_days - 1
            }
            tv_pick.setText("" + os_days + " Days")

        })

        tv_plus.setOnClickListener({
            if (os_days < 15) {
                os_days = os_days + 1
            }
            tv_pick.setText("" + os_days + " Days")

        })


    }


    private fun animationInScreen() {
        if (requireActivity() != null && isAdded && animationLay != null && animationLay.isAttachedToWindow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ViewCompat.postOnAnimation(animationLay, Runnable {
                    val animator = Utility.animateRevealWithoutColorFromCoordinates(animationLay)
                    animator.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            if (requireActivity() != null)
                                SessionSave.saveSession("isFromSplash", false, requireActivity())
                        }

                        override fun onAnimationCancel(animation: Animator) {}

                        override fun onAnimationRepeat(animation: Animator) {}
                    })

                })
            }
        }
    }

    /**
     * Custom alert dialog used in entire project.can call from anywhere with the following
     *
     * @param title       set the title for alert dialog
     * @param message     set the message for alert dialog
     * @param successTxt set the success text in success button
     * @param failureTxt set the failure text in failure button
     */
    private fun alertView(mContext: Activity?, title: String, message: String, successTxt: String, failureTxt: String) {
        try {
            alertDialog = Utility.alert_view_dialog(mContext, "" + title,
                    message,
                    successTxt,
                    failureTxt,
                    true, { dialog, which -> dialog.dismiss() }, { dialog, _ -> dialog.dismiss() }, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun setServiceModelArray(array: String) {
        serviceArray = ArrayList()
        val modelJson = JSONArray(array)
        for (n in 0 until modelJson.length()) {
            serviceArray.add(
                    ServiceData(
                            modelJson.getJSONObject(n).getString("id"),
                            modelJson.getJSONObject(n).getString("label"),
                            modelJson.getJSONObject(n).getString("unfocus_image"),
                            modelJson.getJSONObject(n).getString("focus_image")
                    )
            )

        }

        val ss = LinearLayoutManager(requireActivity())
        ss.orientation = LinearLayoutManager.HORIZONTAL
        rv_service.layoutManager = ss
        serviceListAdapter = ServiceListAdapter(requireActivity(), serviceArray, this)
        rv_service.adapter = serviceListAdapter

    }


    private fun setPaymentType() {
        try {
            val jsonArray = JSONArray(SessionSave.getSession(PASS_PAYMENT_OPTION, requireActivity()))
            if (jsonArray.length() == 1) {
                val jsonObject = jsonArray.getJSONObject(0)
                if (jsonObject.getInt("pay_mod_id") == 1) {
                    intPaymentType = 1
                    viewModel.setPaymentType(NC.getString(R.string.payment_cash))
                    viewModel.cashCardEnable(false)
                } else if (jsonObject.getInt("pay_mod_id") == 2) {
                    intPaymentType = 0
                    viewModel.cashCardEnable(false)
                    viewModel.setPaymentType(NC.getString(R.string.card))
                }
            } else {
                intPaymentType = 0
                viewModel.cashCardEnable(true)
                viewModel.setPaymentType(NC.getString(R.string.cash_card))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun handlingNearestRes(it: NearestDriverDatas) {
        try {
            var driverId: String
            var driverName: String
            var lat: String
            var lng: String
            var nearest: String
            var distance: String
            var driverCoordinates: String
            val jsonResponse: NearestDriverDatas = it
            var nearestJsonResponse = JSONObject(SessionSave.getSession("Server_Response", context))
            if (nearestJsonResponse.getInt("zone_zone_fare")==1) {
                zoneTozone_applicable = true
                zoneTozone_fare = jsonResponse.fare_details.zone_zone_fare
            } else {
                zoneTozone_applicable = false
                zoneTozone_fare = 0.0
            }

            if (TaxiUtil.mDriverdata.size != 0)
                TaxiUtil.mDriverdata.clear()
            driverIdData = ArrayList()

            if (TaxiUtil.mDrivermovementdata != null) {
                TaxiUtil.mDrivermovementdata.clear()
                removeMarker(driverMarkerService)
                driverLiveMovement.removeDriverLiveMovement()
            }

            val status = jsonResponse.status
            if (jsonResponse.zone_fare_applicable == "1") {
                zoneFareApplicable = true
                zoneZoneFare = jsonResponse.zone_zone_fare
            } else {
                zoneFareApplicable = false
            }
            if (approximateDistance != 0.0 && approximateTime != 0.0) {
                findETAFare.findApproximateFare(requireActivity(), approximateDistance, approximateTime)
                if (zoneFareApplicable) {
                //    approximateFare = zoneZoneFare
                    carModelView.setApproximateFare(approximateFare)
                } else {
                    carModelView.setApproximateFare(approximateFare)
                }
                carModelView.setApproximateTime(approximateTime)
            }
            if (searchPage.getDropLatLng().latitude != 0.0) {
                carModelView.setApproximateFare(approximateFare)
                if (mAdapter != null) {
                    if (zoneTozone_applicable)
                        approximateFare = zoneTozone_fare
                    if (model_list != null && !model_list.isComputingLayout)
                        mAdapter!!.updateFare(approximateFare, approximateTime)
                }

            } else {
                carModelView.setApproximateFare(0.0)
                if (mAdapter != null) {
                    if (zoneTozone_applicable)
                        approximateFare = zoneTozone_fare
                    if (model_list != null && !model_list.isComputingLayout)
                        mAdapter!!.updateFare(0.0, 0.0)
                }
            }
            when (status) {
                1 -> {
                    favDriverAvailable = jsonResponse.fav_drivers
                    favDriverMessage = jsonResponse.fav_driver_message
                    if (jsonResponse.detail.isNotEmpty()) {
                        txtRequestTaxi.text = NC.getString(R.string.request_taxi)
                        txtRequestTaxi.isEnabled = true
                        travelModelId = jsonResponse.detail[0].travel_modelid.toString()
                        for (i in 0 until jsonResponse.detail.size) {
                            availablecarcount++
                            val detail = jsonResponse.detail
                            val listLatLng = ArrayList<String>()
                            driverCoordinates = detail[i].driver_coordinates
                            val latLng = driverCoordinates.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            for (x in latLng.indices) {
                                listLatLng.add(latLng[x])
                            }
                            driverId = detail[i].driver_id.toString()
                            driverName = ""
                            lat = detail[i].latitude.toString()
                            lng = detail[i].longitude.toString()
                            nearest = detail[i].nearest_driver
                            distance = detail[i].distance_km
                            driverIdData.add(driverId)
                            val data = DriverData(driverId, driverName, speed, lat, lng, nearest, distance, null, listLatLng)
                            TaxiUtil.mDriverdata.add(data)
                            fareMinimumPpl.text = "1-" + jsonResponse.fare_details.model_size
                        }

                        if (availablecarcount > 0 && bookingState == BOOKINGSTATE.STATE_ONE) {
                            if (SessionSave.getSession(IS_BUISNESS_KEY, requireActivity(), true)) {
                                findETAFare.findETA(requireActivity(), searchPage.pickuplatlng.latitude, searchPage.pickuplatlng.longitude, TaxiUtil.mDriverdata[0].lat.toDouble(), TaxiUtil.mDriverdata[0].lng.toDouble())
                            }
                        }
                        driverLiveMovement.findNearestlocal(requireActivity(), this@BookTaxiHomePage, TaxiUtil.mDriverdata, driverIdData, mMap)
                    } else {
                        txtRequestTaxi.text = NC.getString(R.string.car_not_available)
                    }
                }
                -101 ->
                    forceLogout()
                else -> {
                    ETime = 0.0
                    txtRequestTaxi.text = NC.getString(R.string.car_not_available)
                    driverLiveMovement.removeDriverLiveMovementCallback()
                    driverLiveMovement.removeDriverLiveMovement()
                    if (bookingState == BOOKINGSTATE.STATE_TWO) {
                        PickDropMarker.setPickMarkerWithCustomView(requireActivity(), mMap!!, searchPage.pickupLocTxt, searchPage.pickuplatlng, ETime)
                    }
                    if (searchPage.getDropLatLng().latitude == 0.0)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(searchPage.pickuplatlng, 18f))
                    stopLineProgress()
                    fareMinimumPpl.text = "1-" + jsonResponse.fare_details.model_size
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stopLineProgressOnError()
        }
    }


    private fun handlingSaveBookingRes(it: SaveBookingResponse) {
        try {
            when (it.status) {
                1 -> {
                    SessionSave.saveSession(REQ_TRIP_ID, it.detail.passenger_tripid.toString(), requireActivity())
                    SessionSave.saveSession(PASS_TRIP_ID, it.detail.passenger_tripid.toString(), requireActivity())
                    SessionSave.saveSession(REQUEST_TIME, it.detail.total_request_time.toString(), requireActivity())
                    SessionSave.saveSession(CREDIT_CARD, "" + it.detail.credit_card_status, requireActivity())
//                    SessionSave.saveSession(Total_Request_Time, "" + it.detail.total_request_time, requireActivity())
                    if (bookingType == "now") {
                        getLastKnownLatlngForNearestApiCall(BOOK_NOW_API)
                    } else {
                        // alertView(requireActivity(), "" + NC.getString(R.string.message), "" + it.message, "" + NC.getString(R.string.ok), "")
                        bookingState = BOOKINGSTATE.STATE_ONE
                        doubleBackToExitPressedOnce = false
                        onBackPress()
                        val i = Intent(requireActivity(), ActivityThanks::class.java)
                        startActivity(i)

                    }
                }
                -6, 2, 5, 6 ->
                    alertView(requireActivity(), "" + NC.getString(R.string.message), "" + it.message, "" + NC.getString(R.string.ok), "")
                3 -> {
                    SessionSave.saveSession("trip_id", it.trip_id.toString(), requireActivity())
                    divertToOngoingScreen(requireActivity(), "" + NC.getString(R.string.message), "" + it.message, "" + NC.getString(R.string.ok), "")
                }
                4 -> alertDialog = Dialog_Common().setmCustomDialogs(requireActivity(), this@BookTaxiHomePage, NC.getString(R.string.message), it.message,
                        NC.getString(R.string.ok),
                        NC.getString(R.string.no_thanks), "4")
                -10 -> {
                    val j = JSONObject()
                    j.put("id", SessionSave.getSession(PASS_ID, requireActivity()))
                    if (SessionSave.getSession(LOGOUT, requireActivity()) == "") {
                        TaxiUtil.Logout("type=passenger_logout", requireActivity(), j)
                        (requireActivity() as MainHomeFragmentActivity).fbLogout()
                    } else
                        alertView(requireActivity(), "" + NC.getString(R.string.message), "" + NC.getString(R.string.bookedtaxi), "" + NC.getString(R.string.ok), "")

                    ShowToast.center(requireActivity(), it.message)
                }
                -2678 -> divertToTripHistory(requireActivity(), "" + NC.getString(R.string.message), "" + it.message, "" + NC.getString(R.string.ok), "")
                else -> alertView(requireActivity(), "" + NC.getString(R.string.message), "" + it.message, "" + NC.getString(R.string.ok), "")

            }


        } catch (e: Exception) {
            SessionSave.saveSession(REQ_TRIP_ID, "", requireActivity())
            requireActivity().runOnUiThread { CToast.ShowToast(requireActivity(), getString(R.string.server_con_error)) }
            e.printStackTrace()
        }
    }


    private fun divertToOngoingScreen(mContext: Activity?, title: String, message: String, successTxt: String, failureTxt: String) {
        alertDialog = Utility.alert_view_dialog(mContext, "" + title,
                message,
                successTxt,
                failureTxt,
                true, { dialog, which ->
            dialog.dismiss()
            val i = Intent(requireActivity(), MainHomeFragmentActivity::class.java)
            startActivity(i)
        }, { dialog, which -> dialog.dismiss() }, "")
    }


    private fun divertToTripHistory(mContext: Activity?, title: String, message: String, successTxt: String, failureTxt: String) {
        alertDialog = Utility.alert_view_dialog(mContext, title,
                message,
                successTxt,
                failureTxt,
                true, { dialog, which ->
            dialog.dismiss()
            val i = Intent(requireActivity(), MainHomeFragmentActivity::class.java)
            i.putExtra("goto", "TripHistory")
            startActivity(i)
        }, { dialog, which -> dialog.dismiss() }, "")
    }

    private fun callBookNow() {
        showDialog()
        if (!(searchPage.pickupLocTxt.trim { it <= ' ' }.isEmpty() || searchPage.pickupLocTxt.trim { it <= ' ' } == NC.getString(R.string.fetching_address))) {
//            if (!SessionSave.getSession(TaxiUtil.isSplitOn, requireActivity(), true)) {
            if (favDriverAvailable > 0 && SessionSave.getSession(TaxiUtil.isFavDriverOn, requireActivity(), true)) {
                if (SessionSave.getSession(TaxiUtil.isSplitOn, requireActivity(), true)) {
                    closeDialog()
                    CToast.ShowToast(requireActivity(), NC.getString(R.string.select_the_drop_location))
                } else {
                    alertDialog = Dialog_Common().setmCustomDialogs(requireActivity(), this, NC.getString(R.string.message), favDriverMessage,
                            NC.getString(R.string.ok),
                            NC.getString(R.string.no_thanks), "1")
                }
            } else {
                getLastKnownLatlngForNearestApiCall(BOOK_NOW)
            }
            /*} else {
                if (searchPage.getDropLatLng().latitude == 0.0) {
                    closeDialog()
                    CToast.ShowToast(requireActivity(), NC.getString(R.string.select_the_drop_location))
                } else {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        Handler(Looper.getMainLooper()).post {
                            alertDialog = Utility.alert_view_dialog(requireActivity(), "", NC.getString(R.string.split_fare), NC.getString(R.string.yes), NC.getString(R.string.no), true, { dialog, which ->
                                ActivityCompat.requestPermissions(requireActivity(),
                                        arrayOf(Manifest.permission.READ_CONTACTS),
                                        MY_PERMISSIONS_REQUEST_CONTACTS)
                                dialog.dismiss()
                            }, { dialog, which ->
                                closeDialog()
                                dialog.dismiss()
                            }, "")
                        }
                    } else {
                        closeDialog()
                        val message = handler.obtainMessage(0, "")
                        message.sendToTarget()
                    }
                }
            }*/
        } else {
            alertView(requireActivity(), "" + NC.getString(R.string.message), "" + NC.getString(R.string.select_the_pickup_location), "" + NC.getString(R.string.ok), "")
        }
    }

    private fun startLineProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivLine.visibility = View.VISIBLE
            ivLine.background = requireActivity().getDrawable(R.drawable.progress_line_anim)
            animatedVectorDrawable = ivLine.background as AnimatedVectorDrawable
            repeatAnimation()
        } else {
            ivLine.visibility = View.GONE
        }
    }


    private fun stopLineProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable!!.stop()
                ivLine.visibility = View.GONE
            }
        }
    }


    private fun stopLineProgressOnError() {
        driverLiveMovement.removeDriverLiveMovementCallback()
        driverLiveMovement.removeDriverLiveMovement()
        stopLineProgress()
        txtRequestTaxi.text = NC.getString(R.string.car_not_available)
        CToast.ShowToast(requireActivity(), NC.getString(R.string.server_con_error))
    }

    private fun repeatAnimation() {
        animatedVectorDrawable!!.start()
        ivLine.postDelayed(action, 1000) // Will repeat animation in every 1 second
    }


    internal var action: Runnable = Runnable { repeatAnimation() }

    private fun setCarLay() {
        bookingState = BOOKINGSTATE.STATE_TWO
        isNeedToDrawRoute = true
        drawRoute()
        searchPage.locInVisibility()
        carModelView.visibility = View.VISIBLE
        skipDropLoc.visibility = View.GONE
        botFavLay.visibility = View.GONE
        request_lay.visibility = View.VISIBLE
        /*GONE*/
        ivLine.visibility = View.GONE

        locImg.visibility = View.GONE
        locationImg.visibility = View.GONE
        naviIcon.setImageResource(R.drawable.back)
        instructionHeader.visibility = View.GONE
        doubleBackToExitPressedOnce = false
        instructionHeader.text = NC.getString(R.string.tap_to_edit)
        // carModelView.setCarModelArray(SessionSave.getSession(MODEL_DETAILS, requireActivity()), viewModel)
        /*hide*/
        carModelView.visibility = View.GONE

        bottomViewLay.post {
            /* val layHeight = bottomViewLay.height
             val requiredMapHeight = displayHeight - layHeight
             mapPageLay.layoutParams.height = requiredMapHeight + 10*/
            mMap?.let {
                it.clear()
                PickDropMarker.setPickMarker(it, searchPage.pickuplatlng)
                PickDropMarker.setDropMarker(it, searchPage.getDropLatLng())
                PickDropMarker.moveCamera(requireActivity(), it, bottomViewLay)

                if (bookingState == BOOKINGSTATE.STATE_TWO) {
                    PickDropMarker.setPickMarkerWithCustomView(requireActivity(), mMap!!, searchPage.pickupLocTxt, searchPage.pickuplatlng, ETime)
                }
                if (bookingState == BOOKINGSTATE.STATE_TWO) {
                    PickDropMarker.setDropMarkerWithCustomView(requireActivity(), mMap!!, searchPage.dropLocTxt, searchPage.getDropLatLng())
                }
            }
        }

    }


    private fun drawRoute() {
        val mListLatLng: ArrayList<LatLng> = ArrayList()
        mListLatLng.add(searchPage.pickuplatlng)
        mListLatLng.add(searchPage.getDropLatLng())
        if (searchPage.pickuplatlng.latitude != 0.0 && searchPage.getDropLatLng().latitude != 0.0) {
            findETAFare.drawRoutePickDrop(mMap!!, requireActivity(), searchPage.pickuplatlng, searchPage.getDropLatLng(), searchPage.getLatLngPoints(), isNeedToDrawRoute)
            isNeedToDrawRoute = false
        } else if (searchPage.getDropLatLng().latitude == 0.0) {
            findETAFare.drawRoutePickDrop(mMap!!, requireActivity(), searchPage.pickuplatlng, searchPage.droplatlng, searchPage.getLatLngPoints(), isNeedToDrawRoute)
            isNeedToDrawRoute = false
        }

    }


    private fun addFavPlaceData(locationDataList: List<LocationData>) {
        placesDetailArrayList = ArrayList()
        locationDataList.forEach {
            val id = it._id
            if (it.type == FavouritePlaceType) {
                val lat = it.latitude
                val lng = it.longtitute
                val loc = it.location_name
                val placeId = ""
                val locType = it.label_name
                val favLocType = it.type
                val androidIcon = it.android_icon
                placesDetailArrayList.add(PlacesData(id.toInt(), lat, lng, loc, placeId, locType, favLocType, androidIcon))
            } else {
                val lat = it.latitude
                val lng = it.longtitute
                val loc = it.location_name
                val placeId = ""
                val locType = it.label_name
                val favLocType = it.type
                val androidIcon = it.android_icon
                placesDetailArrayList.add(PlacesData(id.toInt(), lat, lng, loc, placeId, locType, favLocType, androidIcon))
            }
        }
    }

    private fun setFavPlace(it: List<LocationData>) {
        addFavPlaceData(it)
        botFavLay.removeAllViews()
        val placeListSize = placesDetailArrayList.size
        for (list in placesDetailArrayList) {
            if (placeListSize <= 3) {
                val v = LayoutInflater.from(requireActivity()).inflate(R.layout.bottom_fav_itme, botFavLay, false)
                val ll = v.findViewById<LinearLayout>(R.id.fav_item_lay)
                ll.tag = list
                val str = list.android_icon
                Glide.with(requireActivity()).load(str)
                        .apply(RequestOptions.overrideOf(100, 100).centerCrop().placeholder(R.drawable.fav_placeholder).error(R.drawable.fav_placeholder))
                        .into(v.findViewById<View>(R.id.fav_image) as ImageView)
                ll.setOnClickListener { view ->
                    val lists = view.tag as PlacesData
                    if (lists.favPlaceType == FavouritePlaceType) {
                        searchPage.setPickupDropData(0, lists.lat, lists.lng, lists.placeName, lists.placeId, lists.placeType
                                ?: "", FavouritePlaceType)
                    } else {
                        searchPage.setPickupDropData(0, lists.lat, lists.lng, lists.placeName, lists.placeId, lists.placeType
                                ?: "", "0")
                    }
                }

                val favLabel = v.findViewById<TextView>(R.id.fav_label)
                favLabel.isEnabled = true
                favLabel.typeface = Typeface.DEFAULT_BOLD
                favLabel.text = list.placeType
                favLabel.isEnabled = false
                botFavLay.addView(v)
            }
        }
    }


    private fun getLastKnownLatlngForNearestApiCall(lastLocationReqType: Int) {
        getLastKnownLatLng(lastLocationReqType)
    }

    override fun getLastKnownLattitudeLongtitude(lastLatLng: LatLng, lastLocationReqType: Int) {
        var lastKnownLatLng = lastLatLng
        if (lastKnownLatLng.latitude == 0.0) {
            lastKnownLatLng = searchPage.pickuplatlng
        }
        when (lastLocationReqType) {
            NEAREST_API -> viewModel.callNearestApiCall(getNearestJsonObject(lastKnownLatLng))
            BOOK_NOW -> viewModel.callSaveBookingApi(getSaveBookingObject(lastKnownLatLng))
            BOOK_LATER -> getSaveBookingObjectBookLater(lastKnownLatLng)
            BOOK_NOW_API -> callBookNowApi(lastKnownLatLng)
        }
    }

    private fun callBookNowApi(lastKnownLatLng: LatLng) {
        val i = Intent(requireActivity(), ContinousRequest::class.java)
        val url = "type=savebooking"
        i.putExtra("url", url)
        if (zoneFareApplicable) {
          //  approximateFare = zoneZoneFare
            i.putExtra("approx_fare", "" + NumberFormat.convertDouble(String.format(Locale.UK, approximateFare.toString()).toDouble()))
        } else {
            if (approximateFare == 0.0)
                i.putExtra("approx_fare", "")
            else {
                i.putExtra("approx_fare", "" + NumberFormat.convertDouble(String.format(Locale.UK, approximateFare.toString()).toDouble()))
            }
        }
        i.putExtra("json", getSaveBookingObject(lastKnownLatLng).toString())
        startActivityForResult(i, 200)
    }

    private fun getNearestJsonObject(lastKnownLatLng: LatLng): JSONObject {
        val data = JSONObject()
        try {
            val j = JSONObject()
            if (searchPage.pickuplatlng.latitude != 0.0) {
                j.put("latitude", searchPage.pickuplatlng.latitude)
                j.put("longitude", searchPage.pickuplatlng.longitude)
            } else {
                if (lastKnownLatLng.latitude != 0.0) {
                    j.put("latitude", lastKnownLatLng.latitude)
                    j.put("longitude", lastKnownLatLng.longitude)
                } else {
                    j.put("latitude", 0.0)
                    j.put("longitude", 0.0)
                }
            }

            if (searchPage.getDropLatLng().latitude != 0.0) {
                j.put("drop_latitude", searchPage.getDropLatLng().latitude)
                j.put("drop_longitude", searchPage.getDropLatLng().longitude)
            } else {
                j.put("drop_latitude", 0.0)
                j.put("drop_longitude", 0.0)
            }

            j.put("motor_model", /*carModelView.getSelectedCarModel()*/selected_model_id.toString())

            try {
                val ss = searchPage.pickupLocTxt.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (ss.size > 2)
                    defaultCityName = ss[ss.size - 3]
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val id = SessionSave.getSession(PASS_ID, context)
            j.put("passenger_id", id)
            j.put("city_name", if (defaultCityName.trim { it <= ' ' } == "") SessionSave.getSession(DEFAULT_CITY_NAME, requireActivity()) else defaultCityName.trim({ it <= ' ' }))
            j.put("skip_fav", "2")
            j.put("device_token", SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, requireActivity()))
            j.put("skip_pop", SessionSave.getSessionInt(TaxiUtil.SKIP_PAST_BOOKING, requireActivity()))
            j.put("motor_model_size", /*carModelView.getSelectedCarModelSize()*/ selected_model_size)
            j.put("corporate_company_id", if (SessionSave.getSession(TaxiUtil.CORPORATE_COMPANY_ID, requireContext()).isNullOrEmpty()) "0" else SessionSave.getSession(TaxiUtil.CORPORATE_COMPANY_ID, requireContext()))
            data.put("data", j)
            data.put("platform", "ANDROID")
            data.put("app", "PASS")
            data.put("lang", SessionSave.getSession(LANG, requireActivity()))
            data.put("id", id)
            SessionSave.saveSessionInt(TaxiUtil.SKIP_PAST_BOOKING, 0, requireActivity())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return data
    }

    private fun getSaveBookingObject(lastKnownLatLng: LatLng): JSONObject {
        val j = JSONObject()
        try {
            val cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
            val sec = cal.get(Calendar.SECOND)
            val pYear = cal.get(Calendar.YEAR)
            val pMonth = cal.get(Calendar.MONTH)
            val pDay = cal.get(Calendar.DAY_OF_MONTH)
            updateTimer(hour, minute, pDay, pMonth, pYear, sec)

            j.put("latitude", searchPage.pickuplatlng.latitude)
            j.put("longitude", searchPage.pickuplatlng.longitude)
            j.put("pickupplace", if (searchPage.pickupLocTxt != NC.getString(R.string.fetching_address)) searchPage.pickupLocTxt else "")
            j.put("dropplace", if (searchPage.dropLocTxt != NC.getString(R.string.fetching_address)) searchPage.dropLocTxt else "")
            j.put("drop_latitude", searchPage.getDropLatLng().latitude)
            j.put("drop_longitude", searchPage.getDropLatLng().longitude)
            j.put("pickup_time", pickupTime)
            j.put("motor_model", /*carModelView.getSelectedCarModel()*/ selected_model_id)
            j.put("approx_distance", approximateDistance)
            j.put("approx_duration", approximateTime)
            j.put("cityname", if (defaultCityName.trim { it <= ' ' } == "") SessionSave.getSession(DEFAULT_CITY_NAME, requireActivity()) else defaultCityName.trim { it <= ' ' })
            j.put("distance_away", ETime)
            j.put("sub_logid", "")
            j.put("passenger_id", SessionSave.getSession(PASS_ID, requireActivity()))
            j.put("request_type", "1")
            j.put("promo_code", showPromo.getPromo())
            j.put("now_after", "0")
            j.put("notes",/* SessionSave.getSession("notes", requireActivity())*/et_notes.text.toString())
            j.put("service_id", serviceType)
            if (serviceType.equals("2")) {
                j.put("product_name", et_product_name.text.toString())
                j.put("product_weight", et_weight.text.toString())
                j.put("product_size", et_size.text.toString())
                j.put("delivery_person_name", et_name.text.toString())
                j.put("delivery_phone_number", et_phone.text.toString())
                j.put("delivery_date_time", et_date.text.toString())
                j.put("delivery_notes", et_notes.text.toString())
            }

            if (serviceType.equals("4") || serviceType.equals("5")) {
                j.put("rental_outstation", 2)
                j.put("rent_out_tour_id", "4")
                j.put("os_trip_type", os_type.toString())
                j.put("os_days_count", os_days.toString())
            }

            j.put("fav_driver_booking_type", bookFavDriver)
            j.put("friend_id2", friend1S)
            j.put("friend_percentage2", friend1SA)
            j.put("friend_id3", friend2S)
            j.put("friend_percentage3", friend2SA)
            j.put("friend_id4", friend3S)
            j.put("friend_percentage4", friend3SA)
            j.put("friend_id1", SessionSave.getSession(PASS_ID, requireActivity()))
            j.put("friend_percentage1", friendA)
            j.put("passenger_payment_option", intPaymentType)


            if (approximateFare != 0.0 && searchPage.getDropLatLng().latitude != 0.0 && searchPage.getDropLatLng().longitude != 0.0) {
                if (friend2SA != 0.0)
                    j.put("friend_percentage_amt3", friend2SA / 100.00 * approximateFare)
                if (friend1SA != 0.0)
                    j.put("friend_percentage_amt2", friend1SA / 100.00 * approximateFare)
                if (friend3SA != 0.0)
                    j.put("friend_percentage_amt4", friend3SA / 100.00 * approximateFare)

                j.put("friend_percentage_amt1", friendA / 100.00 * approximateFare)
                j.put("approx_trip_fare", approximateFare)
            } else {
                j.put("friend_percentage_amt1", 0)
                j.put("friend_percentage_amt2", 0)
                j.put("friend_percentage_amt3", 0)
                j.put("approx_trip_fare", 0)
            }

            j.put("travel_modelid", Integer.parseInt(if (travelModelId == "") "0" else travelModelId))
            j.put("booked_location", searchPage.pickupLocTxt)
            j.put("booked_latitude", lastKnownLatLng.latitude)
            j.put("booked_longitude", lastKnownLatLng.longitude)
            val curVersion = BuildConfig.VERSION_NAME

            j.put("passenger_app_version", curVersion)
            j.put("route_path", findETAFare.getOverviewPolyline())
            j.put("stops", if (searchPage.getStopPoints() == null) "" else JSONArray(Gson().toJson(searchPage.getStopPoints())))

            val array = JSONArray()
            for (i in 0 until preferencesDataList.selectedPreferencesData.size) {
                if (preferencesDataList.selectedPreferencesData[i].isSelected) {
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("preference_id", preferencesDataList.selectedPreferencesData[i].preference_id.toInt())
                        jsonObject.put("preference_name", preferencesDataList.selectedPreferencesData[i].preference_name)
                        jsonObject.put("preference_fare", preferencesDataList.selectedPreferencesData[i].preference_fare.toDouble())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    array.put(jsonObject)
                }

            }
            j.put("preferences", array)
            // j.put("preferences", JSONArray(Gson().toJson(preferencesDataList.selectedPreferencesData)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return j
    }

    private fun getPromoCodeObject(): JSONObject {
        val j = JSONObject()
        try {
            j.put("passenger_id", SessionSave.getSession(PASS_ID, requireActivity()))
            j.put("promo_code", showPromo.getPromo())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return j
    }

    /**
     * this method is used to set time
     *
     * @param day   set day of the month
     * @param hours set hour of the day
     * @param mins  set minutes of the hour
     * @param month set month of the year
     * @param sec   set seconds of the minute
     * @param year  set the selected year
     */

    private fun updateTimer(hours: Int, mins: Int, day: Int, month: Int, year: Int, sec: Int) {
        var hours = hours

        var timeSet = ""
        when {
            hours > 12 -> {
                hours -= 12
                timeSet = "PM"
            }
            hours == 0 -> {
                hours += 12
                timeSet = "AM"
            }
            hours == 12 -> timeSet = "PM"
            else -> timeSet = "AM"
        }
        var minutes = ""
        minutes = if (mins < 10) {
            "0$mins" // Append in a StringBuilder
        } else
            mins.toString()
        val aTime = StringBuilder().append(hours).append(':').append(minutes).append(':').append(sec).append(" ").append(timeSet).toString()
        pickupTime = aTime
        pickupTimeAndDate = "$day-$month-$year $aTime"
    }

    override fun onResume() {
        //To hide toolbar
        (requireActivity() as MainHomeFragmentActivity).tool_bar_lay.visibility = View.GONE
        (requireActivity() as MainHomeFragmentActivity).homePage_title()
        (requireActivity() as MainHomeFragmentActivity).toolbarRightIcon(false)
        (requireActivity() as MainHomeFragmentActivity).enableSlide()
//        if (viewModel != null) {
//            viewModel.getFavouritePlaces()
//            viewModel.carLayClick.value = true
//        }

        txtRequestTaxi.setOnClickListener {

            bookingType = "now"
            if (availablecarcount > 0) {
                if (bookingState == BOOKINGSTATE.STATE_TWO) {
                    if (approximateDistance != null) {
                        if (approximateDistance > SessionSave.getSession(TaxiUtil.KM_RESTRICT, requireActivity()).toDouble()) {
                            if (SessionSave.getSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, requireActivity(), false)) {
                                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                        NC.getString(R.string.restrict_alert), NC.getString(R.string.continueStr),
                                        NC.getString(R.string.change_drop), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "REQUEST")
                            } else {
                                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                        NC.getString(R.string.restrict_alert), "",
                                        NC.getString(R.string.change_drop), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "REQUEST")
                            }
                        } else {
                            callBookNow()
                        }
                    } else {
                        callBookNow()
                    }
                }
            }
        }

        tv_confirm.setOnClickListener {
            if (serviceType.equals("4", true) || serviceType.equals("5", true)) {
                textBookLater.performClick()
                return@setOnClickListener
            } else if (serviceType.equals("2", true)) {
                if (et_product_name.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Product name must not be empty")
                    return@setOnClickListener
                } else if (et_weight.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Product weight must not be empty")
                    return@setOnClickListener
                } else if (et_size.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Product size must not be empty")
                    return@setOnClickListener
                } else if (et_name.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Name must not be empty")
                    return@setOnClickListener
                } else if (et_phone.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Phone number must not be empty")
                    return@setOnClickListener
                } else if (et_date.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Date and Time must not be empty")
                    return@setOnClickListener

                }
            }
            bookingType = "now"
            if (availablecarcount > 0) {
                if (bookingState == BOOKINGSTATE.STATE_TWO) {
                    if (approximateDistance != null) {
                        println("KM_RESTRICT ${SessionSave.getSession(TaxiUtil.KM_RESTRICT, requireActivity()).toDouble()}")
                        if (approximateDistance > SessionSave.getSession(TaxiUtil.KM_RESTRICT, requireActivity()).toDouble()) {
                            if (SessionSave.getSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, requireActivity(), false)) {
                                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                        NC.getString(R.string.restrict_alert), NC.getString(R.string.continueStr),
                                        NC.getString(R.string.change_drop), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "REQUEST")
                            } else {
                                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                        NC.getString(R.string.restrict_alert), "",
                                        NC.getString(R.string.change_drop), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "REQUEST")
                            }
                        } else {
                            callBookNow()
                        }
                    } else {
                        callBookNow()
                    }
                }
            } else {
                CToast.ShowToast(requireActivity(), NC.getString(R.string.car_not_available))
            }
        }

        promoCodeLay.setOnClickListener {
            showPromo.promoCode(requireActivity())
        }

        textBookLater.setOnClickListener {
            if (serviceType.equals("2", true)) {
                if (et_product_name.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Product name must not be empty")
                    return@setOnClickListener
                } else if (et_weight.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Product weight must not be empty")
                    return@setOnClickListener
                } else if (et_size.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Product size must not be empty")
                    return@setOnClickListener
                } else if (et_name.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Name must not be empty")
                    return@setOnClickListener
                } else if (et_phone.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Phone number must not be empty")
                    return@setOnClickListener
                } else if (et_date.text.toString().trim().isEmpty()) {
                    CToast.ShowToast(requireActivity(), "Date and Time must not be empty")
                    return@setOnClickListener

                }
            }
            if (SessionSave.getSession(TaxiUtil.CORPORATE_COMPANY_BLOCK, requireActivity()) == "1") {
                Utility.alert_view_dialog(requireActivity(),
                        "",
                        NC.getString(R.string.block_company),
                        "" + NC.getString(R.string.ok), "", true,
                        { dialog, which ->
                            dialog.dismiss()
                        }, null, "")
            } else {
                bookingType = "after"
                if (bookingState == BOOKINGSTATE.STATE_TWO) {
                    if (approximateDistance != null) {
                        if (approximateDistance > SessionSave.getSession(TaxiUtil.KM_RESTRICT, requireActivity()).toDouble()) {
                            if (SessionSave.getSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, requireActivity(), false)) {
                                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                        NC.getString(R.string.restrict_alert), NC.getString(R.string.continueStr),
                                        NC.getString(R.string.change_drop), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "REQUEST")
                            } else {
                                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                                        NC.getString(R.string.restrict_alert), "",
                                        NC.getString(R.string.change_drop), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "REQUEST")
                            }
                        } else {
                            bookLaterFun()
                        }
                    }
                }
            }
        }


        naviIcon.setOnClickListener {
            if (bookingState == BOOKINGSTATE.STATE_ONE)
                (requireActivity() as MainHomeFragmentActivity).left_icon.performClick()
            else
                onBackPress()
        }

        super.onResume()

    }

    override fun onPause() {
        if (splitFareDialog != null)
            if (splitFareDialog!!.isVisible)
                splitFareDialog!!.dismiss()
        super.onPause()
    }

    override fun pickUpSet(latitude: Double, longtitue: Double, address: String) {
        if (latitude != 0.0 && address != NC.getString(R.string.fetching_address)) {
            moveTothisLocation(LatLng(latitude, longtitue), address == "", address)
            updatedPickupLatLng(LatLng(latitude, longtitue), address)
        }
    }

    override fun pickupListener() {
        if (ll_choose_service.visibility == View.VISIBLE) {
            locImg.visibility = View.GONE
        } else {
            locImg.visibility = View.VISIBLE
        }
        Glide.with(this).load(R.drawable.flag_green).into(locationImg)
        if (searchPage.pickupLocTxt.isNotEmpty())
            searchPage.setPickLatLng(searchPage.pickuplatlng)
        moveTothisLocation(searchPage.pickuplatlng, false, "")
    }

    override fun kmRestrictListener() {
        isNeedToDrawRoute = true
        mMap?.let {
            it.clear()
            PickDropMarker.setPickMarker(it, searchPage.pickuplatlng)
            PickDropMarker.setDropMarker(it, searchPage.getDropLatLng())
            PickDropMarker.moveCamera(requireActivity(), it, bottomViewLay)
            if (bookingState == BOOKINGSTATE.STATE_TWO) {
                PickDropMarker.setPickMarkerWithCustomView(requireActivity(), mMap!!, searchPage.pickupLocTxt, searchPage.pickuplatlng, ETime)
            }
            if (bookingState == BOOKINGSTATE.STATE_TWO) {
                PickDropMarker.setDropMarkerWithCustomView(requireActivity(), mMap!!, searchPage.dropLocTxt, searchPage.getDropLatLng())
            }
        }
        drawRoute()
    }

    override fun dropSet(latitude: Double, longtitue: Double, address: String, focus: String) {
        if (focus == DropPlace) {
            locImg.visibility = View.GONE
            Glide.with(this).load(R.drawable.flag_red).into(locationImg)
        }
        if (latitude != 0.0 && address != NC.getString(R.string.fetching_address)) {
            moveTothisLocation(LatLng(latitude, longtitue), address == "", address)
        }
        if (isDropSetFirstTime) {
            isDropSetFirstTime = false
            viewModel.skipDropLocClick(1)
        }
    }

    override fun dropListener() {
        locImg.visibility = View.GONE
        Glide.with(this).load(R.drawable.flag_red).into(locationImg)
        if (searchPage.dropLocTxt.isNotEmpty())
            searchPage.setDropLatLng(searchPage.droplatlng)
        moveTothisLocation(searchPage.getDropLatLng(), false, "")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val message = handler.obtainMessage(0, "")
        message.sendToTarget()
        closeDialog()
    }

    override fun onSplitSuccess(primary_Percent: Double, f1: Double, f2: Double, f3: Double, fa1: Double, fa2: Double, fa3: Double) {
        friendA = primary_Percent
        friend1S = f1
        friend1SA = fa1
        friend2S = f2
        friend2SA = fa2
        friend3S = f3
        friend3SA = fa3
        if (favDriverAvailable > 0 && SessionSave.getSession(TaxiUtil.isFavDriverOn, requireActivity(), true)) {
            alertDialog = Dialog_Common().setmCustomDialogs(requireActivity(), this, NC.getString(R.string.message), favDriverMessage,
                    NC.getString(R.string.ok),
                    NC.getString(R.string.no_thanks), "1")
        } else {
            getLastKnownLatlngForNearestApiCall(BOOK_NOW)
        }
    }


    override fun onSuccess(dialog: Dialog?, resultcode: String?) {
        if (resultcode == "1") {
            bookFavDriver = 1
            getLastKnownLatlngForNearestApiCall(BOOK_NOW)
        } else if (resultcode == "4") {
            bookFavDriver = 2
            getLastKnownLatlngForNearestApiCall(BOOK_NOW)
        }
        dialog?.dismiss()
    }

    override fun onFailure(dialog: Dialog?, resultcode: String?) {
        if (resultcode == "1") {
            bookFavDriver = 2
            getLastKnownLatlngForNearestApiCall(BOOK_NOW)
        }
        dialog?.dismiss()
    }

    override fun onPackageFailureClick() {
        searchPage.dropClicked("1")
    }

    override fun onPromoApply() {
        showDialog()
        viewModel.callCheckPromoCode(getPromoCodeObject())
    }

    override fun setDraggedAddress(latitude: Double, longitude: Double, address: String) {
        searchPage.setFavouriteUnselectIcon()
        searchPage.setPickupDropData(1, latitude, longitude, address, "", "", "")
    }

    override fun mapGpsInitialized(map: GoogleMap, currLatLng: LatLng) {
        println("mapGpsInitialized")
        this.mMap = map

        // mMap!!.setIndoorEnabled(true)
        Glide.with(this).load(R.drawable.flag_green).into(locationImg)
        if (mMap == null)
            locationLay.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white))
        if (currLatLng.latitude != 0.0) {
            searchPage.setPickLatLng(currLatLng)
            // getLastKnownLatlngForNearestApiCall(NEAREST_API)
        }
        setServiceModelArray(SessionSave.getSession(SERVICE_DETAILS, requireActivity()))
    }

    override fun openPackage() {
        showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                NC.getString(R.string.continue_package), NC.getString(R.string.ok),
                NC.getString(R.string.cancel), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "")
    }

    override fun getNearestDriver(model_id: Int, model_size: String) {
        selected_model_id = model_id
        selected_model_size = model_size
        getLastKnownLatlngForNearestApiCall(NEAREST_API)
        isNeedToDrawRoute = false
        drawRoute()

    }


    override fun trigger_FragPopFront() {
        (requireActivity() as MainHomeFragmentActivity).showDarkStatusBarIcon()
        if (requireActivity().supportFragmentManager.findFragmentById(R.id.mainFrag) is BookTaxiHomePage) {
            (requireActivity() as MainHomeFragmentActivity).homePage_title()
            (requireActivity() as MainHomeFragmentActivity).toolbarRightIcon(false)
            (requireActivity() as MainHomeFragmentActivity).left_icon.setImageResource(R.drawable.ic_menu)
            (requireActivity() as MainHomeFragmentActivity).left_icon.tag = "menu"
            (requireActivity() as MainHomeFragmentActivity).enableSlide()
        }

        driverLiveMovement.removeStartDriverLiveMovement()

        viewModel.carLayClick.value = true
    }


    internal var listener: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val internetAvail: Boolean = intent.getBooleanExtra(TaxiUtil.INTERNET_AVAIL, true)
            if (!internetAvail) {
                if (NetworkStatus.appContext != null && NetworkStatus.appContext is AppCompatActivity && !(NetworkStatus.appContext as AppCompatActivity).isFinishing) {
                    if (NetworkStatus.errorDialog != null && NetworkStatus.errorDialog.isShowing) {
                        NetworkStatus.errorDialog.dismiss()
                    }
                    Utility.alert_view_dialog(requireActivity(),
                            "",
                            NC.getString(R.string.you_are_offline),
                            "" + NC.getString(R.string.ok), "", true,
                            { dialog, which ->
                                startSOSService(context)
                                dialog.dismiss()
                            }, null, "")
                }
            }
        }
    }

    private fun startSOSService(context: Context) {
        if (SessionSave.getSession("trip_id", context) == "" && SessionSave.getSession(PASS_ID, context) != "") {
            SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, context), context)
            SessionSave.saveSession("user_type", "p", context)
            context.startService(Intent(context, SOSService::class.java))
        }
    }

    private fun forceLogout() {
        TaxiUtil.API_BASE_URL = ""
        SessionSave.saveSession("base_url", "", requireActivity())
        SessionSave.saveSession(PASS_ID, "", requireActivity())
        SessionSave.clearAllSession(requireActivity())
        requireActivity().stopService(Intent(requireActivity(), GetPassengerUpdate::class.java))
        requireActivity().startActivity(Intent(requireActivity(), SplashActivity::class.java))
    }

    /**
     * @param data list of marker to update
     */
    private fun removeMarker(data: ArrayList<Marker>?) {
        if (data != null) {
            for (marker in data) {
                driverLiveMovement.removeMarkerWithAnimation(marker)
            }
            driverMarkerService.removeAll(data)
        }
    }

    fun onBackPress() {
        if (bookingState == BOOKINGSTATE.STATE_ONE) {
            if (doubleBackToExitPressedOnce) {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return
            } else {
                doubleBackToExitPressedOnce = true
                //CToast.ShowToast(requireActivity(), NC.getString(R.string.pressBack))
                Handler().postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000)
                ll_choose_service.visibility = View.VISIBLE
                locImg.visibility = View.GONE
                book_taxi_main_frag.visibility = View.GONE
                txt_skip_drop_lay.visibility = View.GONE
            }
        }
        if (bookingState == BOOKINGSTATE.STATE_TWO) {
            // mapPageLay.layoutParams.height = displayHeight+100
            chooseServiceDialog();


        }
        searchPage.setPickupCardElevation()
        searchPage.setDropCardElevation()
        searchPage.locVisibility()
        carModelView.visibility = View.GONE
        skipDropLoc.visibility = View.VISIBLE
        botFavLay.visibility = View.GONE
        binding.root.request_lay.visibility = View.GONE
        ivLine.visibility = View.GONE
        locationImg.visibility = View.VISIBLE
        mMap!!.clear()
        bookingState = BOOKINGSTATE.STATE_ONE
        cameraChangeListeners(true)
        naviIcon.visibility = View.VISIBLE
        naviIcon.setImageResource(R.drawable.ic_menu)
        viewModel.carLayClick.value = true
        instructionHeader.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        driverLiveMovement.removeDriverLiveMovementCallback()
        driverLiveMovement.removeDriverLiveMovement()
    }

    override fun onDestroy() {
        super.onDestroy()
        IS_HOME_PAGE = false
        driverLiveMovement.removeDriverLiveMovementCallback()
        driverLiveMovement.removeDriverLiveMovement()
    }


    override fun drawRoutePickToDrop(time: Double?, dist: Double?, approxFare: Double) {
        if (dist != null)
            approximateDistance = dist
        if (time != null) {
            approximateTime = time
        }
        if (approxFare != 0.0) {
            approximateFare = approxFare
            carModelView.setApproximateFare(approximateFare)
        } else {
            approximateFare = approxFare
            carModelView.setApproximateFare(approximateFare)
        }
        carModelView.setApproximateTime(approximateTime)
        if (mAdapter != null) {
            if (zoneTozone_applicable)
                approximateFare = zoneTozone_fare
            if (model_list != null && !model_list.isComputingLayout)
                mAdapter!!.updateFare(approximateFare, approximateTime)
        }
    }

    override fun getETADiverToPickup(time: Double?, dist: Double?) {
        try {
            if (time != null) {
                ETime = if (time <= 1)
                    1.0
                else
                    NumberFormat.convertDouble(String.format(Locale.UK, time.toInt().toString()).toDouble()).toDouble()
                if (bookingState == BOOKINGSTATE.STATE_TWO) {
                    PickDropMarker.setPickMarkerWithCustomView(requireActivity(), mMap!!, searchPage.pickupLocTxt, searchPage.pickuplatlng, ETime)
                }
            }
//            carModelView.setApproximateTime(ETime)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }


    private fun chooseServiceDialog() {
        try {
            if (choose_service != null && choose_service!!.isShowing) {
                choose_service!!.dismiss()
            }
            if (serviceType.equals("4") || serviceType.equals("5")) {
                os_days = 0
                os_type = 1
            }
            modelArray = java.util.ArrayList()
            val modelJson = JSONArray(SessionSave.getSession(MODEL_DETAILS, requireActivity()))
            for (n in 0 until modelJson.length()) {
                var modelSize = ""
                if (modelJson.getJSONObject(n).has("model_size")) {
                    modelSize = modelJson.getJSONObject(n).getString("model_size")
                }
                var modelId = ""
                if (modelJson.getJSONObject(n).has("model_id")) {
                    modelId = modelJson.getJSONObject(n).getString("model_id")
                    if (selected_model_id == 0) {
                        selected_model_id = modelId.toInt();
                        selected_model_size = modelSize;
                    }
                }
                if (SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, context) == "1") {
                    if (modelId == "-1") {

                    } else {
                        modelArray.add(
                                ModelData(
                                        modelJson.getJSONObject(n).getString("model_id"),
                                        modelJson.getJSONObject(n).getString("model_name"),
                                        modelJson.getJSONObject(n).getString("unfocus_image"),
                                        modelJson.getJSONObject(n).getString("focus_image"),
                                        modelSize
                                )
                        )
                    }
                } else {
                    modelArray.add(
                            ModelData(
                                    modelJson.getJSONObject(n).getString("model_id"),
                                    modelJson.getJSONObject(n).getString("model_name"),
                                    modelJson.getJSONObject(n).getString("unfocus_image"),
                                    modelJson.getJSONObject(n).getString("focus_image"),
                                    modelSize
                            )
                    )
                }
            }
            val view = View.inflate(requireActivity(), R.layout.choose_service_dialog, null)
            choose_service = Dialog(requireActivity(), R.style.dialogAnimation).also {
                it.setContentView(view)
                it.setCancelable(true)
                it.window?.setBackgroundDrawableResource(R.color.semi_transparent)
                it.show()
                val img_close = it.findViewById<ImageView>(R.id.img_close)
                val request_taxi = it.findViewById<Button>(R.id.request_taxi)
                img_close.setOnClickListener {
                    choose_service!!.dismiss()
                }
                model_list = it.findViewById<RecyclerView>(R.id.model_list)
                val ss = LinearLayoutManager(requireActivity())
                ss.orientation = LinearLayoutManager.VERTICAL
                model_list.layoutManager = ss
                mAdapter = ModelListAdapter(requireActivity(), modelArray, this, selected_model_id)
                model_list.adapter = mAdapter

                request_taxi.setOnClickListener {
                    //choose_service!!.dismiss()
                    getModelPreference();

                }


            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    /**
     * Book later dialog popup
     */
    private fun bookLaterFun() {
        try {
            if (dtMDialog != null && dtMDialog!!.isShowing)
                dtMDialog!!.cancel()
            pickupTime = ""
            val rView = View.inflate(requireActivity(), R.layout.date_time_picker_dialog, null)
            Colorchange.ChangeColor(rView as ViewGroup, requireActivity())
            dtMDialog = Dialog(requireActivity(), R.style.dialogwinddow).also {
                it.setContentView(rView)
                it.setCancelable(true)
                it.show()

                val datePicker = it.findViewById<DatePicker>(R.id.datePicker1)
                val timePicker = it.findViewById<TimePicker>(R.id.timePicker1)

                val c = Calendar.getInstance()
                timePicker.currentHour = c.get(Calendar.HOUR_OF_DAY) + 1
                timePicker.currentMinute = c.get(Calendar.MINUTE) + 1
/*
                val timeManager = TimeManager.getInstance()
                // Use 24-hour time
                timeManager.setTimeFormat(TimeManager.FORMAT_24)

                // Set time zone to Eastern Standard Time
                timeManager.setTimeZone("America/New_York")

                // Set clock time to noon
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.HOUR_OF_DAY, 12)
                val timeStamp = calendar.timeInMillis
                timeManager.setTime(timeStamp)*/
                timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                    val c = Calendar.getInstance()
                    if (datePicker.dayOfMonth == c.get(Calendar.DAY_OF_MONTH)) {
                        if (c.get(Calendar.HOUR_OF_DAY) + 1 > hourOfDay) {
                            timePicker.currentHour = c.get(Calendar.HOUR_OF_DAY) + 1
                            timePicker.currentMinute = c.get(Calendar.MINUTE)
                        }
                        if (c.get(Calendar.HOUR_OF_DAY) + 1 >= hourOfDay && c.get(Calendar.MINUTE) > minute) {
                            timePicker.currentHour = c.get(Calendar.HOUR_OF_DAY) + 1
                            timePicker.currentMinute = c.get(Calendar.MINUTE)
                        }
                    }
                }
                val now = Time()
                now.setToNow()
                datePicker.updateDate(now.year, now.month, now.monthDay)
                datePicker.minDate = c.timeInMillis - 1000
                val butConfirmTime = it.findViewById<Button>(R.id.butConfirmTime)
                val fTextview = it.findViewById<TextView>(R.id.f_textview)
                if (SessionSave.getSession(LANG, requireActivity()) == "ar" || SessionSave.getSession(LANG, requireActivity()) == "fa") {
                    fTextview.text = NC.getString(R.string.select_date_arab)
                    butConfirmTime.text = NC.getString(R.string.submit_arab)
                }
                if (datePicker.visibility == View.VISIBLE) {
                    butConfirmTime.text = NC.getString(R.string.set_date)
                }
                butConfirmTime.setOnClickListener {
                    if (datePicker.isShown) {
                        timePicker.visibility = View.VISIBLE
                        datePicker.visibility = View.GONE
                        if (timePicker.visibility == View.VISIBLE) {
                            butConfirmTime.text = NC.getString(R.string.set_time)
                        }
                    }
                }
                butConfirmTime.setOnClickListener { btnConfirm ->
                    if (datePicker.isShown) {
                        timePicker.visibility = View.VISIBLE
                        datePicker.visibility = View.GONE
                        if (timePicker.visibility == View.VISIBLE) {
                            butConfirmTime.text = NC.getString(R.string.set_time)
                        }
                    } else {
                        getCurrentDateAndTime(timePicker, datePicker)
                        val selectedString = "" + year + "/" + month + "/" + date + " " + timePicker.currentHour + ":" + timePicker.currentMinute
                        if (hoursAgo(selectedString) > 0) {
                            pickupTimeAndDate = "$year-$month-$date $hour:$min:00 $ampm"
                            if (!searchPage.pickupLocTxt.equals("" + NC.getString(R.string.fetching_address), ignoreCase = true))
                                getLastKnownLatlngForNearestApiCall(BOOK_LATER)
                            else
                                CToast.ShowToast(requireActivity(), NC.getString(R.string.need_pickup_address))
                        } else {
                            alertView(requireActivity(), "" + NC.getString(R.string.message), "" + NC.getString(R.string.hour_must_greater_than), "" + NC.getString(R.string.ok), "")
                        }
                        it.dismiss()
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
/*
        dtMDialog!!.setOnDismissListener {
                (requireActivity() as MainHomeFragmentActivity).setLocale()
        }*/
    }


    fun deliveryDateFun() {
        try {
            if (dtMDialog != null && dtMDialog!!.isShowing)
                dtMDialog!!.cancel()
            val rView = View.inflate(requireActivity(), R.layout.date_time_picker_dialog, null)
            //Colorchange.ChangeColor(rView as ViewGroup, requireActivity())
            dtMDialog = Dialog(requireActivity(), R.style.dialogwinddow).also {
                it.setContentView(rView)
                it.setCancelable(true)
                it.show()

                val datePicker = it.findViewById<DatePicker>(R.id.datePicker1)
                val timePicker = it.findViewById<TimePicker>(R.id.timePicker1)

                val c = Calendar.getInstance()
                timePicker.currentHour = c.get(Calendar.HOUR_OF_DAY)
                timePicker.currentMinute = c.get(Calendar.MINUTE) + 1

                val now = Time()
                now.setToNow()
                datePicker.updateDate(now.year, now.month, now.monthDay)
                datePicker.minDate = c.timeInMillis - 1000
                val butConfirmTime = it.findViewById<Button>(R.id.butConfirmTime)
                val fTextview = it.findViewById<TextView>(R.id.f_textview)
                if (datePicker.visibility == View.VISIBLE) {
                    butConfirmTime.text = NC.getString(R.string.setDate)
                }
                butConfirmTime.setOnClickListener {
                    if (datePicker.isShown) {
                        timePicker.visibility = View.VISIBLE
                        datePicker.visibility = View.GONE
                        if (timePicker.visibility == View.VISIBLE) {
                            butConfirmTime.text = NC.getString(R.string.setTime)
                        }
                    }
                }
                butConfirmTime.setOnClickListener { btnConfirm ->
                    if (datePicker.isShown) {
                        timePicker.visibility = View.VISIBLE
                        datePicker.visibility = View.GONE
                        if (timePicker.visibility == View.VISIBLE) {
                            butConfirmTime.text = NC.getString(R.string.setTime)
                        }
                    } else {
                        getCurrentDateAndTime(timePicker, datePicker)
                        val selectedString = "" + year + "/" + month + "/" + date + " " + timePicker.currentHour + ":" + timePicker.currentMinute
                        val currentDateAndTime = "" + "$year-$month-$date $hour:$min:00 $ampm"
                        et_date.setText(currentDateAndTime)

                        it.dismiss()
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /*
     * this method is used to call the api
     * */
    private fun getSaveBookingObjectBookLater(lastKnownLatLng: LatLng) {
        val j = JSONObject()
        try {
            val view = View.inflate(requireActivity(), R.layout.netcon_lay, null)
            Colorchange.ChangeColor(view as ViewGroup, requireActivity())
            mcDialog = Dialog(requireActivity(), R.style.dialogwinddow).also {
                it.setContentView(view)
                it.setCancelable(false)
                it.show()
                FontHelper.applyFont(requireActivity(), it.findViewById(R.id.alert_id))
                val titleText = it.findViewById<TextView>(R.id.title_text)
                val messageText = it.findViewById<TextView>(R.id.message_text)
                val buttonSuccess = it.findViewById<Button>(R.id.button_success)
                val buttonFailure = it.findViewById<Button>(R.id.button_failure)
                titleText.text = NC.getString(R.string.message)
                messageText.text = NC.getString(R.string.confirm_booking)
                buttonSuccess.text = NC.getString(R.string.ok)
                buttonFailure.text = NC.getString(R.string.cancel)
                buttonSuccess.setOnClickListener { btnSuccess ->
                    try {
                        it.dismiss()
                        if (searchPage.pickupLocTxt.trim { it <= ' ' }.isEmpty()) {
                            CToast.ShowToast(requireActivity(), "" + NC.getString(R.string.select_the_pickup_location))
                        } else {
                            showDialog()
                            j.put("latitude", searchPage.pickuplatlng.latitude)
                            j.put("longitude", searchPage.pickuplatlng.longitude)
                            j.put("pickupplace", if (searchPage.pickupLocTxt != null) searchPage.pickupLocTxt else "")
                            j.put("dropplace", if (searchPage.dropLocTxt != null) searchPage.dropLocTxt else "")
                            j.put("drop_latitude", searchPage.getDropLatLng().latitude)
                            j.put("drop_longitude", searchPage.getDropLatLng().longitude)
                            j.put("pickup_time", pickupTimeAndDate)
                            j.put("motor_model",/* carModelView.getSelectedCarModel()*/selected_model_id)
                            j.put("cityname", if (defaultCityName.trim { it <= ' ' } == "") SessionSave.getSession(DEFAULT_CITY_NAME, requireActivity()) else defaultCityName.trim { it <= ' ' })
                            j.put("distance_away", ETime)
                            j.put("sub_logid", "")
                            j.put("sub_logid", "")
                            j.put("friend_id2", "0")
                            j.put("friend_percentage2", "0")
                            j.put("friend_id3", "0")
                            j.put("friend_percentage3", "0")
                            j.put("friend_id4", "0")
                            j.put("friend_percentage4", "0")
                            j.put("friend_id1", SessionSave.getSession(PASS_ID, requireActivity()))
                            j.put("friend_percentage1", "100")
                            j.put("approx_distance", approximateDistance)
                            j.put("approx_duration", approximateTime)
                            j.put("passenger_id", SessionSave.getSession(PASS_ID, requireActivity()))
                            j.put("request_type", "1")
                            j.put("promo_code", showPromo.getPromo())
                            j.put("now_after", "1")
                            //j.put("notes", SessionSave.getSession("notes", requireActivity()))
                            j.put("passenger_app_version", MainActivity.APP_VERSION)
                            j.put("travel_modelid", Integer.parseInt(if (travelModelId == "") "0" else travelModelId))
                            j.put("booked_location", searchPage.pickupLocTxt)
                            j.put("booked_latitude", lastKnownLatLng.latitude)
                            j.put("booked_longitude", lastKnownLatLng.longitude)


                            j.put("notes",/* SessionSave.getSession("notes", requireActivity())*/et_notes.text.toString())
                            j.put("service_id", serviceType)
                            if (serviceType.equals("2")) {
                                j.put("product_name", et_product_name.text.toString())
                                j.put("product_weight", et_weight.text.toString())
                                j.put("product_size", et_size.text.toString())
                                j.put("delivery_person_name", et_name.text.toString())
                                j.put("delivery_phone_number", et_phone.text.toString())
                                j.put("delivery_date_time", et_date.text.toString())
                                j.put("delivery_notes", et_notes.text.toString())
                            }

                            if (serviceType.equals("4") || serviceType.equals("5")) {
                                j.put("rental_outstation", 2)
                                j.put("rent_out_tour_id", "4")
                                j.put("os_trip_type", os_type.toString())
                                j.put("os_days_count", os_days.toString())
                            }

                            if (SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, requireActivity()) == "1") {
                                j.put("corporate_company_id", SessionSave.getSession(TaxiUtil.CORPORATE_COMPANY_ID, requireActivity()))
                            }

                            if (approximateFare != 0.0 && searchPage.getDropLatLng().latitude != 0.0 && searchPage.getDropLatLng().longitude != 0.0) {
                                if (friend2SA != 0.0)
                                    j.put("friend_percentage_amt3", friend2SA / 100.00 * approximateFare)
                                if (friend1SA != 0.0)
                                    j.put("friend_percentage_amt2", friend1SA / 100.00 * approximateFare)
                                if (friend3SA != 0.0)
                                    j.put("friend_percentage_amt4", friend3SA / 100.00 * approximateFare)

                                j.put("friend_percentage_amt1", friendA / 100.00 * approximateFare)
                                j.put("approx_trip_fare", approximateFare)
                            } else {
                                j.put("friend_percentage_amt1", 0)
                                j.put("friend_percentage_amt2", 0)
                                j.put("friend_percentage_amt3", 0)
                                j.put("approx_trip_fare", 0)
                            }
                            j.put("route_path", findETAFare.getOverviewPolyline())
                            val arr = JSONArray()
                            for (i in 0 until searchPage.getStopPoints().size) {
                                val eachData = JSONObject()
                                try {
                                    eachData.put("id", searchPage.getStopPoints()[i].id)
                                    eachData.put("lat", searchPage.getStopPoints()[i].lat)
                                    eachData.put("lng", searchPage.getStopPoints()[i].lng)
                                    eachData.put("placeName", searchPage.getStopPoints()[i].placeName)
                                    eachData.put("placeId", searchPage.getStopPoints()[i].placeId)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                                arr.put(eachData)
                            }
                            j.put("stops", if (searchPage.getStopPoints() == null) "" else arr)


                            val array = JSONArray()
                            for (i in 0 until preferencesDataList.selectedPreferencesData.size) {
                                if (preferencesDataList.selectedPreferencesData[i].isSelected) {
                                    val jsonObject = JSONObject()
                                    try {
                                        jsonObject.put("preference_id", preferencesDataList.selectedPreferencesData[i].preference_id.toInt())
                                        jsonObject.put("preference_name", preferencesDataList.selectedPreferencesData[i].preference_name)
                                        jsonObject.put("preference_fare", preferencesDataList.selectedPreferencesData[i].preference_fare.toDouble())
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }

                                    array.put(jsonObject)
                                }

                            }
                            j.put("preferences", array)
                            // j.put("preferences", JSONArray(Gson().toJson(preferencesDataList.selectedPreferencesData)))
                            viewModel.callSaveBookingApi(j)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                buttonFailure.setOnClickListener { btnFailure ->
                    it.dismiss()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 200 && data != null) {
                alertBundle = data.extras
                alertBundle?.let {
                    alertMsg = it.getString("alert_message", "")
                    if (alertMsg.isNotEmpty()) {
                        alertView(requireActivity(), "" + NC.getString(R.string.message), "" + alertMsg, "" + NC.getString(R.string.ok), "")
                        alertMsg = ""
                    }
                }
            }
        }
    }

    /**
     * Method to get date and time from date picker dialog
     *
     * @param _timePicker - timePikcer object to get selected time
     * @param _datePicker - datePicker object to get selected date
     */
    private fun getCurrentDateAndTime(_timePicker: TimePicker, _datePicker: DatePicker) {
        hour = _timePicker.currentHour
        min = _timePicker.currentMinute
        date = _datePicker.dayOfMonth
        month = _datePicker.month + 1
        year = _datePicker.year
        ampmValidation(hour)
    }


    /**
     * this method is used to check the am & pm for given input time
     *
     * @param inputHour hour is given as input
     */
    private fun ampmValidation(inputHour: Int): String {

        when {
            inputHour >= 13 -> {
                hour = inputHour - 12
                ampm = "PM"
            }
            inputHour == 12 -> ampm = "PM"
            inputHour == 0 -> {
                hour = 12
                ampm = "AM"
            }
            else -> ampm = "AM"
        }
        return ampm
    }


    /**
     * Method used to calculate difference between dates
     *
     * @param datetime - Date with time to find the difference from current time
     * @return - Difference of time between that give date and time
     */
    private fun hoursAgo(datetime: String): Int {
        var date: Date? = null
        try {
            date = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(datetime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val now = Calendar.getInstance().time // Get time now
        val differenceInMillis = date!!.time - now.time
        val differenceInHours = (differenceInMillis) / 1000L / 60L / 60L
        return differenceInHours.toInt()
    }

    private fun showDialog() {
        try {
            if (NetworkStatus.isOnline(requireActivity())) {
                if (loadingDialog != null && loadingDialog!!.isShowing)
                    loadingDialog!!.dismiss()
                val view = View.inflate(requireActivity(), R.layout.progress_bar, null)
                loadingDialog = Dialog(requireActivity(), R.style.dialogwinddow).also {
                    it.setContentView(view)
                    it.setCancelable(false)
                    it.show()
                    val iv = it.findViewById<ImageView>(R.id.giff)
                    val imageViewTarget = DrawableImageViewTarget(iv)
                    Glide.with(this)
                            .load(R.raw.loading_anim)
                            .into(imageViewTarget)
                }
            } else {
                CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //method to close dialog
    private fun closeDialog() {
        try {
            if (loadingDialog != null)
                if (loadingDialog!!.isShowing)
                    loadingDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun bottomSheet(it: Int, cardTxt: TextView) {
        if (bottomSheetFragment != null && bottomSheetFragment!!.isAdded) {
            bottomSheetFragment!!.childFragmentManager.popBackStack()
        }
        bottomSheetFragment = PayModeSelection { promocode: String, paymentType: String ->
            updatePaymentMode(paymentType, promocode, cardTxt)
        }
        bottomSheetFragment?.show(childFragmentManager, bottomSheetFragment?.tag)

    }

    private fun updatePaymentMode(type: String, promocode: String, cardTxt: TextView) {
        if (type == "1") {
            intPaymentType = 1
            viewModel.setPaymentType(NC.getString(R.string.payment_cash))
            cardTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0)
        } else if (type == "2") {
            intPaymentType = 2
            viewModel.setPaymentType(NC.getString(R.string.pay_online))
            cardTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash_b, 0, 0, 0)
        }
        if (promocode != "") {
            showPromo.updatePromo(promocode)
            showDialog()
            viewModel.callCheckPromoCode(getPromoCodeObject())
        }
    }

    fun callGetPassengerInfoApi() {
        var j = JSONObject()
        j.put("passenger_id", SessionSave.getSession(PASS_ID, activity))
        j.put("latitude", searchPage.pickuplatlng.latitude)
        j.put("longitude", searchPage.pickuplatlng.longitude)
        viewModel.callGetPassengerInfoApi(j)
    }

    override fun getModelDetails(id: String?) {
        serviceType = id
        if (NetworkStatus.isOnline(requireActivity())) {
            if (id.equals("4")) {
                showPackage.alertPackage(requireActivity(), NC.getString(R.string.message),
                        NC.getString(R.string.continue_package), NC.getString(R.string.ok),
                        NC.getString(R.string.cancel), searchPage.pickuplatlng, searchPage.getDropLatLng(),
                        searchPage.pickupLocTxt, searchPage.dropLocTxt, driverLiveMovement, "")
            } else {

                showDialog()
                val j = JSONObject()
                j.put("service_type", id?.toInt())
                viewModel.callModelDetailsApi(j)


                if (id?.toInt() == 2) {
                    skipDropLoc.setText(NC.getString(R.string.book_delivery))
                } else {
                    skipDropLoc.setText(NC.getString(R.string.book_ride))
                    println("book_ride text set")
                }
            }
        } else {
            CToast.ShowToast(requireActivity(), NC.getString(R.string.check_internet_connection))
        }

    }

    fun getModelPreference() {
        if (NetworkStatus.isOnline(requireActivity())) {
            showDialog()
            val j = JSONObject()
            j.put("service_type", serviceType?.toInt())
            j.put("model_id", selected_model_id)
            viewModel.callModelPrefrenceApi(j)
        } else {
            CToast.ShowToast(requireActivity(), NC.getString(R.string.check_internet_connection))
        }

    }
}